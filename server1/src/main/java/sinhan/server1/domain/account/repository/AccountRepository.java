package sinhan.server1.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.tempuser.TempUser;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUserAndStatus(TempUser tempUser, Integer status);

}