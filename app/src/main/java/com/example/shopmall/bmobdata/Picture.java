package com.example.shopmall.bmobdata;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Picture extends BmobObject {


    //头像
    private BmobFile Avator;

    public Picture(){

    }

    public Picture(BmobFile file){
        this.Avator = file;
    }

    public BmobFile getAvator() {
        return Avator;
    }

    public void setAvator(BmobFile avator) {
        Avator = avator;
    }

}
