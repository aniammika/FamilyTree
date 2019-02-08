package pl.coderslab.familytree.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.familytree.exceptions.InvalidFamilyRelation;
import pl.coderslab.familytree.model.FamilyMember;
import pl.coderslab.familytree.model.FamilyMemberDetails.Relation;
import pl.coderslab.familytree.model.Home;
import pl.coderslab.familytree.service.FamilyMemberService;
import pl.coderslab.familytree.service.HomeService;
import pl.coderslab.familytree.service.RelationService;
import pl.coderslab.familytree.util.ViewNames;

import javax.transaction.Transactional;
import javax.validation.Valid;
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
    private HomeService homeService;

    @Autowired
    private RelationService relationService;


    @GetMapping(path = "/add")
    public String addFamilyMember(Model model) {
        FamilyMember familyMember = new FamilyMember();
        model.addAttribute("familyMember", familyMember);
        List<Home> homes = homeService.loadAllHomes();
        if (homes.size() > 0) {
            for (Home home : homes
            ) {
                home.setName(home.getAddress(), home.getTown(), home.getCountry());
            }
        }
        model.addAttribute("homes", homes);
        return ViewNames.ADD_MEMBER;
    }

    @PostMapping(path = "/add")
    public String receiveForm(@Valid @ModelAttribute FamilyMember familyMember, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingStatus", "error");
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
            }
            return ViewNames.ADD_MEMBER;
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
            }
        }
        return "redirect:" + familyMember.getId() + "/image";
    }

    @GetMapping(path = "showAll")
    public String showAllFamilyMembers(Model model, @RequestParam(value = "saveStatus", required = false) String saveStatus,
                                       @RequestParam(value = "deleteStatus", required = false) String deleteStatus) {
        List<FamilyMember> allMembers = familyMemberService.loadAllFamilyMembers();
        model.addAttribute("familyMembers", allMembers);
        model.addAttribute("saveStatus", saveStatus);
        model.addAttribute("deleteStatus", deleteStatus);
        return ViewNames.SHOW_ALL_MEMBERS;
    }


    @GetMapping(path = "showDetails/{familyMemberId}")
    public String showDetails(@PathVariable int familyMemberId, Model model){
        Long id = Long.valueOf(familyMemberId);
        FamilyMember familyMemberDetails = familyMemberService.loadFamilyMemberById(id);
        familyMemberDetails.getNameDetails().setName(familyMemberDetails.getNameDetails().getFirstName(), familyMemberDetails.getNameDetails().getLastName());
        familyMemberDetails.getActualHome().setName(familyMemberDetails.getActualHome().getAddress(),
                familyMemberDetails.getActualHome().getTown(), familyMemberDetails.getActualHome().getCountry());
        model.addAttribute("familyMemberDetails", familyMemberDetails);
        return ViewNames.SHOW_MEMBER_DETAILS;
    }

    @GetMapping(path = "edit/{familyMemberId}")
    public String editMember(@PathVariable int familyMemberId, Model model) {
        Long id = Long.valueOf(familyMemberId);
        FamilyMember familyMemberDetails = familyMemberService.loadFamilyMemberById(id);
        familyMemberDetails.getNameDetails().setName(familyMemberDetails.getNameDetails().getFirstName(), familyMemberDetails.getNameDetails().getLastName());
        familyMemberDetails.getActualHome().setName(familyMemberDetails.getActualHome().getAddress(),
                familyMemberDetails.getActualHome().getTown(), familyMemberDetails.getActualHome().getCountry());
        model.addAttribute("familyMemberDetails", familyMemberDetails);
        List<FamilyMember> allFamilyMembers = familyMemberService.loadAllFamilyMembers();
        model.addAttribute("allMembers", allFamilyMembers);
        List<Home> homes = homeService.loadAllHomes();
        if (homes.size() > 0) {
            for (Home home : homes
            ) {
                home.setName(home.getAddress(), home.getTown(), home.getCountry());
            }
        }
        model.addAttribute("homes", homes);
        return ViewNames.EDIT_MEMBER;
    }

    @PostMapping(path = "edit/{familyMemberId}")
    public String receiveEditForm(@Valid @ModelAttribute FamilyMember familyMemberEdit, BindingResult bindingResult, Model model) {
        Long id = familyMemberEdit.getId();
        if (bindingResult.hasErrors()) {
            return ViewNames.OPERATION_FAILED;
        } else {
            familyMemberService.saveFamilyMember(familyMemberEdit);
        }
        return "redirect:/familyMember/showDetails/" + id + "?saveStatus=success";
    }

    @GetMapping(path = "addPrimaryRelation/{id}")
    public String addPrimaryRelation(@PathVariable int id, Model model){
        Long fmId = Long.valueOf(id);
        FamilyMember familyMember = familyMemberService.loadFamilyMemberById(fmId);
        model.addAttribute("familyMember", familyMember);
        return "addPrimaryRelation";
    }

    @PostMapping(path = "addPrimaryRelation/{id}")
    public String receiveAddPrimaryRelationForm(@ModelAttribute FamilyMember familyMember){
        if (familyMember.getPrimaryRelatedPerson() != null) {
            try {
                List<Relation> relations = relationService.joinInRelation(familyMember, familyMember.getPrimaryRelatedPerson(), familyMember.getPrimaryRelationType());
                familyMember.setRelations(relations);
            } catch (InvalidFamilyRelation invalidFamilyRelation) {
                return ViewNames.SHOW_ALL_MEMBERS;
            }
        }
        return "redirect:/familyMember/showAll?saveStatus=success";
    }

    @GetMapping(path = "addAddress/{id}")
    public String addAddressForm(@PathVariable int id, Model model){
        Long fmId = Long.valueOf(id);
        FamilyMember familyMember = familyMemberService.loadFamilyMemberById(fmId);
        model.addAttribute("familyMember", familyMember);
        return "addAddress";
    }
    @PostMapping(path = "addAddress/{id}")
    public String addAddressReceiveForm(@ModelAttribute FamilyMember familyMember){
        if (familyMember.getActualHome() != null) {
            familyMemberService.saveFamilyMember(familyMember);
        }
        return "redirect:/familyMember/showAll?saveStatus=success";
    }

    @GetMapping(path = "delete/{familyMemberId}")
    public String removeMember(@PathVariable int familyMemberId, Model model) {
        Long id = Long.valueOf(familyMemberId);
        FamilyMember familyMemberToDelete = familyMemberService.loadFamilyMemberById(id);
        familyMemberToDelete.setPrimaryRelatedPerson(null);
        familyMemberToDelete.setPrimaryRelationType(null);
        relationService.removeFromRelations(familyMemberToDelete);
        familyMemberService.deleteFamilyMemberById(id);
        return "redirect:/familyMember/showAll?deleteStatus=deleteSuccess";
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

    @ModelAttribute(name = "homesList")
    public List<Home> homesList() {
        List<Home> homes = homeService.loadAllHomes();
        if (homes.size() > 0) {
            for (Home home : homes
            ) {
                home.setName(home.getAddress(), home.getTown(), home.getCountry());
            }
        }
        return homes;
    }

}
