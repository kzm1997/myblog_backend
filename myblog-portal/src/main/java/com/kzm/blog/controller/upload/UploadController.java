package com.kzm.blog.controller.upload;

import cn.hutool.core.lang.UUID;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.YearMonth;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:43 2020/3/30
 * @Version
 */
@RestController
@Slf4j
public class UploadController {

    @Value("${upload.basePath}")
    private String basePath;

    @Value("${upload.nginxPath}")
    private String NginxPath;

    @PostMapping
    @Log(module = "文件上传", operation = "文件上传")
    public Result upload(HttpServletRequest request, MultipartFile file) {
        YearMonth now = YearMonth.now();
        String timePath = TimeUtils.YearMonthToString(now, "yyyyMMdd");
        File baseFoler = new File(basePath + timePath);
        StringBuffer url = new StringBuffer();
        if (!baseFoler.exists()) {
            baseFoler.mkdirs();
        }

        String fileName = UUID.fastUUID() + "_" + file.getOriginalFilename();

        File dest = new File(baseFoler, fileName);
        url.append(NginxPath).append("blog").append(baseFoler).append(fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("文件上传错误,  caused by:", e);
            return Result.error(ResultCode.UPLOAD_ERROR);
        }
        return Result.success(url);
    }

}
