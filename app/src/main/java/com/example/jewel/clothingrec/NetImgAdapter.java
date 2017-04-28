package com.example.jewel.clothingrec;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jewel on 2016/10/25.
 */
public class NetImgAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String, String>> dataList;
    private int itemId;
    private String[] fromArr;
    private int[] toArr;
    //bitmap哈希
    private HashMap<String, Bitmap> bitmapHashMap = new HashMap<String, Bitmap>();
    private Bitmap bitmap;
    //异步处理类
    private MyHandler myHandler = new MyHandler();

    /**
     *
     * @param context 上下文对象
     * @param dataList listview数据
     * @param itemId item layout布局id
     * @param fromArr 需要显示的数据
     * @param toArr 数据的布局id
     */
    public NetImgAdapter(Context context, List<HashMap<String, String>> dataList, int itemId, String[] fromArr, int[] toArr) {
        this.context = context;
        this.dataList = dataList;
        this.itemId = itemId;
        this.fromArr = fromArr;
        this.toArr = toArr;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(itemId, parent, false);
        }
        for (int i = 0; i < toArr.length; i++) {
            View v = ViewHolder.get(convertView, toArr[i]);
            if (v instanceof TextView) {
                //view是textview
                TextView tv = (TextView) v;
                String content = dataList.get(position).get(fromArr[i]);
                tv.setText(content);
            }
            if (v instanceof ImageView) {
                ImageView iv = (ImageView) v;
                final String content = dataList.get(position).get(fromArr[i]);
                final String completeURl = new String("http://119.29.191.103:8080/match2.0/" +content);
                bitmap = bitmapHashMap.get(completeURl);
                if (bitmap != null) {
                    //已经加载了图片，直接显示
                    iv.setImageBitmap(bitmap);
                } else {
                    new Thread() {

                        @Override
                        public void run() {
                            //没有加载图片，从网络加载
//                            bitmap = HttpHelper.getBitMap(content);
                            NetImageManager clothingImg = new NetImageManager();
                            bitmap = clothingImg.getHttpBitmap(completeURl);
                            //bitmapArray.put(toArr[i], bitmap);
                            bitmapHashMap.put(completeURl, bitmap);
                            myHandler.sendEmptyMessage(0);
                            super.run();
                        }

                    }.start();
                }
            }
        }
        return convertView;
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    notifyDataSetChanged();
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }

    }

}
