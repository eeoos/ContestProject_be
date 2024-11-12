package core.contest.contest.dto.request;

import core.contest.contest.entity.ContestApplicationMethod;
import core.contest.contest.entity.ContestField;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ContestCreateRequest {
    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String qualification;
    private String awardScale;
    private String host;
    private ContestApplicationMethod applicationMethod;
    @Email
    private String applicationEmail;
    private String hostUrl;
    private List<ContestField> contestFields;
}
