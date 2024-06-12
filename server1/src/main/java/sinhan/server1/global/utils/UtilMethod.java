package sinhan.server1.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.account.entity.AccountHistory;
import sinhan.server1.domain.account.repository.AccountHistoryRepository;
import sinhan.server1.domain.account.repository.AccountRepository;
import sinhan.server1.domain.tempuser.TempUser;
import sinhan.server1.domain.tempuser.TempUserRepository;
import sinhan.server1.global.exception.CustomException;
import sinhan.server1.global.exception.ErrorCode;

import java.time.LocalDateTime;

@Transactional
public class UtilMethod {

    private AccountRepository accountRepository;
    private TempUserRepository tempUserRepository;
    private AccountHistoryRepository accountHistoryRepository;

    //userId 기준으로 이체
    public void transmitMoneyByUserId(int senderId, int recieverId, int amount, int senderAccountStatus, int recieverAccountStatus){

        TempUser sender = getUser(senderId);
        TempUser reciever = getUser(recieverId);
        Account senderAccount = accountRepository.findByUserAndStatus(sender,senderAccountStatus);
        Account recieverAccount = accountRepository.findByUserAndStatus(reciever,recieverAccountStatus);

        senderAccount.setBalacne(senderAccount.getBalance()-amount);
        recieverAccount.setBalacne(recieverAccount.getBalance()+amount);
        accountRepository.save(senderAccount);
        accountRepository.save(recieverAccount);

        AccountHistory newAccountHistory = AccountHistory.builder()
                .senderAccount(senderAccount)
                .recieverAccount(recieverAccount)
                .amount(amount)
                .messageCode(1)
                .createDate(LocalDateTime.now())
                .build();

        accountHistoryRepository.save(newAccountHistory);
    }

    // 계좌번호 기준으로 이체
    public void transmitMoneyByAccountNum(String senderAccountNum, String recieverAccountNum, int amount){
        Account senderAccount = accountRepository.findByAccountNum(senderAccountNum);
        Account recieverAccount = accountRepository.findByAccountNum(recieverAccountNum);

        senderAccount.setBalacne(senderAccount.getBalance()-amount);
        recieverAccount.setBalacne(recieverAccount.getBalance()+amount);
        accountRepository.save(senderAccount);
        accountRepository.save(recieverAccount);

        AccountHistory newAccountHistory = AccountHistory.builder()
                .senderAccount(senderAccount)
                .recieverAccount(recieverAccount)
                .amount(amount)
                .messageCode(1)
                .createDate(LocalDateTime.now())
                .build();

        accountHistoryRepository.save(newAccountHistory);

    }

    public TempUser getUser(int userId){
        return tempUserRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

}
