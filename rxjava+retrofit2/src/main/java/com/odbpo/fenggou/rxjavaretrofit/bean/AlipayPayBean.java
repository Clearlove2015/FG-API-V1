package com.odbpo.fenggou.rxjavaretrofit.bean;

import java.io.Serializable;

/**
* @desc 支付宝支付调用接口返回实体
* @author DXZ
* @date 2018/1/12 10:18
*/
public class AlipayPayBean implements Serializable{


    /**
     * code : string
     * data : string
     * message : string
     */

    private String code;
    private String data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
