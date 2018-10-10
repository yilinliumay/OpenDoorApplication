 package com.example.dell.opendoorapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

 public class LoginActivity extends AppCompatActivity {
      SQLiteHelper helper;
      Cursor cursor;
     EditText username;
     EditText password;
     EditText ipAddress;
     Button login;
     Button register;
     String getPassword;
     String ipa;
    // Handler handler;
     //SendRunnable sendRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        ipAddress=(EditText)findViewById(R.id.Ip);
        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        helper = new SQLiteHelper(this);


       // Log.e("as","as");
       // handler=new Handler();
        //sendRunnable=new SendRunnable();
       // handler.post(sendRunnable);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipa=ipAddress.getText().toString();
                Log.e("IP",ipa);
                Log.e("IP","aaaaaaaaaaaaaa");
                String userName=username.getText().toString();
                String passWord=password.getText().toString();
                String args[]=new String[]{"%"+userName+"%"};
                cursor=helper.query(args);
                if (cursor.moveToFirst()) {
                    getPassword = cursor.getString(cursor.getColumnIndex("Password"));
                    Log.e("MainTag",getPassword);
                }
                if (getPassword.equals(passWord)) {
                   Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("ip",ipa);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtras(bundle);
                    startActivity( intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }
     /*class SendRunnable implements Runnable {
         @Override
         public void run()
         {
             Log.e("oooooooooooo","0000000000");
             Bundle bundle = new Bundle();
             bundle.putString("ip", ipa);
             Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
             intent.putExtras(bundle);
             startActivityForResult(intent, 001);
             Log.e("oooooooooooo","hahahaha");
         }
     };*/

 }
