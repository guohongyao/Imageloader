package com.example.guohongyao.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by GuoHongyao on 2016/6/18.
 */
public class ImageCache {
    //图片缓存
    private LruCache<String,Bitmap> mImageCache;

    public ImageCache() {
        initImageLoader();
    }
    private void initImageLoader() {
        //计算可使用最大内存，使用内存超出这个值会引起OutOfMemory异常
        int maxMemory= (int) (Runtime.getRuntime().maxMemory()/1024);
        //取四分之一的可用内存作为缓存
        int cacheSize=maxMemory/4;
        mImageCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //重写此方法来衡量每张图片的大小，默认返回图片数量。
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };

    }
    public void put(String imageUrl,Bitmap bitmap){
        mImageCache.put(imageUrl,bitmap);
    }
    public Bitmap get(String imageUrl){
        return mImageCache.get(imageUrl);
    }
}
