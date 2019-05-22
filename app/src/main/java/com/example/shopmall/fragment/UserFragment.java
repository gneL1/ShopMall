package com.example.shopmall.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shopmall.R;
import com.example.shopmall.useractivity.UserFragmentAdapter;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;


public class UserFragment extends BaseFragment {
    private RecyclerView rv_user;
    private UserFragmentAdapter adapter;
    private static final String TAG = "User";


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.user_page_activity,null);

        rv_user = (RecyclerView)view.findViewById(R.id.Rv_User);

        Log.e(TAG,"用户布局初始化成功");
        return view;
    }



    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"用户界面加载成功");
        useradapter();

    }




    public void useradapter(){

        adapter = new UserFragmentAdapter(mContext);
        rv_user.setAdapter(adapter);




//        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        GridLayoutManager manager = new GridLayoutManager(mContext,1);

        //使用线性布局
//        rv_user.setLayoutManager(layoutManager);

        rv_user.setLayoutManager(manager);
        rv_user.setHasFixedSize(true);


    }










}
