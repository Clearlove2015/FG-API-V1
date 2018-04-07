package com.odbpo.fenggou.rxjavaretrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author: zc
 * @Time: 2017/11/13 16:40
 * @Desc:
 */
public interface MovieService {
    @GET("/")//
    Call<ResponseBody> getTopMovie();
}
