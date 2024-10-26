package core.contest.community.comment.service;

import core.contest.community.comment.service.date.*;
import core.contest.community.post.service.data.PostDomain;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CommentRepository {
    Long save(CommentInfo info, PostDomain postDomain);

    CommentDomain findByCommentId(Long commentId);
    List<CommentDomain> findAllByPostId(Long postId);
    Slice<MyCommentDomain> findAllByUserId(Long userId, Pageable pageable);
    boolean existsByParentId(Long parentId);

    Long findAnonymousNumber(Long postId, Long writerId);
    Slice<CommentActivityDomain>findCommentsByTeamMemberCode(String teamMemberCode, Pageable pageable);


    void deleteById(Long commentId);

    void deleteAllByPostId(Long postId);

    void updateCommentStatusToDeleted(Long commentId);

}
