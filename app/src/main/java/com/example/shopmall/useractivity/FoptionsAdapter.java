package com.example.shopmall.useractivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopmall.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FoptionsAdapter extends BaseAdapter {

    private final Context mContext;
    ArrayList<HashMap<String,Object>> listTtem;

    public FoptionsAdapter(Context mContext,ArrayList<HashMap<String,Object>> listItem) {
        this.mContext = mContext;
        this.listTtem = listItem;
    }

    //返回了在适配器中所代表的数据集合的条目数
    @Override
    public int getCount() {
        return listTtem.size();
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
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.user_page_cart_item,null);
            viewHolder.img = (ImageView)convertView.findViewById(R.id.Iv_Img);
            viewHolder.text = (TextView)convertView.findViewById(R.id.Tv_Item);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.img.setImageResource((Integer)listTtem.get(position).get("ItemImage"));
        viewHolder.text.setText((String)listTtem.get(position).get("ItemText"));
        return convertView;
        //返回指定索引对应的视图
    }

    //声明外部静态类
    static class ViewHolder{
        public TextView text;
        public ImageView img;
    }

}
