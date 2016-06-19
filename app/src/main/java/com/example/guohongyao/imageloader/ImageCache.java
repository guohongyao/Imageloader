package com.example.guohongyao.imageloader;

import android.graphics.Bitmap;

/**
 * Created by GuoHongyao on 2016/6/19.
 */
public interface ImageCache {
    public void put(String imageUrl,Bitmap bitmap);
    public Bitmap get(String imageUrl);

}
