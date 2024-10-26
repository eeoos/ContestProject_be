package core.contest.community.refreshtoken.common;

public record AccessAndRefreshToken(
        String accessToken,
        String refreshToken
) {
}
