package com.example.jewel.clothingrec.tagsview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Aria on 2017/4/20.
 */

public class TagsActivity extends Activity {

    TextView[] collar;
    int collarFlag = -1;
    TextView[] sleeve;
    int sleeveFlag = -1;
    TextView[] tight;
    int tightFlag = -1;
    TextView[] hem;
    int hemFlag = -1;
    TextView[] farbric;
    int farbricFlag = -1;
    TextView[] type;
    int typeFlag = -1;
    TextView[] sex;
    int sexFlag = -1;

    Button btnOk;
    Button btnChoose;
    Button btnAnalyse;
    AlertDialog colorPickerDialog;
    AlertDialog.Builder dialogBuilder;
    TextView majorColor;
    int majorRed = -1;
    int majorGreen = -1;
    int majorBlue = -1;
    TextView secondaryColor;
    int secondaryRed = -1;
    int secondaryGreen = -1;
    int secondaryBlue = -1;
    ColorPicker colorPicker;

    ProgressDialog progressDialog;
//http://119.29.191.103:8080/match/center.action?type=up&feature=
    private String baseUrl = "";
    private String url;
    List<HashMap<String, String>> lists;

    private Bitmap thumbnail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        sex = new TextView[2];
        sex[0] = (TextView) findViewById(R.id.sex0);
        sex[1] = (TextView) findViewById(R.id.sex1);

        collar = new TextView[2];
        collar[0] = (TextView) findViewById(R.id.collar01);
        collar[1] = (TextView) findViewById(R.id.collar02);

        sleeve = new TextView[4];
        sleeve[0] = (TextView) findViewById(R.id.sleeve01);
        sleeve[1] = (TextView) findViewById(R.id.sleeve02);
        sleeve[2] = (TextView) findViewById(R.id.sleeve03);
        sleeve[3] = (TextView) findViewById(R.id.sleeve04);

        tight = new TextView[3];
        tight[0] = (TextView) findViewById(R.id.tight01);
        tight[1] = (TextView) findViewById(R.id.tight02);
        tight[2] = (TextView) findViewById(R.id.tight03);

        hem = new TextView[5];
        hem[0] = (TextView) findViewById(R.id.hem01);
        hem[1] = (TextView) findViewById(R.id.hem02);
        hem[2] = (TextView) findViewById(R.id.hem03);
        hem[3] = (TextView) findViewById(R.id.hem04);
        hem[4] = (TextView) findViewById(R.id.hem05);

        farbric = new TextView[5];
        farbric[0] = (TextView) findViewById(R.id.fabric01);
        farbric[1] = (TextView) findViewById(R.id.fabric02);
        farbric[2] = (TextView) findViewById(R.id.fabric03);
        farbric[3] = (TextView) findViewById(R.id.fabric04);
        farbric[4] = (TextView) findViewById(R.id.fabric05);

        type = new TextView[9];
        type[0] = (TextView) findViewById(R.id.type01);
        type[1] = (TextView) findViewById(R.id.type02);
        type[2] = (TextView) findViewById(R.id.type03);
        type[3] = (TextView) findViewById(R.id.type04);
        type[4] = (TextView) findViewById(R.id.type05);
        type[5] = (TextView) findViewById(R.id.type06);
        type[6] = (TextView) findViewById(R.id.type07);
        type[7] = (TextView) findViewById(R.id.type08);
        type[8] = (TextView) findViewById(R.id.type09);

        btnOk = (Button) findViewById(R.id.btnOk);

        btnChoose =  (Button) findViewById(R.id.btn_choose);

        btnAnalyse = (Button) findViewById(R.id.btn_analyse);

        majorColor = (TextView) findViewById(R.id.MajorColor);
        secondaryColor = (TextView) findViewById(R.id.SecondaryColor);

        dialogBuilder = new AlertDialog.Builder(this);
    }

    private void initView() {
        for (int i = 0; i < collar.length; i++) {
            collar[i].setPadding(30, 15, 30, 15);
        }
        for (int i = 0; i < sleeve.length; i++) {
            sleeve[i].setPadding(30, 15, 30, 15);
        }
        for (int i = 0; i < tight.length; i++) {
            tight[i].setPadding(30, 15, 30, 15);
        }
        for (int i = 0; i < hem.length; i++) {
            hem[i].setPadding(30, 15, 30, 15);
        }
        for (int i = 0; i < farbric.length; i++) {
            farbric[i].setPadding(30, 15, 30, 15);
        }
        for (int i = 0; i < type.length; i++) {
            type[i].setPadding(30, 15, 30, 15);
        }

        majorColor.setPadding(30, 15, 30, 15);
        secondaryColor.setPadding(30, 15, 30, 15);
    }

    private void initListener() {
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
                            baseUrl = "http://119.29.191.103:8080/match/center.action?type=up&sex="+sexFlag+"&feature=";
                        } else {
                            sex[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            sex[j].setPadding(30, 15, 30, 15);
                            sex[j].setTextColor(Color.BLACK);
                        }
                    }
                }
            });
        }

        //领子
        for (int i = 0; i < collar.length; i++) {
            final int pos = i;
            collar[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < collar.length; j++) {
                        if (j == pos) {
                            collar[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            collar[j].setTextColor(Color.WHITE);
                            collar[j].setPadding(30, 15, 30, 15);
                            collarFlag = j;
                        } else {
                            collar[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            collar[j].setPadding(30, 15, 30, 15);
                            collar[j].setTextColor(Color.BLACK);
                        }
                    }

                }
            });
        }

        //袖长
        for (int i = 0; i < sleeve.length; i++) {
            final int pos = i;
            sleeve[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < sleeve.length; j++) {
                        if (j == pos) {
                            sleeve[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            sleeve[j].setTextColor(Color.WHITE);
                            sleeve[j].setPadding(30, 15, 30, 15);
                            sleeveFlag = j;
                        } else {
                            sleeve[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            sleeve[j].setPadding(30, 15, 30, 15);
                            sleeve[j].setTextColor(Color.BLACK);
                        }
                    }

                }
            });
        }

        //下摆
        for (int i = 0; i < hem.length; i++) {
            final int pos = i;
            hem[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < hem.length; j++) {
                        if (j == pos) {
                            hem[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            hem[j].setTextColor(Color.WHITE);
                            hem[j].setPadding(30, 15, 30, 15);
                            hemFlag = j;
                        } else {
                            hem[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            hem[j].setPadding(30, 15, 30, 15);
                            hem[j].setTextColor(Color.BLACK);
                        }
                    }

                }
            });
        }

        //松紧度
        for (int i = 0; i < tight.length; i++) {
            final int pos = i;
            tight[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < tight.length; j++) {
                        if (j == pos) {
                            tight[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            tight[j].setTextColor(Color.WHITE);
                            tight[j].setPadding(30, 15, 30, 15);
                            tightFlag = j;
                        } else {
                            tight[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            tight[j].setPadding(30, 15, 30, 15);
                            tight[j].setTextColor(Color.BLACK);
                        }
                    }

                }
            });
        }

        //面料
        for (int i = 0; i < farbric.length; i++) {
            final int pos = i;
            farbric[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < farbric.length; j++) {
                        if (j == pos) {
                            farbric[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            farbric[j].setTextColor(Color.WHITE);
                            farbric[j].setPadding(30, 15, 30, 15);
                            farbricFlag = j;
                        } else {
                            farbric[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            farbric[j].setPadding(30, 15, 30, 15);
                            farbric[j].setTextColor(Color.BLACK);
                        }
                    }

                }
            });
        }

        //类型
        for (int i = 0; i < type.length; i++) {
            final int pos = i;
            type[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < type.length; j++) {
                        if (j == pos) {
                            type[j].setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                            type[j].setTextColor(Color.WHITE);
                            type[j].setPadding(30, 15, 30, 15);
                            typeFlag = j;
                        } else {
                            type[j].setBackgroundResource(R.drawable.tag_frame_unselected);
                            type[j].setPadding(30, 15, 30, 15);
                            type[j].setTextColor(Color.BLACK);
                        }
                    }

                }
            });
        }

        //确认按钮
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (collarFlag == -1 || sleeveFlag == -1 || tightFlag == -1 || hemFlag == -1
                        || farbricFlag == -1 || typeFlag == -1 || majorRed == -1 || majorGreen == -1
                        || majorBlue == -1 || secondaryRed == -1 || secondaryGreen == -1 || secondaryBlue == -1) {
                    Toast.makeText(TagsActivity.this, "请完成所有选项", Toast.LENGTH_SHORT).show();
                    return;
                }
                url = baseUrl + collarFlag +
                        "/" + sleeveFlag +
                        "/" + majorRed + "/" + majorGreen + "/" + majorBlue +
                        "/" + secondaryRed + "/" + secondaryGreen + "/" + secondaryBlue +
                        "/" + tightFlag +
                        "/" + hemFlag +
                        getFabricFlag() +
                        getTypeFlag();
                Log.d("MainActivity", "url:" + url);
                progressDialog = ProgressDialog.show(TagsActivity.this, "发送数据", "正在发送数据");
                doPost(url);
            }
        });

        //选择图片按钮
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });

        //特征分析按钮
        btnAnalyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureAnalyse();
            }
        });

        //主色
        majorColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initColorDialog(true);
            }
        });

        //辅色
        secondaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initColorDialog(false);
            }
        });
    }

    private String getFabricFlag() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < farbric.length; i++) {
            builder.append("/");
            if (farbricFlag == i) {
                builder.append("1");
            } else builder.append("0");
        }
        return builder.toString();
    }

    private String getTypeFlag() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < type.length; i++) {
            builder.append("/");
            if (typeFlag == i) builder.append("1");
            else builder.append("0");
        }
        return builder.toString();
    }

    private void doPost(String url) {
        Networks.getInstance().doPost(url).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                        Toast.makeText(TagsActivity.this, "请求超时！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    progressDialog.cancel();
                    String result = response.body().string();
                    JSONManager jsonManager = new JSONManager();
                    try {
                        lists = jsonManager.Analysis(result);
                        Log.d("MainActivity", "lists:" + lists.size());
                        if (lists.size() == 0) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(TagsActivity.this);
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

                            Intent intent =new Intent(TagsActivity.this,ShowListActivity.class);
                            intent.putExtra("cases",(Serializable)lists);
                            System.out.println("Tags is "+ lists.size());
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();

                            Toast.makeText(TagsActivity.this, "访问出错！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initColorDialog(final boolean isMajor) {
        View view = LayoutInflater.from(TagsActivity.this).inflate(R.layout.item_color_picker, null, false);
        colorPickerDialog = dialogBuilder.create();
        colorPickerDialog.setView(view);
        colorPicker = (ColorPicker) view.findViewById(R.id.colorPicker);
//        colorPicker.addOpacityBar((OpacityBar) view.findViewById(R.id.opacityBar));
        colorPicker.addSVBar((SVBar) view.findViewById(R.id.svBar));
        view.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerDialog.dismiss();
                if (isMajor) {
                    int[] rgb = TranseToRGB(colorPicker.getColor());
                    majorColor.setText("选择颜色：" + rgb[0] + "," + rgb[1] + "," + rgb[2]);
                    majorColor.setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                    majorColor.setTextColor(Color.WHITE);
                    majorColor.setPadding(30,15,30,15);
                    majorRed = rgb[0];
                    majorGreen = rgb[1];
                    majorBlue = rgb[2];
                } else {
                    int[] rgb = TranseToRGB(colorPicker.getColor());
                    secondaryColor.setText("选择颜色：" + rgb[0] + "," + rgb[1] + "," + rgb[2]);
                    secondaryColor.setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
                    secondaryColor.setTextColor(Color.WHITE);
                    secondaryColor.setPadding(30,15,30,15);
                    secondaryRed = rgb[0];
                    secondaryGreen = rgb[1];
                    secondaryBlue = rgb[2];
                }

            }
        });
        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
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

    private void setChoose(TextView view)
    {
        view.setBackground(getResources().getDrawable(R.drawable.tag_frame_selected));
        view.setTextColor(Color.WHITE);
        view.setPadding(30, 15, 30, 15);
    }

    private void PictureAnalyse()
    {
        if(thumbnail == null)
            return;


        Mat rgbMat = new Mat();

        Utils.bitmapToMat(thumbnail, rgbMat);//convert original bitmap to Mat, R G B.


        /**********主要颜色识别***********************/
        int range = 10;
        int size = 255/range + (255%range==0?0:1);
        int[][][] bucket = new int[size][size][size];

        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                for(int k=0;k<size;k++) {
                    bucket[i][j][k] = 0;
                }



        for(int i=0; i<rgbMat.rows();i++)
            for(int j=0; j<rgbMat.cols(); j++)
            {
                double[] pixel = rgbMat.get(i,j);//返回的是rgba四个通道的值。
                int r = (int) pixel[0];
                int g = (int) pixel[1];
                int b = (int) pixel[2];

                ++bucket[r/range][g/range][b/range];

            }


        int max = bucket[0][0][0];

        int max_i=0, max_j = 0, max_k = 0;

        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                for(int k=0;k<size;k++) {
                    if (bucket[i][j][k] > max) {
                        max = bucket[i][j][k];
                        max_i = i;
                        max_j = j;
                        max_k = k;
                    }
                }

        int max_r = (max_i * range) + range /2;
        int max_g = (max_j * range) + range /2;
        int max_b = (max_k * range) + range /2;

        majorRed = max_r;
        majorBlue = max_b;
        majorGreen = max_g;
        majorColor.setText("选择颜色：" + max_r + "," +max_g + "," + max_b);

        /**********主要颜色识别完毕***********************/




        int collarIndex = 0;
        setChoose(collar[collarIndex]);
        collarFlag = collarIndex;

        int sleeveIndex = 1;
        setChoose(sleeve[sleeveIndex]);
        sleeveFlag = sleeveIndex;

        int hemIndex = 1;
        setChoose(hem[hemIndex]);
        hemFlag  = hemIndex;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                int sourceWidth = bitmap.getWidth();
                int sourceHeight = bitmap.getHeight();
                int bigger = sourceHeight >sourceWidth ?sourceHeight : sourceWidth;
                double ratio = bigger > 400 ? 400/(double)bigger : 1;

                thumbnail = ThumbnailUtils.extractThumbnail(bitmap, (int)(sourceWidth*ratio), (int)(sourceHeight*ratio));

                ImageView imageView = (ImageView) findViewById(R.id.up_cloth_pic);
    /*
                Mat rgbMat = new Mat();
                Mat grayMat = new Mat();

                Bitmap grayBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
                Utils.bitmapToMat(bitmap, rgbMat);//convert original bitmap to Mat, R G B.
                Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);//rgbMat to gray grayMat
                Utils.matToBitmap(grayMat, grayBitmap); //convert mat to bitmap
    */
                /* 将thumbnail设定到ImageView */
                imageView.setImageBitmap(thumbnail);

            }catch(Exception ex) {

                Log.e(TAG, "onActivityResult: fail when open flie!");
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        }
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            // TODO Auto-generated method stub
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    Log.i(TAG, "成功加载");
                    break;
                default:
                    super.onManagerConnected(status);
                    Log.i(TAG, "加载失败");
                    break;
            }
        }
    };
}
