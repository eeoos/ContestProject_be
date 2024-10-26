package core.contest.community.comment.service.date;

import core.contest.community.comment.service.date.ChildCommentDomain;
import core.contest.community.comment.service.date.CommentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ParentCommentDomain{
    private Long id;
    private CommentInfo info;
    private LocalDateTime createAt;
    private boolean isDeleted;

    private boolean isWriter;
    private Long commentLikeCount;
    private boolean isLiked;
    private List<core.contest.community.comment.service.date.ChildCommentDomain> replies;

    public void associateChild(ChildCommentDomain child){
        replies.add(child);
    }
}