package com.odbpo.fenggou.rxjavaretrofit.net;

import com.odbpo.fenggou.rxjavaretrofit.bean.AlipayPayBean;
import com.odbpo.fenggou.rxjavaretrofit.bean.LoginResponse;
import com.odbpo.fenggou.rxjavaretrofit.bean.MemberLevelBean;
import com.odbpo.fenggou.rxjavaretrofit.bean.OrderListBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author: zc
 * @Time: 2017/11/14 9:10
 * @Desc:
 */
public interface Service {

    @GET("/")
    Call<ResponseBody> getBaidu();

    @GET("/")
    Observable<ResponseBody> getBaidu2();

    @POST(Global.LOGIN)
    Observable<LoginResponse> login(@Body RequestBody requestBody);

    @GET(Global.CUSTOMERS)
    Observable<ResponseBody> getCustomer();

//    @POST(Global.MEMBER_LEVEL)
//    Observable<ResponseBody> getMemberLevel();

    @POST(Global.MEMBER_LEVEL)
    Observable<MemberLevelBean> getMemberLevel2();

//    @PUT(Global.UPDATE_NICKNAME)
//    Call<ResponseBody> updateNickName(@Body RequestBody requestBody);

    @PUT(Global.UPDATE_NICKNAME)
    Observable<ResponseBody> updateNickName(@Body RequestBody requestBody);

    @PUT(Global.REAL_NAME)
    Observable<ResponseBody> updateRealName(@Body RequestBody requestBody);

    @PUT(Global.UPDATE_SEX)
    Observable<ResponseBody> updateSex(@Body RequestBody requestBody);

    @GET(Global.GOODS_FOLLOW)
    Observable<ResponseBody> goodsFollow(@QueryMap Map<String,String> maps);

    @GET(Global.ORDER_NUMBER)
    Observable<ResponseBody> order_number();

    @GET(Global.ORDER_GOODSLIST)
    Observable<ResponseBody> getOrderList(@Path("status") String status, @QueryMap Map<String, String> maps);

    @GET(Global.HISTORY_LIST)
    Observable<ResponseBody> history(@QueryMap Map<String, String> maps);

    @DELETE(Global.CLEAR_HISTORY_LIST)
    Observable<ResponseBody> clear_history();

    @GET(Global.POINT_LIST)
    Observable<ResponseBody> pointList(@QueryMap Map<String,String> maps);

    @GET(Global.PRODUCT_CATEGORY)
    Observable<ResponseBody> getProductCategory(@QueryMap Map<String, String> maps);

    @GET(Global.ORDER_DETAIL)
    Observable<ResponseBody> getOrderDetail(@Path("orderId") String orderId);

    @GET(Global.RETURN_DETAIL)
    Observable<ResponseBody> getReturnDetail(@Path("orderId") String orderId,@Path("isBack") String isBack);

    @Multipart
    @POST(Global.UPLOAD_IMG_TEST)
    Observable<ResponseBody> upLoadImgList(@Part List<MultipartBody.Part> parts);

    @Multipart
    @POST(Global.UPLOAD_IMG)
    Observable<ResponseBody> upLoadImgArr(@Part("image") MultipartBody.Part[] parts);

    @Multipart
    @POST(Global.UPLOAD_IMG)
    Observable<ResponseBody> upLoadImgMap(@PartMap Map<String,List<MultipartBody.Part>> maps);

    @Multipart
    @POST(Global.UPLOAD_IMG_SINGLE)
    Observable<ResponseBody> upLoadImgSingle(@Part MultipartBody.Part part);

    @Multipart
    @POST(Global.UPLOAD_IMG)
    Observable<ResponseBody> upLoadImgsRemote(@Part List<MultipartBody.Part> parts);

    @GET(Global.GET_LOGISTICS_INFO)
    Observable<ResponseBody> getLogisticsInfo(@Path("orderId") String param);

    @GET(Global.GET_KUAIDI)
    Observable<ResponseBody> getKuaiDi(@Query("type") String param1, @Query("postid") String param2);

    @GET(Global.GET_COUPON)
    Observable<ResponseBody> getCoupon(@Path("goodsInfoId") String param);

    @GET(Global.GET_COUPONS)
    Observable<ResponseBody> getCoupons(@QueryMap Map<String,String> maps);

    @GET(Global.GET_ACCOUNT)
    Observable<ResponseBody> getAccount(@Path("status") String status,@QueryMap Map<String,String> maps);

    @GET(Global.MSG_NUMBER)
    Observable<ResponseBody> getMsgNumber();

    @HTTP(method = "DELETE",path = Global.DELETED_GOODS,hasBody = true)
    Observable<ResponseBody> deletedGoods(@Body RequestBody requestBody);

    @GET(Global.LIKE_LOGIN)
    Observable<ResponseBody> likeLogin();

    @POST(Global.WECHAT_LOGIN)
    Observable<ResponseBody> wechat_login(@Body RequestBody requestBody);

    @GET(Global.ORDER_GOODSLIST)
    Observable<OrderListBean> getRvOrderList(@Path("status") String status, @QueryMap Map<String, String> maps);

    @POST(Global.ALIPAY)
    Observable<AlipayPayBean> alipay(@Body RequestBody requestBody);
}
