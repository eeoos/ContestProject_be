package core.contest.community.comment.repository;

import core.contest.community.comment.entity.Comment;
import core.contest.community.comment.service.*;
import core.contest.community.comment.service.date.*;
import core.contest.global.exception.CustomException;
import core.contest.global.exception.ErrorCode;
import core.contest.community.post.entity.Post;
import core.contest.community.post.repository.PostJpaRepository;
import core.contest.community.post.service.data.PostDomain;
import core.contest.user.entity.User;
import core.contest.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryImpl implements CommentRepository {
    private final CommentJpaRepository commentJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;


    @Override
    public Long save(CommentInfo info, PostDomain postDomain) {
        log.info("[CommentRepositoryImpl][save]");

        User writer = userJpaRepository.getReferenceById(info.getWriterDomain().getId());
        Post post = postJpaRepository.getReferenceById(postDomain.getId());
        Comment parent=null;
        if(info.isReply()){parent  = commentJpaRepository.getReferenceById(info.getParentId());}

        Comment comment = Comment.builder()
                .post(post)
                .writer(writer)
                .parent(parent)
                .content(info.getContent())
                .createAt(LocalDateTime.now())
                .isAnonymous(info.isAnonymous())
                .anonymousNumber(info.getAnonymousNumber())
                .isDeleted(false)
                .build();

        return commentJpaRepository.save(comment).getId();
    }

    @Override
    public CommentDomain findByCommentId(Long commentId) {
        return commentJpaRepository.findByCommentId(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "comment not found"))
                .toCommentDomain();
    }


    @Override
    public List<CommentDomain> findAllByPostId(Long postId) {
        List<Comment> comments = commentJpaRepository.findAllByPostId(postId);
        return comments.stream().map(Comment::toCommentDomain).collect(toList());
    }

    @Override
    public Slice<MyCommentDomain> findAllByUserId(Long userId, Pageable pageable) {
        Slice<Comment> comments = commentJpaRepository.findAllByUserId(userId, pageable);
        return comments.map(Comment::toMyCommentDomain);
    }

    @Override
    public boolean existsByParentId(Long parentId) {
        log.info("[CommentRepositoryImpl][existsByParentId]");
        log.info("parentId= {}", parentId);

        boolean b = commentJpaRepository.existsByParentId(parentId);
        log.info("b= {}", b);

        return b;
    }


    @Override
    public Long findAnonymousNumber(Long postId, Long writerId) {
        return commentJpaRepository.findAnonymousCommentByPostIdAndWriterId(postId, writerId);
    }

    @Override
    public Slice<CommentActivityDomain> findCommentsByTeamMemberCode(String teamMemberCode, Pageable pageable) {
        Slice<CommentActivityDomain> posts = commentJpaRepository.findCommentsByTeamMemberCode(teamMemberCode, pageable)
                .map((comment)->new CommentActivityDomain(comment.getId(), comment.getPost().getTitle(), comment.getContent()));
        return posts;
    }

    /**
     *
     * @param commentId
     * 댓글이 영속성 컨테이너에 있어야 될 거 같은데?
     */

    @Override
    public void deleteById(Long commentId) {
        commentJpaRepository.deleteById(commentId);
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        commentJpaRepository.deleteChildByPostId(postId);
        commentJpaRepository.deleteAllByPostId(postId);
    }

    @Override
    public void updateCommentStatusToDeleted(Long commentId) {
        commentJpaRepository.updateCommentToDeleted(commentId);
    }


}
