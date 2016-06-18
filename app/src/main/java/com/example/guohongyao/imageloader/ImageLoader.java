package com.example.guohongyao.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by GuoHongyao on 2016/6/18.
 */
public class ImageLoader {
    //图片缓存
    private LruCache<String,Bitmap> mImageCache;
    //线程池，线程数量为CPU的数量
    private ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public ImageLoader() {
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

    public void displayImage(final String imageUrl, final ImageView imageView) {
        imageView.setTag(imageUrl);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=downloadImage(imageUrl);
                if(bitmap==null){
                    return;
                }
                if(imageView.getTag()==imageUrl){
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(imageUrl,bitmap);
            }
        });
    }

    public Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap=null;
        try {
            URL url=new URL(imageUrl);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            bitmap= BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
