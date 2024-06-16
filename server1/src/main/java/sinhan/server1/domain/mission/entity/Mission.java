package sinhan.server1.domain.mission.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import sinhan.server1.domain.mission.dto.MissionFindOneResponse;
import sinhan.server1.domain.user.entity.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "child_sn")})
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "parents_sn", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private long parentsSn;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "child_sn", referencedColumnName = "serial_num", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private User child;
    @Column(name = "content", nullable = false)
    @Size(max = 255)
    private String content;
    @Column(name = "price", nullable = false, columnDefinition = "MEDIUMINT UNSIGNED")
    private int price;
    @Column(name = "create_date", nullable = false, updatable = false)
    private Timestamp createDate;
    @Column(name = "due_date")
    private Timestamp dueDate;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    @Min(1)
    @Max(5)
    private int status;

    public Mission(long parentsSn, User child, String content, int price, int status) {
        this.parentsSn = parentsSn;
        this.child = child;
        this.content = content;
        this.price = price;
        this.createDate = Timestamp.valueOf(LocalDateTime.now());
        this.dueDate = Timestamp.valueOf(LocalDateTime.now().plusHours(72));
        this.status = status;
    }

    public MissionFindOneResponse convertToMissionFindOneResponse() {
        return new MissionFindOneResponse(id, parentsSn, child, content, price, createDate, dueDate, status);
    }
}

