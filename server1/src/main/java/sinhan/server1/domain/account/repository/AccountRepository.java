package sinhan.server1.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.user.User;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUserAndStatus(User user, Integer status);

}
