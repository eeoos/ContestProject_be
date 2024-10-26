package core.contest.community.post_like.service;

import core.contest.community.post.service.PostUpdater;
import core.contest.community.post_like.service.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostLikeCreator {
    private final PostLikeRepository postLikeRepository;
    private final PostUpdater postUpdater;

    public void create(Long postId, Long userId) {
        postUpdater.increaseLikeCount(postId);
        postLikeRepository.save(postId, userId);
    }
}
