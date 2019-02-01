package pl.coderslab.familytree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.familytree.model.FamilyMember;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    List<FamilyMember> findAllByNameDetailsFirstNameAndNameDetailsLastName(String firstName, String lastName);

    List<FamilyMember> findAllByNameDetailsLastName(String lastName);

    List<FamilyMember> findAllByBirthDetailsBirthDate(LocalDate birthDate);

    List<FamilyMember> findTop5OrOrderByCreatedTime (LocalDateTime createdTime);

}
