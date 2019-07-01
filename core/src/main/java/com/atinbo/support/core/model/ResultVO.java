package com.atinbo.support.core.model;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class ResultVO implements IResult, Serializable {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    /**
     * 状态码
     */
    private String code;
    /**
     * 前台提示信息
     */
    private String msg;
    /**
     * 后台错误信息
     */
    private String errorMsg;
    /**
     * 业务数据
     */
    private Object data;
    /**
     * 请求失败返回数据
     */
    private Object errorData;

    private ResultVO(String code, String msg, String errorMsg) {
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
    }

    public ResultVO(Object data) {
        this.code = SUCCESS;
        this.msg = null;
        this.data = data;
    }

    public static ResultVO data(Object data) {
        return new ResultVO(data);
    }

    public static ResultVO success() {
        return new ResultVO(SUCCESS, null, null);
    }

    public static ResultVO failure() {
        return new ResultVO(FAILURE, null, null);
    }

    public static ResultVO succFail(boolean result) {
        return result ? success() : failure();
    }

    public static ResultVO msg(String msg) {
        return new ResultVO(FAILURE, msg, null);
    }

    public static ResultVO msg(String code, String msg) {
        return new ResultVO(code, msg, null);
    }

    public static ResultVO errorMsg(String errorMsg) {
        return new ResultVO(FAILURE, null, errorMsg);
    }

    public static ResultVO errorMsg(String code, String errorMsg) {
        return new ResultVO(code, null, errorMsg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getErrorData() {
        return errorData;
    }

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }

    //    public static ResultVO dataBoolean(boolean result) {
//    	Map<String, Boolean> returnMap = new HashMap<String, Boolean>();
//    	returnMap.put("result", result);
//    	return data(returnMap);
//    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("code", code)
                .append("msg", msg)
                .append("data", data)
                .toString();
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }
}
