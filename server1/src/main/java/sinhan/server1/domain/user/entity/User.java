package sinhan.server1.domain.user.entity;

import jakarta.persistence.*;

import lombok.*;
import sinhan.server1.domain.user.dto.UserFindOneResponse;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "phone_num")
    private String phoneNum;
    private String name;
    private String birthdate;
    @Column(name = "account_info")
    private String accountInfo;
    private int role;
    @Column(name = "profile_id")
    private int profileId;

    public UserFindOneResponse convertToUserFindOneResponse() {
        return new UserFindOneResponse(id, phoneNum, name, birthdate, accountInfo, role, profileId);
    }
}