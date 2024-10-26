package core.contest.community.user_detail.service;

import core.contest.community.user_detail.UserDetailType;
import core.contest.community.user_detail.service.UserDetailInfo;

import java.util.List;

public interface UserDetailRepository {

    void saveAll(UserDetailType userDetailType, List<String> details, Long userId);
    UserDetailInfo findAllByUser(Long userId);
    void deleteAll(UserDetailType userDetailType, List<String> details, Long userId);

    void deleteAll(Long userId);
}
