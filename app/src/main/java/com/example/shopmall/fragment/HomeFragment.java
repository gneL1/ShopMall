package com.example.shopmall.fragment;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmall.R;
import com.example.shopmall.apipackage.GetRequest_Interface;
import com.example.shopmall.apipackage.Translation;
import com.example.shopmall.homeactivity.HomeFragmentAdapter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private final static String TAG = HomeFragment.class.getSimpleName();
    //getSimpleName得到类的简写名称即HomeFragment



    private TextView tv_search_home;
    private TextView tv_message_home;
    private RecyclerView rv_home;
    private ImageView ib_top;

    private HomeFragmentAdapter adapter;

    //返回的数据
    private Translation.ResultBean resultBean;


    @Override
    public View initView() {
        Log.e(TAG,"主界面的Fragment的UI被初始化了");
        View view = View.inflate(mContext,R.layout.fragment_home,null );

        //初始化布局控件
        tv_search_home = (TextView)view.findViewById(R.id.Tv_Search_Home);
        tv_message_home = (TextView)view.findViewById(R.id.Tv_Message_Home);
        rv_home = (RecyclerView)view.findViewById(R.id.Rv_Home);
        ib_top = (ImageView)view.findViewById(R.id.Ib_Top);//或者ImageButton?

        //设置点击事件
        tv_message_home.setOnClickListener(this);
        tv_search_home.setOnClickListener(this);
        ib_top.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Ib_Top:
                //点击图片让recyclerview滚动到顶部
                rv_home.scrollToPosition(0);
                break;
            case R.id.Tv_Search_Home:
                Toast.makeText(mContext,"搜素",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Tv_Message_Home:
                Toast.makeText(mContext,"进入消息中心",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private static final String TAG1 = "Rxjava";
    private void apiConnect(){
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://192.168.106.65:8080/")    //设置网络请求Url
                                .addConverterFactory(GsonConverterFactory.create())     //设置使用Gson解析（记得加入依赖）
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //支持RxJava
                                .build();

        //创建网络请求接口的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //采用Observable<...>形式对网络请求进行封装
        Observable<Translation> observable = request.getCall();

        //发送网络请求
        observable.subscribeOn(Schedulers.io())     //在IO线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread())      //回到主线程处理请求结果
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG1,"开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Translation value) {
                        //对返回的数据进行处理
                        value.show();
                        System.out.println("---------------------------------");
                        System.out.println(value.getResult().getHot_info().get(0).getName());
                        System.out.println("-------------------------------");
                        System.out.println(Integer.valueOf(value.getResult().getSeckill_info().getEnd_time()));
                        System.out.println("-------------------------------");
                        System.out.println(Integer.valueOf(value.getResult().getSeckill_info().getStart_time()));

                        resultBean = value.getResult();

                        if (resultBean != null){
                            //有数据就设置适配器
                            adapter = new HomeFragmentAdapter(mContext,resultBean);
                            rv_home.setAdapter(adapter);

                            //spanCount为每行排列item个数
                            GridLayoutManager manager = new GridLayoutManager(mContext,1);
                            //设置跨度大小监听
                            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(int i) {
                                    if (i <= 3){
                                        //隐藏
                                        ib_top.setVisibility(View.GONE);
                                    }else {
                                        //显示
                                        ib_top.setVisibility(View.VISIBLE);
                                    }
                                    //只能返回1
                                    return 1;
                                }
                            });
                            //设置布局管理者
                            rv_home.setLayoutManager(manager);
                        }else {
                            //没有数据
                        }

                        Log.e(TAG1,"解析成功==" + resultBean.getHot_info().get(0).getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG1,"请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG1,"请求成功");
                    }
                });

    }


    @Override
    public void initData() {
        super.initData();
        apiConnect();
        Log.e(TAG1,"主页面的Fragment的数据被初始化了");

    }
}
