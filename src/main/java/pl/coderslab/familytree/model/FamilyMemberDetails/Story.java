/*package pl.coderslab.familytree.model.FamilyMemberDetails;

import lombok.Data;
import pl.coderslab.familytree.model.FamilyMember;

import javax.persistence.*;

@Entity
@Table(name = "stories")
@Data
public class Story {


TO BE IMPLEMENTED IN THE FUTURE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storyText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_member_story_id")
    private FamilyMember familyMemberHavingStory;

    public Story() {
    }

    public Story(String storyText, FamilyMember familyMember) {
        this.storyText = storyText;
        this.familyMemberHavingStory = familyMember;
    }
}*/
