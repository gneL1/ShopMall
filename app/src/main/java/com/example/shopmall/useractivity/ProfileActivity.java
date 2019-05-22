package com.example.shopmall.useractivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopmall.MainActivity;
import com.example.shopmall.R;
import com.example.shopmall.bmobdata.User;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ProfileActivity extends TakePhotoActivity {
    @Bind(R.id.Tv_Left_Hp)
    TextView TvLeftHp;
    @Bind(R.id.Tv_Left_Name)
    TextView TvLeftName;
    @Bind(R.id.Tv_Left_Sex)
    TextView TvLeftSex;
    @Bind(R.id.Tv_Left_Email)
    TextView TvLeftEmail;
    @Bind(R.id.Tv_Left_Pwd)
    TextView TvLeftPwd;
    @Bind(R.id.Img_Touxiang)
    RoundedImageView ImgTouxiang;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page__person_profile);
        ButterKnife.bind(this);
        Bmob.initialize(this, "118dd35efdfa0d30c2f6f916fdcd5168");


        //申请相关权限
        initPermission();

        //设置压缩，裁剪参数
        initData();

        setImgTouXiang();

    }

    //设置头像
    private void setImgTouXiang(){
        SharedPreferences userchcae =getSharedPreferences("usercache", Context.MODE_PRIVATE);
        String avator = userchcae.getString("avator","");
        Glide.with(this).load(avator).into(ImgTouxiang);
    }


    @OnClick({R.id.Tv_Left_Hp, R.id.Tv_Left_Name, R.id.Tv_Left_Sex, R.id.Tv_Left_Email, R.id.Tv_Left_Pwd})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.Tv_Left_Hp:
                imageUri = getImageCropUri();
                takePhoto.onPickFromGalleryWithCrop(imageUri,cropOptions);
                break;
            case R.id.Tv_Left_Name:
                intent = new Intent(ProfileActivity.this, SetNameActivity.class);
                break;
            case R.id.Tv_Left_Sex:
                break;
            case R.id.Tv_Left_Email:
                break;
            case R.id.Tv_Left_Pwd:
                intent = new Intent(ProfileActivity.this, SetNewPasswordActivity.class);
                break;
        }
        if (intent !=null) {
            startActivity(intent);
        }
    }


    //头像设置

    //TakePhotos
    private TakePhoto takePhoto;

    //裁剪参数
    private CropOptions cropOptions;

    //压缩参数
    private CompressConfig compressConfig;

    //图片保存路径
    private Uri imageUri;


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String iconPath = result.getImage().getOriginalPath();
        System.out.println("图片是什么类型" + result.getImage().getFromType());
        //Toast显示图片路径
        Toast.makeText(this,"imagePath:" + iconPath,Toast.LENGTH_SHORT).show();
        //Google Glide库用于加载图片资源
        Glide.with(this).load(iconPath).into(ImgTouxiang);

        //上传头像
        if (User.isLogin()) {

            updateAvator(iconPath);

        }
        else {
            Log.e("TAG","请先登录账号");
        }


    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Toast.makeText(ProfileActivity.this,"Error:" + msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }







    private void initData() {
        ////获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        //设置压缩参数
        compressConfig=new CompressConfig.Builder().setMaxSize(50*1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig,true);  //设置为需要压缩
    }

    private void initPermission() {
        //申请权限
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale((context, permissions, executor) -> {
                    //此处可以选择显示提示弹窗
                    executor.execute();
                })
                //用户给权限了
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(ProfileActivity.this,"申请到了权限",Toast.LENGTH_SHORT).show();
                    }
                })
                //用户拒绝权限，包括不再显示权限弹窗也在此列
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(ProfileActivity.this,"未申请到权限",Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

    }




    //获得照片的输出保存Uri
    public Uri getImageCropUri() {
        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }


    private void updateAvator(String iconPath){
        User user = BmobUser.getCurrentUser(User.class);
        BmobFile bmobFile = new BmobFile(new File(iconPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //返回的上传文件的完整地址
                    System.out.println("上传成功" + bmobFile.getFileUrl());
                    user.setAvatar(bmobFile);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                System.out.println("更新头像成功");
                                System.out.println("用文字来表达头像" + user.getAvatar().getFileUrl());
                            } else {
                                System.out.println("更新头像失败");
                                Log.e("error", e.getMessage());
                            }
                        }
                    });

                }
                else {
                    System.out.println("上传失败");
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                //返回上传的进度
            }
        });





//        user.setAvatar(bmobFile);
//        user.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null){
//                    Toast.makeText(ProfileActivity.this,"头像上传成功",Toast.LENGTH_SHORT).show();
//                    System.out.println("头像上传成功");
//                }
//                else {
//                    Toast.makeText(ProfileActivity.this,"头像上传失败",Toast.LENGTH_SHORT).show();
//                    Log.e("error", e.getMessage());
//                    System.out.println("头像上传失败");
//                }
//            }
//        });
    }

}
