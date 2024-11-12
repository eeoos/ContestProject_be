package core.contest.refreshtoken.service;

public interface RefreshTokenRepository {
    Long save(String refreshToken, Long userId);
}
