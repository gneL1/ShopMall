package com.example.shopmall.useractivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopmall.R;
import com.example.shopmall.bmobdata.User;
import com.example.shopmall.fragment.MainPageActivity;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;


public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.Et_Username)
    EditText EtUsername;
    @Bind(R.id.Iv_Nameclear)
    ImageView IvNameclear;
    @Bind(R.id.Et_Userpassword)
    EditText EtUserpassword;
    @Bind(R.id.Iv_Passwordclear)
    ImageView IvPasswordclear;
    @Bind(R.id.Cb_Remem)
    CheckBox CbRemem;
    @Bind(R.id.Btn_Register)
    Button BtnRegister;
    @Bind(R.id.Btn_Login)
    Button BtnLogin;

    private Boolean check;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_page);
        ButterKnife.bind(this);
        Bmob.initialize(this, "118dd35efdfa0d30c2f6f916fdcd5168");


        ListenerEdit();
        Listener();
        openPageCheck();

    }


    @OnClick(R.id.Btn_Register)
    public void onClick1() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    @SuppressLint("CheckResult")
    //监听输入框来判断是否显示清空视图
    private void ListenerEdit() {
//         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher）
        RxTextView.textChanges(EtUsername).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                IvNameclear.setVisibility(TextUtils.isEmpty(EtUsername.getText()) ? View.INVISIBLE : View.VISIBLE);
            }
        });

        RxTextView.textChanges(EtUserpassword).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                IvPasswordclear.setVisibility(TextUtils.isEmpty(EtUserpassword.getText()) ? View.INVISIBLE : View.VISIBLE);
            }
        });
    }

    private int SCP;

    private void setValue(int scp) {
        this.SCP = scp;
    }

    private int getValue() {
        return SCP;
    }


    //每次打开页面进行一次判断
    private void openPageCheck(){
        //拿到保存着记录是否记住密码的SharedPreferences
        SharedPreferences checkshare = getSharedPreferences("check", Context.MODE_PRIVATE);
        Boolean value = checkshare.getBoolean("check", false);
        if (value) {
            SharedPreferences userchcae = getSharedPreferences("usercache", Context.MODE_PRIVATE);
            String name = userchcae.getString("name", "");
            String password = userchcae.getString("password", "");
            EtUsername.setText(name);
            EtUserpassword.setText(password);
            CbRemem.setChecked(true);
        }
    }


    //对记住密码设置监听事件，只要没选中就把SharedPreferences设置为false
    @OnClick(R.id.Cb_Remem)
    public void onClickCheck() {
        if(CbRemem.isChecked()){

        }
        else {
            chechShared(false);
        }
    }

    //用SharedPreferences记录是否选中记住密码
    private void chechShared(Boolean check) {
        SharedPreferences checkshare = getSharedPreferences("check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = checkshare.edit();
        editor.putBoolean("check", check);
        editor.commit();
    }


    @SuppressLint("CheckResult")
    private void Listener() {
        Observable<CharSequence> nameObservable = RxTextView.textChanges(EtUsername).skip(1);
        Observable<CharSequence> passwordObservable = RxTextView.textChanges(EtUserpassword).skip(1);

        //特别注意BiFunction即为对应的Function2
        Observable.combineLatest(nameObservable, passwordObservable, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2) throws Exception {
                boolean isUserNameValid = !TextUtils.isEmpty(EtUsername.getText());
                boolean isUserPasswordValid = !TextUtils.isEmpty(EtUserpassword.getText());

                return isUserNameValid && isUserPasswordValid;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    setValue(1);
                } else {
                    setValue(0);
                }
            }
        });
    }

//
//    @SuppressLint("CheckResult")
//    private void Listener() {
//        Observable<CharSequence> nameObservable = RxTextView.textChanges(EtUsername).skip(1);
//        Observable<CharSequence> passwordObservable = RxTextView.textChanges(EtUserpassword).skip(1);
//        Observable<CharSequence> nullObsservable = RxTextView.textChanges(CbRemem).skip(1);
//
//        Observable.combineLatest(nameObservable, passwordObservable, nullObsservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
//            @Override
//            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
//                boolean isUserNameValid = !TextUtils.isEmpty(EtUsername.getText());
//                boolean isUserPasswordValid = !TextUtils.isEmpty(EtUserpassword.getText());
//
//                return isUserNameValid && isUserPasswordValid;
//            }
//        }).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                if (aBoolean) {
//                    BtnLogin.setEnabled(true);
////                    onClickLogin();
//                    setValue(1);
//
//
//                } else {
//                    setValue(0);
//
//                }
//
//            }
//        });
//    }


    @OnClick(R.id.Btn_Login)
    public void onClick() {
        int value = getValue();
        if (value == 1) {

            loginByAccount(EtUsername.getText().toString(), EtUserpassword.getText().toString());
            System.out.println("输入了账号和密码的SCP" + SCP);
        } else {
            Toast.makeText(LoginActivity.this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
//        if (!TextUtils.isEmpty(EtUsername.getText()) && !TextUtils.isEmpty(EtUserpassword.getText())){
//            loginByAccount(EtUsername.getText().toString(), EtUserpassword.getText().toString());
//            System.out.println("输入了账号和密码的SCP" + SCP);
//        }
//        else {
//            Toast.makeText(LoginActivity.this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
//            System.out.println("没输入账号和密码的SCP" + SCP);
        }
//        if (value == 0) {
//            Toast.makeText(LoginActivity.this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
//        }
//        else if (value == 1){
//            loginByAccount(EtUsername.getText().toString(), EtUserpassword.getText().toString());
//        }

//        System.out.println("点击的SCP = " + SCP);
    }


    private void loginByAccount(String name, String password) {
//        User user = new User();
//        user.setUsername(name);
//        user.setPassword(password);
        BmobUser.loginByAccount(name, password, new LogInListener<User>() {
            //        User.login(new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    System.out.println("登陆成功");
                    if (CbRemem.isChecked()) {
                        chechShared(true);
                    } else {
                        chechShared(false);
                    }

                    //跳转到用户界面
                    userCache(user.getUsername(), EtUserpassword.getText().toString(),user.getAvatar().getFileUrl().toString());
                    EventBus.getDefault().post(MainPageActivity.LOGOUT);
                    finish();
//                    Intent intent = new Intent(LoginActivity.this, UserFragment.class);
//                    startActivity(intent);
//                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //缓存用户数据到本地SharedPreferences
    private void userCache(String name, String password,String avator) {
        SharedPreferences userchcae = getSharedPreferences("usercache", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userchcae.edit();
        editor.putString("name", name);
        editor.putString("password", password);
        editor.putString("avator",avator);
//        String avator =
        editor.commit();
    }


    //点击清空视图清空输入内容
    @OnClick({R.id.Iv_Nameclear, R.id.Iv_Passwordclear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Iv_Nameclear:
                EtUsername.setText("");
                break;
            case R.id.Iv_Passwordclear:
                EtUserpassword.setText("");
                break;
        }
    }


}
