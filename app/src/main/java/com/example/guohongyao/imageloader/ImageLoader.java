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
    //内存缓存
    private ImageCache imageCache = new ImageCache();
    //SD卡缓存
    DiskCache diskCache = new DiskCache();
    //是否使用SD卡缓存
    boolean isUseDiskCache = false;
    //线程池，线程数量为CPU的数量
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader() {
        imageCache = new ImageCache();
    }


    public void displayImage(final String imageUrl, final ImageView imageView) {
        imageView.setTag(imageUrl);
        Bitmap bitmap = isUseDiskCache ? diskCache.get(imageUrl) : imageCache.get(imageUrl);
        if(bitmap==null) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = downloadImage(imageUrl);
                    if (bitmap == null) {
                        return;
                    }
                    showImage(bitmap, imageView, imageUrl);
                }
            });
        }else{
            showImage(bitmap, imageView, imageUrl);
        }
    }

    private void showImage(Bitmap bitmap, ImageView imageView, String imageUrl) {
        if (imageView.getTag() == imageUrl) {
            imageView.setImageBitmap(bitmap);
        }
        imageCache.put(imageUrl, bitmap);
    }

    public Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
