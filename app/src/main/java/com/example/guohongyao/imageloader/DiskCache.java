package com.example.guohongyao.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by GuoHongyao on 2016/6/19.
 */
public class DiskCache implements ImageCache {
    private String cacheDir = "sdcard/cache/";

    @Override
    public Bitmap get(String imageUrl) {
        return BitmapFactory.decodeFile(cacheDir + imageUrl);
    }

    @Override
    public void put(String imageUrl, Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir + imageUrl);
            //如果不压缩是100，表示压缩率为0；如果是30，表示压缩70%;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
