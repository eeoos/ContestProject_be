package core.contest_project.user.dto.request;

import core.contest_project.user.Grade;
import core.contest_project.user.Role;
import core.contest_project.user.service.data.UserInfo;


public record SignUpRequest(
        String nickname,
        String email,
        String snsProfileImageUrl,

        String userField,
        String duty,

        String school,
        String major,
        Grade grade,
        Role role

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
                .role(role)
                .build();
    }
}
