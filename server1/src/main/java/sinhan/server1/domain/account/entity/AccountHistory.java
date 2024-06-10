package sinhan.server1.domain.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountHistory {
    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id")
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reciever_account_id")
    private Account recieverAccount;

    private int amount;

    private int messageCode;
    private LocalDateTime createDate;

    @Builder

    public AccountHistory( Account senderAccount, Account recieverAccount, int amount, int messageCode, LocalDateTime createDate) {
        this.senderAccount = senderAccount;
        this.recieverAccount = recieverAccount;
        this.amount = amount;
        this.messageCode = messageCode;
        this.createDate = createDate;
    }
}
