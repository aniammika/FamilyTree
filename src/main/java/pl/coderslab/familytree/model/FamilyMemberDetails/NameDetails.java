package pl.coderslab.familytree.model.FamilyMemberDetails;


import lombok.Data;
import org.springframework.lang.Nullable;
import pl.coderslab.familytree.model.FamilyMember;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "names")
@Data
public class NameDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Trzeba podać imię")
    private String firstName;

    @NotNull(message = "Trzeba podać nazwisko")
    private String lastName;

    private String name;

    @Nullable
    private String familyName;

    @OneToOne
    private FamilyMember familyMember;

    public NameDetails() {
    }

    public NameDetails(String firstName, String lastName, @Nullable String familyName, FamilyMember familyMember) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.familyName = familyName;
        this.familyMember = familyMember;

    }

    public void setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
    }

}
