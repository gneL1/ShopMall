package com.example.shopmall.publicview;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public  abstract class onLoadMoreListener extends RecyclerView.OnScrollListener {
    //    private int countItem;
//    private int lastItem;
    //是否可以滑动
    private boolean isScrolled = false;

//    private RecyclerView.LayoutManager layoutManager;

    //当前滑动的状态
//    private int currentScrollState = 0;

//    protected  abstract void onLoading(int countItem,int lastItem);

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //拖拽或惯性滑动时isScrolled设置为true
//        if (newState == recyclerView.SCROLL_STATE_DRAGGING||newState == recyclerView.SCROLL_STATE_SETTLING){
//            isScrolled = true;
//        }
//        else {
//            isScrolled = false;
//        }


        super.onScrollStateChanged(recyclerView,newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //当不滑动时
        if (newState == recyclerView.SCROLL_STATE_IDLE){
            //获取最后一个完全显示的itemPosition
            int lastitemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();

            //判断是否滑动到了最后一个item,并且是向上滑动
            if (lastitemPosition == (itemCount - 1) && isScrolled){
                //加载更多
                onLoadMore();
            }
        }


    }

    /**
     * On load more.
     * //加载更多回调
     */
    public abstract void onLoadMore();

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
//            layoutManager = recyclerView.getLayoutManager();
//            countItem = layoutManager.getItemCount();
//            lastItem = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
//        }
//        if (isScrolled && countItem != lastItem && lastItem == countItem - 1){
//            onLoading(countItem,lastItem);
//        }
        super.onScrolled(recyclerView,dx,dy);
        //大于0表示正在向上滑动，小于等于0表示停止或者向下滑动
        isScrolled = dy > 0;

    }
}

