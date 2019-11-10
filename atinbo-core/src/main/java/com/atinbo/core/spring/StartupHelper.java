package com.atinbo.core.spring;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 打印启动信息
 *
 * @author breggor
 */
public abstract class StartupHelper {

    public static void printStartInfo(Logger logger, ConfigurableApplicationContext application) {
        try {
            Environment env = application.getEnvironment();
            logger.info("\n----------------------------------------------------------\n\t" +
                            "Application '{}' is running! Access URLs:\n\t" +
                            "Local: \t\thttp://localhost:{}\n\t" +
                            "External: \thttp://{}:{}\n\t" +
                            "Doc: \thttp://{}:{}/doc.html\n" +
                            "----------------------------------------------------------" ,
                    env.getProperty("spring.application.name"),
                    env.getProperty("server.port"),
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port"),
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
