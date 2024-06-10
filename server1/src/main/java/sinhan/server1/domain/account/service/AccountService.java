package sinhan.server1.domain.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.account.dto.AccountFindOneResponse;
import sinhan.server1.domain.account.dto.AccountHistoryFindAllResponse;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.account.repository.AccountHistoryRepository;
import sinhan.server1.domain.account.repository.AccountRepository;
import sinhan.server1.domain.tempuser.TempUser;
import sinhan.server1.domain.tempuser.TempUserRepository;

import java.time.LocalDate;
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

    public AccountFindOneResponse findAccount(int userId, Integer ao) {

        TempUser tempUser = getUser(userId);
        Account findAccount = accountRepository.findByUserAndStatus(tempUser, ao);
        return AccountFindOneResponse.from(findAccount);
    }

    public List<AccountHistoryFindAllResponse> findAccountHistory(int userId, Integer year, Integer month, Integer status){

        TempUser tempUser = getUser(userId);
        LocalDate start = LocalDate.of(year,month,1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<AccountHistoryFindAllResponse> findAccountHistories = accountHistoryRepository.findByUserAndStatusAndCreateDateBetween(tempUser, status, start, end)
                .stream()
                .map(history -> {
                    Account senderAccount = getAccount(history.getSenderAccount().getId());
                    Account recieverAccount = getAccount(history.getRecieverAccount().getId());
                    String senderName = getUser(senderAccount.getUser().getId()).getName();
                    String recieverName = getUser(recieverAccount.getUser().getId()).getName();

                    return AccountHistoryFindAllResponse.of(history, senderName, recieverName);
                }
                ).toList();

        return findAccountHistories;
    }


    private TempUser getUser(int userId){
        return tempUserRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    private Account getAccount(int accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }
}
