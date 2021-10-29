package com.i2f.common.api.file.core;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author ltb
 * @date 2021/9/6
 */
public interface IFileProcessor {
    String saveAndReturnFileName(MultipartFile file, HttpServletRequest request);
    File getByFileName(String fileName,HttpServletRequest request);
}
