package com.i2f.common.api.file.core.impl;

import com.i2f.common.api.file.core.IFileProcessor;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author ltb
 * @date 2021/9/6
 */
public class DefaultContextPathFileProcessor implements IFileProcessor {
    private String relativePath="webFileRoot";
    private ThreadLocal<SimpleDateFormat> fmt=ThreadLocal.withInitial(()->{
        return new SimpleDateFormat("yyyy/MM/dd");
    });
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
    public String saveAndReturnFileName(MultipartFile file, HttpServletRequest request) {
        String originalFileName=file.getOriginalFilename();
        File pfile=new File(originalFileName);
        String name=pfile.getName();
        int exdot=name.lastIndexOf(".");
        String ext="";
        if(exdot>=0){
            ext=name.substring(exdot);
        }

        String saveFileName= UUID.randomUUID().toString().toLowerCase().replaceAll("-","")+ext;
        Date now=new Date();

        String subPath=fmt.get().format(now);

        saveFileName=subPath+"/"+saveFileName;
        File saveFile=getSaveFile(request.getSession(),saveFileName);
        if(!saveFile.getParentFile().exists()){
            saveFile.getParentFile().mkdirs();
        }

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFileName;
    }

    @Override
    public File getByFileName(String fileName,HttpServletRequest request) {
        File file=getSaveFile(request.getSession(),fileName);
        return file;
    }
}
