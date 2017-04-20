package com.example.jewel.clothingrec;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.print.PrinterId;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by jewel on 16/8/16.
 */
public class ItemDetailActivity extends Activity {
    private Button shareBtn,backBtn;
    private TextView detailView,perView;
    private ImageView picView;
    private Bitmap bitmap;
    private String picUrl;
    private HashMap<String, Bitmap> bitmapHashMap = new HashMap<String, Bitmap>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    System.out.println("线程已接收");
                    bitmap = (Bitmap) msg.obj;
                    if (bitmap !=null) System.out.println("in handler   it's  not null");
                    //  latch.countDown();
                    picView.setImageBitmap(bitmap);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listdetail);

        shareBtn = (Button) findViewById(R.id.shareBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        detailView = (TextView) findViewById(R.id.detailDe);
        perView  =(TextView)findViewById(R.id.perDetail);
        picView = (ImageView)findViewById(R.id.imageView);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("info");
        String per = bundle.getString("percent");
        String detail = bundle.getString("detail");
        String pic  =bundle.getString("picUrl");
         picUrl = new String("http://"+pic);
//        int imageId = bundle.getInt("picId");

        detailView.setText(detail);
        perView.setText(per);
        bitmap = bitmapHashMap.get(picUrl);
        if (bitmap != null) {
            //已经加载了图片，直接显示
            picView.setImageBitmap(bitmap);
        } else {
            new Thread() {

                @Override
                public void run() {
                    //没有加载图片，从网络加载
//                            bitmap = HttpHelper.getBitMap(content);
                    NetImageManager clothingImg = new NetImageManager();
                    System.out.println(picUrl);
                    bitmap = clothingImg.getHttpBitmap(picUrl);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = bitmap;

                    System.out.println(bitmap);
                    handler.sendMessage(message);
                    //bitmapArray.put(toArr[i], bitmap);
                    super.run();
                }

            }.start();

        }


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
