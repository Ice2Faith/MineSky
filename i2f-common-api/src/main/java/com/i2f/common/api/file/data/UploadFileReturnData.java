package com.i2f.common.api.file.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ltb
 * @date 2021/9/6
 */
@Data
@NoArgsConstructor
public class UploadFileReturnData {
    private String originalFileName;
    private String fileName;
    private String downloadUrl;
    private String externDownloadUrl;
}
