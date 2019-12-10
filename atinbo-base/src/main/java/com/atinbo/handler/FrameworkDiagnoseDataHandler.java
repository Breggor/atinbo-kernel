package com.atinbo.handler;

import com.atinbo.utils.LogUtils;

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
            response.addHeader("req_no" , LogUtils.getReqNo());
        }

    }
}
