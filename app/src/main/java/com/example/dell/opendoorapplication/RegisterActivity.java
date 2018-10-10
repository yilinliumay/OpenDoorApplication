package com.example.dell.opendoorapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button register;
    SQLiteHelper helper;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        register=(Button)findViewById(R.id.register);
        helper = new SQLiteHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=username.getText().toString();
                String passWord=password.getText().toString();
                if (helper.insert(userName, passWord)) {
                    startActivity( new Intent(RegisterActivity.this, LoginActivity.class));
                }
                //else {
                    //Toast.makeText(RegisterActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                //}

            }
        });
    }

}
