package com.i2f.common.api.files.core;

import com.i2f.common.api.files.data.FileItem;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author ltb
 * @date 2021/9/6
 */
public interface IFilesManageProcessor {
    String saveFileTo(String path,MultipartFile file, HttpServletRequest request);
    File getFile(String fileName, HttpServletRequest request);
    List<FileItem> listPathFiles(String path,HttpServletRequest request);
}
