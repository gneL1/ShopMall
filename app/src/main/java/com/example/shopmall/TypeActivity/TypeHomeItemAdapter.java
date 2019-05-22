package com.example.shopmall.TypeActivity;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopmall.R;

import java.util.List;

public class TypeHomeItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<CategoryBean.DataBean.DataListBean> foodDatas;


    public TypeHomeItemAdapter(Context context, List<CategoryBean.DataBean.DataListBean> foodDatas){
        this.mContext = context;
        this.foodDatas = foodDatas;
    }

    @Override
    public int getCount() {
        if (foodDatas != null){
            return foodDatas.size();
        }
        else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        CategoryBean.DataBean.DataListBean dataListBean = foodDatas.get(position);
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.type_page_item_home_category,null);
            holder.item_album = (ImageView)convertView.findViewById(R.id.Item_Album);
            holder.tv_home_name = (TextView)convertView.findViewById(R.id.Tv_Home_Name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置标题
        holder.tv_home_name.setText(dataListBean.getTitle());
        //设置图片
        Uri uri = Uri.parse(dataListBean.getImgURL());
        holder.item_album.setImageURI(uri);

        return convertView;
    }

    static class ViewHolder{
        private ImageView item_album;
        private TextView tv_home_name;
    }


}
