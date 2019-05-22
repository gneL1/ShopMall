package com.example.shopmall.ShoppingGoods;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopmall.R;
import com.example.shopmall.ShoppingCart.CartStorage;
import com.example.shopmall.apipackage.Translation;
import com.example.shopmall.fragment.CartFragment;
import com.example.shopmall.homeactivity.Constants;
import com.example.shopmall.homeactivity.HomeFragmentAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingGoodsActivity extends AppCompatActivity {
    @Bind(R.id.Iv_Img_Back)
    ImageView IvImgBack;
    @Bind(R.id.Iv_Img_More)
    ImageView IvImgMore;
    @Bind(R.id.V_Line)
    View VLine;
    @Bind(R.id.Iv_Goods_Img)
    ImageView IvGoodsImg;
    @Bind(R.id.Tv_Goods_Name)
    TextView TvGoodsName;
    @Bind(R.id.Tv_Goods_Desc)
    TextView TvGoodsDesc;
    @Bind(R.id.Tv_Goods_Price)
    TextView TvGoodsPrice;
    @Bind(R.id.Tv_Goods_Store)
    TextView TvGoodsStore;
    @Bind(R.id.Tv_Goods_Style)
    TextView TvGoodsStyle;
    @Bind(R.id.V_Line_Scroll)
    View VLineScroll;
    @Bind(R.id.Wv_Goods_More)
    WebView WvGoodsMore;
    @Bind(R.id.Tv_Goods_Root_Collect)
    Button TvGoodsRootCollect;
    @Bind(R.id.Tv_Root_Text1)
    TextView TvRootText1;
    @Bind(R.id.Tv_Goods_Root_Cart)
    Button TvGoodsRootCart;
    @Bind(R.id.Tv_Root_Text2)
    TextView TvRootText2;
    @Bind(R.id.Tv_Goods_Root_Addcart)
    Button TvGoodsRootAddcart;
    @Bind(R.id.Ri_Goods_Root)
    RelativeLayout RiGoodsRoot;

    private GoodsBean goodsBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_goods_activity);
        ButterKnife.bind(this);

        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsBean");
        //记得判断是否为空
        if (goodsBean != null) {
            Toast.makeText(this, goodsBean.toString(), Toast.LENGTH_SHORT).show();
            setDataForView(goodsBean);
        }
    }

    //设置数据
    private void setDataForView(GoodsBean goodsBean){
        Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(IvGoodsImg);
        TvGoodsName.setText(goodsBean.getName());
        TvGoodsPrice.setText("￥" + goodsBean.getCover_price());
    }


    @OnClick({R.id.Iv_Img_Back, R.id.Iv_Img_More, R.id.Tv_Goods_Style, R.id.Tv_Goods_Root_Collect, R.id.Tv_Goods_Root_Cart, R.id.Tv_Goods_Root_Addcart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Iv_Img_Back:
                finish();
                break;
            case R.id.Iv_Img_More:
                break;
            case R.id.Tv_Goods_Style:
                break;
            case R.id.Tv_Goods_Root_Collect:
                break;
            case R.id.Tv_Goods_Root_Cart:
                break;
            case R.id.Tv_Goods_Root_Addcart:
                CartStorage.getInstance().addData(goodsBean);
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
