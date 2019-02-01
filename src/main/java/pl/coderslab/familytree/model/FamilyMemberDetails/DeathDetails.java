package pl.coderslab.familytree.model.FamilyMemberDetails;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.familytree.model.FamilyMember;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "death_details")
@Data
public class DeathDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate deathDate;

    private String deathPlace;

    private String deathReason;

    @OneToOne
    private FamilyMember familyMember;

    public DeathDetails() {
    }

    public DeathDetails(LocalDate deathDate, String deathPlace, String deathReason, FamilyMember familyMember) {
        this.deathDate = deathDate;
        this.deathPlace = deathPlace;
        this.deathReason = deathReason;
        this.familyMember = familyMember;
    }
}
