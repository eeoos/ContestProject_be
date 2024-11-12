package core.contest.community.post.service.data;

import core.contest.file.service.data.FileDomain;
import core.contest.user.service.data.UserDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostUpdateDomain {
    private Long id;

    private UserDomain writer;

    private PostInfo info;

    private List<FileDomain> files;



}
