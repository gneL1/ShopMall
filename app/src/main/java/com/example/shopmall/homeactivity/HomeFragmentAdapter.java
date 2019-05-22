package com.example.shopmall.homeactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopmall.R;
import com.example.shopmall.ShoppingGoods.GoodsBean;
import com.example.shopmall.ShoppingGoods.ShoppingGoodsActivity;
import com.example.shopmall.apipackage.Translation;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.youth.banner.transformer.ScaleInOutTransformer;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//首页适配器
public class HomeFragmentAdapter extends RecyclerView.Adapter {
    //广告幅类型
    public static final int BANNER = 0;

    //频道类型
    public static final int CHANNEL = 1;

    //活动类型
    public static final int ACT = 2;

    //秒杀类型
    public static final int SECKILL = 3;

    //推荐类型
    public static final int RECOMMEND = 4;

    //热卖类型
    public static final int HOT = 5;
    private static final String GOODS_BEAN = "goodsBean";

    //初始化布局
    private LayoutInflater mLayoutInflater;

    //数据
    private Translation.ResultBean resultBean;

    private Context mContext;

    //当前类型
    private int currentType = BANNER;




//可能需要去掉super()?
    public HomeFragmentAdapter(Context context, Translation.ResultBean resultBean) {
  //      super(context, (AttributeSet) resultBean);
        this.mContext = context;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //根据视图类型创建ViewHolder
    //相当于getView创建ViewHolder部分代码
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        //广告幅
        if (viewType == BANNER){
            //创建BannerViewHolder,Banner里面传布局文件
        return new BannerViewHolder(mContext,mLayoutInflater.inflate(R.layout.banner_viewpager,null));
        }
        //频道
        else if (viewType == CHANNEL){
            return new ChannelViewHolder(mContext,mLayoutInflater.inflate(R.layout.channel_item,null));
        }
        //活动
        else if (viewType == ACT){
            return new ActViewHolder(mContext,mLayoutInflater.inflate(R.layout.act_item,null));
        }
        //秒杀
        else if (viewType == SECKILL){
            return new SeckillViewHolder(mContext,mLayoutInflater.inflate(R.layout.seckill_item,null));
        }
        //推荐
        else if (viewType == RECOMMEND){
            return new RecommendViewHolder(mContext,mLayoutInflater.inflate(R.layout.recommend_item,null));
        }
        //热卖
        else if (viewType == HOT){
            return new HotViewHolder(mContext,mLayoutInflater.inflate(R.layout.hot_item,null));
        }
        return null;
    }

    //相当于getView中绑定数据模块
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        if (getItemViewType(position) == BANNER){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        }
        else if (getItemViewType(position) == CHANNEL){
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        }
        else if (getItemViewType(position) == ACT){
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        }
        else if (getItemViewType(position) == SECKILL){
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        }
        else if (getItemViewType(position) == RECOMMEND){
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        }
        else if (getItemViewType(position) == HOT){
            HotViewHolder hotViewHolder = (HotViewHolder)holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }


    //广告幅
    class BannerViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        private Banner banner;


        public BannerViewHolder(Context mContext,View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<Translation.ResultBean.BannerInfoBean> bannerInfo){
            //得到图片集合地址
            List<String> imagesUrl = new ArrayList<>();
            for (int i = 0;i < bannerInfo.size();i++){
                String imageUrl = bannerInfo.get(i).getImage();
               imagesUrl.add(imageUrl);
            }
            //设置循环指示点
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

            //设置手风琴效果
            banner.setBannerAnimation(Transformer.Accordion);

            //设置Banner图片数据
            banner.setImages(imagesUrl,new OnLoadImageListener(){
                public void OnLoadImage(ImageView view,Object url){
                    //联网请求图片-Glide
                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE + url).into(view);
                }

            });

            //设置点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(mContext,"position==" + position,Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


    //频道
    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private GridView gv_channel;
        private ChannelAdapter adapter;


        public ChannelViewHolder(Context mContext,View itemView ) {
            super(itemView);
            this.mContext = mContext;
            gv_channel = (GridView)itemView.findViewById(R.id.gv_channel);
        }

        public void setData(List<Translation.ResultBean.ChannelInfoBean> channel_info){
            //得到数据后，设置GridView的适配器
            adapter = new ChannelAdapter(mContext,channel_info);
            gv_channel.setAdapter(adapter);

            //设置item的点击事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position" + position,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    //得到不同的类型
    public int getItemViewType(int position){
        switch (position){
            case BANNER://广告幅
                currentType = BANNER;
                break;
            case CHANNEL://频道
                currentType = CHANNEL;
                break;
            case ACT://活动
                currentType = ACT;
                break;
            case SECKILL://秒杀
                currentType = SECKILL;
                break;
            case RECOMMEND://推荐
                currentType = RECOMMEND;
                break;
            case HOT://热卖
                currentType = HOT;
                break;
        }
        return currentType;
    }


    //活动
    class ActViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private ViewPager act_viewpager;

        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.act_viewpager = (ViewPager)itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<Translation.ResultBean.ActInfoBean> act_info){
            //设置间距
            act_viewpager.setPageMargin(20);

            //动画效果
            act_viewpager.setOffscreenPageLimit(3);//>=3
            act_viewpager.setPageTransformer(true,new ScaleInOutTransformer());

            //有数据后，就设置数据适配器
            act_viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                    return view == o;
                }

                public Object instantiateItem(ViewGroup container,final int position){
                    //实例化ImageView
                    ImageView imageView = new ImageView(mContext);

                    //设置ImageView的拉伸
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    //根据Glide设置图片
                    Glide.with(mContext).load(act_info.get(position).getIcon_url()).into(imageView);

                    //添加到容器中
                    container.addView(imageView);

                    //设置点击事件
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext,"活动" + position,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return imageView;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    container.removeView((View)object);
                }
            });
        }
    }


    //秒杀
    class SeckillViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private SeckillRecyclerViewAdapter adapter;

        //相差多少时间（毫秒）
        private long dt = 0;

        //使用Handler实现秒杀倒计时效果
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt = dt - 1000;

                //时间格式化
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

                //获取当前系统时间
                String time = dateFormat.format(new Date(dt));
                tv_time_seckill.setText(time);
                handler.removeMessages(0);

                //发送时间，不断减少时间
                handler.sendEmptyMessageDelayed(0,1000);
                if (dt <= 0){
                    //把消息移除
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };


        public SeckillViewHolder(Context mContext,View itemView) {
            super(itemView);
            this.mContext = mContext;

            //初始化布局
            tv_time_seckill = (TextView)itemView.findViewById(R.id.tv_time_seckill);
            tv_more_seckill = (TextView)itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill = (RecyclerView)itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(Translation.ResultBean.SeckillInfoBean seckill_info){
            //得到数据后，设置数据
            adapter = new SeckillRecyclerViewAdapter(mContext,seckill_info.getList());
            rv_seckill.setAdapter(adapter);

            //设置布局管理器
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));

            //设置item的点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext,"秒杀" + position,Toast.LENGTH_SHORT).show();

                }
            });

            tv_time_seckill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"点击事件",Toast.LENGTH_SHORT).show();
                }
            });

            tv_more_seckill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"查看更多",Toast.LENGTH_SHORT).show();
                }
            });

            //秒杀倒计时（毫秒）
            dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());


            //进入后1秒钟就回去发送这个消息
            handler.sendEmptyMessageDelayed(0,1000);
        }
    }


    //推荐
    class RecommendViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private RecommendGridViewAdapter adapter;

        public RecommendViewHolder(final Context mContext,View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_more_recommend = (TextView)itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView)itemView.findViewById(R.id.gv_recommend);

            //设置点击事件
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position==" + position,Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void setData(List<Translation.ResultBean.RecommendInfoBean> recommend_info){
            //有了数据之后，就设置适配器
            adapter = new RecommendGridViewAdapter(mContext,recommend_info);
            gv_recommend.setAdapter(adapter);
        }
    }


    //热卖
    class HotViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;
        private TextView tv_more_hot;
        private GridView gv_hot;
        private HotGridViewAdapter adapter;

        public HotViewHolder(Context mContext,View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_more_hot = (TextView)itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView)itemView.findViewById(R.id.gv_hot);

        }

        public void setData(List<Translation.ResultBean.HotInfoBean> hot_info){
            adapter = new HotGridViewAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);

            //设置点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position=" + position,Toast.LENGTH_SHORT).show();

                    //热卖商品信息类
                    Translation.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    startShoppingGoodsActivity(goodsBean);
                }
            });

        }
    }

    //跳转到商品详情页面
    private void startShoppingGoodsActivity(GoodsBean goodsBean) {
        Intent intent = new Intent(mContext, ShoppingGoodsActivity.class);
        intent.putExtra(GOODS_BEAN,goodsBean);
        mContext.startActivity(intent);
    }


    //总共有多少个item
    public int getItemCount(){
        return 6;
    }



}
