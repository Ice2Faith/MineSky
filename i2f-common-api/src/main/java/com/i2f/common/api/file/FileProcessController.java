package com.i2f.common.api.file;

import com.i2f.common.api.file.core.IFileProcessor;
import com.i2f.common.api.file.core.impl.DefaultContextPathFileProcessor;
import com.i2f.common.api.file.data.UploadFileReturnData;
import com.i2f.common.data.RespData;
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

/**
 * @author ltb
 * @date 2021/9/6
 */
@RestController
@RequestMapping("common/api/file")
public class FileProcessController {

    @Autowired(required = false)
    private IFileProcessor fileProcessor;

    @Value("${server.port}")
    private String port;

    private IFileProcessor getProcessor(){
        if(fileProcessor!=null){
            return fileProcessor;
        }
        return new DefaultContextPathFileProcessor();
    }

    @PostMapping("upload")
    public RespData uploadFile(MultipartFile file,HttpServletRequest request){

        IFileProcessor processor=getProcessor();
        String fileName= processor.saveAndReturnFileName(file,request);

        String originalFileName=file.getOriginalFilename();
        String downloadUrl="/common/api/file/download/"+fileName;
        String externDownloadUrl= ServerUtils.getDomain(request)+downloadUrl;

        UploadFileReturnData data=new UploadFileReturnData();
        data.setFileName(fileName);
        data.setOriginalFileName(originalFileName);
        data.setDownloadUrl(downloadUrl);
        data.setExternDownloadUrl(externDownloadUrl);

        return RespData.success(data);
    }

    @GetMapping("download/**")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url=request.getRequestURI();
        System.out.println("down:url:"+url);// /common/api/file/download/2021/09/06/4f4bfc368f9241208b3585b95671378f.dat

        String fileName="";

        String prefix="common/api/file/download/";
        int idx=url.indexOf(prefix);
        if(idx>=0){
            fileName=url.substring(idx+prefix.length());
        }

        if(fileName.contains("..")){
            ServletUtils.respJsonObj(RespData.error(404,"illegal file download path."));
            return;
        }

        IFileProcessor processor=getProcessor();
        File file=processor.getByFileName(fileName,request);

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
}
