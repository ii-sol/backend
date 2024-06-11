package sinhan.server1.domain.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.account.dto.AccountFindOneResponse;
import sinhan.server1.domain.account.dto.AccountHistoryFindAllResponse;
import sinhan.server1.domain.account.dto.AccountTransmitOneRequest;
import sinhan.server1.domain.account.dto.AccountTransmitOneResponse;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.account.entity.AccountHistory;
import sinhan.server1.domain.account.repository.AccountHistoryRepository;
import sinhan.server1.domain.account.repository.AccountRepository;
import sinhan.server1.domain.tempuser.TempUser;
import sinhan.server1.domain.tempuser.TempUserRepository;
import sinhan.server1.global.exception.CustomException;
import sinhan.server1.global.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final TempUserRepository tempUserRepository;

    //개별 계좌 조회
    public AccountFindOneResponse findAccount(int userId, Integer ao) {

        TempUser tempUser = getUser(userId);
        Account findAccount = accountRepository.findByUserAndStatus(tempUser, ao);
        return AccountFindOneResponse.from(findAccount);
    }

    //계좌 내역 조회
    public List<AccountHistoryFindAllResponse> findAccountHistory(int userId, Integer year, Integer month, Integer ao){

        TempUser tempUser = getUser(userId);
        Account findAccount = accountRepository.findByUserAndStatus(tempUser, ao);

        LocalDate start = LocalDate.of(year,month,1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        List<AccountHistoryFindAllResponse> findAccountHistories = accountHistoryRepository.findByUserAndCreateDateBetween(findAccount, startDateTime, endDateTime)
                .stream()
                .map(history -> {
                    Account senderAccount = getAccount(history.getSenderAccount().getId());
                    Account recieverAccount = getAccount(history.getRecieverAccount().getId());
                    TempUser sender = getUser(senderAccount.getUser().getId());
                    TempUser reciever = getUser(recieverAccount.getUser().getId());

                    return AccountHistoryFindAllResponse.of(history, sender, reciever);
                }
                ).toList();

        return findAccountHistories;
    }

    //이체하기
    public AccountTransmitOneResponse transmitMoney(AccountTransmitOneRequest transmitRequest) {

        Account sendAccount = accountRepository.findByAccountNum(transmitRequest.getSendAccountNum());
        Account recieverAccount = accountRepository.findByAccountNum(transmitRequest.getReceiveAccountNum());
        TempUser reciever = getUser(recieverAccount.getUser().getId());

        //sender와 reciever 계좌 잔액 update
        updateBalance(sendAccount, sendAccount.getBalance()-transmitRequest.getAmount());
        updateBalance(recieverAccount, recieverAccount.getBalance()+transmitRequest.getAmount());

        //계좌 내역에 저장하기
        AccountHistory newAccountHistory = AccountHistory.builder()
                .senderAccount(sendAccount)
                .recieverAccount(recieverAccount)
                .amount(transmitRequest.getAmount())
                .messageCode(0)
                .createDate(LocalDateTime.now())
                .build();

        accountHistoryRepository.save(newAccountHistory);

        return AccountTransmitOneResponse.of(sendAccount, transmitRequest, reciever);

    }


    //사용자 조회
    private TempUser getUser(int userId){
        return tempUserRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    //계좌 조회
    private Account getAccount(int accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    //계좌 잔액 update 후 레포지토리에 update
    private void updateBalance(Account account, int balance){
        account.setBalacne(balance);
        accountRepository.save(account);
    }
}
