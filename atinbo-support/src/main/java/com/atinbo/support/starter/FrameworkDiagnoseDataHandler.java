//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrameworkDiagnoseDataHandler extends Handler {
    public FrameworkDiagnoseDataHandler() {
    }

    @Override
    public void handle(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            this.next.handle(url, request, response);
        } finally {
            response.addHeader("req_no", LoggerHelper.getReqNo());
        }

    }
}
