package com.example.shopmall.homeactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopmall.R;
import com.example.shopmall.apipackage.Translation;

import java.util.List;

public class ChannelAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Translation.ResultBean.ChannelInfoBean> datas;

    public ChannelAdapter(Context mContext, List<Translation.ResultBean.ChannelInfoBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    //返回了在适配器中所代表的数据集合的条目数
    @Override
    public int getCount() {
        return datas.size();
    }

    //返回了数据集合中与指定索引position对应的数据项
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewgroup) {
        ViewHolder viewHolder;
        if (convertView == null){//缓存
            //第二个参数是要加载的布局id
            convertView = View.inflate(mContext, R.layout.channel_item_cell,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_channel = (ImageView)convertView.findViewById(R.id.iv_channel);
            viewHolder.tv_channel = (TextView) convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
            //setTag()把Object对象作为参数对view进行存储
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //根据位置得到对应的数据
        Translation.ResultBean.ChannelInfoBean channelInfoBean = datas.get(position);
        //根据Glide设置图片
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + channelInfoBean.getImage()).into(viewHolder.iv_channel);
        viewHolder.tv_channel.setText(channelInfoBean.getChannel_name());
        return convertView;
    }

    //声明一个
    static class ViewHolder{
        ImageView iv_channel;
        TextView tv_channel;
    }

}
