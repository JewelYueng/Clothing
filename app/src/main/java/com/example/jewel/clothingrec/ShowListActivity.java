package com.example.jewel.clothingrec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by jewel on 16/8/16.
 */
public class ShowListActivity extends Activity implements AbsListView.OnItemClickListener,AbsListView.OnScrollListener{
    private ListView matchList;
    private ArrayAdapter<String> arr_adapter;
    private NetImgAdapter sim_adapter;
    private List<HashMap<String,String>> datalist,intentList;
    private  Context list;
    private SuitCase cases[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intentList = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlist);
        matchList = (ListView) findViewById(R.id.listView);
        list = this;
        datalist = new ArrayList<HashMap<String,String>>();
        cases = new SuitCase[6];
//        get the data from the last activity
        Intent recIntent = getIntent();
        intentList = (List<HashMap<String,String>>) recIntent.getSerializableExtra("cases");
//
        System.out.println("showList is"+intentList.size());

        sim_adapter = new NetImgAdapter(this, getData(), R.layout.listitem, new String[]{"pic", "percent", "detail"}, new int[]{R.id.pic, R.id.percent, R.id.matchDetail});
        matchList.setAdapter(sim_adapter);
        matchList.setOnItemClickListener(this);
        matchList.setOnScrollListener(this);

//        else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(ShowListActivity.this);
//            builder.setTitle("温馨提示");
//            builder.setMessage("没有找到合适的搭配，请重新选择标签");
//            builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    finish();
//                }
//            });
//            builder.show();
//        }
    }
    private List<HashMap<String,String>> getData(){
        cases[0] = new SuitCase("同一色系的搭配降低不违和感，搭配指数为20%",20,R.mipmap.img3);
        DecimalFormat df = new DecimalFormat( "0.00 ");
//        cases[1] = new SuitCase("同一色系的搭配降低不违和感，毛织衫搭配针织短裤，提高干爽度",30,R.mipmap.img2);
//        cases[2] = new SuitCase("白T恤搭配黑裤子是屡次不爽的搭配，如果嫌单调可以选择黑色系带波点的裤子",40,R.mipmap.img1);
//        cases[3] = new SuitCase("上身条纹，下身搭配简单的半身裙，不会显得村姑反而更加清爽",50,R.mipmap.img5);
//        cases[4] = new SuitCase("针织毛衣，在冬天还可以搭配针织半身裙，看起来就很暖和",60,R.mipmap.img6);
//        cases[5] = new SuitCase("白色衬衫，搭配浅色系半身裙，显得高雅",70,R.mipmap.img7);
        for (int i = 0;i < intentList.size();i++){
            String percent = cases[0].getPer() + "%";
            String detail = cases[0].getDetail() + "";
//            int id = cases[i].getPic();
            String id = intentList.get(i).get("url");
            String perFloat = intentList.get(i).get("val");
            String per = df.format(Double.parseDouble(perFloat) * 100) + "%";
            String name = intentList.get(i).get("name");
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("pic",id);
            map.put("percent",name);
            map.put("detail","搭配指数为"+per);
            datalist.add(map) ;
        }

        return datalist;
    }
    @Override
    public void onItemClick(AdapterView<?>parent, View view,int position,long id){
//        String text = matchList.getItemAtPosition(position).toString();

//        用Bundle对象储存点击的项目的参数
        Bundle bundle = new Bundle();
        TextView detailView = (TextView)view.findViewById(R.id.matchDetail);
        String detail = detailView.getText().toString();
        TextView percentView = (TextView)view.findViewById(R.id.percent);
        String perStr = percentView.getText().toString();
//        ImageView picView = (ImageView)view.findViewById(R.id.pic);
//        String pic = picView.getDrawable().toString();
        bundle.putString("detail",detail);
        bundle.putString("percent",perStr);
        bundle.putString("picUrl",datalist.get(position).get("pic"));

        Toast.makeText(this,"position="+position+",text="+datalist.get(position).get("pic"),Toast.LENGTH_LONG).show();

        Intent intent = new Intent(list,ItemDetailActivity.class);
        intent.putExtra("info",bundle);
        startActivityForResult(intent,position);
    }
    @Override
    public void onScroll( AbsListView view,int firstVisibleItem,int visibleItemCount,int totalItemCount){

    }
    @Override
    public void onScrollStateChanged( AbsListView view,int state){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
