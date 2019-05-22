package com.example.shopmall.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopmall.CommunityActivity.CommunityAdapter;
import com.example.shopmall.R;
import com.example.shopmall.publicview.onLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommunityFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView rv_simple;
    private SwipeRefreshLayout srl_simple;

    private List<String> listData = new ArrayList<>();

    private CommunityAdapter adapter;

    private void initList(){
        for (int i = 0 ; i < 30 ;i++){
            listData.add("这是第" + i + "行");
        }
    }

    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.community_activity, null);
        rv_simple = view.findViewById(R.id.Rv_Simple);
        srl_simple = view.findViewById(R.id.Srl_Simple);
        //给SrlSimple设置下拉刷新监听器
        srl_simple.setOnRefreshListener(this);
        //设置下拉刷新布局的进度圆圈颜色
        srl_simple.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark);


//        textView = new TextView(mContext);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextSize(25);;
        //       return textView;


        return view;

    }


    @Override
    public void initData() {
        super.initData();
//        textView.setText("发现");
        getData();

        adapter = new CommunityAdapter(mContext,listData);
        rv_simple.setAdapter(adapter);
//        GridLayoutManager manager = new GridLayoutManager(SwipeRefreshActivity.this,1);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rv_simple.setLayoutManager(manager);
        rv_simple.setHasFixedSize(true);

        setRefreshListener();


    }

    //一旦在下拉刷新布局内部往下拉动页面，就出发下拉监听器的onRefresh方法
    @Override
    public void onRefresh() {
        //延迟若干秒后启动刷新任务
        mHandler.postDelayed(mRefresh,1000);
//        getData("refresh");
    }

    //声明一个处理器对象
    private Handler mHandler = new Handler();

    //定义一个刷新任务
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            //结束下拉刷新布局的刷新动作
            srl_simple.setRefreshing(false);
        }
    };


    private void setRefreshListener(){
        rv_simple.addOnScrollListener(new onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(adapter.LOADING);
                if (listData.size() < 52){
                    //模拟获取网络数据，延时1秒
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
                                    adapter.setLoadState(adapter.LOADING_COMPLETE);
                                }
                            });
                        }
                    },1000);
                }
                else {
                    //显示加载到底的提示
                    adapter.setLoadState(adapter.LOADING_END);
                }
            }
//            @Override
//            protected void onLoading(int countItem, int lastItem) {
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getData("loadMore");
//                    }
//                },1000);
//            }
        });
    }


    private void getData(){
        for (int i = 0; i < 7; i++){
            listData.add("此处是第" + i + "行数据");

        }
    }

//    private int count = 0;
//
//    private void getData(final String type){
//        if ("refresh".equals(type)){
//
//        }
//        else {
//            for (int i = 0;i < 5;i++){
//                count += 1;
//                listData.add("这是第" + count + "行");
//            }
//        }
//        adapter.notifyDataSetChanged();
//        if (SrlSimple.isRefreshing()){
//            SrlSimple.setRefreshing(false);
//        }
//        if ("refresh".equals(type)){
//            Toast.makeText(getApplicationContext(),"刷新完毕",Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"加载完毕",Toast.LENGTH_SHORT).show();
//        }
//
//    }



}
