package sinhan.server1.domain.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountTransmitOneRequest {
    private int sendAccountId;
    private int receiveAccountId;
    private int amount;
    private String message;
}
