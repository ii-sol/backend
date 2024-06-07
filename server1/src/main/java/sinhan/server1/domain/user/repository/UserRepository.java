package sinhan.server1.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByPhoneNumAndAccountInfo(String phoneNum, String accountInfo);
}
