package sinhan.server1.domain.allowance.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinhan.server1.domain.tempuser.TempUser;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Allowance{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parents_id")
    TempUser parents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    TempUser child;

    String content;

    int price;

    LocalDateTime createDate;

    int status;

    @Builder
    public Allowance(TempUser parents, TempUser child, String content, int price, LocalDateTime createDate, int status) {
        this.parents = parents;
        this.child = child;
        this.content = content;
        this.price = price;
        this.createDate = createDate;
        this.status = status;
    }
}
