package com.example.shopmall.ShoppingCart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopmall.R;
import com.example.shopmall.ShoppingGoods.GoodsBean;
import com.example.shopmall.fragment.MainPageActivity;
import com.example.shopmall.homeactivity.Constants;
import com.example.shopmall.publicview.AddSubView;
import com.example.shopmall.utils.CacheUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.IllegalFormatCodePointException;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private final Context mContext;
    private final List<GoodsBean> datas;
    private final CheckBox checkall;
    private final TextView shopCartTotal;

    //完成状态下的checkBox
    private final CheckBox cball;



    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList, CheckBox checkAll, TextView tvShopcartTotal, CheckBox cbAll) {
        this.mContext = mContext;
        this.datas = goodsBeanList;
        this.checkall = checkAll;
        this.shopCartTotal = tvShopcartTotal;
        this.cball = cbAll;
        showTotalPrice();
        //设置点击事件
        setListener();
        //校验是否全选
        checkAll();

        //设置全选监听点击事件
        setCheckallListener();

        //设置完成状态下的监听事件
        setCbAllListener();



    }

    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //1.根据位置找到对应的Bean对象
                GoodsBean goodsBean = datas.get(position);
                //2.设置取反状态
                goodsBean.setSelected(!goodsBean.isSelected());
                //3.刷新状态
                //使用EventBus来重新刷新页面
//                EventBus.getDefault().post(MainPageActivity.REFRESH);

                notifyItemChanged(position);
                //4.校验是否全选
                checkAll();
                //5.重新计算总价格
                showTotalPrice();
                System.out.println("check监听成功");
            }

        });

    }

    //设置全选点击事件
    public void setCheckallListener(){
        //设置全选点击事件
        checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = checkall.isChecked();
                //2.根据状态设置全选和非全选
                checkall_none(isCheck);
                //3.计算总价格
                showTotalPrice();
            }


        });
    }

    //设置完成状态下的全选点击事件
    public void setCbAllListener(){
        cball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = cball.isChecked();
                //2.根据状态设置全选和非全选
                checkall_none(isCheck);
            }
        });
    }

    //设置全选和非全选
    public void checkall_none(boolean isCheck) {
        if (datas != null && datas.size() > 0){
            for (int i = 0;i < datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }


    public void deleteData(){
        if (datas != null && datas.size() > 0){
            for (int i = 0; i < datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                //删除选中的
                if (goodsBean.isSelected()) {
                    //内存中移除,注意此处清除的是goodBean而不是i这个位置
                    datas.remove(goodsBean);
                    //保存到本地
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);

                    notifyItemRangeChanged(i,datas.size() - i);
                    //每移除一次循环站的size减一
                    i--;
                }
            }
        }
    }




    //for循环 ，有一个没被选中就为非全选
    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isSelected()){
                    //非全选
                    checkall.setChecked(false);
                    cball.setChecked(false);
                }
                else {
                    number++;
                }
            }
            //全选
            if (number == datas.size()){
                checkall.setChecked(true);
                cball.setChecked(true);
            }
        }
        //如果没有数据
        else {
            checkall.setChecked(false);
            cball.setChecked(false);
        }
    }

    //显示总价格
    public void showTotalPrice() {
        shopCartTotal.setText( "￥" + getTotalPrice().toString() );
    }


    //计算商品总价格
    public Double getTotalPrice() {
        double totalPrice = 0.0;
        if (datas != null && datas.size() > 0){
            for (int i = 0; i < datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                //只计算被选中的商品
                if (goodsBean.isSelected()){
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getCover_price()) * Double.valueOf(goodsBean.getNumber());
                }
            }
        }
        return totalPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_shop_cart,null));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        //1.根据位置得到对应的Bean对象
//        GoodsBean goodsBean = datas.get(i);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setDate(datas.get(i));
        viewHolder.setCheckGovListener(i);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubView addSubView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_gov = (CheckBox)itemView.findViewById(R.id.Cb_Gov);
            iv_gov = (ImageView)itemView.findViewById(R.id.Iv_Gov);
            tv_desc_gov = (TextView)itemView.findViewById(R.id.Tv_Desc_Gov);
            tv_price_gov = (TextView)itemView.findViewById(R.id.Tv_Price_Gov);
            addSubView = (AddSubView)itemView.findViewById(R.id.AddSubView);
            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                        System.out.println("点击了item页面");
                    }
                }
            });


            //设置check的点击事件
            //返回的是当前check所在的那个位置即哪一行
//            cb_gov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (onItemClickListener != null){
//                        onItemClickListener.onItemClick(getLayoutPosition());
//                    }
//                }
//            });

        }

        //设置左侧每行单选框的点击事件
        public void setCheckGovListener(int position){
            cb_gov.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //1.根据位置找到对应的Bean对象
                    GoodsBean goodsBean = datas.get(position);
                    //2.设置取反状态
                    goodsBean.setSelected(!goodsBean.isSelected());
                    //3.刷新状态
                    //使用EventBus来重新刷新页面
//                EventBus.getDefault().post(MainPageActivity.REFRESH);

                    notifyItemChanged(position);
                    //4.校验是否全选
                    checkAll();
                    //5.重新计算总价格
                    showTotalPrice();

                }
            });
        }

        public void setDate(final GoodsBean goodsBean){
            //2.设置数据
            cb_gov.setChecked(goodsBean.isSelected());
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(iv_gov);
            tv_desc_gov.setText(goodsBean.getName());
            tv_price_gov.setText("￥" + goodsBean.getCover_price());
            //设置数量显示
//        holder.setClick();
            addSubView.setValue(goodsBean.getNumber());
            addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
                @Override
                public void addValue(View view, int value) {
                    //1.当前列表内存中
                    goodsBean.setNumber(value);
                    //2.本地要更新
                    CartStorage.getInstance().updateData(goodsBean);
                    //3.刷新适配器
//                    notifyItemChanged(i);
                    //再次计算总价格
                    showTotalPrice();


                }

                @Override
                public void subValue(View view, int value) {
                    //1.当前列表内存中
                    goodsBean.setNumber(value);
                    //2.本地要更新
                    CartStorage.getInstance().updateData(goodsBean);
                    //3.刷新适配器
//                    notifyItemChanged(i);
                    //再次计算总价格
                    showTotalPrice();

                }
            });
        }


//        public void setClick(){
//            cb_gov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        System.out.println("选中checkbox");
//                        if (onItemClickListener != null){
//                            onItemClickListener.onItemClick(getLayoutPosition());
//                        }
//
//                }
//            });
//        }
    }


    //点击item的监听者
    public interface OnItemClickListener{
        //当点击某一条的时候被回调
        public void onItemClick(int position);
    }

    //设置item的监听
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

}
