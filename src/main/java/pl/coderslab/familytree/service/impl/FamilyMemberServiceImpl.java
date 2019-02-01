package pl.coderslab.familytree.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.familytree.model.FamilyMember;
import pl.coderslab.familytree.repository.FamilyMemberRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FamilyMemberServiceImpl implements pl.coderslab.familytree.service.FamilyMemberService {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Override
    public void saveFamilyMember(FamilyMember familyMember) {
        familyMemberRepository.save(familyMember);
    }

    public boolean isDuplicate(FamilyMember familyMember) {
        boolean isDuplicate = false;
        // == checking for duplicates ==
        List<FamilyMember> allFamilyMembers = familyMemberRepository.findAll();
        for (FamilyMember fm : allFamilyMembers
        ) {
            String currentFirstName = fm.getNameDetails().getFirstName();
            String currentLastName = fm.getNameDetails().getLastName();
            String currentYear = String.valueOf(fm.getBirthDetails().getBirthDate().getYear());

            if ((familyMember.getNameDetails().getFirstName().equals(currentFirstName) &&
                    (familyMember.getNameDetails().getLastName().equals(currentLastName)) &&
                    String.valueOf(familyMember.getBirthDetails().getBirthDate().getYear()).equals(currentYear))) {
                // == jest duplikatem ==
                isDuplicate = true;
                log.info("nie zapisano - pozycja jest duplikatem");
                break;
            }
        }
        if (isDuplicate) {

            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<FamilyMember> loadAllFamilyMembers() {
        List<FamilyMember> familyMembers = familyMemberRepository.findAll();
        return familyMembers;
    }

    @Override
    public FamilyMember loadFamilyMemberById(Long id) {
        Optional<FamilyMember> familyMember = familyMemberRepository.findById(id);
        if (familyMember.isPresent()) {
            FamilyMember fm = familyMember.get();
            return fm;
        } else {
            return null;
        }
        //TODO: dopisac swoją metodę find by id
    }

    @Override
    public List<FamilyMember> loadFamilyMembersByFirstNameAndLastName(String firstName, String lastName) {
        List<FamilyMember> familyMembers = familyMemberRepository.findAllByNameDetailsFirstNameAndNameDetailsLastName(firstName, lastName);
        return familyMembers;
    }

    @Override
    public List<FamilyMember> loadFamilyMembersByLastName(String lastName) {
        List<FamilyMember> familyMembers = familyMemberRepository.findAllByNameDetailsLastName(lastName);
        return familyMembers;
    }

    @Override
    public List<FamilyMember> loadFamilyMembersByBirthDate(LocalDate birthDate) {
        List<FamilyMember> familyMembers = familyMemberRepository.findAllByBirthDetailsBirthDate(birthDate);
        return familyMembers;
    }

    @Override
    public void deleteFamilyMemberById(Long id) {
        System.out.println("usuwanie rekordu " + id);
        familyMemberRepository.deleteById(id);
    }


    @Override
    public List<FamilyMember> findTop5OrOrderByCreatedTime(LocalDateTime createdTime) {
        List<FamilyMember> familyMembers = familyMemberRepository.findTop5OrOrderByCreatedTime(createdTime);
        return familyMembers;
    }
}
