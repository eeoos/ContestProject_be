package core.contest.contest.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ContestException extends RuntimeException{
    private final ContestErrorResult contestErrorResult;
}
