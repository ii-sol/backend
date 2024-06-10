package sinhan.server1.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"parents_id", "child_id"})})
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parents_id", referencedColumnName = "id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User parents;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "child_id", referencedColumnName = "id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User child;

    public Family(User parents, User child) {
        this.parents = parents;
        this.child = child;
    }
}
