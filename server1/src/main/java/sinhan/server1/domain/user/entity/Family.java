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
    @Column(name = "parents_id", nullable = false)
    private int parentsId;
    @Column(name = "child_id", nullable = false)
    private int childId;

    public Family(int parentsId, int childId) {
        this.parentsId = parentsId;
        this.childId = childId;
    }
}
