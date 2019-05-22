package com.example.shopmall.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmall.R;
import com.example.shopmall.ShoppingCart.CartStorage;
import com.example.shopmall.ShoppingCart.ShoppingCartAdapter;
import com.example.shopmall.ShoppingGoods.GoodsBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartFragment extends BaseFragment {


    @Bind(R.id.Tv_Edit)
    TextView TvEdit;
    @Bind(R.id.Rv_List)
    RecyclerView RvList;
    @Bind(R.id.Check_All)
    CheckBox CheckAll;
    @Bind(R.id.Tv_Shopcart_Total)
    TextView TvShopcartTotal;
    @Bind(R.id.Btn_Check_Out)
    Button BtnCheckOut;
    @Bind(R.id.Ll_Check_All)
    LinearLayout LlCheckAll;
    @Bind(R.id.Cb_All)
    CheckBox CbAll;
    @Bind(R.id.Btn_Delete)
    Button BtnDelete;
    @Bind(R.id.Btn_Collect)
    Button BtnCollect;
    @Bind(R.id.Ll_Delete)
    LinearLayout LlDelete;

    private RelativeLayout RlEmptyCart;
    private ShoppingCartAdapter adapter;


    //编辑状态
    private  static final String ACTION_EDIT = "1";
    //完成状态
    private static final String ACTION_COMPLETE = "2";


    @Override
    public View initView() {
        //注入布局
        View view = View.inflate(mContext, R.layout.fragment_shopping_carts, null);
        RlEmptyCart = (RelativeLayout)view.findViewById(R.id.Ri_Empty_Cart);



        return view;
    }



    //设置默认的状态
    private void initListener() {
        //设置为编辑状态
        TvEdit.setTag(ACTION_EDIT);
        TvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前状态
                String action = v.getTag().toString();
                //切换状态
                if (action.equals(ACTION_EDIT)){
                    //切换为完成状态
                    showDelete();
                }
                else {
                    //切换为编辑状态
                    hideDelete();
                }
            }


        });
    }

    private void hideDelete() {
        //1.设置状态和文本变为编辑状态
        TvEdit.setTag(ACTION_EDIT);
        TvEdit.setText("编辑");
        //2.变为勾选
        if (adapter != null){
            adapter.checkall_none(true);
            adapter.checkAll();
            //编辑状态显示总价格
            adapter.showTotalPrice();
        }
        //3.删除视图隐藏
        LlDelete.setVisibility(View.GONE);
        //4.结算视图显示
        LlCheckAll.setVisibility(View.VISIBLE);
    }

    private void showDelete() {
        //1.设置状态和文本变为完成状态
        TvEdit.setTag(ACTION_COMPLETE);
        TvEdit.setText("完成");
        //2.变成非勾选
        if (adapter != null){
            adapter.checkall_none(false);
            adapter.checkAll();
        }
        //3.删除视图显示
        LlDelete.setVisibility(View.VISIBLE);
        //4.结算视图隐藏
        LlCheckAll.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        super.initData();

        //设置默认的状态
        initListener();


    }

    @Override
    public void onResume() {
        super.onResume();
        showDate();
    }

    //显示数据
    private void showDate() {
        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            //设置右上角编辑栏显示
            TvEdit.setVisibility(View.VISIBLE);
            LlCheckAll.setVisibility(View.VISIBLE);

            //有数据
            //把当没有数据时显示的布局隐藏
            RlEmptyCart.setVisibility(View.GONE);
            //设置适配器
            //传参数上下文和数据
            adapter = new ShoppingCartAdapter(mContext,goodsBeanList,CheckAll,TvShopcartTotal,CbAll);
            RvList.setAdapter(adapter);

            //设置布局管理器
            //第一个参数上下文，第二个参数选择竖直方向，第三个参数是否反转
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
            RvList.setLayoutManager(layoutManager);


        }
        else {
            //没有数据
            //显示数据为空的布局
//            RlEmptyCart.setVisibility(View.GONE);
            emptyShoppingCart();
        }

    }

    //设置为空购物车页面
    private void emptyShoppingCart(){
        RlEmptyCart.setVisibility(View.VISIBLE);
        TvEdit.setVisibility(View.GONE);
        LlDelete.setVisibility(View.GONE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.Tv_Edit, R.id.Check_All, R.id.Btn_Check_Out, R.id.Cb_All, R.id.Btn_Delete, R.id.Btn_Collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Tv_Edit:
                Toast.makeText(mContext,"点击了编辑框",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Check_All:
                break;
            case R.id.Btn_Check_Out:
                break;
            case R.id.Cb_All:
                break;
            case R.id.Btn_Delete:
                //删除选中的
                adapter.deleteData();
                //校验状态
                adapter.checkAll();

                //数据大小为0
                if (adapter.getItemCount() == 0){
                    emptyShoppingCart();
                }

                break;
            case R.id.Btn_Collect:
                break;
        }
    }
}
