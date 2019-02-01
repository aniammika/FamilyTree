package pl.coderslab.familytree.model.FamilyMemberDetails;

import lombok.Data;
import pl.coderslab.familytree.model.FamilyMember;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sexes")
@Data
public class Sex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String sexType;

    @OneToMany
        private List<FamilyMember> familyMemberList = new ArrayList<>();

    public Sex() {
    }

    public Sex(String sex, List<FamilyMember> familyMemberList) {
        this.sexType = sex;
        this.familyMemberList = familyMemberList;
    }
}
