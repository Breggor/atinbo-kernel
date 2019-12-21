//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public abstract class HttpRequestUtils {
    private static final Gson GSON = new Gson();

    public static String readData(HttpServletRequest request) {
        try (BufferedReader br = request.getReader()) {
            StringBuilder result = new StringBuilder();

            for (String line = br.readLine(); line != null; ) {
                result.append(line).append("\n");
            }
            return result.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void flushJson(HttpServletResponse response, byte[] jsonBytes) {
        response.setCharacterEncoding("UTF-8");

        try (ServletOutputStream out = response.getOutputStream()) {
            out.write(jsonBytes);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void flushJson(HttpServletResponse response, Object record) {
        response.setContentType("application/json");
        LogUtils.pushResponseData(GSON.toJson(record));
        byte[] jsonBytes = GSON.toJson(record).getBytes();
        flushJson(response, jsonBytes);
    }
}
