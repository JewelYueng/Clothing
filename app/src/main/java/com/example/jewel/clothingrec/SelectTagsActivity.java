package com.example.jewel.clothingrec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertPathBuilderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by jewel on 2016/10/23.
 */
public class SelectTagsActivity extends Activity{
    private Button sendTagsBtn;
    private RadioGroup collarGrp,sleeveGrp,looseGrp,lapGrp,materialGrp,typeGrp;
    private EditText majorR,majorB,majorG,secR,secG,secB;
    private String baseUrl = "http://119.29.191.103:8080/match2.0/center?type=up&feature=";
    private String url;
    List<HashMap<String, String>> lists;
    //    private RadioButton collarRBtn,sleeveGBtn,looseRBtn,lapRBtn,materialRBtn,typeRBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tags);
//        match the Object of this class to the view of the layout file
        sendTagsBtn = (Button)findViewById(R.id.sendBtn);
        collarGrp = (RadioGroup) findViewById(R.id.collarGroup);
        sleeveGrp = (RadioGroup) findViewById(R.id.sleeveGroup);
        looseGrp = (RadioGroup) findViewById(R.id.looseGroup);
        lapGrp = (RadioGroup) findViewById(R.id.lapGroup);
        materialGrp = (RadioGroup) findViewById(R.id.materialGroup);
        typeGrp = (RadioGroup) findViewById(R.id.typeGroup);
        majorR = (EditText)findViewById(R.id.majorR);
        majorG = (EditText)findViewById(R.id.majorG);
        majorB = (EditText)findViewById(R.id.majorB);
        secR = (EditText)findViewById(R.id.secR);
        secG = (EditText)findViewById(R.id.secG);
        secB = (EditText)findViewById(R.id.secB);

//        get the selected radioButtons' value

        lists = null;
        sendTagsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent intent =new Intent(SelectTagsActivity.this,ShowListActivity.class);
                url = baseUrl + getCollar(collarGrp.getCheckedRadioButtonId()) + getSleeve(sleeveGrp.getCheckedRadioButtonId()) + getRGB() + getLoose(looseGrp.getCheckedRadioButtonId()) + getLap(lapGrp.getCheckedRadioButtonId()) + getType(typeGrp.getCheckedRadioButtonId());
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            JSONManager jsonManager = new JSONManager();
                            System.out.println(url);
                            lists = jsonManager.Analysis(jsonManager.readParse(url));//解析出json数据
//                            if (lists.size()== 0){
//                                AlertDialog.Builder builder = new AlertDialog.Builder(SelectTagsActivity.this);
//                                builder.setTitle("温馨提示");
//                                builder.setMessage("没有找到合适的搭配，请重新选择标签");
//                                builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        finish();
//                                    }
//                                });
//                                builder.show();
//                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }.start();

                if (lists != null){

                    intent.putExtra("cases",(Serializable)lists);
                    System.out.println("Tags is "+ lists.size());
                    startActivity(intent);
                    finish();
                }
//                else {
//                    AlertDialog.Builder builder = builder = new AlertDialog.Builder(SelectTagsActivity.this);
//                    builder.setTitle("温馨提示");
//                    builder.setMessage("没有找到合适的搭配，请重新选择标签");
//                    builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
//                        }
//                    });
//                    builder.show();
//                }

            }
        });
    }
    public String getCollar(int id){
        switch (id){
            case R.id.collar1:return"1";
            case R.id.collar0:return"0";
        }
        return"0";
    }
    public String getSleeve(int id){
        switch (id){
            case R.id.sleeve0:
                return"/0";
            case R.id.sleeve1:return"/1";
            case R.id.sleeve2:return"/2";
            case R.id.sleeve3:return"/3";
        }
        return "/0";
    }
    public String getLoose(int id){
        switch (id){
            case R.id.loose0:return "/0";
            case R.id.loose1:return "/1";
            case R.id.loose2:return "/2";
        }
        return "/0";
    }
    public String getLap(int id){
        switch (id){
            case R.id.lap0:return "/0";
            case R.id.lap1:return "/1";
            case R.id.lap2:return "/2";
            case R.id.lap3:return "/3";
            case R.id.lap4:return "/4";
        }
        return "/0";
    }
    public String getMaterial(int id){
        switch (id){
            case R.id.material0:return "/1/0/0/0/0";
            case R.id.material1:return "/0/1/0/0/0";
            case R.id.material2:return "/0/0/1/0/0";
            case R.id.material3:return "/0/0/0/1/0";
            case R.id.material4:return "/0/0/0/0/1";
        }
        return "(1,0,0,0,0)";
    }
    public String getType(int id){
        switch (id){
            case R.id.type0:return"/1/0/0/0/0/0/0/0/0";
            case R.id.type1:return"/0/1/0/0/0/0/0/0/0";
            case R.id.type2:return"/0/0/1/0/0/0/0/0/0";
            case R.id.type3:return"/0/0/0/1/0/0/0/0/0";
            case R.id.type4:return"/0/0/0/0/1/0/0/0/0";
            case R.id.type5:return"/0/0/0/0/0/1/0/0/0";
            case R.id.type6:return"/0/0/0/0/0/0/1/0/0";
            case R.id.type7:return"/0/0/0/0/0/0/0/1/0";
            case R.id.type8:return"/0/0/0/0/0/0/0/0/1";
        }
        return "/1/0/0/0/0/0/0/0/0";
    }
    public String getRGB(){

        String mR =  majorR.getText().toString();
        String mG =  majorG.getText().toString();
        String mB =  majorB.getText().toString();
        String sR =  secR.getText().toString();
        String sG =  secG.getText().toString();
        String sB =  secB.getText().toString();
        return "/" + mR + "/" + mG + "/" + mB + "/" + sR + "/" + sG + "/" + sB;
    }


}
