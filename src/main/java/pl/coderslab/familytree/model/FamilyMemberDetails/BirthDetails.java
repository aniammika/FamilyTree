package pl.coderslab.familytree.model.FamilyMemberDetails;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.familytree.model.FamilyMember;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "birth_details")
@Data
public class BirthDetails {

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "podaj datę urodzenia")
    private LocalDate birthDate;

    private String birthPlace;

    @OneToOne
    private FamilyMember familyMember;

    public BirthDetails() {
    }

    public BirthDetails(LocalDate birthDate, String birthPlace, FamilyMember familyMember) {
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.familyMember = familyMember;
    }
}
