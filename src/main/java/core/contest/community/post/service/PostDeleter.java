package core.contest.community.post.service;

import core.contest.community.comment.service.CommentDeleter;
import core.contest.file.service.db.FileDeleter;
import core.contest.community.post.service.data.PostDomain;
import core.contest.community.post_like.service.PostLikeDeleter;
import core.contest.community.scrap.service.ScrapDeleter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostDeleter {
    private final PostRepository postRepository;
    private  final CommentDeleter commentDeleter;
    private final ScrapDeleter scrapDeleter;
    private final PostLikeDeleter postLikeDeleter;
    private final FileDeleter fileDeleter;


    public void delete(PostDomain postDomain) {
        Long postId = postDomain.getId();
        // scrap
        scrapDeleter.removeAll(postId);
        // like
        postLikeDeleter.removeAll(postId);

        // 댓글
        commentDeleter.removeAll(postId);

        // 파일
        fileDeleter.delete(postId);

        postRepository.deleteByPostId(postId);
    }


}
