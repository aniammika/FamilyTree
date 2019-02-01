package pl.coderslab.familytree.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.familytree.exceptions.FamilyMemberNotFoundException;
import pl.coderslab.familytree.model.FamilyMember;
import pl.coderslab.familytree.model.FamilyMemberDetails.NameDetails;
import pl.coderslab.familytree.repository.NameDetailsRepository;
import pl.coderslab.familytree.service.FamilyMemberService;
import pl.coderslab.familytree.service.HomeService;
import pl.coderslab.familytree.service.RelationService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FamilyMemberRestController {

    @Autowired
    private FamilyMemberService familyMemberService;

    //będzie zamieniony na nameDetailsService
    @Autowired
    private NameDetailsRepository nameDetailsRepository;

    //będzie zamieniony na homeService
    @Autowired
    private HomeService homeService;

    @Autowired
    private RelationService relationService;

    private List<String> relationsList;
    private List<FamilyMember> allFamilyMembers = new ArrayList<>();

    @PostConstruct
    public void loadAllMembers(){
        FamilyMember member1 = new FamilyMember();
        NameDetails nameDetails = new NameDetails("Anna", "Nowak", "Mika", member1);
        member1.setNameDetails(nameDetails);
        allFamilyMembers.add(member1);
    }

    @GetMapping(path = "/familyMembers")
    public List<FamilyMember> showAllFamilyMembers(){
        return allFamilyMembers;
    }

    @GetMapping(path = "/familyMembers/{familyMemberId}")
    public FamilyMember getFamilyMember(@PathVariable int familyMemberId){

        if ( (familyMemberId >= allFamilyMembers.size()) || familyMemberId < 0) {
            throw new FamilyMemberNotFoundException("Family member is not found - " + familyMemberId);
        }
        return allFamilyMembers.get(familyMemberId);
    }
}
