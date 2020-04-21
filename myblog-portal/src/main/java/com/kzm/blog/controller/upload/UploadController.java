package com.kzm.blog.controller.upload;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.utils.KblogUtils;
import com.kzm.blog.common.utils.TimeUtils;
import com.kzm.blog.mapper.user.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public Result upload(HttpServletRequest request, @RequestPart("file") MultipartFile file, @RequestParam("type") String type) {
        YearMonth now = YearMonth.now();
        String timePath = TimeUtils.YearMonthToString(now, "yyyyMM");
        File baseFoler = new File(basePath + timePath);
        StringBuffer url = new StringBuffer();
        if (!baseFoler.exists()) {
            baseFoler.mkdirs();
        }
        String fileName = UUID.fastUUID().toString().replace("-", "") + "_" + file.getOriginalFilename();
        File dest = new File(baseFoler, fileName);
        url.append(NginxPath).append("blog/")
                .append(baseFoler.getPath().substring(basePath.length()).replace("\\", "/"))
                .append("/" + fileName);
        try {
            file.transferTo(dest);
            //根据类型保存
            if (type.equals("avatar")) {
                String userName = KblogUtils.getUserName();
                Integer id = userMapper.getIdByAccount(userName);
                UserEntity userEntity = new UserEntity();
                userEntity.setAvatar(url.toString());
                userMapper.update(userEntity, new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId, id));
            }
        } catch (IOException e) {
            log.error("文件上传错误,  caused by:", e);
            return Result.error(ResultCode.UPLOAD_ERROR);
        }
        return Result.success(url);
    }

}
