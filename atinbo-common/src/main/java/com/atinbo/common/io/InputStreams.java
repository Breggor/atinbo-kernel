package com.atinbo.common.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public abstract class InputStreams {

    /**
     * 从输入流中读取数据
     *
     * @param input
     * @return
     * @throws Exception
     */
    public static String readInputStream(InputStream input, String encode) throws Exception {
        return new String(InputStreams.readInputStream(input), encode);
    }

    /**
     * 从输入流中读取数据
     *
     * @param input
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream input) throws Exception {
        byte[] data;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()){
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            data = output.toByteArray();
        } finally {
            input.close();
        }
        return data;
    }
}
