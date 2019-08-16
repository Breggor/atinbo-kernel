//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.handler;


import com.atinbo.config.AppConfig;
import com.atinbo.constants.UrlPatternType;
import com.atinbo.utils.CommonUtils;
import com.atinbo.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class LoggerHandler extends Handler {

    public LoggerHandler(String... patternConsts) {
        super(patternConsts);
    }

    public LoggerHandler(UrlPatternType patternType, String... patternConsts) {
        super(patternType, patternConsts);
    }

    @Override
    public void handle(String url, HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        this.configLogAttr(request);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        try {
            this.printRequestLog(request);
            this.next.handle(url, request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("<= <<Response-Http-Status>> : {}", response.getStatus());
            log.info("<= <<Response-Content-Type>> : {}", LogUtils.getResponseData().length() > 0 ? "application/json" : "NULL");
            this.printResponseLog();
            log.info("<= <<Time-Taken>> : {}", System.currentTimeMillis() - startTime);
            log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            LogUtils.release();
        }

    }

    private void configLogAttr(HttpServletRequest request) {
        String remoteAddr = LogUtils.getRemoteAddr();
        LogUtils.pushRemoteAddr(remoteAddr);
        String requestId = null;

//        try {
//            requestId = RequestMediaResolver.getGatewayRequestId(request);
//        } catch (RequestMediaNotFoundException var5) {
//            requestId = UUID.randomUUID().toString();
//        }

        LogUtils.pushReqNo(requestId);
    }

    private void printResponseLog() {
        if (AppConfig.APP_ENV.getDebug()) {
            System.out.println("----- <<Response Body>> ----------------------------------");
            System.out.println(LogUtils.getResponseData());
            System.out.println("--------------------------------------------------------");
        } else {
            log.info("<= {}", LogUtils.getResponseData());
        }

    }

    private void printRequestLog(HttpServletRequest request) throws IOException {
        String requestURI = request.getRequestURI();
        StringBuilder stringBuilder = new StringBuilder();
        if (request.getQueryString() != null) {
            requestURI = requestURI + "?" + request.getQueryString();
        }

        log.info("=> <<{}>> {}", request.getMethod(), requestURI);
        if (request.getContentType() != null) {
            if (request.getContentType().contains("application/x-www-form-urlencoded")) {
                Map<String, String[]> parameterMap = request.getParameterMap();
                parameterMap.forEach((key, value) -> {
                    stringBuilder.append(key).append(" => ").append(value.length > 1 ? "\"" + value[0] + "\"(more)" : "\"" + value[0] + "\"").append("; ");
                });
            }

            if (request.getContentType().contains("multipart/form-data")) {
                stringBuilder.append(request.getContentLength() / 1000).append("KB");
            }

            if (request.getContentType().contains("application/json") || request.getContentType().contains("application/xml")) {
                String requestBody = CommonUtils.readInputStreamToString(request.getInputStream()).replace("\r", "").replace("\n", "");
                stringBuilder.append(requestBody);
            }

            if (!AppConfig.APP_ENV.getDebug()) {
                log.info("=> <<Request-Content-Type>> : {}", request.getContentType());
                log.info("=> {}", stringBuilder.toString());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("----- Request Content-Type : ").append(request.getContentType()).append(" ---");
                int headLength = sb.length();
                sb.append("\n");
                String[] var6 = stringBuilder.toString().split(";");
                int var7 = var6.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    String s = var6[var8];
                    if (!StringUtils.isEmpty(s.trim())) {
                        sb.append(s.trim()).append("\n");
                    }
                }

                sb.append("\n");

                for (int i = 0; i < headLength; ++i) {
                    sb.append("-");
                }

                System.out.println(sb.toString());
            }
        }

    }
}
