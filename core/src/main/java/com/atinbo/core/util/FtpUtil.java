package com.atinbo.core.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


/**
 * @author fangqy
 */
public class FtpUtil {
    private final static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    protected static FTPClient getClient(FtpConfig ftpConfig) throws Exception {
        FTPClient ftpClient = new FTPClient();
        String ftpPortStr = ftpConfig.getFtpPort();
        if (StringUtils.isBlank(ftpPortStr)) {
            throw new Exception("ftp_port为空");
        }
        String ftpPwd = ftpConfig.getFtpPwd();
        if (StringUtils.isBlank(ftpPwd)) {
            throw new Exception("ftp_password为空");
        }
        byte[] pwdByte = Base64.getDecoder().decode(ftpPwd);

        String password = new String(pwdByte);

        String ftpIp = ftpConfig.getFtpIp();
        if (StringUtils.isBlank(ftpIp)) {
            throw new Exception("ftp_ip为空");
        }

        String ftpUser = ftpConfig.getFtpUser();
        if (StringUtils.isBlank(ftpUser)) {
            throw new Exception("ftp_user为空");
        }

        ftpClient.connect(ftpIp, Integer.valueOf(ftpPortStr));
        boolean login = ftpClient.login(ftpUser, password);
        if (login) {
            logger.info("ftp 登录成功..........");
        }
        return ftpClient;
    }

    protected static void closeClient(FTPClient client) throws Exception {
        logger.info("ftp disconnect.......");
        client.disconnect();
    }

    protected static boolean changeWorkingDir(FTPClient ftpClient, String path) throws Exception {
        boolean opt = ftpClient.changeWorkingDirectory(path);
        if (!opt) {
            String[] dirs = path.split("/");
            StringBuilder currDir = new StringBuilder();
            for (String dir : dirs) {
                currDir.append(dir).append("/");
                opt = ftpClient.changeWorkingDirectory(dir);
                if (!opt) {
                    if (ftpClient.makeDirectory(dir)) {
                        opt = ftpClient.changeWorkingDirectory(dir);
                    }
                }
            }
            logger.info("创建路径：currDir={}", currDir.toString());
        }
        return opt;
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                StringBuffer tempPath = new StringBuffer();
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath.append(dir);
                    tempPath.append("/");
                    if (!ftp.changeWorkingDirectory(tempPath.toString())) {
                        ftp.makeDirectory(dir);
                        ftp.changeWorkingDirectory(dir);
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param host       FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return
     */
//    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
//                                       String fileName, String localPath) {
//        boolean result = false;
        /*FTPClient ftp = new FTPClient();
        try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}*/
//        return result;
//    }

//    public static void main(String[] args) {
//        try (FileInputStream in = new FileInputStream(new File("D:\\1.jpg"))){
//            Date dateTime = new Date();
//            String filePath = "images" + DateUtil.dateToStringImage(dateTime);
//            String imageName = IDUtils.genImageName();
//            boolean flag = uploadFile("172.17.0.10", 21, "vir_devstatic", "ogYHXdKdRLXI", "", filePath, imageName + ".png", in);
//            System.out.println(flag);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
