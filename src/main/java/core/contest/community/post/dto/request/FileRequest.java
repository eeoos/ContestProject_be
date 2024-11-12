package core.contest.community.post.dto.request;

import core.contest.file.FileLocation;
import core.contest.file.service.data.FileDomain;
import core.contest.file.service.data.FileInfo;

public record FileRequest(
        Long order,
        String url
) {

    public FileDomain toFileDomain(){
        FileInfo info = FileInfo.builder()
                .location(FileLocation.POST)
                .build();
        return FileDomain.builder()
                .order(order)
                .info(info)
                .url(url).build();
    }
}
