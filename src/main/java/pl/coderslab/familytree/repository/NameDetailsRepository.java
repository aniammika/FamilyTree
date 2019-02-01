package pl.coderslab.familytree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.familytree.model.FamilyMemberDetails.NameDetails;

public interface NameDetailsRepository extends JpaRepository<NameDetails, Long> {
}
