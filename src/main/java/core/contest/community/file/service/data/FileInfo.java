package core.contest.community.file.service.data;


import core.contest.community.file.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor(access = PROTECTED)
public class FileInfo{
    private String uploadFileName;
    private String storeFileName;
    private FileType fileType;


    public void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}


