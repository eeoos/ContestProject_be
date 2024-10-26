package core.contest.community.file.repository;

import core.contest.community.file.repository.FileJpaRepository;
import core.contest.community.file.service.data.FileDomain;
import core.contest.community.file.service.data.FileInfo;
import core.contest.community.file.entity.File;
import core.contest.community.file.service.FileRepository;
import core.contest.community.post.entity.Post;
import core.contest.community.post.repository.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FileRepositoryImpl implements FileRepository {
    private final FileJpaRepository fileJpaRepository;
    private final PostJpaRepository postJpaRepository;


    @Override
    public Long save(FileInfo fileInfo) {
        File file = File.builder()
                .post(null)
                .storeName(fileInfo.getStoreFileName())
                .uploadName(fileInfo.getUploadFileName())
                .createAt(LocalDateTime.now())
                .fileType(fileInfo.getFileType())
                .build();

        return fileJpaRepository.save(file).getId();
    }



    @Override
    public List<FileDomain> findAllByPostId(Long postId) {
        return fileJpaRepository.findAllByPostIdOrderByOrderIndex(postId).stream()
                .map(File::toDomain)
                .toList();
    }

    @Override
    public FileDomain findThumbnailByPostId(Long postId) {
        Optional<File> file = fileJpaRepository.findThumbnail(postId);
        return file.map(File::toDomain).orElse(null);
    }

    @Override
    public List<FileDomain> findOrphanFiles() {
        //        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime yesterday=LocalDateTime.now().minusSeconds(5);
        List<File> orphanFiles = fileJpaRepository.findOrphanFiles(yesterday);
        return orphanFiles.stream().map(File::toDomain).toList();
    }

    @Override
    public void updateAllByPost(Long postId, Map<Long, String> orderAndStoreFileName) {
        Post post = postJpaRepository.getReferenceById(postId);
        for(Long order: orderAndStoreFileName.keySet()) {
            String storeFileName = orderAndStoreFileName.get(order);
            fileJpaRepository.updateAllByPost(post, order, storeFileName);
        }
    }

    @Override
    public void updateAllByPost(Long postId, List<FileDomain> files) {
        Post post = postJpaRepository.getReferenceById(postId);
        for(FileDomain file: files) {
            String storeFileName = file.getStoreFileName();
            Long order = file.getOrder();
            fileJpaRepository.updateAllByPost(post, order, storeFileName);
        }
    }


    @Override
    public void deleteAllByPostId(Long postId, List<String> storeFileNames) {
        fileJpaRepository.deleteAllByPostId(postId, storeFileNames);
    }

    @Override
    public void deleteAll(List<FileDomain> files) {
        List<Long> ids=new ArrayList<>();
        for (FileDomain file : files) {
            ids.add(file.getId());
        }

        fileJpaRepository.deleteAll(ids);
    }

    @Override
    public void delete(Long postId) {
        fileJpaRepository.delete(postId);

    }




}
