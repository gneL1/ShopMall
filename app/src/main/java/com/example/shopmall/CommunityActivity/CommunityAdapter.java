package com.example.shopmall.CommunityActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shopmall.R;

import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter {
    private Context mContext;

    //正常内容
    private final static int TYPE_CONTENT = 0;

    //加载内容
    private final static int TYPE_FOOTER = 1;

    //当前加载状态，默认为加载完成
    private int loadState = 2;

    //正在加载
    public final int LOADING = 1;

    //加载完成
    public final int LOADING_COMPLETE = 2;

    //加载到底
    public final int LOADING_END = 3;

    private List<String> listData;

    private LayoutInflater inflater;

    @Override
    public int getItemViewType(int position) {
        //最后一个item设置为search_foot
        if (position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
    }

    public CommunityAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.listData = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return new ViewHolder(mContext,View.inflate(mContext,R.layout.search_recycler_item,null));
        //进行判断显示类型，来创建返回不同的View
        if (i == TYPE_FOOTER){
//            return new FootViewHolder(View.inflate(mContext,R.layout.search_foot,null));
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.community_page_foot,viewGroup,false);
            return new FootViewHolder(view);

//            View view =  inflater.inflate(R.layout.search_foot,viewGroup,false);
//            FootViewHolder footViewHolder = new FootViewHolder(view);
//            return footViewHolder;
        }
        else if (i == TYPE_CONTENT){
            return new ViewHolder(View.inflate(mContext, R.layout.community_page_item,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
//         if (getItemViewType(i) == TYPE_FOOTER){
//
//         }
//         else {
//             ViewHolder viewHolder = (ViewHolder) holder;
//             viewHolder.tv_name.setText("第" + i + "行");
//             viewHolder.tv_price.setText(listData.get(i));
//         }

        if (holder instanceof ViewHolder){
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tv_name.setText("第" + i + "行");
            viewHolder.tv_price.setText(listData.get(i));
        }
        else if (holder instanceof FootViewHolder){
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState){
                //正在加载
                case LOADING:
                    footViewHolder.tvloading.setVisibility(View.VISIBLE);
                    footViewHolder.progressBar.setVisibility(View.VISIBLE);
                    break;
                //加载完成
                case LOADING_COMPLETE:
                    footViewHolder.tvloading.setVisibility(View.INVISIBLE);
                    footViewHolder.progressBar.setVisibility(View.INVISIBLE);
                    break;
                //加载到底
                case LOADING_END:
                    footViewHolder.tvloading.setVisibility(View.GONE);
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return listData.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private TextView tv_name;
        private TextView tv_price;
        private TextView tv_desc;
        private ImageView iv_img;

        public ViewHolder( View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_desc = (TextView)itemView.findViewById(R.id.Tv_Desc);
            tv_name = (TextView)itemView.findViewById(R.id.Tv_Name);
            tv_price = (TextView)itemView.findViewById(R.id.Tv_Price);
            iv_img = (ImageView)itemView.findViewById(R.id.Iv_Img);
        }
    }


    class FootViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        private Context mContext;
        private TextView tvloading;
        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mContext = mContext;
            progressBar = (ProgressBar)itemView.findViewById(R.id.Pb_Progress);
            tvloading = (TextView)itemView.findViewById(R.id.Tv_Loading);
        }
    }


    /**
     * Set load state.
     *  设置上拉加载状态
     * @param loadState the load state
     */
    public void setLoadState(int loadState){
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
