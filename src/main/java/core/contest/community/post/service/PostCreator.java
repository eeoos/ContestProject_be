package core.contest.community.post.service;


import core.contest.community.post.service.PostRepository;
import core.contest.community.post.service.data.PostInfo;
import core.contest.community.user.service.data.UserDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostCreator {
    private final PostRepository postRepository;

    public Long create(PostInfo post, UserDomain user){
        return postRepository.save(post, user.getId());
    }

}
