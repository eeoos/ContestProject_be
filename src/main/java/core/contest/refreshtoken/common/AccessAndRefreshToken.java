package core.contest.refreshtoken.common;

public record AccessAndRefreshToken(
        String accessToken,
        String refreshToken
) {
}
