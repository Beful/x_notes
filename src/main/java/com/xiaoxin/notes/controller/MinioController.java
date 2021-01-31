package com.xiaoxin.notes.controller;

import com.xiaoxin.notes.dto.MinioUploadDto;
import com.xiaoxin.notes.entity.FileEntity;
import com.xiaoxin.notes.mapper.FileDao;
import com.xiaoxin.notes.utils.R;
import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created on 2021/1/11.
 *
 * @author XiaoXinZai
 */
@Api(tags = "MinioController", description = "Minio管理")
@Controller
@RequestMapping("/minio")
public class MinioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioController.class);
    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;
    @Autowired
    private FileDao fileDao;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public R upload(@RequestParam("file") MultipartFile file) {
        try {
            //创建一个MinIO的Java客户端
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            boolean isExist = minioClient.bucketExists(BUCKET_NAME);
            if (isExist) {
                LOGGER.info("存储桶已经存在！");
            } else {
                //创建存储桶并设置只读权限
                minioClient.makeBucket(BUCKET_NAME);
                minioClient.setBucketPolicy(BUCKET_NAME, "*.*", PolicyType.READ_ONLY);
            }
            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 设置存储对象名称
            String objectName = sdf.format(new Date()) + "/" + filename;
            // 使用putObject上传一个文件到存储桶中
            minioClient.putObject(BUCKET_NAME, objectName, file.getInputStream(), file.getContentType());

            // 生成文件记录
            FileEntity fileEntity = new FileEntity();
            fileEntity.setOwnCode("关联id");
            fileEntity.setOrgName(filename);
            fileEntity.setFileName(UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf(".")));
            fileEntity.setFtpPath(objectName);
            fileEntity.setCreateTime(new Date());
            fileEntity.setFileFormat(file.getContentType());
            fileEntity.setFileDes("文件描述");
            fileEntity.setFileType(file.getContentType());
            fileEntity.setFileMd5("Md5值");
            fileEntity.setFileSize(String.valueOf(file.getSize()));
            fileDao.insert(fileEntity);

            LOGGER.info("文件上传成功!");
            MinioUploadDto minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(filename);
            minioUploadDto.setUrl(ENDPOINT + "/" + BUCKET_NAME + "/" + objectName);
            return R.ok(minioUploadDto);
        } catch (Exception e) {
            LOGGER.info("上传发生错误: {}！", e.getMessage());
        }
        return R.failure(R.State.ERR_Error,"文件上传失败！");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public R delete(@PathVariable String id) {
        try {
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            minioClient.removeObject(BUCKET_NAME, id);
            return R.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.failure(R.State.ERR_Error,"文件删除失败！");
    }
}
