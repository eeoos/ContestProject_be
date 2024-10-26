package core.contest.community.comment_like.service;

import core.contest.community.comment_like.service.CommentLikeDomain;

import java.util.List;

public interface CommentLikeRepository {
    boolean isLiked(Long commentId, Long userId);
    boolean isLiked(Long commentId);

    Long count(Long commentId);

    void delete(Long commentId, Long userId);
    void deleteAll(List<Long> commentIds);
    void deleteAll(Long commentId);
    void deleteAllByUserId(Long userId);

    void save(CommentLikeDomain commentLikeDomain);
    void save(Long commentId, Long userId);
}
