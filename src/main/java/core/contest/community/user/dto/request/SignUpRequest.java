package core.contest.community.user.dto.request;

import core.contest.community.user.Grade;
import core.contest.community.user.service.data.UserInfo;

public record SignUpRequest(
        String nickname,
        String email,
        String snsProfileImageUrl,

        String userField,
        String duty,

        String school,
        String major,
        Grade grade

) {

    public UserInfo toUserInfo() {
        return UserInfo.builder()
                .nickname(nickname)
                .email(email)
                .snsProfileImageUrl(snsProfileImageUrl)
                .userField(userField)
                .duty(duty)
                .school(school)
                .major(major)
                .grade(grade)
                .build();
    }
}
