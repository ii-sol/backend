package sinhan.server1.domain.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.account.dto.AccountFindOneResponse;
import sinhan.server1.domain.account.dto.AccountHistoryFindAllResponse;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.account.entity.AccountHistory;
import sinhan.server1.domain.account.repository.AccountHistoryRepository;
import sinhan.server1.domain.account.repository.AccountRepository;
import sinhan.server1.domain.user.User;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
//    private final UserJPARepository userJPARepository;

    public AccountFindOneResponse findAccount(int userId, Integer ao) {

        User user = getUser(userId);
        Account findAccount = accountRepository.findByUserAndStatus(user, ao);
        return AccountFindOneResponse.from(findAccount);
    }

    public AccountHistoryFindAllResponse findAccountHistory(int userId, Integer year, Integer month, Integer status){
        User user = getUser(userId);
//        AccountHistory accountHistory = accountHistoryRepository
    }

    
}
