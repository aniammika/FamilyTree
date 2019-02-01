package pl.coderslab.familytree.model.FamilyMemberDetails;

import lombok.Data;
import pl.coderslab.familytree.model.FamilyMember;

import javax.persistence.*;

@Entity
@Table(name = "relations")
@Data
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FamilyMember primaryPerson;

    @ManyToOne
    private FamilyMember relatedPerson;

    private String kindOfRelation;

    public Relation() {
    }

    public Relation(FamilyMember primaryPerson, FamilyMember relatedPerson, String kindOfRelation) {
        this.primaryPerson = primaryPerson;
        this.relatedPerson = relatedPerson;
        this.kindOfRelation = kindOfRelation;
    }
}
