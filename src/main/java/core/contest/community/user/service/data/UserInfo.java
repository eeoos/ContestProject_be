package core.contest.community.user.service.data;

import core.contest.community.user.Grade;
import core.contest.community.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserInfo {
    private String email;
    private String nickname;
    private String snsProfileImageUrl;

    private Grade grade;
    private String school;
    private String major;

    private Role role;
    private String userField;
    private String duty;

    private boolean isRatingPublic;
}