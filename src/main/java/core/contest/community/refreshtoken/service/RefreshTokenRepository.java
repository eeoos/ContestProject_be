package core.contest.community.refreshtoken.service;

public interface RefreshTokenRepository {
    Long save(String refreshToken, Long userId);
}
