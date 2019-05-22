package com.example.shopmall.TypeActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shopmall.R;

import java.util.List;

public class TypeMeneAdapter extends BaseAdapter {

    private Context mContext;

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = 0;
    private List<String> list;


    //声明构造函数
    public TypeMeneAdapter(Context context,List<String> list){
        this.list = list;
        this.mContext = context;
    }

    //返回在适配器中所代表的数据集合的条目数
    @Override
    public int getCount() {
        return list.size();
    }


    //返回了数据集合中与指定索引position对应的数据项
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.type_page_item_menu,null);
            holder.tv_name = (TextView)convertView.findViewById(R.id.Tv_Item_Name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }


        //如果位置是被选中的就设置为橙色，否则白色
        if (position == selectItem){
            holder.tv_name.setBackgroundColor(Color.WHITE);
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.colorOrange));
        }
        else {
            holder.tv_name.setBackgroundColor(Color.WHITE);
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }
        holder.tv_name.setText(list.get(position));
        return convertView;

    }


    //声明一个外部静态类
    static class ViewHolder{
        private TextView tv_name;
    }
}
