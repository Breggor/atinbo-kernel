//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.utils;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author breggor
 */
public class LogUtils {
    public static final String X_REQ_NO = "X-Req-No";
    public static final String X_REMOTE = "X-REMOTE";
    public static final String X_RESPONSE_DATA = "X-RESPONSE-DATA";
    public static final String LOG_FILE_PATH = "LOG-FILE-PATH";
    public static final String X_HTTP_STATUS = "X_HTTP_STATUS";
    public static final String LOG_MAX_HISTORY = "LOG-MAX-HISTORY";

    public LogUtils() {
    }

    public static void pushLogFilePath(String logFilePath) {
        String property = System.getProperty("LOG-FILE-PATH");
        if (StringUtils.isEmpty(property)) {
            System.setProperty("LOG-FILE-PATH", logFilePath);
        }

    }

    public static void pushLogMaxHistory(String logMaxHistory) {
        String property = System.getProperty("LOG-MAX-HISTORY");
        if (StringUtils.isEmpty(property)) {
            System.setProperty("LOG-MAX-HISTORY", logMaxHistory);
        }

    }

    public static void pushRemoteAddr(String remoteAddr) {
        MDC.put("X-REMOTE", remoteAddr);
    }

    public static void pushReqNo(String reqNo) {
        MDC.put("X-Req-No", StringUtils.isEmpty(reqNo) ? UUID.randomUUID().toString() : reqNo);
    }

    public static void pushResponseData(String dataStr) {
        MDC.put("X-RESPONSE-DATA", dataStr);
    }

    public static void pushHttpStatus(String status) {
        MDC.put("X_HTTP_STATUS", status);
    }

    public static String getRemoteAddr() {
        String result = MDC.get("X-REMOTE");
        return result == null ? "" : result;
    }

    public static String getReqNo() {
        String result = MDC.get("X-Req-No");
        return result == null ? "" : result;
    }

    public static String getResponseData() {
        String result = MDC.get("X-RESPONSE-DATA");
        return result == null ? "" : result;
    }

    public static String getHttpStatus() {
        String result = MDC.get("X_HTTP_STATUS");
        return result == null ? "" : result;
    }

    public static void release() {
        MDC.remove("X-RESPONSE-DATA");
        MDC.remove("X-REMOTE");
        MDC.remove("X-Req-No");
        MDC.remove("X_HTTP_STATUS");
    }
}
