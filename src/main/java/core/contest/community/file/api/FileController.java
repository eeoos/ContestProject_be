package core.contest.community.file.api;

import core.contest.community.file.FileType;
import core.contest.community.file.dto.FileResponse;
import core.contest.community.file.service.FileService;
import core.contest.community.file.service.data.FileDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(value="/api/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map> images(@RequestPart(value="file") MultipartFile file){

        String url = fileService.upload(file, FileType.IMAGE);
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("url", url);

        return ResponseEntity.ok(map);
    }

    @PostMapping(value="/api/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map> attachments(@RequestPart(value="file") MultipartFile file){

        String url = fileService.upload(file, FileType.ATTACHMENT);
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("url", url);

        return ResponseEntity.ok(map);
    }

    @PostMapping(value="/api/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map> storeProfileImage(@RequestPart(value="file") MultipartFile file){
        String url = fileService.uploadOnlyStorage(file);
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("profileImage", url);
        return ResponseEntity.ok(map);
    }


    // == test == //

    @GetMapping("test/files/{post-id}")
    public ResponseEntity<FileResponse> getFiles(@PathVariable("post-id") Long postId) {
        List<FileDomain> files = fileService.getFiles(postId);
        FileResponse fileResponse = FileResponse.fromDomain(files);

        return ResponseEntity.ok(fileResponse);
    }

    @GetMapping("/test/files/thumbnail/{post-id}")
    public ResponseEntity<?> getThumbnail(@PathVariable("post-id") Long postId) {
        FileDomain thumbnail = fileService.getThumbnail(postId);
        return ResponseEntity.ok(thumbnail);
    }


}
