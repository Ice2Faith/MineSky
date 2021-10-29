package com.i2f.common.api.qrcode;

import com.i2f.common.api.qrcode.core.QrCodeWorker;
import com.i2f.common.data.RespData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author ltb
 * @date 2021/9/7
 */
@RestController
@RequestMapping("common/api/qrcode")
public class QrcodeProcessController {
    @RequestMapping("make")
    public void makeQrCode(String content, HttpServletResponse response) throws Exception {
        response.reset();
        String virtualFileName = "qrcode-"+System.currentTimeMillis()+".jpg";

        response.setContentType( "image/jpeg;charset=UTF-8");

        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(virtualFileName, "UTF-8"));
        OutputStream os = response.getOutputStream();

        QrCodeUtil.toQrCode(content, 300, os);
        os.close();
    }

    @PostMapping("parse")
    public RespData upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            InputStream is = file.getInputStream();
            String data = new QrCodeWorker().parseQrCode(is);
            return RespData.success(data);
        }
        return RespData.error("二维码图片不正确。");
    }
}
