package core.contest.community.scrap.service;

import core.contest.community.scrap.service.ScrapDomain;
import core.contest.community.scrap.service.ScrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScrapDeleter {
    private final ScrapRepository scrapRepository;

    public void remove(ScrapDomain scrapDomain) {
        log.info("[ScrapDeleter][remove]");
        log.info("scrapDomain: {}", scrapDomain);

      scrapRepository.delete(scrapDomain);
    }

    public void remove(Long postId, Long userId) {
        scrapRepository.delete(postId, userId);
    }

    public void removeAll(Long postId) {
        scrapRepository.deleteAll(postId);
    }
}
