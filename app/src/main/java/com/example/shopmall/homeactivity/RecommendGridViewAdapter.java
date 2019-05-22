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

public class RecommendGridViewAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Translation.ResultBean.RecommendInfoBean> dates;

    public RecommendGridViewAdapter(Context mContext, List<Translation.ResultBean.RecommendInfoBean> dates) {
        this.mContext = mContext;
        this.dates = dates;
    }


    @Override
    public int getCount() {
        return dates.size();
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
            convertView = View.inflate(mContext, R.layout.recommend_item_cell,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_recommend = (ImageView)convertView.findViewById(R.id.iv_recommend);
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //根据位置得到对应的数据
        Translation.ResultBean.RecommendInfoBean recommendInfoBean = dates.get(position);
        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText(recommendInfoBean.getCover_price());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + recommendInfoBean.getFigure()).into(viewHolder.iv_recommend);


        return convertView;
    }

    static class ViewHolder{
        ImageView iv_recommend;
        TextView tv_name;
        TextView tv_price;
    }
}
