package sinhan.server1.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sinhan.server1.domain.auth.dto.FamilyInfoResponse;
import sinhan.server1.domain.user.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Integer> {

    Optional<User> findByPhoneNumAndAccountInfo(String phoneNum, String accountInfo);

    @Query(value = "#{@queryConfig.getQuery('SELECT_QUERY_ALL_CHILD_INFO')}", nativeQuery = true)
    List<FamilyInfoResponse> findAllChildInfo(@Param("id") int id);

//    @Query(value = "${SELECT_QUERY_ALL_CHILD_INFO}", nativeQuery = true)
//    List<Map<Integer, String>> findAllChildInfo(@Param("id") int id);

    @Query(value = "${SELECT_QUERY_ALL_PARENTS_INFO}", nativeQuery = true)
    List<FamilyInfoResponse> findAllParentsInfo(@Param("id") int id);
}
