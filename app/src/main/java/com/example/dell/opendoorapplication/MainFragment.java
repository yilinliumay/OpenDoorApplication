package com.example.dell.opendoorapplication;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    ListView listView;
    PictureAdapter adapter;
    public static ArrayList<String> time= new ArrayList<>();;
    public static ArrayList<String> picture_number=new ArrayList<>();;
    public static ArrayList<Bitmap> bitmaps;

    static final int GET_JSON  = 1;
    static final int GET_IMAGE = 2;
    ImageRunnable imageRunnable;
    JsonRunnable jsonRunnable;
    Myhandler myhandler;
    private getIpString myCallback;
   String ipAddress;//="192.168.31.187";
    MediaPlayer mp3;
    int start = 0;
    int i;
    int counter;
    int judge=0;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main2, container, false);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new PictureAdapter(getActivity().getApplicationContext(), R.layout.row_layout);
        listView.setAdapter(adapter);
        ipAddress=myCallback.getString();
       // mp3 = new MediaPlayer(); // create MediaPlayer
       // mp3 = MediaPlayer.create(MainActivity.this, R.raw.a);
        bitmaps = new ArrayList<>();

        myhandler = new Myhandler();
        imageRunnable = new ImageRunnable();
        jsonRunnable = new JsonRunnable();
        myhandler.postDelayed(jsonRunnable, 5000);
        return view;
    }
    class Myhandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GET_JSON:
                    this.post(imageRunnable);
                    break;
                case GET_IMAGE:
                    AlertDialog.Builder alertDialogBuilder_Temp = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder_Temp.setTitle("open door reminder")
                            .setMessage("Someone Visit! ")
                            .setPositiveButton("OK,I know", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();
                    for (i = start; i < bitmaps.size(); i++) {
                        PictureProvider pictureDataProvider = new PictureProvider(bitmaps.get(i), time.get(i));
                        adapter.add(pictureDataProvider);
                    }
                    start=bitmaps.size();
                    break;
                default:
                    break;
            }
        }
    };


    class ImageRunnable implements Runnable {
        @Override
        public void run() {
            counter=0;
            bitmaps.clear();
            for(int j=0;j<picture_number.size();j++) {
                //ipAddress=getArguments().getString("ipaddress");
                //Log.e("ip",ipAddress);
                String server_ul = "http://"+ipAddress+ "/"+ picture_number.get(j) + ".jpg";
                ImageRequest imageRequest = new ImageRequest(server_ul,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                Log.e("Get Image","Picture");
                                bitmaps.add(response);
                                counter++;
                                if(counter==picture_number.size()) {
                                    Message msg = myhandler.obtainMessage();
                                    msg.what = GET_IMAGE;
                                    myhandler.sendMessage(msg);
                                }
                            }
                        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                MySingleton.getmInstance(getActivity()).addToRequestQue(imageRequest);
            }

        }
    };



    class JsonRunnable implements Runnable{

        @Override
        public void run() {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://"+ipAddress+"/cgi-bin/3.py",null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {//s为请求返回的字符串数据
                            Log.e("Get jsonString",jsonObject.toString());
                            Iterator<String> sIterator = jsonObject.keys();
                            while (sIterator.hasNext()) {
                                try {
                                    String key = sIterator.next();
                                    if(!key.equals("kong")) {
                                        time.add(key);
                                        String value = jsonObject.getString(key);
                                        picture_number.add(value);
                                    }
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(time.size()!=judge){
                                Message msg = myhandler.obtainMessage();
                                msg.what = GET_JSON;
                                myhandler.sendMessage(msg);}
                            judge=picture_number.size();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
            jsonObjectRequest.setTag("testGet");
            //将请求加入全局队列中
            MySingleton.getmInstance(getActivity()).addToRequestQue(jsonObjectRequest);
            // handler.postDelayed(this, 1000);
            myhandler.postDelayed(this, 2000);

        }
    };
  public void judge(String szContent)
  {
      myhandler.removeCallbacks(jsonRunnable);
      Log.e("hhhhhhhhhhhhhhhhhh","success");
  }
    public void judgePhoto(String sContent)
    {
        if(sContent.equals("stop"))
        {
            myhandler.removeCallbacks(jsonRunnable);

        }
        else if(sContent.equals("restart"))
        {
            myhandler.postDelayed(jsonRunnable, 5000);
        }
        Log.e("aaaaaaaaaaaaa","success");
    }
    public interface getIpString {
        public String getString();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            myCallback = (MainFragment.getIpString) activity;
        }
    }
}
