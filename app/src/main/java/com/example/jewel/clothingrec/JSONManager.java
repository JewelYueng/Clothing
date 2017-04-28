package com.example.jewel.clothingrec;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jewel on 2016/10/27.
 */
public class JSONManager {
    /**
     * 从指定的URL中获取数组
     *
     *
     * @throws Exception
     */
    public static String readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(6000);
        conn.setDoInput(true);
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        return new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
    }

    /**
     * 解析
     *
     * @throws JSONException
     */
    public static ArrayList<HashMap<String, String>> Analysis(String jsonStr)
            throws JSONException {
        /******************* 解析 ***********************/
        JSONArray jsonArray = null;
        // 初始化list数组对象
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        String suitState;
//        json对象的话
        JSONObject listItem = null;
        listItem = new JSONObject(jsonStr);
        suitState = listItem.getString("state");
        Log.d("MainActivity","state:"+suitState);
        if (suitState.equals("true")) {
            jsonArray = listItem.getJSONArray("clothes");
            int jsonLen = jsonArray.length();
            System.out.println(jsonLen);
            for (int i = 0; i < jsonLen; i++) {
                JSONObject clothing = jsonArray.getJSONObject(i);
                HashMap<String, String> jsonItem = null;
                jsonItem = new HashMap<String, String>();
                jsonItem.put("name", clothing.getString("name"));
                jsonItem.put("val", clothing.getString("matchRate"));
                jsonItem.put("url", clothing.getString("url"));
                list.add(jsonItem);
            }
        }
        return list;

    }
}
