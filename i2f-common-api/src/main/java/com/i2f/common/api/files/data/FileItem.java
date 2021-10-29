package com.i2f.common.api.files.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ltb
 * @date 2021/9/6
 */
@Data
@NoArgsConstructor
public class FileItem {
    private String path;
    private String fileName;
    private boolean isDir;
    private Long size;
}
