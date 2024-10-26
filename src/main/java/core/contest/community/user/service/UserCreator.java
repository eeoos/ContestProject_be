package core.contest.community.user.service;

import core.contest.community.user.service.UserRepository;
import core.contest.community.user.service.data.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreator {
    private final UserRepository userRepository;


    public Long signup(UserInfo user){
        return userRepository.create(user);
    }

}