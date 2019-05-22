package com.example.shopmall.ShoppingCart;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.shopmall.MyApplication;
import com.example.shopmall.ShoppingGoods.GoodsBean;
import com.example.shopmall.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CartStorage {

    private static final String JSON_CART = "json_cart";

    private  Context mContext;


    private  SparseArray<GoodsBean> sparseArray;


    private static CartStorage instance;

    private CartStorage(Context context){
        this.mContext = context;
        //把之前存储的数据读取出来
        sparseArray = new SparseArray<>(100);

        listToSparseArray();
    }



    //单例模式

    /**
     * Get instance cart storage.
     *  得到购物车实例
     * @return the cart storage
     */
    public static CartStorage getInstance(){
        if (instance == null){
            //一定要记得在xml中声明Application子类
            instance = new CartStorage(MyApplication.getContext());
        }
        return instance;
    }


    //从本地读取的数据加入到SparseArray中
    private void listToSparseArray(){
        List<GoodsBean> goodsBeanList = getAllData();
        //把List数据转换成SparseArray
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i);
                //put(int key, E value);
                sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }
        }
    }







    /**
     * Get all data list.
     *  获取本地所有的数据
     * @return the list
     */
    public List<GoodsBean> getAllData(){
        //GoodsBean类
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        
        //从本地获取
        String json = CacheUtils.getString(mContext,JSON_CART);

        //使用Gson转换成列表
        if (!TextUtils.isEmpty(json)){
            //把String转换成List
            goodsBeanList = new Gson().fromJson(json,new TypeToken<List<GoodsBean>>(){
            }.getType());
        }
        return goodsBeanList;
    }


    /**
     * Add data.
     *  添加数据
     * @param goodsBean the goods bean
     */
    public void addData(GoodsBean goodsBean){
        //1.添加到内存中SparseArray
        //如果当前数据已存在，就修改成number递增
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData != null){
            //内存中有了这条数据
            tempData.setNumber(tempData.getNumber() + 1);
        }else {
            tempData = goodsBean;
        }
        System.out.println("addData成功");
        System.out.println(goodsBean.toString());
        //同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);

        //2.同步到本地
        saveLocal();
    }


    /**
     * Delete data.
     *  删除数据
     * @param goodsBean the goods bean
     */
    public void deleteData(GoodsBean goodsBean){
        //1.内存中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        //2.把内存的保存到本地
        saveLocal();
    }


    /**
     * Update data.
     *  更新数据
     * @param goodsBean the goods bean
     */
    public void updateData(GoodsBean goodsBean){
        //内存中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        //同步到本地
        saveLocal();
    }



    //保存数据到本地
    private void saveLocal() {
        //SparseArray转换成List
        List<GoodsBean> goodsBeanList = sparseToList();
        
        //使用Gson把列表List转换成String类型
        String json = new Gson().toJson(goodsBeanList);
        //把String数据保存
        CacheUtils.saveString(mContext,JSON_CART,json);

        System.out.println(CacheUtils.getString(mContext,JSON_CART));
        System.out.println("同步到本地成功");
    }

    private List<GoodsBean> sparseToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i = 0;i < sparseArray.size();i++){
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
            System.out.println("此处是循环遍历");
            System.out.println(goodsBean.toString());
        }

        System.out.println(goodsBeanList.toString());

        return goodsBeanList;
    }


}
