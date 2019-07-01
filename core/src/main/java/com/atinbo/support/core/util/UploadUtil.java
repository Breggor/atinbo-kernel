package com.atinbo.support.core.util;


import org.apache.commons.io.IOUtils;

import java.io.InputStream;

/**
 * 文件上传工具类
 *
 * @author robin on 2016年5月3日
 */
public class UploadUtil {


    public static void main(String[] args) {
        //生成静态文件
        String des = "<p><img class=\"maxImg\" src=\"http://product2-10025267.image.myqcloud.com/86ce8baa-c959-4fc2-bf95-11cc9c065f28\" title=\"QQ截图20170505114338.jpg\" alt=\"QQ截图20170505114338.jpg\"/><img class=\"maxImg\" src=\"http://product2-10025267.image.myqcloud.com/bee3b871-cbaf-4972-8b41-73a893223390\" title=\"QQ截图20170505114352.jpg\" alt=\"QQ截图20170505114352.jpg\"/><img class=\"maxImg\" src=\"http://product2-10025267.image.myqcloud.com/8d48c6dc-f652-471a-b98f-5274fb844919\" title=\"QQ截图20170505114401.jpg\" alt=\"QQ截图20170505114401.jpg\"/></p>";
        FtpConfig ftp = new FtpConfig();
        ftp.setFtpHead("http://pre-static.525happy.cn/");
        ftp.setFtpIp("192.168.10.31");
        ftp.setFtpPort("9581");
        ftp.setFtpUser("vir_prestcjava");
        ftp.setFtpPwd("TWlyWmlaYm5RVzFI");


        try {
            String staticUrl = UploadUtil.uploadProduct(
                    java.net.URLDecoder.decode(des, "UTF-8"), ftp);
            System.out.println("staticUrl = " + staticUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传商品
     *
     * @param content   功效内容
     * @param ftpConfig ftp配置
     * @return 文件url地址
     * @throws Exception
     */
    public static String uploadProduct(String content, FtpConfig ftpConfig) throws Exception {
        InputStream inputStream = GenerateStatic.class.getClassLoader().getResourceAsStream("commodity_detail.html");
        try {
            String template = GenerateStatic.readFile(inputStream);
            String outputPath = "data/ftp/bcpapp/product/" + GenerateStatic.datePath();
            return GenerateStatic.generateFile(template, content, outputPath, ftpConfig);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ftp上传失败", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 上传文章
     *
     * @param content   文章内容
     * @param ftpConfig ftp配置
     * @return 文件url地址
     * @throws Exception
     */
    public static String uploadArticle(String content, FtpConfig ftpConfig) throws Exception {
        InputStream inputStream = GenerateStatic.class.getClassLoader().getResourceAsStream("article_detail.html");
        try {
            String template = GenerateStatic.readFile(inputStream);
            String outputPath = "data/ftp/bcpapp/article/" + GenerateStatic.datePath();
            String uploadPath = GenerateStatic.generateFile(template, content, outputPath, ftpConfig);
            return uploadPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ftp上传失败", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 上传话题
     *
     * @param content   话题内容
     * @param ftpConfig ftp配置
     * @return 文件url地址
     * @throws Exception
     */
    public static String uploadTopic(String content, FtpConfig ftpConfig) throws Exception {
        InputStream inputStream = GenerateStatic.class.getClassLoader().getResourceAsStream("article_detail.html");
        try {
            String template = GenerateStatic.readFile(inputStream);
            String outputPath = "data/ftp/bcpapp/topic/" + GenerateStatic.datePath();
            String uploadPath = GenerateStatic.generateFile(template, content, outputPath, ftpConfig);
            return uploadPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ftp上传失败", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


    public static String uploadStaicFile(String content, FtpConfig ftpConfig, String fileName) throws Exception {
        String uploadPath = GenerateStatic.uploadStaicFile(content, ftpConfig, fileName);
        return uploadPath;

    }

    /**
     * 上传小心机
     *
     * @param content
     * @param ftpConfig
     * @return
     * @throws Exception
     */
    public static String uploadSolution(String content, FtpConfig ftpConfig) throws Exception {
        InputStream inputStream = GenerateStatic.class.getClassLoader().getResourceAsStream("cheats_detail.html");
        try {
            String template = GenerateStatic.readFile(inputStream);
            String outputPath = "data/ftp/bcpapp/solution/" + GenerateStatic.datePath();
            String uploadPath = GenerateStatic.generateFile(template, content, outputPath, ftpConfig);
            return uploadPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ftp上传失败", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
