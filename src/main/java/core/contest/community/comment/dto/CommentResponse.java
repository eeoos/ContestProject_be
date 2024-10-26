package core.contest.community.comment.dto;

import core.contest.community.comment.dto.ParentCommentResponse;

import java.util.List;

public record CommentResponse(
        List<ParentCommentResponse> comments
) {
}
