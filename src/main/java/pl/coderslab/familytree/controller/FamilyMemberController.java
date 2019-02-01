package pl.coderslab.familytree.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.familytree.exceptions.InvalidFamilyRelation;
import pl.coderslab.familytree.model.FamilyMember;
import pl.coderslab.familytree.model.FamilyMemberDetails.Relation;
import pl.coderslab.familytree.model.Home;
import pl.coderslab.familytree.service.FamilyMemberService;
import pl.coderslab.familytree.service.HomeService;
import pl.coderslab.familytree.service.NameDetailsService;
import pl.coderslab.familytree.service.RelationService;
import pl.coderslab.familytree.util.ViewNames;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(path = "/familyMember")
@Transactional
public class FamilyMemberController {

    @Autowired
    private FamilyMemberService familyMemberService;

    @Autowired
    private NameDetailsService nameDetailsService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private RelationService relationService;

    @GetMapping(path = "/add")
    public String addFamilyMember(Model model) {
        FamilyMember familyMember = new FamilyMember();
        model.addAttribute("familyMember", familyMember);
        return ViewNames.ADD_MEMBER;
    }

    @PostMapping(path = "/add")
    public String receiveForm(@Valid @ModelAttribute FamilyMember familyMember, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return ViewNames.OPERATION_FAILED;
        } else {

            if (familyMemberService.isDuplicate(familyMember)) {
                model.addAttribute("duplicate", "duplicate");
                return ViewNames.ADD_MEMBER;
            } else {
                // == zapisanie pierwszej relacji z formularza ==
                if (familyMember.getPrimaryRelatedPerson() != null) {
                    try {
                        List<Relation> relations = relationService.joinInRelation(familyMember, familyMember.getPrimaryRelatedPerson(), familyMember.getPrimaryRelationType());
                        familyMember.setRelations(relations);
                    } catch (InvalidFamilyRelation invalidFamilyRelation) {
                        model.addAttribute("errorMessage", invalidFamilyRelation.getMessage());
                        return ViewNames.ADD_MEMBER;
                    }
                }

                // == zapisanie osoby do bazy danych ==
                familyMemberService.saveFamilyMember(familyMember);
                System.out.println("wielkosc listy relacji: " + familyMember.getRelations().size());
            }
        }
        return ViewNames.SUCCESS;
    }

    @GetMapping(path = "showAll")
    public String showAllFamilyMembers(Model model) {
        List<FamilyMember> allMembers = familyMemberService.loadAllFamilyMembers();
        model.addAttribute("familyMembers", allMembers);
        return ViewNames.SHOW_ALL_MEMBERS;
    }



    @GetMapping(path = "showDetails/{familyMemberId}")
    public String showDetails(@PathVariable int familyMemberId, Model model){
        Long id = Long.valueOf(familyMemberId);
        System.out.println("jeste tutaj");
        FamilyMember familyMemberDetails = familyMemberService.loadFamilyMemberById(id);
        System.out.println("jestem tu");
        model.addAttribute("familyMemberDetails", familyMemberDetails);
        System.out.println("jestem po ustaleniu model attribute");
        // == lista istniejących pokrewieństw
        List<Relation> relations = familyMemberDetails.getRelations();
        System.out.println("wielkosc listy relacji: " + relations.size());
        model.addAttribute("relations", relations);
        boolean isPersonDead = familyMemberDetails.isDead();
        model.addAttribute("isPersonDead", isPersonDead);

        // == atrybuty dla nagłówka ==
        String firstName = familyMemberDetails.getNameDetails().getFirstName();
        String lastName = familyMemberDetails.getNameDetails().getLastName();
        model.addAttribute("memberFirstName", firstName);
        model.addAttribute("memberLastName", lastName);
        return ViewNames.SHOW_MEMBER_DETAILS;
    }

    @GetMapping(path = "edit/{familyMemberId}")
    public String editMember(@PathVariable int familyMemberId, Model model) {
        Long id = Long.valueOf(familyMemberId);
        FamilyMember familyMemberDetails = familyMemberService.loadFamilyMemberById(id);
        model.addAttribute("familyMemberDetails", familyMemberDetails);
        // == lista istniejących pokrewieństw
        List<Relation> relations = familyMemberDetails.getRelations();
        model.addAttribute("relations", relations);

        // == atrybuty dla nagłówka ==
        String firstName = familyMemberDetails.getNameDetails().getFirstName();
        String lastName = familyMemberDetails.getNameDetails().getLastName();
        model.addAttribute("memberFirstName", firstName);
        model.addAttribute("memberLastName", lastName);
        return ViewNames.EDIT_MEMBER;
    }

    @PostMapping(path = "edit/{familyMemberId}")
    public String receiveEditForm(@Valid @ModelAttribute FamilyMember familyMemberEdit, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return ViewNames.OPERATION_FAILED;
        } else {
            model.addAttribute("editStatus", "editSuccess");
            familyMemberService.saveFamilyMember(familyMemberEdit);
        }
        return ViewNames.SUCCESS;
    }

    @GetMapping(path = "delete/{familyMemberId}")
    public String removeMember(@PathVariable int familyMemberId, Model model) {
        Long id = Long.valueOf(familyMemberId);
        FamilyMember familyMemberToDelete = familyMemberService.loadFamilyMemberById(id);
        relationService.removeFromRelations(familyMemberToDelete);
        familyMemberService.deleteFamilyMemberById(id);
        model.addAttribute("deleteStatus", "deleteSuccess");
        return "redirect:/familyMember/showAll";
    }

    @ModelAttribute("relationsList")
    public List<String> relationsList() {
        List<String> relationsList = new ArrayList<>();
        relationsList.add("parent");
        relationsList.add("child");
        relationsList.add("sibling");
        relationsList.add("spouse");
        return relationsList;
    }

    @ModelAttribute(name = "familyMembersList")
    public List<FamilyMember> familyMembersList() {
        List<FamilyMember> familyMembersList = familyMemberService.loadAllFamilyMembers();
        for (FamilyMember fm : familyMembersList
        ) {
            fm.getNameDetails().setName(fm.getNameDetails().getFirstName(), fm.getNameDetails().getLastName());
        }
        return familyMembersList;
    }

    @ModelAttribute(name = "familyMembersTop5List")
    public List<FamilyMember> familyMembersTop5List() {
        List<FamilyMember> familyMembersTop5List = familyMemberService.findTop5OrOrderByCreatedTime(LocalDateTime.now());
        return familyMembersTop5List;
    }

    @ModelAttribute(name = "homesList")
    public List<Home> homesList() {
        List<Home> homesList = homeService.loadAllHomes();
        return homesList;
    }

    @ModelAttribute(name = "townList")
    public List<String> townList() {
        List<Home> homes = homeService.loadAllHomes();
        List<String> townList = new ArrayList<>();
        for (Home h : homes
        ) {
            townList.add(h.getTown());
        }
        return townList;
    }
}
