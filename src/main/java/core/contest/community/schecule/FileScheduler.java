package core.contest.community.schecule;

import core.contest.file.service.storage.FileManager;
import core.contest.file.service.FileRepository;
import core.contest.file.service.data.FileDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileScheduler {
    private final FileRepository fileRepository;
    private final FileManager fileManager;


    //    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
//    @Scheduled(fixedDelay = 30000) // 30초마다 실행
    public void deleteOrphanFiles() {
        log.info("[deleteOrphanFiles]= {}", LocalDateTime.now());
        List<FileDomain> orphanFiles = fileRepository.findOrphanFiles();

        log.info("orphanFile.size()= {}", orphanFiles.size());
        for (FileDomain orphanFile : orphanFiles) {
            log.info("Deleting orphan file= {}", orphanFile);
        }
        fileRepository.deleteAll(orphanFiles);

        fileManager.setUrls(orphanFiles);
        List<String> urls = fileManager.getUrls(orphanFiles);
        fileManager.delete(urls);
    }




}
