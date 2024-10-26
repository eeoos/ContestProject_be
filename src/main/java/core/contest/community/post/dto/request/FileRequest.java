package core.contest.community.post.dto.request;

import core.contest.community.file.service.data.FileDomain;

public record FileRequest(
        Long order,
        String url
) {

    public FileDomain toFileDomain(){
        return FileDomain.builder()
                .order(order)
                .url(url).build();
    }
}
