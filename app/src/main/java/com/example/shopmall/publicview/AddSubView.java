package com.example.shopmall.publicview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.TintTypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shopmall.R;

import butterknife.Bind;
import butterknife.OnClick;

public class AddSubView extends RelativeLayout implements View.OnClickListener {

    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;

    //默认值为1
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;



    private Context mContext;

    public AddSubView(Context context){
        this(context,null);
    }

    public AddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        this.mContext = context;
        initView();
        initData(attrs);
    }

    public AddSubView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }


//    public AddSubView(Context context,AttributeSet attrs){
//        this(context,attrs,0);
//        this.mContext = context;
//
//        //初始化布局
//        initView();
//
//        //填充初始数据
//       initData(attrs);
//
//
//
//    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.add_sub_view,this,true);
        iv_add = (ImageView)findViewById(R.id.Iv_Add);
        iv_sub = (ImageView)findViewById(R.id.Iv_Sub);
        tv_value = (TextView)findViewById(R.id.Tv_Value);
    }




    private void initData(AttributeSet attrs) {
        iv_add.setOnClickListener(this);
        iv_sub.setOnClickListener(this);
        if (attrs != null){
//            //取出属性
//            @SuppressLint("RestrictedApi") TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(mContext,attrs,R.styleable.AddSubView);
//            @SuppressLint("RestrictedApi") int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 1);
//            if (value > 0) {
//                setValue(value);
//            }
//            @SuppressLint("RestrictedApi") int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 1);
//            if (value > 0) {
//                setMinValue(minValue);
//            }
//            @SuppressLint("RestrictedApi") int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 10);
//            if (value > 0) {
//                setMaxValue(maxValue);
//            }
//            @SuppressLint("RestrictedApi") Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
//            if (addDrawable != null) {
//                iv_add.setImageResource(R.drawable.add);
//            }
//            @SuppressLint("RestrictedApi") Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
//
//            if (subDrawable != null) {
//                iv_sub.setImageResource(R.drawable.sub);
//            }

            getValue();
            setValue(value);

//        TypedArray attributes = mContext.obtainStyledAttributes(attrs,R.styleable.AddSubView);
//        if (attributes != null){
//            int valueInt = getValue();
//            String  value1Str = String.valueOf(valueInt);
//            setValue(value);
//            String value = attributes.getString(R.styleable.AddSubView_TvValue);
//            tv_value.setText((value == null ? value1Str : value) + "");
        }
    }


    //返回整数型
    public int getValue() {
        //文本内容
        String valueStr = tv_value.getText().toString().trim();
        if (valueStr != null && !valueStr.equals("") && !valueStr.equals("null") ){
            value = Integer.parseInt(valueStr);
        }

        return value;
    }

//        public int getValue() {
//
//        String valueStr = tv_value.getText().toString().trim();
//        if (!TextUtils.isEmpty(valueStr)){
//            value = Integer.parseInt(valueStr);
//        }
//        return value;
//    }

    //特别注意整形和字符型的转换
    public void setValue(int value) {
        this.value = value;
//        tv_value.setText(value);
        tv_value.setText(String.valueOf(value));
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }



    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Iv_Sub:
                subValue();
                if (onNumberChangeListener != null){
                    onNumberChangeListener.subValue(view,value);
                }
                break;
            case R.id.Iv_Add:
                addValue();
                if (onNumberChangeListener != null){
                    onNumberChangeListener.addValue(view,value);
                }
                break;
        }

    }

    private void addValue() {
        if (value < maxValue){
            value++;
            setValue(value);
//            if (onNumberChangeListener != null){
//                onNumberChangeListener.onNumberChange(value);
//            }
        }
    }

    private void subValue() {
        if (value > minValue){
            value--;
            setValue(value);
//            if (onNumberChangeListener != null){
//                onNumberChangeListener.onNumberChange(value);
//            }
        }
    }


    //当数量发生变化时回调
    public interface OnNumberChangeListener{
//        public void onNumberChange(int value);
        void addValue(View view,int value);
        void subValue(View view,int value);

    }

    //设置数据数量变化的监听
    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    private OnNumberChangeListener onNumberChangeListener;
}
//
//    private Context mContext;
//    @Bind(R.id.Iv_Sub)
//    ImageView IvSub;
//    @Bind(R.id.Iv_Add)
//    ImageView IvAdd;
//    @Bind(R.id.Tv_Value)
//    TextView TvValue;
//


//
//    private AddSubView addSubView;
//
//    public AddSubView(Context context){
//        this(context,null);
//    }
//
//
//    public AddSubView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//        this.mContext = context;
//
//        View.inflate(mContext, R.layout.add_sub_view, this);
//
//        int value = getValue();
//        setValue(value);
//    }
//
//
//    public AddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs,defStyleAttr);
//    }
//
//
//
//
//
//    public int getValue() {
//        String valueStr = TvValue.getText().toString().trim();
//        if (!TextUtils.isEmpty(valueStr)){
//            value = Integer.parseInt(valueStr);
//        }
//        return value;
//    }
//
//    public void setValue(int value) {
//        this.value = value;
//        TvValue.setText(value);
//    }
//
//    public int getMinValue() {
//        return minValue;
//    }
//
//    public void setMinValue(int minValue) {
//        this.minValue = minValue;
//    }
//
//    public int getMaxValue() {
//        return maxValue;
//    }
//
//    public void setMaxValue(int maxValue) {
//        this.maxValue = maxValue;
//    }
//
//
//
//    @OnClick({R.id.Iv_Sub, R.id.Iv_Add, R.id.Tv_Value})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.Iv_Sub:
//                subValue();
//                break;
//            case R.id.Iv_Add:
//                addValue();
//                break;
//        }
//
//    }
//
//    private void addValue() {
//        if (value < maxValue){
//            value++;
//            setValue(value);
//            if (onNumberChangeListener != null){
//                onNumberChangeListener.onNumberChange(value);
//            }
//        }
//    }
//
//    private void subValue() {
//        if (value > minValue){
//            value--;
//            setValue(value);
//            if (onNumberChangeListener != null){
//                onNumberChangeListener.onNumberChange(value);
//            }
//        }
//    }
//
//
//    //当数量发生变化时回调
//    public interface OnNumberChangeListener{
//        public void onNumberChange(int value);
//    }
//
//    //设置数据数量变化的监听
//    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
//        this.onNumberChangeListener = onNumberChangeListener;
//    }
//
//    private OnNumberChangeListener onNumberChangeListener;
//}
