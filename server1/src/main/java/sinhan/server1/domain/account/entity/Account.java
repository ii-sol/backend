package sinhan.server1.domain.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private int id;

    //TODO: User랑 연결

    private String accountNum;

    private int balance;

    private Integer status;

}
