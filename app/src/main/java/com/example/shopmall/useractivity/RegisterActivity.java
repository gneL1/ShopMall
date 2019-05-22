package com.example.shopmall.useractivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopmall.R;
import com.example.shopmall.bmobdata.User;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

public class RegisterActivity extends AppCompatActivity {
    @Bind(R.id.Btn_Register)
    Button BtnRegister;
    @Bind(R.id.Et_Register_Username)
    EditText EtRegisterUsername;
    @Bind(R.id.Et_Register_Password1)
    EditText EtRegisterPassword1;
    @Bind(R.id.Et_Register_Password2)
    EditText EtRegisterPassword2;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register_page);
        ButterKnife.bind(this);
        Bmob.initialize(this, "118dd35efdfa0d30c2f6f916fdcd5168");

        Listener();
    }


    @SuppressLint("CheckResult")
    private void Listener() {

//         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher），需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
//         * 2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值（下面会详细说明）
//         * 3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值

        Observable<CharSequence> nameObservable = RxTextView.textChanges(EtRegisterUsername);
        Observable<CharSequence> passwordObservable1 = RxTextView.textChanges(EtRegisterPassword1);
        Observable<CharSequence> passwordObservable2 = RxTextView.textChanges(EtRegisterPassword2);

        //通过combineLatest（）合并事件 & 联合判断
        Observable.combineLatest(nameObservable, passwordObservable1, passwordObservable2, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
                //规定输入信息不能为空
                boolean isUserNameValid = !TextUtils.isEmpty(EtRegisterUsername.getText());

                boolean isUserPasswordValid1 = !TextUtils.isEmpty(EtRegisterPassword1.getText());

                boolean isUserPasswordValid2 = !TextUtils.isEmpty(EtRegisterPassword2.getText());


                //返回信息=联合判断，即三个信息同时填写，提交按钮才可以点击
                return isUserNameValid && isUserPasswordValid1 && isUserPasswordValid2;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            //对给定的参数执行定义的操作
            public void accept(Boolean aBoolean) throws Exception {
                BtnRegister.setEnabled(aBoolean);
                //依据判断结果设置按钮显示颜色
                //不是空值
                if (aBoolean) {
                    BtnRegister.setBackgroundResource(R.drawable.user_round_corner);
                }
                //是空值
                else {
                    BtnRegister.setBackgroundResource(R.drawable.user_round_corner_un);
                }
//                System.out.println(EtRegisterPassword1.getText());
//                System.out.println(EtRegisterPassword2.getText());
            }
        });
    }


    @OnClick(R.id.Btn_Register)
    public void onClick() {
        //点击先验证密码是否一致，Bmob会自动验证用户名是否已存在
        if (EtRegisterPassword1.getText().toString().equals(EtRegisterPassword2.getText().toString())) {
            signUp(EtRegisterUsername.getText().toString(),EtRegisterPassword1.getText().toString());
        }
        else {
            Toast.makeText(RegisterActivity.this,"密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
        }
    }


    User user = new User();

    private void signUp(String name, String password) {
//        final User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    System.out.println("注册成功");
                } else {
                    Toast.makeText(RegisterActivity.this,"该用户名已存在,请重新输入",Toast.LENGTH_SHORT).show();
                    System.out.println("注册失败");
                }
            }
        });
    }

    private void volidateName() {
        BmobQuery<User> query = new BmobQuery<>();
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    System.out.println(list.get(3).getUsername());
                    System.out.println(list.size());
                    ArrayList names = new ArrayList();
                    for (int i = 0;i < list.size();i++){
                        names.add(list.get(i).getUsername());
                        System.out.println(list.get(i).getUsername());
                    }
                    System.out.println(names.toString());


                } else {
                    System.out.println("查询失败");
                }
            }
        });
    }



}
