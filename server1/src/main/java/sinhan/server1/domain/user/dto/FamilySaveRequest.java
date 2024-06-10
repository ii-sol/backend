package sinhan.server1.domain.user.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FamilySaveRequest {

    private int id; // 자기 자신
    private int role; // 자기 자신
    private String phoneNum;

    public FamilySaveRequest(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
