package com.creation.creative.creativecreation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    UserDataHelper dataHelper;
    Cursor cursor;
    String login;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("com.creation.creative.creativecreation", Context.MODE_PRIVATE);
        String state = sharedPreferences.getString("state", null);

        if (state == null || state.equals("logout")|| state.equals("skip")) {

            Thread t=new Thread(){
                public void run(){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }finally{
                        Intent i=new Intent(MainActivity.this,LoginSignup.class);
                        startActivity(i);
                    }
                }
            };
            t.start();
        } else if (state.equals("login")) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }
    }
}
