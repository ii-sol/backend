package sinhan.server1.domain.tempuser;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempUser {
    @Id
    private int id;
    private String name;

    public TempUser(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
