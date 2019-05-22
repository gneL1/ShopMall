package com.example.shopmall.homeactivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopmall.R;
import com.example.shopmall.apipackage.Translation;

import java.util.List;

public class HotGridViewAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Translation.ResultBean.HotInfoBean> datas;

    public HotGridViewAdapter(Context mContext, List<Translation.ResultBean.HotInfoBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.hot_item_cell,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_hot = (ImageView)convertView.findViewById(R.id.iv_hot);
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Translation.ResultBean.HotInfoBean hotInfoBean = datas.get(position);
        viewHolder.tv_price.setText("￥" + hotInfoBean.getCover_price());
        viewHolder.tv_name.setText(hotInfoBean.getName());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + hotInfoBean.getFigure()).into(viewHolder.iv_hot);
        return convertView;
    }

    static class ViewHolder{
        ImageView iv_hot;
        TextView tv_price;
        TextView tv_name;
    }
}
