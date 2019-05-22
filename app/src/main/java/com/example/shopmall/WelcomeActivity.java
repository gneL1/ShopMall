package com.example.shopmall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.shopmall.fragment.MainPageActivity;
import com.example.shopmall.utils.CacheUtils;

import cn.bmob.v3.Bmob;

public class WelcomeActivity extends AppCompatActivity {
    //    传参：postDelayed中需要传两个参数，一个是Runnable对象，一个是以毫秒为单位的时间
//    作用：postDelayed经常被用于延时任务，或者定时器。
//    mHandler.removeCallbacks(runnable);清除定时器


    //    Handler mainHandler;
//    Handler workHandler;
//    HandlerThread mHandlerThread;
//    @Bind(R.id.Btn_Wlc)
//    Button BtnWlc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);




        //定义一个handle对象，3秒后执行线程
        Handler handler = new Handler();
        //停顿3秒，执行runnable这个线程
        handler.postDelayed(runnable, 3000);

    }

    //定义一个Handler要执行的线程
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                startActivity(new Intent(WelcomeActivity.this, MainPageActivity.class));
                //结束当前页面
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}


//        ButterKnife.bind(this);
//
//
//        //创建与主线程相关联的Handler
//        mainHandler = new Handler();
//
//        //步骤一：创建HandlerThread实例对象
//        mHandlerThread = new HandlerThread("welcome");
//
//        //步骤二：启动线程
//        mHandlerThread.start();
//
//        //步骤三：创建工作线程Handler  复写handleMessage()
//        workHandler = new Handler(mHandlerThread.getLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 1:
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        startMainActivity();
//
//                        mHandlerThread.quit();
////                        finish();
//                        break;
////                    case 2:
////                        mHandlerThread.quit();
////                        finish();
////                        break;
//                }
//            }
//        };
//
//        Message msg = Message.obtain();
//        msg.what = 1;
//        msg.obj = "A";   //这个参数可以去掉？？？？？？？？？
//        workHandler.sendMessage(msg);
//    }
//
//
//    private void startMainActivity(){
//        startActivity(new Intent(WelcomeActivity.this,MainPageActivity.class));
//        finish();
//    }
//
//
//    protected void onDestoty(){
//        super.onDestroy();
//        workHandler.removeCallbacksAndMessages(null);
//
//    }
//
//    @OnClick(R.id.Btn_Wlc)
//    public void onViewClicked() {
//        mHandlerThread.interrupt();
////        Message msg1 = Message.obtain();
////        msg1.what = 2;
////        msg1.obj = "B";
////        workHandler.sendMessage(msg1);
//        mHandlerThread.quit();
//        onDestoty();
//        startMainActivity();
//
//        //       finish();//关闭当前页面
//
//
//
//    }
