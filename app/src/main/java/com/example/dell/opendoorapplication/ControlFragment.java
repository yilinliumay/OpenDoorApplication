package com.example.dell.opendoorapplication;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {
    Button open;
    Button photo;
    ImageView imageView;
    TextView textView;

    private takePhoto myCallback;
    String ipString;//="192.168.31.187";

    public ControlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        open = (Button) view.findViewById(R.id.open);
        photo = (Button) view.findViewById(R.id.photo);
        imageView = (ImageView) view.findViewById(R.id.takePhoto);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ipString=myCallback.getString();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://"+ipString+"/cgi-bin/1.py", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {//s为请求返回的字符串数据
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                // Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                jsonObjectRequest.setTag("openGet");
                Log.e("Opendoor", "open");
                MySingleton.getmInstance(getActivity()).addToRequestQue(jsonObjectRequest);
            }

        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCallback.showPhoto("stop");
                ipString=myCallback.getString();
                //Log.e("ipipipip",ipString);

                StringRequest request = new StringRequest(Request.Method.GET, "http://"+ipString+"/cgi-bin/2.py",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Log.e("Control", "in1");

                                ImageRequest imageRequest = new ImageRequest("http://"+ipString+"/c.jpg",
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap response) {
                                                Log.e("Control", "in2");
                                                imageView.setImageBitmap(response);
                                                myCallback.showPhoto("restart");

                                            }
                                        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                        error.printStackTrace();
                                    }
                                });

                                MySingleton.getmInstance(getActivity()).addToRequestQue(imageRequest);


                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                // Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                request.setTag("photoGet");
                Log.e("Photo", "photo");
                MySingleton.getmInstance(getActivity()).addToRequestQue(request);


            }

        });
        return view;
    }

        public interface takePhoto {
            public void showPhoto(String name);
             public String getString();
        }
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            if (activity != null) {
                myCallback = (takePhoto) activity;
            }
        }






}
