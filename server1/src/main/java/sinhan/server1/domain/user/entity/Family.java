package sinhan.server1.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JoinColumn(name = "parents_id", referencedColumnName = "id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User parents;
    @JoinColumn(name = "child_id", referencedColumnName = "id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User child;

    public Family(User parents, User child) {
        this.parents = parents;
        this.child = child;
    }
}
