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

    private String name;

    private String address;

    private String town;

    private String country;

    @ManyToMany(mappedBy = "actualHome")
    private List<FamilyMember> actualFamilyMembers = new ArrayList<>();

    @Lob
    @Column(name = "images", columnDefinition = "BLOB")
    private Byte[] image;

    public Home() {
    }

    public void setName(String address, String town, String country) {
        this.name = address + ", " + town + ", " + country;
    }
}
