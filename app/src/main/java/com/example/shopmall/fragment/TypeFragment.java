package com.example.shopmall.fragment;




import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shopmall.R;
import com.example.shopmall.TypeActivity.CategoryBean;
import com.example.shopmall.TypeActivity.TypeMeneAdapter;
import com.example.shopmall.TypeActivity.TypeHomeAdapter;
import com.example.shopmall.TypeActivity.Type_GetRequest_Interface;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TypeFragment extends BaseFragment {
    private List<String> menuList = new ArrayList<>();
    private List<CategoryBean.DataBean> homeList = new ArrayList<>();
    private List<Integer> showTitle;

    //左侧菜单
    private ListView lv_menu;
    //右侧列表
    private ListView lv_home;
    //右侧标题
    private TextView tv_title;

    private TypeMeneAdapter menuAdapter;
    private TypeHomeAdapter typeHomeAdapter;

    private int currentItem;




    @Override
    public View initView() {
        View view =View.inflate(mContext, R.layout.type_page_activity,null);
        //初始化Fresco
        Fresco.initialize(mContext);
        lv_menu = (ListView)view.findViewById(R.id.Lv_Menu);
        lv_home = (ListView)view.findViewById(R.id.Lv_Home);
        tv_title = (TextView)view.findViewById(R.id.Tv_Title);
        menuAdapter = new TypeMeneAdapter(mContext,menuList);
        lv_menu.setAdapter(menuAdapter);
        typeHomeAdapter = new TypeHomeAdapter(mContext,homeList);
        lv_home.setAdapter(typeHomeAdapter);
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelectItem(position);
                //notifyDataSetInvalidated()，当改变Adapter数据后，刷新试图
                //会清空所有信息，重新布局
                menuAdapter.notifyDataSetInvalidated();
                tv_title.setText(menuList.get(position));
                lv_home.setSelection(showTitle.get(position));
            }
        });



        lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    return;
                }
                int current = showTitle.indexOf(firstVisibleItem);
                if (currentItem != current && current >= 0){
                    currentItem = current;
                    tv_title.setText(menuList.get(currentItem));
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });

        apiConnect();
        return view;


    }

    @Override
    public void initData() {
        super.initData();
//        apiConnect();
//        menuAdapter.notifyDataSetChanged();
//        typeHomeAdapter.notifyDataSetChanged();
        System.out.println("数据初始化成功");
    }

    private static final String TAG1 = "Rxjava";
    private void apiConnect(){
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.106.65:8080/")    //设置网络请求Url
                .addConverterFactory(GsonConverterFactory.create())     //设置使用Gson解析（记得加入依赖）
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //支持RxJava
                .build();

        //创建网络请求接口的实例
        Type_GetRequest_Interface request = retrofit.create(Type_GetRequest_Interface.class);

        //采用Observable<...>形式对网络请求进行封装
        Observable<CategoryBean> observable = request.getCall();

        //发送网络请求
        observable.subscribeOn(Schedulers.io())     //在IO线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread())      //回到主线程处理请求结果
                .subscribe(new Observer<CategoryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG1,"开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(CategoryBean value) {
                        //对返回的数据进行处理
                        value.toString();
                        System.out.println("---------------------------------");
//                        System.out.println(value.getResult().getHot_info().get(0).getName());
//                        System.out.println("-------------------------------");
//                        System.out.println(Integer.valueOf(value.getResult().getSeckill_info().getEnd_time()));
//                        System.out.println("-------------------------------");
//                        System.out.println(Integer.valueOf(value.getResult().getSeckill_info().getStart_time()));
                        CategoryBean categoryBean = value;
                        if (value == null){
                            System.out.println("未获取到value");
                        }
                        else {
                            System.out.println("以获取到value");
                        }


                        Log.e(TAG1,"解析成功==" + value.getData().get(0).getModuleTitle());
//                        String json = getJson(this, "category.json");
//                        CategoryBean categoryBean = JSONObject.parseObject(json, CategoryBean.class);
                        showTitle = new ArrayList<>();
                        for (int i = 0; i < value.getData().size(); i++) {
                            CategoryBean.DataBean dataBean = value.getData().get(i);
                            menuList.add(value.getData().get(i).getModuleTitle());
                            showTitle.add(i);
                            homeList.add(value.getData().get(i));
                            System.out.println(dataBean.getModuleTitle());
                        }
                        tv_title.setText(value.getData().get(0).getModuleTitle());
                        menuAdapter.notifyDataSetChanged();
                        typeHomeAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG1,"请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG1,"请求成功");
                    }
                });

    }

}
