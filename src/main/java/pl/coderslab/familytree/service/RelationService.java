package pl.coderslab.familytree.service;

import org.springframework.stereotype.Service;
import pl.coderslab.familytree.model.FamilyMember;
import pl.coderslab.familytree.model.FamilyMemberDetails.Relation;

import java.util.List;

@Service
public interface RelationService {

    List<Relation> joinInRelation(FamilyMember primaryPerson, FamilyMember relatedPerson, String kindOfRelation);

    void removeFromRelations(FamilyMember personToDelete);
}
