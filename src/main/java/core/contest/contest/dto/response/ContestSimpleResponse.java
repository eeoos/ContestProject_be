package core.contest.contest.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import core.contest.contest.entity.Contest;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Builder
public record ContestSimpleResponse (
        Long contestId,
        String title,
        Long bookmarkCount,
        Long remainingDays,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
        LocalDateTime endDate,

        String posterUrl,

        List<String> postFields,
        Long userId

){
        public static ContestSimpleResponse from(Contest contest) {

                Long remainingDays = ChronoUnit.DAYS.between(contest.getStartDate().toLocalDate(), contest.getEndDate().toLocalDate());
                // 마감일이 지나면 -1 고정
                if (remainingDays < 0) {
                        remainingDays = -1L;
                }
                return ContestSimpleResponse.builder()
                        .contestId(contest.getId())
                        .title(contest.getTitle())
                        .bookmarkCount(contest.getBookmarkCount())
                        .remainingDays(remainingDays)
                        .endDate(contest.getEndDate())
                        .posterUrl(contest.getPosterImage().getPath())
                        .postFields(contest.getContestFields())
                        .userId(contest.getWriter().getId())
                        .build();
        }
}
