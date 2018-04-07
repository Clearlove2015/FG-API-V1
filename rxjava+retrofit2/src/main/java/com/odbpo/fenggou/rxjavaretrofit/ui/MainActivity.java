package com.odbpo.fenggou.rxjavaretrofit.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.odbpo.fenggou.rxjavaretrofit.R;
import com.odbpo.fenggou.rxjavaretrofit.bean.AlipayPayBean;
import com.odbpo.fenggou.rxjavaretrofit.bean.LoginResponse;
import com.odbpo.fenggou.rxjavaretrofit.bean.MemberLevelBean;
import com.odbpo.fenggou.rxjavaretrofit.net.Global;
import com.odbpo.fenggou.rxjavaretrofit.net.Service;
import com.odbpo.fenggou.rxjavaretrofit.util.PayResult;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http://gank.io/post/560e15be2dca930e00da1083
 * http://gank.io/post/56e80c2c677659311bed9841
 * http://www.jianshu.com/p/308f3c54abdd
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zc_tag";

    @Bind(R.id.btn_click)
    Button btnClick;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.btn_get)
    Button btnGet;
    @Bind(R.id.btn_update_nickname)
    Button btnUpdateNickname;
    @Bind(R.id.et_update_nick)
    EditText etUpdateNick;
    @Bind(R.id.btn_update_sex)
    Button btnUpdateSex;
    @Bind(R.id.btn_goods_follow)
    Button btnGoodsFollow;
    @Bind(R.id.btn_order_number)
    Button btnOrderNumber;
    @Bind(R.id.btn_order_list)
    Button btnOrderList;
    @Bind(R.id.btn_history)
    Button btnHistory;
    @Bind(R.id.btn_point_list)
    Button btnPointList;
    @Bind(R.id.btn_category)
    Button btnCategory;
    @Bind(R.id.btn_customerinfo)
    Button btnCustomerinfo;
    @Bind(R.id.btn_order_detail)
    Button btnOrderDetail;
    @Bind(R.id.btn_select_img)
    Button btnCall;
    @Bind(R.id.btn_return_detail)
    Button btnReturnDetail;
    @Bind(R.id.btn_uploadimg)
    Button btnUploadimg;
    @Bind(R.id.btn_uploadimg_single)
    Button btnUploadimgSingle;
    @Bind(R.id.btn_logistics)
    Button btnLogistics;
    @Bind(R.id.btn_kuaidi)
    Button btnKuaidi;
    @Bind(R.id.btn_coupon)
    Button btnCoupon;
    @Bind(R.id.btn_get_coupons)
    Button btnGetCoupons;
    @Bind(R.id.btn_getaccount)
    Button btnGetaccount;
    @Bind(R.id.btn_msg_number)
    Button btnMsgNumber;
    @Bind(R.id.btn_deleted_goods)
    Button btnDeletedGoods;
    @Bind(R.id.btn_like_login)
    Button btnLikeLogin;
    @Bind(R.id.btn_wechat_login)
    Button btnWechatLogin;
    @Bind(R.id.btn_rv)
    Button btnRv;
    @Bind(R.id.btn_uploadimgs_remote)
    Button btnUploadimgsRemote;
    @Bind(R.id.btn_clear_history)
    Button btnClearHistory;
    @Bind(R.id.btn_alipay)
    Button btnAlipay;
    private SharedPreferences sp;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sp = getSharedPreferences("sp_file", Context.MODE_PRIVATE);
    }

    @OnClick({
            R.id.btn_click,
            R.id.btn_get,
            R.id.btn_update_nickname,
            R.id.btn_update_sex,
            R.id.btn_goods_follow,
            R.id.btn_order_number,
            R.id.btn_order_list,
            R.id.btn_history,
            R.id.btn_clear_history,
            R.id.btn_point_list,
            R.id.btn_category,
            R.id.btn_customerinfo,
            R.id.btn_order_detail,
            R.id.btn_select_img,
            R.id.btn_return_detail,
            R.id.btn_uploadimg,
            R.id.btn_uploadimg_single,
            R.id.btn_logistics,
            R.id.btn_kuaidi,
            R.id.btn_coupon,
            R.id.btn_get_coupons,
            R.id.btn_getaccount,
            R.id.btn_msg_number,
            R.id.btn_deleted_goods,
            R.id.btn_like_login,
            R.id.btn_wechat_login,
            R.id.btn_rv,
            R.id.btn_uploadimgs_remote,
            R.id.btn_alipay
    })
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_click:
                get_request();
                break;
            case R.id.btn_get:
                get_member_level();
                break;
            case R.id.btn_update_nickname:
                update_nickname();
                break;
            case R.id.btn_update_sex:
                updateSex();
                break;
            case R.id.btn_goods_follow:
                goodsFollow();
                break;
            case R.id.btn_order_number:
                orderNumber();
                break;
            case R.id.btn_order_list:
                orderList();
                break;
            case R.id.btn_history:
                history();
                break;
            case R.id.btn_clear_history:
                clear_history();
                break;
            case R.id.btn_point_list:
                pointList();
                break;
            case R.id.btn_category:
                category();
                break;
            case R.id.btn_customerinfo:
                getcustomerinfo();
                break;
            case R.id.btn_order_detail:
                getOrderDetail();
                break;
            case R.id.btn_select_img:
                select_img();
                break;
            case R.id.btn_return_detail:
                returnDetail();
                break;
            case R.id.btn_uploadimg:
                uploadimg();
                break;
            case R.id.btn_uploadimg_single:
                uploadimgsingle();
                break;
            case R.id.btn_uploadimgs_remote:
                uploadimgs_remote();
                break;
            case R.id.btn_logistics:
                getLogistics();
                break;
            case R.id.btn_kuaidi:
                getKuaiDi();
                break;
            case R.id.btn_coupon:
                getCoupon();
                break;
            case R.id.btn_get_coupons:
                getCoupons();
                break;
            case R.id.btn_getaccount:
                getAccount();
                break;
            case R.id.btn_msg_number:
                getMsgNumber();
                break;
            case R.id.btn_deleted_goods:
                deletedGoods();
                break;
            case R.id.btn_like_login:
                likeLogin();
                break;
            case R.id.btn_wechat_login:
                wechat_login();
                break;
            case R.id.btn_rv:
                startActivity(new Intent(MainActivity.this, XActivity.class));
                break;
            case R.id.btn_alipay:
                do_alipay();
                break;
        }

    }

    /**
     * 拨打电话（API22以上需要动态权限请求）
     */
    private void select_img() {
//        String phone = "13698362194";
//        //此处应该对电话号码进行验证。。
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        startActivity(intent);

        pickImage();
    }

    public void get_request() {

        /**
         * 单使用retrofit
         */
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://www.baidu.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//
//        Service service = retrofit.create(Service.class);
//        Call<ResponseBody> select_img = service.getBaidu();
//        select_img.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> select_img, Response<ResponseBody> response) {
//                try {
//                    tvContent.setText(response.body().string().toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> select_img, Throwable t) {
//
//            }
//        });


        /**
         * Retrofit与RxJava配合使用
         * 被观察者（Observable）-->订阅（subscribe）-->观察者（Observer & Subscriber）
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

//        service.getBaidu2()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        try {
//                            tvContent.setText(responseBody.string().toString());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });


//        service.getBaidu2()
//                .subscribeOn(Schedulers.io())//io线程
//                .observeOn(AndroidSchedulers.mainThread())//主线程
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        try {
//                            tvContent.setText(responseBody.string().toString());
//                            System.out.println("zc = " + responseBody.string().toString());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

        //添加请求参数
        RequestBody requestBody = new FormBody.Builder()
                .add("user", Global.PHONE)
                .add("password", Global.PASSWORD)
                .build();

        service.login(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {

                        SharedPreferences.Editor editor = sp.edit();
                        System.out.println("token:" + loginResponse.getToken());
                        editor.putString("token", loginResponse.getToken());
                        editor.commit();
                        tvContent.setText(loginResponse.getToken());
                    }
                });

    }

    /**
     * Android Retrofit2&OkHttp3添加统一的请求头Header
     * http://blog.csdn.net/jdsjlzx/article/details/51578231
     *
     * @param token
     * @return
     */
    public static OkHttpClient genericClient(final String token) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }

    /**
     * 请求会员级别
     */
    private void get_member_level() {

        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(genericClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

//        service.getMemberLevel()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        try {
//                            System.out.println("zc = " + responseBody.string().toString());
//                            tvContent.setText(responseBody.string().toString());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

        service.getMemberLevel2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MemberLevelBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MemberLevelBean memberLevelBean) {
                        tvContent.setText(memberLevelBean.getName());
                    }
                });
    }

    public static String mapToJson(Map map) {
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    public static OkHttpClient okHttpClient(final String token) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(request);
                    }

                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))//打印Log
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return httpClient;
    }

    /**
     * 修改昵称
     */
    private void update_nickname() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> map = new HashMap<>();
        String nick = etUpdateNick.getText().toString();
        map.put("nickName", nick);
        String json = mapToJson(map);
        System.out.println("zc_json = " + json);
        RequestBody requestBody2 = RequestBody.create(JSON, json);

//        RequestBody requestBody1 = new FormBody.Builder()
//                .add("nickName","testname")
//                .build();

//        service.updateNickName(requestBody2)
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> select_img, retrofit2.Response<ResponseBody> response) {
//                        try {
//                            if(response.body()!=null){
//                                String resutl = response.body().string().toString();
//                                Log.i("json_response = ",resutl);
//                            }else{
//                                Log.i(TAG,"response = null");
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> select_img, Throwable t) {
//                        Log.i(TAG,t.getMessage());
//                    }
//                });

        service.updateNickName(requestBody2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            if (responseBody != null) {
                                if (responseBody.string().toString().equals("1")) {
                                    Toast.makeText(MainActivity.this, "昵称修改成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 修改性别
     */
    public void updateSex() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> map = new HashMap<>();
        map.put("genderFlag", "1");
        String json = mapToJson(map);
        System.out.println("zc_json = " + json);
        RequestBody requestBody2 = RequestBody.create(JSON, json);

        service.updateSex(requestBody2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Log.i(TAG, responseBody.string().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 我的关注列表
     */
    private void goodsFollow() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("size", "10");
        map.put("pageNum", "0");
        map.put("region", "3052");

        service.goodsFollow(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 请求订单数量
     */
    private void orderNumber() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        service.order_number()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 请求全部订单
     */
    private void orderList() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("pageNum", "0");
        map.put("pageSize", "100");

        service.getOrderList("", map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 浏览历史
     */
    public void history() {
        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("size", "10");
        map.put("PageNum", "2");
        map.put("region", "3052");

        service.history(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 清空浏览历史
     */
    private void clear_history() {
        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        service.clear_history()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 积分列表
     */
    private void pointList() {
        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("type", 1 + "");
        map.put("pageNum", 1 + "");
        map.put("pageSize", 10 + "");

        service.pointList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 分类
     */
    private void category() {
        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("recursion ", "true");

        service.getProductCategory(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 获取用户信息
     */
    private void getcustomerinfo() {
        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        service.getCustomer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 根据订单号查询物流信息
     */
    private void getLogistics() {
        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        String orderId = "1097";

        service.getLogisticsInfo(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * kuaidi100查询
     */
    private void getKuaiDi() {
        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        service.getKuaiDi("debangwuliu", "00000000")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 订单详情
     */
    private void getOrderDetail() {
        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        String orderId = "3638";

        service.getOrderDetail(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void returnDetail() {
        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        String orderId = "4130";
        String isBack = "1";

        service.getReturnDetail(orderId, isBack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getCoupon() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        service.getCoupon("545")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 优惠券
     */
    private void getCoupons() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("codeStatus", "1");
        service.getCoupons(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 账户明细
     */
    private void getAccount() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("pageNum", "0");
        map.put("pageSize", "10");
        service.getAccount("0", map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 消息数量
     */
    private void getMsgNumber() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        service.getMsgNumber()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void likeLogin() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        service.likeLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static RequestBody toRequestBody(String body) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
    }

    /**
     * 删除商品
     */
    private void deletedGoods() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("goodsInfoIds", "[1160,741,25]");
        service.deletedGoods(toRequestBody(mapToJson(map)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static RequestBody toFormDataRequestBody(Map map) {
        return getStringRequestBody(map);
    }

    /**
     * 添加文本请求体参数
     */
    @SuppressWarnings("rawtypes")
    public static RequestBody getStringRequestBody(Map<?, ?> stringBodyMap) {
        FormBody.Builder multipartBodyBuilder = new FormBody.Builder();
        if ((stringBodyMap == null) || (stringBodyMap.size() <= 0)) {
            return null;
        }
        if ((stringBodyMap == null) || (stringBodyMap.size() <= 0)) {
            return null;
        }
        /*解析文本请求体*/
        if ((stringBodyMap != null) && (stringBodyMap.size() > 0)) {
            Iterator iterator = stringBodyMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                multipartBodyBuilder.add(entry.getKey() + "", entry.getValue() + "");
            }
        }
        return multipartBodyBuilder.build();
    }

    private void wechat_login() {
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("unionid", "oK7A-0g55Ub_to2CSxkw1GgsTeog");
        service.wechat_login(toFormDataRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
//                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(MainActivity.this,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
//                    } else {
//                        // 其他状态值则为授权失败
//                        Toast.makeText(MainActivity.this,
//                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
//
//                    }
//                    break;
//                }
                default:
                    break;
            }
        };
    };

    private void do_alipay(){
        String token = sp.getString("token", "token is null");
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        //传入实际支付参数
        map.put("orderCode", "201804071616295");
        map.put("payUserIP","192.168.1.103");
        String json = mapToJson(map);
        RequestBody requestBody = toRequestBody(json);
        service.alipay(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AlipayPayBean>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AlipayPayBean tempAlipayPayBean) {
                        AlipayPayBean alipayPayBean = tempAlipayPayBean;
                        if (alipayPayBean.getMessage().length() != 0) {
                            //  final String orderInfo = "app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D";   // 订单信息
                            String orderInfo = alipayPayBean.getData();   // 订单信息
                            Runnable payRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(MainActivity.this);
                                    Map<String, String> result = alipay.payV2(orderInfo, true);
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }
                    }
                });

    }

    private void uploadimgsingle() {
        String path = "/storage/emulated/0/DCIM/IMG_186834310.jpg";
        File file = new File(path);

        String token = sp.getString("token", "token is null");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .client(okHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("image", file.getName(), photoRequestBody);

        service.upLoadImgSingle(photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public class FormHeaderInterceptor implements Interceptor {
        String token;

        public FormHeaderInterceptor(String token) {
            this.token = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = new Request.Builder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Content-Type", "multipart/form-data")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(request);
        }
    }

    private void uploadimg() {
        String token = sp.getString("token", "token is null");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new FormHeaderInterceptor(token))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Global.API_PATH)
                .baseUrl("http://10.33.133.21:8080")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();

        Service service = retrofit.create(Service.class);

        List<File> files = new ArrayList<>();
        for (String path : mSelectPath) {
            File file = new File(path);
            files.add(file);
        }

        List<MultipartBody.Part> parts = filesToMultipartBodyParts(files);

        service.upLoadImgList(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public class MultiHeaderInterceptor implements Interceptor {
        String token;

        public MultiHeaderInterceptor(String token) {
            this.token = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "multipart/form-data;charset=utf-8")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(request);
        }
    }

    private void uploadimgs_remote() {
        String token = sp.getString("token", "token is null");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MultiHeaderInterceptor(token))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
//                .baseUrl("http://10.33.133.21:8080")
                .addConverterFactory(GsonConverterFactory.create())//转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .client(okHttpClient)
                .build();

        Service service = retrofit.create(Service.class);

        List<File> files = new ArrayList<>();
        for (String path : mSelectPath) {
            File file = new File(path);
            files.add(file);
        }

        List<MultipartBody.Part> parts = filesToMultipartBodyParts(files);

        service.upLoadImgsRemote(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string().toString();
                            Log.i(TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            // MultipartBody.Part和后端约定好Key，这里的partName是用mFile
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("multipart", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    //==============================================================================================
    private ArrayList<String> mSelectPath;

    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            MultiImageSelector selector = MultiImageSelector.create(MainActivity.this);
            selector.showCamera(true);//是否显示相机，默认显示
            selector.count(9);//最大选择图片数量，默认为9
            selector.multi();//选择多张图片
            selector.origin(mSelectPath);// 默认已选择图片. 只有在选择模式为多选时有效
            selector.start(MainActivity.this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);//选择图片的路径

            }
        }
    }

}
