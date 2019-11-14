package com.atinbo.oss.processor;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.atinbo.common.id.UUID;
import com.atinbo.core.utils.FileUtil;
import com.atinbo.core.utils.IoUtil;
import com.atinbo.oss.config.OssProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author zenghao
 * @date 2019-11-13
 */
@Slf4j
@Component
@AllArgsConstructor
public class OssProcessor {

    private OSS ossClient;
    private OssProperties ossProperties;

    /**
     * 创建Bucket
     *
     * @param bucketName
     */
    public void createBucket(String bucketName) {
        boolean exist = ossClient.doesBucketExist(bucketName);
        if (exist) {
            log.warn("The bucket exist.");
            return;
        }
        ossClient.createBucket(bucketName);
    }

    /**
     * 文件上传到阿里OSS
     *
     * @param inputStream 文件流
     * @param oldName 原始文件名
     * @return 上传后文件路径
     */
    public String upload(InputStream inputStream, String oldName) {
        if (inputStream == null || StringUtils.isEmpty(oldName)) {
            return null;
        }

        //设置新的文件名
        String ext = FileUtil.getFileExtension(oldName);
        String lastName = String.format("%s.%s", UUID.randomUUID(), ext);
        String filePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/")) + lastName;
        try {

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            //设置ContentLength
            meta.setContentLength(inputStream.available());
            log.debug("开始上传：{} 文件至 OSS", oldName);
            PutObjectResult putObjectResult = ossClient.putObject(ossProperties.getBucketName(), filePath, inputStream, meta);
            log.debug("【{}】上传成功,返回信息为：{},新的文件名为：{}", oldName, putObjectResult.getETag(), lastName);
        } catch (IOException e) {
            log.error("【{}】上传到OSS失败,失败原因为：{}", oldName, e);
            return null;
        }
        return filePath;
    }

    /**
     * 下载
     * @param filePath 文件路径
     * @param outputStream 输出流
     * @return
     */
    public boolean download(String filePath, OutputStream outputStream) {
        try {
            OSSObject ossObject = ossClient.getObject(ossProperties.getBucketName(), filePath);
            IoUtil.copy(ossObject.getObjectContent(), outputStream);
            return true;
        } catch (Exception e) {
            log.error("【{}】下载失败,失败原因为：{}", filePath, e);
            return false;
        }
    }

    /**
     * 删除
     * @param filePath 文件路径
     * @return
     */
    public boolean delete(String filePath) {
        try {
            ossClient.deleteObject(ossProperties.getBucketName(), filePath);
            return true;
        } catch (Exception e) {
            log.error("【{}】删除失败,失败原因为：{}", filePath, e);
            return false;
        }
    }
}