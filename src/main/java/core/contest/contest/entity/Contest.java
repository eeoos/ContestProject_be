package core.contest.contest.entity;

import core.contest.file.entity.File;
import core.contest.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    // 본문 이미지
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest")
    private List<File> contentImages = new ArrayList<>();

    // 포스터 이미지
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "contest")
    private File posterImage;

    // 첨부 파일(지원서)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contest")
    private List<File> attachments = new ArrayList<>();

    //접수일
    @Column(nullable = false)
    private LocalDateTime startDate;

    //마감일
    @Column(nullable = false)
    private LocalDateTime endDate;

    // 조회수
    @Column(name = "view_count")
    private Long viewCount = 0L;

    // 북마크수
    @Column(name = "bookmark_count")
    private Long bookmarkCount = 0L;

    //응모 자격
    @Column(name = "qualification")
    private String qualification;

    //시상 규모
    @Column(name = "award_scale")
    private String awardScale;

    //주최 기관
    @Column(name = "host")
    private String host;

    // 신청 방법
    @Enumerated(EnumType.STRING)
    @Column(name = "application_method")
    private ContestApplicationMethod applicationMethod;

    // 신청 이메일
    @Column(name = "application_email")
    private String applicationEmail;

    // 작성자
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    // 주최 홈페이지 url
    @Column(name = "host_url")
    private String hostUrl;

    // 모집글 분야
    @ElementCollection(targetClass = ContestField.class)
    @CollectionTable(name = "contest_fields", joinColumns = @JoinColumn(name = "contest_id"))
    @Enumerated(EnumType.STRING)
    private List<ContestField> contestFields = new ArrayList<>();

    // 모집글 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContestStatus contestStatus;

    // 생성일
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 수정일
    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Awaiter> awaiters = new ArrayList<>(); // 대기자 목록

    public static Contest createContest(String title,
                                        String content,
                                        LocalDateTime startDate,
                                        LocalDateTime endDate,
                                        String qualification,
                                        String awardScale,
                                        String host,
                                        ContestApplicationMethod applicationMethod,
                                        String applicationEmail,
                                        String hostUrl,
                                        List<ContestField> contestFields,
                                        User writer){
        return Contest.builder()
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .viewCount(0L)
                .bookmarkCount(0L)
                .qualification(qualification)
                .awardScale(awardScale)
                .host(host)
                .applicationMethod(applicationMethod)
                .applicationEmail(applicationEmail)
                .hostUrl(hostUrl)
                .contestFields(contestFields)
                .writer(writer)
                .build();
    }

}
