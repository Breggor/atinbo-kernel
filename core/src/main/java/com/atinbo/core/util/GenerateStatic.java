/**
 *
 */
package com.atinbo.core.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author zhuhp
 */
public class GenerateStatic {
    final static Logger logger = LoggerFactory.getLogger(GenerateStatic.class);

    /**
     * 通过模板生成文件
     *
     * @param template   模板
     * @param content    要替换的字符串组
     * @param outputPath 生成的文件保存路径
     * @param ftpConfig  生成的文件保存路径
     * @throws Exception
     */
    public static String generateFile(String template, String content, String outputPath, FtpConfig ftpConfig) throws Exception {
        if (StringUtils.isBlank(template)) {
            throw new RuntimeException("模版文件读取失败");
        }
        String contents = replaceContents(template, content);
        String fileName = UUID.randomUUID() + ".html";

        if (StringUtils.isBlank(contents)) {
            throw new RuntimeException("生成html内容为空");
        }

        File file = createFile(fileName, contents);
        if (!file.exists()) {
            throw new RuntimeException("创建文件失败");
        }
        String uploadPath = upload(file, outputPath, ftpConfig);
        if (StringUtils.isBlank(uploadPath)) {
            throw new RuntimeException("获取ftp上传文件URL失败");
        }
        return uploadPath;
    }

    /**
     * 通过模板生成文件(小心机专属)
     *
     * @throws Exception
     */
    public static String generateFile(InputStream inputStream, String content, String topImg, String outputPath, FtpConfig ftpConfig) throws Exception {
        String contents = readFile(inputStream);

        if (StringUtils.isBlank(contents)) {
            throw new Exception("模版文件读取失败");
        }
        contents = replaceContentsAndImg(contents, topImg, content);
        String fileName = UUID.randomUUID() + ".html";

        File file = createFile(fileName, contents);
        String uploadPath = upload(file, outputPath, ftpConfig);
        return uploadPath;
    }

    /**
     * 读取文件内容
     *
     * @param inputStream 文件绝对路径名称
     */
    public static String readFile(InputStream inputStream) {
        StringBuilder contents = new StringBuilder();
        String str = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));) {
            while ((str = br.readLine()) != null) {
                contents.append(str);
            }
            return contents.toString();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 替换文件内容（替换的内容部分前后都需要使用##包围）
     *
     * @param template 要替换的文件内容
     * @param content  用于替换的字符串数组
     * @return
     */
    public static String replaceContents(String template, String content) {
        template = template.replaceAll(("##content##"), content);
        return template;
    }

    /**
     * 替换文件内容（替换的内容部分前后都需要使用##包围）
     *
     * @param contents 要替换的文件内容
     * @param topImg   要替换的图片
     * @param content  用于替换的字符串数组
     * @return
     */
    public static String replaceContentsAndImg(String contents, String topImg, String content) {
        contents = contents.replaceAll(("##content##"), content);
        contents = contents.replaceAll(("##topImg##"), topImg);
        return contents;
    }


    /**
     * 创建指定内容和指定名称的文件
     *
     * @param name     要创建的文件路径
     * @param contents 要保存的文件内容
     */
    public static File createFile(String name, String contents) {
        File file = new File(name);
        FileOutputStream fos = null;
        OutputStreamWriter write = null;
        BufferedWriter writer = null;
        try {
            fos = new FileOutputStream(file);
            write = new OutputStreamWriter(fos, "UTF-8");
            writer = new BufferedWriter(write);
            writer.write(contents);
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage(), e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage(), e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                IOUtils.closeQuietly(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("file absolutePath:{}", file.getAbsolutePath());
        return file;
    }

    /**
     * 判断文件夹是否存在
     *
     * @param pathName 文件夹路径
     * @return
     */
    public static Boolean isExistsDirectory(String pathName) {
        File file = new File(pathName);
        return file.isDirectory() && file.exists();
    }

    /**
     * 创建文件夹
     *
     * @param pathName
     */
    public static void createDirectory(String pathName) {
        File file = new File(pathName);
        // 当前是文件夹且不存在
        if (!isExistsDirectory(pathName)) {
            file.mkdirs();
        }
    }


    /**
     * 根据日期创建路径
     *
     * @return
     */
    public static String datePath() {
        Calendar cal = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        sb.append(cal.get(Calendar.YEAR));
        sb.append("/");
        sb.append(cal.get(Calendar.MONTH) + 1);
        sb.append("/");
        sb.append(cal.get(Calendar.DAY_OF_MONTH));
        sb.append("/");
        return sb.toString();
    }

    public static String upload(File file, String uploadPath, FtpConfig ftpConfig) throws Exception {
        return upload(file, uploadPath, ftpConfig, file.getName());
    }

    public static String upload(File file, String uploadPath, FtpConfig ftpConfig, String fileName) throws Exception {
        FTPClient ftpClient = FtpUtil.getClient(ftpConfig);
        int reply = ftpClient.getReplyCode();
        boolean loginSuceccs = FTPReply.isPositiveCompletion(reply);
        if (loginSuceccs == false) {
            FtpUtil.closeClient(ftpClient);
            throw new Exception("FTP帐号或密码错误");
        }

        try (InputStream inputStream = new FileInputStream(file);) {
            boolean updir = FtpUtil.changeWorkingDir(ftpClient, uploadPath);
            if (!updir) {
                logger.info("上传路径是否存在:(true/false),结果={}", updir);
                return "";
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            boolean status = ftpClient.storeFile(file.getName(), inputStream);
            if (status) {
                StringBuffer result = new StringBuffer(ftpConfig.getFtpHead());
                result.append(uploadPath);
                result.append(file.getName());
                return result.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage(), e);
            throw new RuntimeException("ftp上传失败", e);
        } finally {
            FtpUtil.closeClient(ftpClient);
            if (file != null) {
                file.delete();
            }
        }
        return "";
    }


    public static String upload(byte[] content, String uploadPath, FtpConfig ftpConfig, String fileName) throws Exception {
        FTPClient ftpClient = FtpUtil.getClient(ftpConfig);
        int reply = ftpClient.getReplyCode();
        boolean loginSuceccs = FTPReply.isPositiveCompletion(reply);
        if (loginSuceccs == false) {
            FtpUtil.closeClient(ftpClient);
            throw new Exception("FTP帐号或密码错误");
        }
        ByteArrayInputStream is = null;
        try {
            is = new ByteArrayInputStream(content);
            FtpUtil.changeWorkingDir(ftpClient, uploadPath);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            boolean status = ftpClient.storeFile(fileName, is);
            if (status) {
                StringBuffer result = new StringBuffer(ftpConfig.getFtpHead());
                result.append(uploadPath);
                result.append(fileName);
                return result.toString();
            }
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("ftp上传失败", e);
        } finally {
            FtpUtil.closeClient(ftpClient);
            IOUtils.closeQuietly(is);
        }
        return "";
    }

    /**
     * 读取用户协议
     *
     * @param content
     * @param ftpConfig
     * @return
     * @throws Exception
     */
    public static String uploadStaicFile(String content, FtpConfig ftpConfig, String fileName) throws Exception {
        File file = createFile(fileName, content);
        String uploadPath = upload(file, "", ftpConfig);
        return uploadPath;
    }

}
