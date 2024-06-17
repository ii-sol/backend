package sinhan.server1.global.security.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private long sn;
    private List<FamilyInfoResponse> familyInfo;
}
