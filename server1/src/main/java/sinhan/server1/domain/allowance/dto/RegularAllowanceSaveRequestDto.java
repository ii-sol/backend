package sinhan.server1.domain.allowance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegularAllowanceSaveRequestDto {

    private int amount;
    private int period;
}
