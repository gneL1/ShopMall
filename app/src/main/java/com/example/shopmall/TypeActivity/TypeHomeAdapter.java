package com.example.shopmall.TypeActivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shopmall.R;

import java.util.List;

public class TypeHomeAdapter extends BaseAdapter {

    private Context mContext;
    private List<CategoryBean.DataBean> foodDatas;


    public TypeHomeAdapter(Context context, List<CategoryBean.DataBean> foodDatas){
        this.foodDatas = foodDatas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (foodDatas != null){
            return  foodDatas.size();
        }
        else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
//        return foodDatas.get(position);
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //获取指定索引的菜单列表
        CategoryBean.DataBean data = foodDatas.get(position);
        //获取指定索引的菜单列表里面的右侧详细列表
        List<CategoryBean.DataBean.DataListBean> dataListBeans = data.getDataList();
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.type_page_item_home,null);
            holder.tv_blank =(TextView) convertView.findViewById(R.id.Tv_Blank);
            holder.gv_scroll = (GridView) convertView.findViewById(R.id.Gv_Scroll);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        TypeHomeItemAdapter adapter = new TypeHomeItemAdapter(mContext,dataListBeans);
        //设置右侧标题
        holder.tv_blank.setText(data.getModuleTitle());
        //设置右侧详细列表的适配器
        holder.gv_scroll.setAdapter(adapter);


        return convertView;
    }

    //声明一个外部静态类
    static class ViewHolder{
        public TextView tv_blank;
        public GridView gv_scroll;
    }
}
