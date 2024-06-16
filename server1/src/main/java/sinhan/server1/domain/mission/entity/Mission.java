package sinhan.server1.domain.mission.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private long childSn;
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

    public Mission(long parentsSn, long childSn, String content, int price, int status) {
        this.parentsSn = parentsSn;
        this.childSn = childSn;
        this.content = content;
        this.price = price;
        this.createDate = Timestamp.valueOf(LocalDateTime.now());
        this.dueDate = Timestamp.valueOf(LocalDateTime.now().plus(72, ChronoUnit.HOURS));
        this.status = status;
    }
}

