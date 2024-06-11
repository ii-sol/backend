package sinhan.server1.domain.allowance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.allowance.entity.Allowance;

public interface AllowanceRepository extends JpaRepository<Allowance, Integer> {

}
