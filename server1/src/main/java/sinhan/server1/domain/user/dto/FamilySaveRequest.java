package sinhan.server1.domain.user.dto;

import lombok.*;

@RequiredArgsConstructor
@Setter
@Getter
public class FamilySaveRequest {

    private long userSn;
    private final long familySn;
}
