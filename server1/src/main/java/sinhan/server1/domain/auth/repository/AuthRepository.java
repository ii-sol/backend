package sinhan.server1.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.user.entity.User;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Integer> {

    Optional<User> findByPhoneNumAndAccountInfo(String phoneNum, String accountInfo);
}
