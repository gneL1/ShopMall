package com.example.shopmall.useractivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shopmall.R;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetNameActivity extends AppCompatActivity {
    @Bind(R.id.Iv_img)
    ImageView IvImg;
    @Bind(R.id.Et_Name)
    EditText EtName;
    @Bind(R.id.Btn_Sure)
    Button BtnSure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page_set_name);
        ButterKnife.bind(this);
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






}
