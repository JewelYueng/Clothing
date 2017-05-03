package com.example.jewel.clothingrec.global;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;


/**
 * Created by DKJ on 2017/5/1.
 */

public class Utils {

    public static Bitmap compressBySize(Uri uri, int targetWidth, int targetHeight, ContentResolver cr) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；

        try {
            BitmapFactory.decodeStream(cr.openInputStream(uri));
            // 得到图片的宽度、高度；
            int imgWidth = opts.outWidth;
            int imgHeight = opts.outHeight;
            // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
            int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
            int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
            if (widthRatio > 1 || widthRatio > 1) {
                if (widthRatio > heightRatio) {
                    opts.inSampleSize = widthRatio;
                } else {
                    opts.inSampleSize = heightRatio;
                }
            }
            // 设置好缩放比例后，加载图片进内容；
            opts.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath(), opts);
            return bitmap;
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(), e);
            return null;
        }
    }
}
