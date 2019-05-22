package com.example.shopmall.useractivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopmall.R;
import com.example.shopmall.bmobdata.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class SetNewPasswordActivity extends AppCompatActivity {
    @Bind(R.id.Iv_img)
    ImageView IvImg;
    @Bind(R.id.Et_Now_Password)
    EditText EtNowPassword;
    @Bind(R.id.Et_New_Password1)
    EditText EtNewPassword1;
    @Bind(R.id.Et_New_Password2)
    EditText EtNewPassword2;
    @Bind(R.id.Btn_Sure)
    Button BtnSure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page_set_newpassword);
        ButterKnife.bind(this);
        Bmob.initialize(this, "118dd35efdfa0d30c2f6f916fdcd5168");
    }

    @OnClick({R.id.Iv_img, R.id.Btn_Sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Iv_img:
                finish();
                break;
            case R.id.Btn_Sure:
                break;
        }
    }

    //提供旧密码修改密码
    private void updatePassword(String oldPwd,String newPwd){
        Bmob.initialize(this, "118dd35efdfa0d30c2f6f916fdcd5168");
        User.updateCurrentUserPassword(oldPwd, newPwd, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    Toast.makeText(SetNewPasswordActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                }
                else {

                }
            }
        });

    }
}
