package com.odbpo.fenggou.rxjavaretrofit.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.odbpo.fenggou.rxjavaretrofit.net.Global;
import com.odbpo.fenggou.rxjavaretrofit.ui.adapter.MyAdapter;
import com.odbpo.fenggou.rxjavaretrofit.R;
import com.odbpo.fenggou.rxjavaretrofit.net.Service;
import com.odbpo.fenggou.rxjavaretrofit.bean.OrderListBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class XActivity extends AppCompatActivity {

    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private MyAdapter mAdapter;
    private ArrayList<String> listData = new ArrayList<>();

    SharedPreferences sp;

    int pageNum = 0;
    boolean isRefresh = false;
    boolean isLoadMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x);
        ButterKnife.bind(this);

        sp = getSharedPreferences("sp_file", Context.MODE_PRIVATE);

        mRecyclerView = (XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mRecyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
        mRecyclerView.addHeaderView(header);

        mRecyclerView.getDefaultFootView().setLoadingHint("自定义加载中提示");
        mRecyclerView.getDefaultFootView().setNoMoreHint("自定义加载完毕提示");

        final int itemLimit = 10;

        mRecyclerView.setLoadingListener(listener);

        mAdapter = new MyAdapter(listData);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.refresh();//启动刷新
    }

    XRecyclerView.LoadingListener listener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pageNum = 0;
                    isRefresh = true;
                    getList(pageNum);
                    pageNum++;
                }
            }, 1000);
        }

        @Override
        public void onLoadMore() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isRefresh = false;
                    isLoadMore = true;
                    getList(pageNum);
                    pageNum++;
                }
            }, 1000);
        }
    };

    public void getList(int pageNum) {

        String token = sp.getString("token", "token is null");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.API_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient(token))
                .build();

        Service service = retrofit.create(Service.class);

        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("pageSize", "10");
        service.getRvOrderList("", map)//全部订单->"",待付款->"0",待收货->"2",待评价->"unappraised",退款/退货->"19"
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderListBean>() {
                    @Override
                    public void onCompleted() {
                        //Toast.makeText(XActivity.this,"onCompleted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(XActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(OrderListBean orderListBean) {
                        Toast.makeText(XActivity.this, "page=" + pageNum, Toast.LENGTH_SHORT).show();
                        ArrayList<String> list = new ArrayList<>();
                        List<OrderListBean.DataBean> data = orderListBean.getData();
                        for (OrderListBean.DataBean bean : data) {
                            String orderCode = bean.getOrderCode();
                            list.add("page" + pageNum + "-" + orderCode);
                        }

                        //如果是下拉刷新
                        if (isRefresh) {
                            listData.clear();
                            listData.addAll(list);
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete();//刷新完成
                        }

                        //如果是上拉加载
                        if (isLoadMore) {
                            isRefresh = false;
                            if (list.size() < 10) {//没有更多数据
                                //Toast.makeText(XActivity.this, "size=" + list.size(), Toast.LENGTH_SHORT).show();
                                listData.addAll(list);
                                Toast.makeText(XActivity.this, "total=" + listData.size(), Toast.LENGTH_SHORT).show();
                                mRecyclerView.setNoMore(true);
                                mAdapter.notifyDataSetChanged();
                                isLoadMore = false;
                            } else {//上拉加载
                                listData.addAll(list);
                                mRecyclerView.loadMoreComplete();
                                mAdapter.notifyDataSetChanged();
                                isLoadMore = true;
                            }
                        }
                    }
                });

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
}
