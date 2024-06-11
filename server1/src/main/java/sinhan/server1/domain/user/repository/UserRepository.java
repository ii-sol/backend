package sinhan.server1.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sinhan.server1.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByPhoneNumAndAccountInfo(String phoneNum, String accountInfo);
    Optional<User> findByPhoneNum(String phoneNum);

    @Query("SELECT u.phoneNum FROM User u")
    List<String> findAllPhones();
}
