package com.example.dell.opendoorapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dell on 2016/12/17.
 */

public class MySingleton
{
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;
    //private ImageLoader mImageLoader;
   /* mImageLoader = new ImageLoader(mRequestQueue,

                                   new ImageLoader.ImageCache() {

        private final LruCache<String, Bitmap>

                cache = new LruCache<String, Bitmap>(20);
        @Override
         public Bitmap getBitmap(String url) {

            return cache.get(url);

        }
        @Override
         public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);

        }

    });

}
public ImageLoader getImageLoader() {

return mImageLoader;

}
*/
    private MySingleton(Context context)
    {
        mCtx=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized MySingleton getmInstance(Context context)
    {
        if (mInstance==null)
        {
            mInstance=new MySingleton(context);
        }

        return mInstance;

    }
    public<T> void addToRequestQue(Request<T> request)
    {
        requestQueue.add(request);
    }

}
