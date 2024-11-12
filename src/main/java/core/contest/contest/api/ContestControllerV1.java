package core.contest.contest.api;

import core.contest.contest.dto.request.ContestCreateRequest;
import core.contest.contest.dto.response.ContestResponse;
import core.contest.contest.dto.response.ContestSimpleResponse;
import core.contest.contest.entity.ContestField;
import core.contest.contest.service.ContestService;
import core.contest.user.service.data.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contests")
public class ContestControllerV1 {

    private final ContestService contestService;
    private static final int PAGE_SIZE = 20;

    // 공모전 생성
    @PostMapping(value = "/{login-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> createContest(
            @RequestPart("contestCreateRequest") ContestCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart(value = "poster", required = false) MultipartFile poster,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments,
            @PathVariable("login-id") Long userId
    ) {
        // 임시로
        UserDomain writer = UserDomain.builder()
                .id(userId)
                .build();

        Long contestId = contestService.createContest(request, images, poster, attachments, writer.getId());
        return ResponseEntity.ok(contestId);
    }

    // 공모전 상세 조회
    @GetMapping("/{contestId}/{login-id}")
    public ResponseEntity<ContestResponse> getContest(
            @PathVariable Long contestId,
            @PathVariable("login-id") Long userId
    ) {
        // 임시로
        UserDomain writer = UserDomain.builder()
                .id(userId)
                .build();

        ContestResponse contest = contestService.getContest(contestId, writer.getId());
        return ResponseEntity.ok(contest);
    }

    // 필드로 공모전들 조회(미선택 시 전체)
    @GetMapping("/{login-id}")
    public ResponseEntity<Slice<ContestSimpleResponse>> getContestsByField(
            @RequestParam(defaultValue = "전체")  List<ContestField> fields,
            @RequestParam(required = false) Long lastClubId,
            @PathVariable("login-id") Long userId
    ) {

        // 임시로
        UserDomain writer = UserDomain.builder()
                .id(userId)
                .build();

        Slice<ContestSimpleResponse> contests = contestService.getContestsByField(fields, lastClubId, PAGE_SIZE, writer.getId());
        return ResponseEntity.ok(contests);
    }

    // 공모전 업데이트 // 제목, 내용, 이미지, 첨부파일 따로 수정해야할지?
    @PutMapping("/{contestId}/{login-id}")
    public ResponseEntity<Void> updateContest(
            @PathVariable Long contestId,
            @PathVariable("login-id") Long user
    ) {
        // 임시로
        UserDomain writer = UserDomain.builder()
                .id(user)
                .build();

        contestService.updateContest(contestId, writer.getId());
        return ResponseEntity.noContent().build();
    }

    // 공모전 삭제
    @DeleteMapping("/{contestId}/{login-id}")
    public ResponseEntity<Void> deleteContest(
            @PathVariable Long contestId,
            @PathVariable("login-id") Long user
    ) {
        // 임시로
        UserDomain writer = UserDomain.builder()
                .id(user)
                .build();

        contestService.deleteContest(contestId, writer.getId());
        return ResponseEntity.noContent().build();
    }

    //관심 분야 목록
    @GetMapping("/fields")
    public ResponseEntity<List<String>> getFields() {

    }
}
