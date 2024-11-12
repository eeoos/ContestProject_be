package core.contest.community.comment.dto;

import java.util.List;

public record CommentResponse(
        List<ParentCommentResponse> comments
) {
}
