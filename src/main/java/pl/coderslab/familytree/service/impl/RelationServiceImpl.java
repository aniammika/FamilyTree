package pl.coderslab.familytree.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.familytree.exceptions.InvalidFamilyRelation;
import pl.coderslab.familytree.model.FamilyMember;
import pl.coderslab.familytree.model.FamilyMemberDetails.Relation;
import pl.coderslab.familytree.repository.FamilyMemberRepository;
import pl.coderslab.familytree.repository.RelationRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RelationServiceImpl implements pl.coderslab.familytree.service.RelationService {

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    //  == metoda, która dodaje pierwszą relację i wszystkie kolejne wynikające z relacji ze wskazaną osobą ==
    @Override
    public List<Relation> joinInRelation(FamilyMember primaryPerson, FamilyMember relatedPerson, String kindOfRelation) throws InvalidFamilyRelation {

        // == dodajemy pierwszą relację wskazaną w formularzu ==
        System.out.println("jestem w metodzie łączenia");
        List<Relation> relationsOfPrimaryPerson = new ArrayList<>();
        Relation firstRelation = new Relation(primaryPerson, relatedPerson, kindOfRelation);
        relationsOfPrimaryPerson.add(firstRelation);
        if (kindOfRelation.equals("parent")) {
            relatedPerson.getRelations().add(new Relation(relatedPerson, primaryPerson, "child"));
        } else if (kindOfRelation.equals("child")){
            relatedPerson.getRelations().add(new Relation(relatedPerson, primaryPerson, "parent"));
        } else if (kindOfRelation.equals("sibling")){
            relatedPerson.getRelations().add(new Relation(relatedPerson, primaryPerson, "sibling"));
        } else if (kindOfRelation.equals("spouse")) {
            relatedPerson.getRelations().add(new Relation(relatedPerson, primaryPerson, "spouse "));

        }
        System.out.println("pierwsza relacja z " + relatedPerson.getNameDetails().getFirstName() + " " + relatedPerson.getNameDetails().getLastName());

        // == iterujemy po liście relacji wskazanej osoby ==
        List<Relation> relationsOfRelatedPerson = relatedPerson.getRelations();
        if (relationsOfRelatedPerson.size() > 0) {
            for (Relation relationOfRelatedPerson : relationsOfRelatedPerson
            ) {
                if (!primaryPerson.equals(relationOfRelatedPerson.getRelatedPerson())) {
                    if (relationOfRelatedPerson.getKindOfRelation().equals("child")) {
                        if (kindOfRelation.equals("child")) { //relacja między primary i related
                            // == relacja -> utworzone wzajemne powiązanie ==
                            relationsOfPrimaryPerson.add(new Relation(primaryPerson, relationOfRelatedPerson.getRelatedPerson(), "grand-children"));
                            relationOfRelatedPerson.getRelatedPerson().getRelations().add(new Relation(relationOfRelatedPerson.getRelatedPerson(), primaryPerson, "grand-parent"));
                        } else if (kindOfRelation.equals("spouse")) { //relacja między primary i related
                            relationsOfPrimaryPerson.add(new Relation(primaryPerson, relationOfRelatedPerson.getRelatedPerson(), "child"));
                            relationOfRelatedPerson.getRelatedPerson().getRelations().add(new Relation(relationOfRelatedPerson.getRelatedPerson(), primaryPerson, "parent"));
                        }
                    } else if (relationOfRelatedPerson.getKindOfRelation().equals("parent")) {
                        if (kindOfRelation.equals("child")) {
                            relationsOfPrimaryPerson.add(new Relation(primaryPerson, relationOfRelatedPerson.getRelatedPerson(), "spouse"));
                            relationOfRelatedPerson.getRelatedPerson().getRelations().add(new Relation(relationOfRelatedPerson.getRelatedPerson(), primaryPerson, "spouse"));
                        } else if (kindOfRelation.equals("sibling")) {
                            relationsOfPrimaryPerson.add(new Relation(primaryPerson, relationOfRelatedPerson.getRelatedPerson(), "parent"));
                            relationOfRelatedPerson.getRelatedPerson().getRelations().add(new Relation(relationOfRelatedPerson.getRelatedPerson(), primaryPerson, "child"));
                        }
                    } else if (relationOfRelatedPerson.getKindOfRelation().equals("sibling")) {
                        if (kindOfRelation.equals("child")) {
                            relationsOfPrimaryPerson.add(new Relation(primaryPerson, relationOfRelatedPerson.getRelatedPerson(), "child"));
                            relationOfRelatedPerson.getRelatedPerson().getRelations().add(new Relation(relationOfRelatedPerson.getRelatedPerson(), primaryPerson, "parent"));
                        } else if (kindOfRelation.equals("sibling")) {
                            relationsOfPrimaryPerson.add(new Relation(primaryPerson, relationOfRelatedPerson.getRelatedPerson(), "sibling"));
                            relationOfRelatedPerson.getRelatedPerson().getRelations().add(new Relation(relationOfRelatedPerson.getRelatedPerson(), primaryPerson, "sibling"));
                        }
                    } else if (relationOfRelatedPerson.getKindOfRelation().equals("spouse")) {
                        if (kindOfRelation.equals("parent")) {
                            relationsOfPrimaryPerson.add(new Relation(primaryPerson, relationOfRelatedPerson.getRelatedPerson(), "parent"));
                            relationOfRelatedPerson.getRelatedPerson().getRelations().add(new Relation(relationOfRelatedPerson.getRelatedPerson(), primaryPerson, "child"));
                        }
                    }
                }
            }
        }
        return relationsOfPrimaryPerson;
    }

    @Override
    public void removeFromRelations(FamilyMember personToDelete) {
        System.out.println("jestem w metodzie usuwania:" + personToDelete.getNameDetails().getFirstName());
        List<Relation> reltionsOfDeletingPerson = personToDelete.getRelations();

        if (reltionsOfDeletingPerson.size() > 0) {
            for (Relation reltionOfDeletingPerson: reltionsOfDeletingPerson
             ){
                System.out.println("sprawdzam relacje osoby do usunięcia: " + reltionOfDeletingPerson.getRelatedPerson().getNameDetails().getFirstName() + reltionOfDeletingPerson.getRelatedPerson().getNameDetails().getLastName());
                List<Relation> relationsOfRelatedPerson = reltionOfDeletingPerson.getRelatedPerson().getRelations();
                for (Relation relationOfRelatedPerson: relationsOfRelatedPerson
                     ) {
                    System.out.println("sprawdzam relacje krewnego: " + relationOfRelatedPerson.getRelatedPerson().getNameDetails().getFirstName() + relationOfRelatedPerson.getRelatedPerson().getNameDetails().getLastName());
                    if (relationOfRelatedPerson.getRelatedPerson().equals(personToDelete)){
                        relationsOfRelatedPerson.remove(relationOfRelatedPerson);
                        System.out.println("relacja została usunięta");

                        break;
                    }
                    reltionOfDeletingPerson.getRelatedPerson().setRelations(relationsOfRelatedPerson);
                    System.out.println("ponownie sprawdzam relacje krewnego: " + relationOfRelatedPerson.getRelatedPerson().getNameDetails().getFirstName() + relationOfRelatedPerson.getRelatedPerson().getNameDetails().getLastName() + " wielkość listy: " + relationsOfRelatedPerson.size());
                }

            }
        }

    }
}

