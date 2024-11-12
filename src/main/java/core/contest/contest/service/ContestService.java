package core.contest.contest.service;

import core.contest.contest.dto.request.ContestCreateRequest;
import core.contest.contest.dto.response.ContestResponse;
import core.contest.contest.entity.Contest;
import core.contest.contest.error.ContestErrorResult;
import core.contest.contest.error.ContestException;
import core.contest.contest.error.UserErrorResult;
import core.contest.contest.error.UserException;
import core.contest.contest.repository.ContestRepository;
import core.contest.file.service.FileService;
import core.contest.user.entity.User;
import core.contest.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContestService {

    private final FileService fileService;
    private final UserJpaRepository userRepository;
    private final ContestRepository contestRepository;

    @Transactional
    public Long createContest(ContestCreateRequest request, List<MultipartFile> images, MultipartFile poster, List<MultipartFile> attachments, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorResult.USER_NOT_EXIST));

        Contest contest = Contest.createContest(
                request.getTitle(),
                request.getContent(),
                request.getStartDate(),
                request.getEndDate(),
                request.getQualification(),
                request.getAwardScale(),
                request.getHost(),
                request.getApplicationMethod(),
                request.getApplicationEmail(),
                request.getHostUrl(),
                request.getContestFields(),
                user
        );

        contestRepository.save(contest);
        uploadFiles(images);
        uploadFile(poster);
        uploadFiles(attachments);

        return contest.getId();
    }

    @Transactional
    public ContestResponse getContest(Long contestId, Long userId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ContestException(ContestErrorResult.CONTEST_NOT_EXIST));

        incrementViewAsync(contest);

        return ContestResponse.from(contest);
    }

    @Async
    @Transactional
    protected boolean incrementViewAsync(Contest contest) {
        contestRepository.incrementViewCount(contest.getId());
    }

    private void uploadFile(MultipartFile file) {
        fileService.uploadFile(file);
    }

    private void uploadFiles(List<MultipartFile> files) {
        fileService.uploadFiles(files);
    }
}
