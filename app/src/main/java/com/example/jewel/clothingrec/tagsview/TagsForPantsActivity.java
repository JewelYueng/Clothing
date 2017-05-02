package com.example.jewel.clothingrec.tagsview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jewel.clothingrec.JSONManager;
import com.example.jewel.clothingrec.R;
import com.example.jewel.clothingrec.ShowListActivity;
import com.example.jewel.clothingrec.global.Networks;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by jewel on 2017/4/23.
 */

public class TagsForPantsActivity extends Activity {
    //    定义界面的元素
    TextView[] downType;
    int downTypeFlag = -1;
    TextView[] length;
    int lengthFlag = -1;
    TextView[] tight;
    int tightFlag = -1;
    TextView[] farbric;
    int farbricFlag = -1;
    TextView[] type;
    int typeFlag = -1;
    TextView[] sex;
    int sexFlag = -1;

    Button btnOk;
    AlertDialog colorPickerDialog;
    AlertDialog.Builder dialogBuilder;
    TextView color;
    int red = -1;
    int green = -1;
    int blue = -1;
    ColorPicker colorPicker;

    ProgressDialog progressDialog;
// http://119.29.191.103:8080/match/center.action?type=down&feature=
    private String baseUrl = "";
    private String url;
    List<HashMap<String, String>> lists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tags_pants);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initListener() {
//        下装类型
        for (int i = 0; i < downType.length; i++){
            final int pos = i;
            downType[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    for (int j = 0;j < downType.length; j++){
                        if(j == pos){
                            downType[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            downType[j].setTextColor(Color.WHITE);
                            downType[j].setPadding(30,15,30,15);
                            downTypeFlag = j;
                        }else{
                            downType[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            downType[j].setPadding(30,15,30,15);
                            downType[j].setTextColor(Color.BLACK);
                        }
                    }
                }
            });
        }
        //性别
        for(int i = 0; i < sex.length; i++){
            final int pos = i;
            sex[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < sex.length; j++) {
                        if (j == pos) {
                            sex[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            sex[j].setTextColor(Color.WHITE);
                            sex[j].setPadding(30, 15, 30, 15);
                            sexFlag = j;
                            baseUrl = "http://119.29.191.103:8080/match/center.action?type=down&sex="+sexFlag+"&feature=";
                        } else {
                            sex[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            sex[j].setPadding(30, 15, 30, 15);
                            sex[j].setTextColor(Color.BLACK);
                        }
                    }
                }
            });
        }

//        下装长度
        for (int i = 0; i < length.length; i++){
            final int pos = i;
            length[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    for (int j = 0;j < length.length; j++){
                        if(j == pos){
                            length[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            length[j].setTextColor(Color.WHITE);
                            length[j].setPadding(30,15,30,15);
                            lengthFlag = j;
                        }else{
                            length[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            length[j].setPadding(30,15,30,15);
                            length[j].setTextColor(Color.BLACK);
                        }
                    }
                }
            });
        }

//        松紧度
        for (int i = 0; i < tight.length; i++){
            final int pos = i;
            tight[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    for (int j = 0;j < tight.length; j++){
                        if(j == pos){
                            tight[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            tight[j].setTextColor(Color.WHITE);
                            tight[j].setPadding(30,15,30,15);
                            tightFlag = j;
                        }else{
                            tight[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            tight[j].setPadding(30,15,30,15);
                            tight[j].setTextColor(Color.BLACK);
                        }
                    }
                }
            });
        }

//        面料
        for (int i = 0; i < farbric.length; i++){
            final int pos = i;
            farbric[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    for (int j = 0;j < farbric.length; j++){
                        if(j == pos){
                            farbric[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            farbric[j].setTextColor(Color.WHITE);
                            farbric[j].setPadding(30,15,30,15);
                            farbricFlag = j;
                        }else{
                            farbric[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            farbric[j].setPadding(30,15,30,15);
                            farbric[j].setTextColor(Color.BLACK);
                        }
                    }
                }
            });
        }

//        类型
        for (int i = 0; i < type.length; i++){
            final int pos = i;
            type[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    for (int j = 0;j < type.length; j++){
                        if(j == pos){
                            type[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            type[j].setTextColor(Color.WHITE);
                            type[j].setPadding(30,15,30,15);
                            typeFlag = j;
                        }else{
                            type[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            type[j].setPadding(30,15,30,15);
                            type[j].setTextColor(Color.BLACK);
                        }
                    }
                }
            });
        }

//        确认按钮
        btnOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(downTypeFlag == -1 || lengthFlag == -1 || red == -1 || green == -1 || blue ==-1 || tightFlag == -1 ||farbricFlag == -1 || typeFlag == -1){
                    Toast.makeText(TagsForPantsActivity.this, "请完成所有选项", Toast.LENGTH_SHORT).show();
                    return ;
                }
                url = baseUrl + downTypeFlag + "/" + lengthFlag + "/" + red + "/" + green + "/" + blue + "/" + tightFlag + getFabricFlag() + getTypeFlag();
//                url例子：http://119.29.191.103:8080/match2.0/center?type=down&feature=1/2/21/21/21/1/0/1/0/0/0/0/0/0/0/1/0/0/0/0
                Log.d("MainActivity","url:"+url);
                progressDialog = ProgressDialog.show(TagsForPantsActivity.this, "发送数据", "正在发送数据");
                doPost(url);
            }
        });

//        色调
        color.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                initColorDialog();
            }
        });

    }

    private String getTypeFlag() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < type.length; i++){
            builder.append("/");
            if(typeFlag == i) builder.append("1");
            else builder.append("0");
        }
        return builder.toString();

    }

    private String getFabricFlag() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < farbric.length; i++){
            builder.append("/");
            if(farbricFlag == i)
                builder.append("1");
            else
                builder.append("0");
        }
        return builder.toString();
    }

    private void initColorDialog() {
        View view = LayoutInflater.from(TagsForPantsActivity.this).inflate(R.layout.item_color_picker,null,false);
        colorPickerDialog = dialogBuilder.create();
        colorPickerDialog.setView(view);
        colorPicker = (ColorPicker) view.findViewById(R.id.colorPicker);
        colorPicker.addOpacityBar((OpacityBar) view.findViewById(R.id.opacityBar));
        colorPicker.addSVBar((SVBar) view.findViewById(R.id.svBar));
        view.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                colorPickerDialog.dismiss();
                int []rgb = TranseToRGB(colorPicker.getColor());
                color.setText("选择颜色： "+rgb[0]+","+rgb[1]+","+rgb[2]);
                red = rgb[0];
                green = rgb[1];
                blue = rgb[2];
            }
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                colorPickerDialog.dismiss();
            }
        });
        colorPickerDialog.show();
    }

    private int[] TranseToRGB(int color) {
        int[] colors = new int[3];
        colors[0] = (color & 0xff0000) >> 16;//red
        colors[1] = (color & 0x00ff00) >> 8; //green
        colors[2] = (color & 0x0000ff); //blue
        return colors;
    }

    private void doPost(String url) {
        Networks.getInstance().doPost(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                        Toast.makeText(TagsForPantsActivity.this,"请求超时！",Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() == 200){
                    progressDialog.cancel();
                    String result = response.body().string();
                    JSONManager jsonManager = new JSONManager();
                    Log.d("MainActivity", "result:" + result);
                    try {
                        lists = jsonManager.Analysis(result);
                        Log.d("MainActivity", "lists:" + lists.size());
                        if (lists.size() == 0) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(TagsForPantsActivity.this);
                            builder.setTitle("温馨提示");
                            builder.setMessage("没有找到合适的搭配，请重新选择标签");
                            builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    builder.show();
                                }
                            });

                        }else{

                            Intent intent =new Intent(TagsForPantsActivity.this,ShowListActivity.class);
                            intent.putExtra("cases",(Serializable)lists);
                            System.out.println("Tags is "+ lists.size());
                            startActivity(intent);
                            finish();
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();
                            Toast.makeText(TagsForPantsActivity.this,"访问出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        for (int i = 0; i < downType.length; i++) {
            downType[i].setPadding(30, 15, 30, 15);
        }
        for (int i = 0; i < length.length; i++) {
            length[i].setPadding(30, 15, 30, 15);
        }
        for (int i = 0; i < tight.length; i++) {
            tight[i].setPadding(30, 15, 30, 15);
        }
        for (int i = 0; i < farbric.length; i++) {
            farbric[i].setPadding(30, 15, 30, 15);
        }
        for (int i = 0; i < type.length; i++) {
            type[i].setPadding(30, 15, 30, 15);
        }
        color.setPadding(30, 15, 30, 15);
    }

    private void initData() {
        sex = new TextView[2];
        sex[0] = (TextView) findViewById(R.id.pants_sex0);
        sex[1] = (TextView) findViewById(R.id.pants_sex1);
//        下装类型
        downType = new TextView[2];
        downType[0] = (TextView) findViewById(R.id.down_type01);
        downType[1] = (TextView) findViewById(R.id.down_type02);

//        下装长度
        length = new TextView[5];
        length[0] = (TextView) findViewById(R.id.pants_length01);
        length[1] = (TextView) findViewById(R.id.pants_length02);
        length[2] = (TextView) findViewById(R.id.pants_length03);
        length[3] = (TextView) findViewById(R.id.pants_length04);
        length[4] = (TextView) findViewById(R.id.pants_length05);

//        色调
        color = (TextView) findViewById(R.id.pants_color);

//        松紧度
        tight = new TextView[3];
        tight[0] = (TextView) findViewById(R.id.pants_tight01);
        tight[1] = (TextView) findViewById(R.id.pants_tight02);
        tight[2] = (TextView) findViewById(R.id.pants_tight03);

//        面料
        farbric = new TextView[5];
        farbric[0] = (TextView) findViewById(R.id.pants_fabric01);
        farbric[1] = (TextView) findViewById(R.id.pants_fabric02);
        farbric[2] = (TextView) findViewById(R.id.pants_fabric03);
        farbric[3] = (TextView) findViewById(R.id.pants_fabric04);
        farbric[4] = (TextView) findViewById(R.id.pants_fabric05);

//        类型
        type = new TextView[9];
        type[0] = (TextView) findViewById(R.id.pants_type01);
        type[1] = (TextView) findViewById(R.id.pants_type02);
        type[2] = (TextView) findViewById(R.id.pants_type03);
        type[3] = (TextView) findViewById(R.id.pants_type04);
        type[4] = (TextView) findViewById(R.id.pants_type05);
        type[5] = (TextView) findViewById(R.id.pants_type06);
        type[6] = (TextView) findViewById(R.id.pants_type07);
        type[7] = (TextView) findViewById(R.id.pants_type08);
        type[8] = (TextView) findViewById(R.id.pants_type09);

//        确定按钮
        btnOk = (Button) findViewById(R.id.pants_ok);

//        弹出框
        dialogBuilder = new AlertDialog.Builder(this);
    }

}
