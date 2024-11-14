package core.contest_project.community.post.dto.request;


import core.contest_project.file.service.data.FileDomain;

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
