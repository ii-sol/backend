package sinhan.server1.domain.user.entity;

import jakarta.persistence.*;

import lombok.*;
import sinhan.server1.domain.user.dto.UserFindOneResponse;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "phone_num", nullable = false)
    private String phoneNum;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String birthdate;
    @Column(name = "account_info", nullable = false)
    private String accountInfo;
    @Column(nullable = false)
    private int role;
    @Column(name = "profile_id", nullable = false)
    private int profileId;

    public UserFindOneResponse convertToUserFindOneResponse() {
        return new UserFindOneResponse(id, phoneNum, name, birthdate, accountInfo, role, profileId);
    }
}