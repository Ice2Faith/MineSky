package com.i2f.common.api.files;

import com.i2f.common.api.file.data.UploadFileReturnData;
import com.i2f.common.api.files.core.IFilesManageProcessor;
import com.i2f.common.api.files.core.impl.DefaultContextPathFilesManageProcessor;
import com.i2f.common.api.files.data.FileItem;
import com.i2f.common.data.RespData;
import com.i2f.common.util.IpUtils;
import com.i2f.common.util.ServerUtils;
import com.i2f.common.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author ltb
 * @date 2021/9/6
 */
@RestController
@RequestMapping("common/api/files")
public class FileManageController {

    @Autowired(required = false)
    private IFilesManageProcessor filesManageProcessor;

    @Value("${server.port}")
    private String port;

    private IFilesManageProcessor getProcessor(){
        if(filesManageProcessor!=null){
            return filesManageProcessor;
        }
        return new DefaultContextPathFilesManageProcessor();
    }

    @GetMapping("list/**")
    public RespData getFileList(HttpServletRequest request, HttpServletResponse response){
        String prefix="common/api/files/list";

        String url=request.getRequestURI();
        String path="";
        int idx=url.indexOf(prefix);
        if(idx>=0){
            path=url.substring(idx+prefix.length());
        }

        if(path.contains("..")){
            return RespData.error("bad directory found.");
        }

        List<FileItem> items=getProcessor().listPathFiles(path,request);
        return RespData.success(items);
    }

    @PostMapping("upload/**")
    public RespData upload2(HttpServletRequest request, MultipartFile file){
        String prefix="common/api/files/upload";

        String url=request.getRequestURI();
        String path="";
        int idx=url.indexOf(prefix);
        if(idx>=0){
            path=url.substring(idx+prefix.length());
        }
        if(path.contains("..")){
            return RespData.error("bad directory found.");
        }

        String fileName=getProcessor().saveFileTo(path,file,request);

        String originalFileName=file.getOriginalFilename();
        String downloadUrl="/common/api/files/download/"+fileName;
        String externDownloadUrl= ServerUtils.getDomain(request) +downloadUrl;

        UploadFileReturnData data=new UploadFileReturnData();
        data.setFileName(fileName);
        data.setOriginalFileName(originalFileName);
        data.setDownloadUrl(downloadUrl);
        data.setExternDownloadUrl(externDownloadUrl);

        return RespData.success(data);
    }

    @GetMapping("download/**")
    public void download(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String prefix="common/api/files/download/";

        String url=request.getRequestURI();
        System.out.println("down:url:"+url);

        String fileName="";

        int idx=url.indexOf(prefix);
        if(idx>=0){
            fileName=url.substring(idx+prefix.length());
        }

        if(fileName.contains("..")){
            ServletUtils.respJsonObj(RespData.error("bad directory found."));
            return;
        }

        IFilesManageProcessor processor=getProcessor();
        File file=processor.getFile(fileName,request);

        if(!file.exists()){
            ServletUtils.respJsonObj(RespData.error(404,"request file not found."));
            return;
        }

        response.reset();
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition","attachment; filename=" +java.net.URLEncoder.encode(file.getName(), "UTF-8")); // 设置文件名称

        OutputStream os= response.getOutputStream();
        BufferedInputStream is=new BufferedInputStream(new FileInputStream(file));
        byte[] buf=new byte[4096];
        int len=0;
        while((len=is.read(buf))>0){
            os.write(buf,0,len);
        }
        is.close();
        os.flush();
    }

    @GetMapping("delete/**")
    public RespData delete(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String prefix = "common/api/files/delete/";

        String url = request.getRequestURI();
        System.out.println("down:url:" + url);

        String fileName = "";

        int idx = url.indexOf(prefix);
        if (idx >= 0) {
            fileName = url.substring(idx + prefix.length());
        }

        if(fileName.contains("..")){
            return RespData.error("bad directory found.");
        }

        IFilesManageProcessor processor = getProcessor();
        File file = processor.getFile(fileName, request);

        if (!file.exists()) {
            return RespData.error(404, "request file not found.");
        }

        file.delete();
        return RespData.success("file has deleted:"+file.getName());
    }

    @GetMapping("rename/**")
    public RespData rename(HttpServletRequest request,String newName,HttpServletResponse response) throws IOException {
        String prefix = "common/api/files/rename/";

        String url = request.getRequestURI();
        System.out.println("down:url:" + url);

        String fileName = "";

        int idx = url.indexOf(prefix);
        if (idx >= 0) {
            fileName = url.substring(idx + prefix.length());
        }

        if(newName==null || "".equals(newName)){
            return RespData.error("rename operation require 'newName' argument.");
        }

        if(fileName.contains("..") || newName.contains("..")){
            return RespData.error("bad directory found.");
        }

        IFilesManageProcessor processor = getProcessor();
        File file = processor.getFile(fileName, request);

        if (!file.exists()) {
            return RespData.error(404, "request file not found.");
        }

        File parent=file.getParentFile();
        File nname=new File(newName);
        File newFile=new File(parent,nname.getName());
        file.renameTo(newFile);
        return RespData.success("file has renamed:"+newFile.getName());
    }
}
