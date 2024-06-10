package sinhan.server1.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "parents_id")
    private int parentsId;
    @Column(name = "child_id")
    private int childId;

    public Family(int parentsId, int childId) {
        this.parentsId = parentsId;
        this.childId = childId;
    }
}
