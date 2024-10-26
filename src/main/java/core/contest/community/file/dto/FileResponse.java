package core.contest.community.file.dto;

import core.contest.community.file.FileType;
import core.contest.community.file.dto.AttachmentResponse;
import core.contest.community.file.service.data.FileDomain;

import java.util.ArrayList;
import java.util.List;

public record FileResponse(
        List<String> imageUrls,
        List<core.contest.community.file.dto.AttachmentResponse> attachments
) {

    public static FileResponse fromDomain(List<FileDomain> files) {
        List<String> images = new ArrayList<>();
        List<core.contest.community.file.dto.AttachmentResponse> attachments=new ArrayList<>();

        for (FileDomain file : files) {
            if(file.getInfo().getFileType()== FileType.IMAGE){
                images.add(file.getUrl());
            }
            else if(file.getInfo().getFileType()==FileType.ATTACHMENT){
                attachments.add(new AttachmentResponse(file.getInfo().getUploadFileName(), file.getUrl()));
            }
        }

        return new FileResponse(images, attachments);
    }
}
