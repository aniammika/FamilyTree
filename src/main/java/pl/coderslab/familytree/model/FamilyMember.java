package pl.coderslab.familytree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import pl.coderslab.familytree.model.FamilyMemberDetails.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "family_members")
@Data
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade= CascadeType.ALL)
    private NameDetails nameDetails;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    @OneToOne(cascade = {CascadeType.ALL})
    private BirthDetails birthDetails;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "familyMember_actualHome",
            joinColumns = { @JoinColumn(name = "family_member_id") },
            inverseJoinColumns = { @JoinColumn(name = "actual_home_id") }
    )
    private Home actualHome;

    @Size(max = 2000)
    private String biographicalNote;

    @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    private FamilyMember primaryRelatedPerson;

    private String primaryRelationType;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity= Relation.class, mappedBy="primaryPerson", fetch= FetchType.LAZY)
    private List<Relation> relations = new ArrayList<>();

    private boolean isDead;

    @OneToOne(cascade= CascadeType.ALL)
    private DeathDetails deathDetails;

    @Lob
    @Column(name = "images", columnDefinition="LONGBLOB")
    private Byte[]image;

    @CreationTimestamp
    private LocalDateTime createdTime;

    // == to be implemented
    // private Animal animal ==

    public FamilyMember() {

    }

    public void setNameDetails(NameDetails nameDetails) {
        this.nameDetails = nameDetails;
        nameDetails.setFamilyMember(this);
    }

    public void setBirthDetails(BirthDetails birthDetails) {
        this.birthDetails = birthDetails;
        birthDetails.setFamilyMember(this);
    }

}
