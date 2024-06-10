package sinhan.server1.domain.account.repository;

import sinhan.server1.domain.account.entity.AccountHistory;
import sinhan.server1.domain.tempuser.TempUser;

import java.time.LocalDate;
import java.util.List;

public interface AccountHistoryRepositoryCustom {
    List<AccountHistory> findByUserAndStatusAndCreateDateBetween(TempUser tempUser, Integer status , LocalDate start, LocalDate end);
}
