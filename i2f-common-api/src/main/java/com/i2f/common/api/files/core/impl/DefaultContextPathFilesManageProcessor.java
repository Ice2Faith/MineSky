package com.i2f.common.api.files.core.impl;

import com.i2f.common.api.files.data.FileItem;
import com.i2f.common.api.files.core.IFilesManageProcessor;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ltb
 * @date 2021/9/6
 */
public class DefaultContextPathFilesManageProcessor implements IFilesManageProcessor {
    private String relativePath="webManageFileRoot";

    protected File getSaveRoot(HttpSession session){
        String root=session.getServletContext().getRealPath("");
        File file=new File(root);
        file=file.getParentFile();
        file=new File(root,relativePath);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }
    private File getSaveFile(HttpSession session,String subPath){
        File file=getSaveRoot(session);
        return new File(file,subPath);
    }

    @Override
    public String saveFileTo(String path, MultipartFile file, HttpServletRequest request) {
        File oriFile=new File(file.getOriginalFilename());
        String fileName=path+"/"+oriFile.getName();
        File saveFile=getSaveFile(request.getSession(), fileName);
        if(!saveFile.getParentFile().exists()){
            saveFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public File getFile(String fileName, HttpServletRequest request) {
        return getSaveFile(request.getSession(),fileName);
    }

    @Override
    public List<FileItem> listPathFiles(String path,HttpServletRequest request) {
        File file=getSaveFile(request.getSession(), path);
        List<FileItem> ret=new ArrayList<>();
        if(!file.exists()){
            return ret;
        }
        if(!file.isDirectory()){
            return ret;
        }
        File[] files=file.listFiles();
        for(File item : files){
            FileItem fitem=new FileItem();
            fitem.setPath(path);
            fitem.setDir(item.isDirectory());
            fitem.setFileName(item.getName());
            fitem.setSize(item.length());
            ret.add(fitem);
        }
        return ret;
    }
}
