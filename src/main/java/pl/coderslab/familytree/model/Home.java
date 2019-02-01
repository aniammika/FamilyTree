package pl.coderslab.familytree.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "homes")
@Data
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String town;

    private String country;

    @ManyToMany(mappedBy = "actualHome")
    private List<FamilyMember> actualFamilyMemberEntities = new ArrayList<>();

/*    @ManyToMany(mappedBy = "formerHomes")
    private List<FamilyMember> formerFamilyMemberEntities = new ArrayList<>();*/

    @OneToMany(mappedBy = "home")
    private List<Image> images = new ArrayList<>();

    public Home() {
    }
}
