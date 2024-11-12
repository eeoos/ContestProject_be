package core.contest.contest.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import core.contest.contest.entity.Contest;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ContestResponse(
        Long contestId,
        String title,
        String content,
        List<String> contentImageUrls,
        String posterUrl,
        String attachmentUrls,
        Long viewCount,
        Long bookmarkCount,
        Long remainingDays,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
        LocalDateTime endDate,

        String qualification,

        String awardScale,

        String host,

        String applicationMethod,
        String applicationEmail,

        String hostUrl,

        List<String> postFields,
        String contestStatus,
        Long writerId
){
        public static ContestResponse from(Contest contest) {
                return ContestResponse.builder()
                        .contestId(contest.getId())
                        .title(contest.getTitle())
                        .content(contest.getContent())
                        .contentImageUrls(contest.getContentImages().stream().map(file -> new FileResponse(file.getId(), file.getPath())))
                        .posterUrl()
                        .attachmentUrls(contest.getAttachments())
                        .build();
        }

}
