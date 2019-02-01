package pl.coderslab.familytree.service;

import org.springframework.stereotype.Service;
import pl.coderslab.familytree.model.FamilyMember;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface FamilyMemberService {

    void saveFamilyMember(FamilyMember familyMember);

    boolean isDuplicate(FamilyMember familyMember);

    List<FamilyMember> loadAllFamilyMembers();

    FamilyMember loadFamilyMemberById(Long id);

    List<FamilyMember> loadFamilyMembersByLastName(String lastName);

    List<FamilyMember> loadFamilyMembersByFirstNameAndLastName(String firstName, String lastName);

    List<FamilyMember> loadFamilyMembersByBirthDate(LocalDate birthDate);

    void deleteFamilyMemberById(Long id);

    List<FamilyMember> findTop5OrOrderByCreatedTime (LocalDateTime createdTime);
}
