package com.odbpo.fenggou.rxjavaretrofit.bean;

import okhttp3.RequestBody;

/**
 * @author: zc
 * @Time: 2017/11/24 17:30
 * @Desc:
 */
public class CInformation {
    RequestBody requestBody;

    public CInformation(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
}
