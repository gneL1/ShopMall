package com.example.shopmall.useractivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopmall.R;
import com.example.shopmall.bmobdata.User;
import com.example.shopmall.fragment.MainPageActivity;
import com.example.shopmall.fragment.UserFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

import static android.app.PendingIntent.getActivity;
import static android.app.PendingIntent.getService;
import static android.content.Context.MODE_PRIVATE;

public class UserFragmentAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private int currentType = 1;






    //得到不同的类型
    public int getItemViewType(int position){
        switch (position){
            case 0:
                currentType = 0;
                break;
            case 1:
                currentType = 1;
                break;
            case 2:
                currentType = 2;
                break;
    }
        return currentType;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0){
                return new LoginViewHolder(mContext,mLayoutInflater.inflate(R.layout.user_page_account,null));
        }
        else if (i == 1){
                return  new CartOptionsViewHolder(mContext,mLayoutInflater.inflate(R.layout.user_page_cart,null));
        }
        else if (i == 2){
            return new UserListViewHolder(mContext,mLayoutInflater.inflate(R.layout.user_page_list,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
//        if (i == 0){
//            LoginViewHolder loginViewHolder = (LoginViewHolder)holder;
//            loginViewHolder.setClick();
//        }
//        else if (i == 1){
//            CartOptionsViewHolder cartOptionsViewHolder = (CartOptionsViewHolder)holder;
//            cartOptionsViewHolder.setData();
//        }
        //获得第0行，返回第0行对应的第0种类型
       if (getItemViewType(i) == 0){
                LoginViewHolder loginViewHolder = (LoginViewHolder)holder;
                loginViewHolder.setClick();
           //一定要记得初始化
           Bmob.initialize(mContext, "118dd35efdfa0d30c2f6f916fdcd5168");
           if (isLogin()) {
               SharedPreferences userchcae = mContext.getSharedPreferences("usercache", Context.MODE_PRIVATE);
               String text = userchcae.getString("name", "");
               String avator = userchcae.getString("avator","");
               System.out.println("登录成功后显示的图片路径" + avator);
               loginViewHolder.tv_name.setText(text);
               Glide.with(mContext).load(avator).into(loginViewHolder.ri_img);
           }
           else {
               loginViewHolder.tv_name.setText("登录/注册");
           }

       }
       //获得第1行，返回第1行对应的第1种类型
       else if (getItemViewType(i) == 1){
                CartOptionsViewHolder cartOptionsViewHolder = (CartOptionsViewHolder)holder;
                cartOptionsViewHolder.setData();
       }
       else if (getItemViewType(i) == 2){
                UserListViewHolder userListViewHolder = (UserListViewHolder)holder;
                userListViewHolder.serClick();
       }
    }

    //返回Item数目
    @Override
    public int getItemCount() {
        return 3;
    }


    public UserFragmentAdapter(Context context){
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //登录
    class LoginViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private ImageView ri_img;
        private TextView tv_name;





        public LoginViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ri_img = (ImageView)itemView.findViewById(R.id.Ri_img);
            tv_name = (TextView)itemView.findViewById(R.id.Tv_Name);
//            if (isLogin()){
//                User user = BmobUser.getCurrentUser(User.class);
//                tv_name.setText(user.getUsername().toString());
//            }




        }




        public void setClick(){
            ri_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (User.isLogin()) {
                        System.out.println("用户已经登陆");
                    }
                    else {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(intent);



                    }
                }
            });

            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLogin()) {
                        User user = User.getCurrentUser(User.class);
                        System.out.println("用户已经登陆");
                    }
                    else {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(intent);
                    }

                }
            });
        }
    }




    //
    class CartOptionsViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private TextView tv_allcart;
        private GridView gv_fpotions;
        private FoptionsAdapter adapter;

        public CartOptionsViewHolder(Context mContext,View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_allcart = (TextView)itemView.findViewById(R.id.Tv_Allcart);
            gv_fpotions = (GridView)itemView.findViewById(R.id.Gv_Foptions);

            //设置item的点击事件
            gv_fpotions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"点击了" + position,Toast.LENGTH_SHORT).show();
                }
            });

            //设置全部订单的点击事件
            tv_allcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"点击了全部订单",Toast.LENGTH_SHORT).show();
                }
            });
        }



        public void setData(){
            ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String, Object>>();//在数组中存放数据
            HashMap<String,Object> map1 = new HashMap<String, Object>();
            map1.put("ItemImage",R.drawable.pay);
            map1.put("ItemText","待付款");
            listItem.add(map1);
            HashMap<String,Object> map2 = new HashMap<String, Object>();
            map2.put("ItemImage",R.drawable.get);
            map2.put("ItemText","待收货");
            listItem.add(map2);
            HashMap<String,Object> map3 = new HashMap<String, Object>();
            map3.put("ItemImage",R.drawable.evaluate);
            map3.put("ItemText","待评价");
            listItem.add(map3);
            HashMap<String,Object> map4 = new HashMap<String, Object>();
            map4.put("ItemImage",R.drawable.hammer);
            map4.put("ItemText","退换修");
            listItem.add(map4);

                adapter = new FoptionsAdapter(mContext,listItem);
                gv_fpotions.setAdapter(adapter);
            }
        }


        class UserListViewHolder extends RecyclerView.ViewHolder{
            private Context mContext;
            private TextView tv_userdata;
            private TextView tv_quit;
            public UserListViewHolder(Context mContext,View itemView) {
                super(itemView);
                this.mContext = mContext;
                tv_userdata = (TextView)itemView.findViewById(R.id.Tv_Userdata);
                tv_quit = (TextView)itemView.findViewById(R.id.Tv_Quit);
            }


            public void serClick(){
                tv_userdata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isLogin()){
                        Toast.makeText(mContext,"跳转到设置界面",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext,ProfileActivity.class);
                        mContext.startActivity(intent);
                        }
                        else {
                            Toast.makeText(mContext,"请先登陆账号",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                tv_quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isLogin()){
                            BmobUser.logOut();
                            EventBus.getDefault().post(MainPageActivity.LOGOUT);


                        }
                        else {
                            Toast.makeText(mContext,"您尚未登录账号",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }


    //判断是否已经登录
    public Boolean isLogin(){
        if (User.isLogin()) {
            return true;
        }
        else {
            return false;
        }
    }

    //缓存数据
    private void fetchUserInfo(){
//        User.fetchUserInfo(new FetchUserInfoListener<User>() {
//            @Override
//            public void done(User User, BmobException e) {
//
//            }
//        });

    }



    //定义点击接口
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;
    //点击方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
