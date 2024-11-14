package core.contest_project.file.entity;

import core.contest_project.common.error.file.FileErrorResult;
import core.contest_project.common.error.file.FileException;
import core.contest_project.contest.entity.Contest;
import core.contest_project.file.FileLocation;
import core.contest_project.file.FileType;
import core.contest_project.file.service.data.FileDomain;
import core.contest_project.file.service.data.FileInfo;
import core.contest_project.community.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class File {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name="file_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="contest_id")
    private Contest contest;

    private FileLocation location;

    private String storeName;
    private String uploadName;
    @Enumerated(EnumType.STRING)
    private FileType fileType;
    private LocalDateTime createAt;
    private Long orderIndex;
    public FileDomain toDomain(){
        return FileDomain.builder()
                .id(id)
                .info(FileInfo.builder()
                        .storeFileName(storeName)
                        .uploadFileName(uploadName)
                        .fileType(fileType)
                        .build())
                .build();
    }

    private File(Contest contest, String storeName, String uploadName, FileType fileType) {
        validateFileName(uploadName);
        validateFileType(fileType);

        this.contest = contest;
        this.storeName = storeName;
        this.uploadName = uploadName;
        this.fileType = fileType;
        this.createAt = LocalDateTime.now();
    }

    private void validateFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new FileException(FileErrorResult.INVALID_FILE_NAME);
        }
    }

    private void validateFileType(FileType fileType) {
        if (fileType == null) {
            throw new FileException(FileErrorResult.INVALID_FILE_TYPE);
        }
    }
}
