package core.contest.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file) throws IOException {
        // 파일 이름 생성(UUID)
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 파일 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // S3에 파일 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        // 업로드된 파일의 URL 반환
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public List<String> uploadFiles(List<MultipartFile> files) {
        return files.stream()
                .filter(file -> !file.isEmpty())
                .map(file -> {
                    try {
                        return uploadFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException("파일 업로드 실패: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());
    }

}
