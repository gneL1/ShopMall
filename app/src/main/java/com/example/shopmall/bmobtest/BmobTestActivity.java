package com.example.shopmall.bmobtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopmall.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BmobTestActivity extends AppCompatActivity {
    @Bind(R.id.Btn_add)
    Button BtnAdd;
    @Bind(R.id.Btn_Delete)
    Button BtnDelete;
    @Bind(R.id.Btn_Get)
    Button BtnGet;
    @Bind(R.id.Btn_Update)
    Button BtnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmob_test_activity);
        ButterKnife.bind(this);
        Bmob.initialize(this, "fc8656088debc4298292e989dae05e80");

    }

    private void show(String msg){
        Toast.makeText(BmobTestActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    //对Person类初始化和赋值
    Person person = new Person();

    private void addPerson(){
        person.setAddress("address");
        person.setName("name");
        person.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                //Bmob sdk对云端数据库的操作通过异步的形式
                //会异步回调到done方法中
                if (e != null){
                    Toast.makeText(BmobTestActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BmobTestActivity.this,"成功添加数据",Toast.LENGTH_SHORT).show();
                    System.out.println(s);
                }
            }
        });
    }

    private void queryPerson(){
        BmobQuery<Person> query = new BmobQuery<>();

        query.getObject("b6b94776e7", new QueryListener<Person>() {
            @Override
            public void done(Person person, BmobException e) {
                //第一个参数是查询成功后返回的对象
                if (e == null){
                    show(person.toString());
                }
            }
        });
    }

    private void updatePerson(){
        person.setName("denny");
        person.update("f062aaeb5f", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    show("修改成功");
                    System.out.println(person.toString());
                }
            }
        });
    }

    private void deletePerson(){
        person.setObjectId("f062aaeb5f");
        person.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    show("删除成功");
                }
            }
        });
    }


    @OnClick({R.id.Btn_add, R.id.Btn_Delete, R.id.Btn_Get, R.id.Btn_Update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_add:
                addPerson();
                break;
            case R.id.Btn_Delete:
                deletePerson();
                break;
            case R.id.Btn_Get:
                queryPerson();
                break;
            case R.id.Btn_Update:
                updatePerson();
                break;
        }
    }
}
