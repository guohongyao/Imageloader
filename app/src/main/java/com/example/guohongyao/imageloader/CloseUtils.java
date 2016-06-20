package com.example.guohongyao.imageloader;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by GuoHongyao on 2016/6/20.
 */
public final class  CloseUtils {
    private CloseUtils() {
    }
    public static void closeQuickly(Closeable closeable){
        if(closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
