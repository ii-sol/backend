package sinhan.server1.domain.user.entity;

import jakarta.persistence.*;

import java.sql.Date;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import sinhan.server1.domain.user.dto.UserFindOneResponse;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "phone_num", nullable = false, unique = true)
    private String phoneNum;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Date birthdate;
    @Column(name = "account_info", nullable = false)
    private String accountInfo;
    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private int role;
    @Column(name = "profile_id", nullable = false, columnDefinition = "TINYINT UNSIGNED DEFAULT 1")
    private int profileId;

    public User(String phoneNum, String name, Date birthdate, String accountInfo, int role,
        int profileId) {
        this.phoneNum = phoneNum;
        this.name = name;
        this.birthdate = birthdate;
        this.accountInfo = accountInfo;
        this.role = role;
        this.profileId = profileId;
    }

    public UserFindOneResponse convertToUserFindOneResponse() {
        return new UserFindOneResponse(id, phoneNum, name, birthdate, accountInfo, role, profileId);
    }
}