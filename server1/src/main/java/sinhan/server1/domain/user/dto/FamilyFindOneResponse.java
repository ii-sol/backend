package sinhan.server1.domain.user.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import sinhan.server1.domain.user.entity.User;

@Entity
@AllArgsConstructor
public class FamilyFindOneResponse {

    private int id;
    private User parents;
    private User child;
}
