package com.example.shopmall.homeactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopmall.R;
import com.example.shopmall.apipackage.Translation;

import java.util.List;

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final List<Translation.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillRecyclerViewAdapter(Context mContext, List<Translation.ResultBean.SeckillInfoBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }





    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(View.inflate(mContext,R.layout.seckill_item_cell,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        //根据位置得到对应的数据
        Translation.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);

        //绑定数据
        //使用Glide获取到图片数据
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + listBean.getFigure()).into(viewHolder.iv_figure);

        //获取价钱的数据
        viewHolder.tv_cover_price.setText(listBean.getCover_price());

        //获取降价的数据
        viewHolder.tv_origin_price.setText(listBean.getOrigin_price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_figure;
        private TextView tv_cover_price;
        private TextView tv_origin_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_figure = (ImageView)itemView.findViewById(R.id.iv_figure);
            tv_cover_price = (TextView)itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = (TextView)itemView.findViewById(R.id.tv_origin_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSeckillRecyclerView != null){
                        onSeckillRecyclerView.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    //监听器
    public interface OnSeckillRecyclerView{
        //当某条被点击的时候回调
        public void onItemClick(int position);
    }

    private OnSeckillRecyclerView onSeckillRecyclerView;

    //设置item的监听器
    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView){
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }
}
