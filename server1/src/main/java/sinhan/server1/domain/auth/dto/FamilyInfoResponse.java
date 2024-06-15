package sinhan.server1.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FamilyInfoResponse {

    long sn;
    String name;

    public FamilyInfoResponse(long sn) {
        this.sn = sn;
    }
}
