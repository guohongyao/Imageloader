package com.example.guohongyao.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    ImageCache imageCache = new MemoryCache();
    //线程池，线程数量为CPU的数量
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //注入实现缓存
    public void setImageCache(ImageCache imageCache) {
        this.imageCache = imageCache;
    }

    public void displayImage(final String imageUrl, final ImageView imageView) {
        Bitmap bitmap=imageCache.get(imageUrl);
        if(bitmap==null) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = downloadImage(imageUrl);
                    if (bitmap == null) {
                        return;
                    }
                    showImage(bitmap, imageView, imageUrl);
                    imageCache.put(imageUrl, bitmap);
                }
            });
        } else {
            showImage(bitmap, imageView, imageUrl);
        }
    }

    private void showImage(Bitmap bitmap, ImageView imageView, String imageUrl) {
        if (imageView.getTag() == imageUrl) {
            imageView.setImageBitmap(bitmap);
        }
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
