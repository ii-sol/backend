package sinhan.server1.domain.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinhan.server1.domain.tempuser.TempUser;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private int id;

    //TODO: User랑 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private TempUser user;

    private String accountNum;

    private int balance;

    private Integer status;

    @Builder
    public Account(int id, String accountNum, int balance, Integer status) {
        this.id = id;
        this.accountNum = accountNum;
        this.balance = balance;
        this.status = status;
    }

    public void updateBalacne(int balance) {
        this.balance = balance;
    }
}
