package pl.coderslab.familytree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.familytree.model.FamilyMemberDetails.Relation;

public interface RelationRepository extends JpaRepository<Relation, Long> {
}
