package core.contest.community.comment.service.date;

public record CommentAnonymousDomain(
        Long id,
        boolean isAnonymous,
        Long anonymousNumber
) {
}
