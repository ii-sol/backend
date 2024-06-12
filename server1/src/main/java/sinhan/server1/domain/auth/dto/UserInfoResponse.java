package sinhan.server1.domain.auth.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private int role;
    private int userId;
    private List<FamilyInfoInterface> familyInfo;
}
