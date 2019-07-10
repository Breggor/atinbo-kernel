package com.atinbo.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * 获取异常栈工具类
 *
 * @author breggor
 */
public class Exceptions {

    /**
     * 获取异常的堆栈信息
     *
     * @param ex
     * @return
     */
    public static String getStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            ex.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
