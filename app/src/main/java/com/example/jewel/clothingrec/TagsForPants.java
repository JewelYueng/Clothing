//package com.example.jewel.clothingrec;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import org.w3c.dom.Text;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by jewel on 2016/10/25.
// */
//public class TagsForPants extends Activity {
//    private Button sendTagsBtn;
//    List<HashMap<String, String>> lists;
//    private EditText colorR,colorG,colorB;
//    private RadioGroup pTypeGrp,pLenGrp,pLooseGrp,pMaterialGrp,pPrintGrp;
//    private String baseUrl = "http://119.29.191.103:8080/match2.0/center?type=down&feature=";
//    private String url;
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        lists = null;
//        setContentView(R.layout.tags_pants);
//        colorR = (EditText) findViewById(R.id.colorR);
//        colorG = (EditText) findViewById(R.id.colorG);
//        colorB = (EditText) findViewById(R.id.colorB);
//        pTypeGrp = (RadioGroup) findViewById(R.id.pTypeGroup);
//        pLenGrp = (RadioGroup) findViewById(R.id.pLengthGroup);
//        pLooseGrp = (RadioGroup) findViewById(R.id.pLoseGroup);
//        pMaterialGrp = (RadioGroup) findViewById(R.id.pMaterialGroup);
//        pPrintGrp = (RadioGroup) findViewById(R.id.printingTypeGroup);
//        sendTagsBtn = (Button)findViewById(R.id.sureBtn);
//        sendTagsBtn.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//
//                url = baseUrl + getType(pTypeGrp.getCheckedRadioButtonId()) + getLen(pLenGrp.getCheckedRadioButtonId()) + getRGB() + getLoose(pLooseGrp.getCheckedRadioButtonId()) + getMaterial(pMaterialGrp.getCheckedRadioButtonId()) + getPrintType(pPrintGrp.getCheckedRadioButtonId());
//                new Thread(){
//                    @Override
//                    public void run(){
//                        try {
//                            JSONManager jsonManager = new JSONManager();
//                            System.out.println(url);
//                            lists = jsonManager.Analysis(jsonManager.readParse(url));//解析出json数据
//                            System.out.println(lists.size());
////                            if (lists.size() == 0){
////                                AlertDialog.Builder builder = new AlertDialog.Builder(TagsForPants.this);
////                                builder.setTitle("温馨提示");
////                                builder.setMessage("没有找到合适的搭配，请重新选择标签");
////                                builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
////                                @Override
////                                public void onClick(DialogInterface dialogInterface, int i) {
////                                finish();
////                                }
////                                });
////                                builder.show();
////                            }
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
//
//                if (lists != null){
//                    Intent intent =new Intent(TagsForPants.this,ShowListActivity.class);
//                    intent.putExtra("cases",(Serializable)lists);
//                    System.out.println("Tags is "+ lists.size());
//                    startActivity(intent);
//                }
////                else {
////                    AlertDialog.Builder builder = builder = new AlertDialog.Builder(TagsForPants.this);
////                    builder.setTitle("温馨提示");
////                    builder.setMessage("没有找到合适的搭配，请重新选择标签");
////                    builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialogInterface, int i) {
////                            finish();
////                        }
////                    });
////                    builder.show();
////                }
//            }
//        });
//    }
//    public  String getType(int id){
//        switch (id){
//            case R.id.pType0 : return "0";
//            case R.id.pType1 :return "1";
//        }
//        return "0";
//    }
//    public String getLen(int id){
//        switch (id){
//            case R.id.pLen0:return "/0";
//            case R.id.pLen1:return "/1";
//            case R.id.pLen2:return "/2";
//            case R.id.pLen3:return "/3";
//            case R.id.pLen4:return "/4";
//        }
//        return"/0";
//    }
//    public String getRGB(){
//        String R = colorR.getText().toString();
//        String G = colorG.getText().toString();
//        String B = colorB.getText().toString();
//        return "/"+ R + "/" + G + "/" + B;
//    }
//    public String getLoose(int id){
//        switch (id){
//            case R.id.pLoose0: return"/0";
//            case R.id.pLoose1: return"/1";
//            case R.id.pLoose2: return"/2";
//        }
//        return "/0";
//    }
//    public String getMaterial(int id){
//        switch (id){
//            case R.id.pMaterial0: return"/1/0/0/0/0";
//            case R.id.pMaterial1: return"/0/1/0/0/0";
//            case R.id.pMaterial2: return"/0/0/1/0/0";
//            case R.id.pMaterial3: return"/0/0/0/1/0";
//            case R.id.pMaterial4: return"/0/0/0/0/1";
//        }
//        return"/1/0/0/0/0";
//    }
//    public String getPrintType(int id){
//        switch (id){
//            case R.id.print0:return "/1/0/0/0/0/0/0/0/0";
//            case R.id.print1:return "/0/1/0/0/0/0/0/0/0";
//            case R.id.print2:return "/0/0/1/0/0/0/0/0/0";
//            case R.id.print3:return "/0/0/0/1/0/0/0/0/0";
//            case R.id.print4:return "/0/0/0/0/1/0/0/0/0";
//            case R.id.print5:return "/0/0/0/0/0/1/0/0/0";
//            case R.id.print6:return "/0/0/0/0/0/0/1/0/0";
//            case R.id.print7:return "/0/0/0/0/0/0/0/1/0";
//            case R.id.print8:return "/0/0/0/0/0/0/0/0/1";
//        }
//        return "/1/0/0/0/0/0/0/0/0";
//    }
//
//
//}
