package com.creation.creative.creativecreation;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class LoginSignup extends AppCompatActivity {
SharedPreferences sharedPreferences;
    InputFilter[] filters;
    Button login_btn;
    Button signin, signup;
    TextView skip_btn;
    EditText email_id, pass_wd;
    int i=0;

    UserDataHelper dataHelper;

    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    NavigationView nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(10);
        email_id = (EditText)findViewById(R.id.Mail);
        pass_wd = (EditText)findViewById(R.id.Password);
        pass_wd.setFilters(filters);
        login_btn = (Button)findViewById(R.id.insign);
        signup = (Button)findViewById(R.id.Sign_Up);

        skip_btn = (TextView)findViewById(R.id.skip);

       // dataHelper = new UserDataHelper(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHelper = new UserDataHelper(LoginSignup.this);
                try {
                   dataHelper.getReadableDatabase();
                    dataHelper.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }

                try {
                    //dataHelper = new UserDataHelper(LoginSignup.this);

                    sqLiteDatabase = dataHelper.getWritableDatabase();
                    dataHelper.openDataBase();
                    //boolean hasUserFound = false;


                    String emailText = email_id.getText().toString();
                    String passText = pass_wd.getText().toString();
                   boolean result= ForSignUp(emailText, passText);
                    if(result==true) {
                        cursor = sqLiteDatabase.rawQuery("SELECT * FROM user_info", null);
                        if (cursor.moveToFirst()) {
                            do {


                                String id1 = cursor.getString(0);
                                String pass1 = cursor.getString(1);

                                if ((id1.equals(emailText)) && (pass1.equals(passText))) {
                                    int b = cursor.getPosition();
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("user_login", id1);
                                    sqLiteDatabase.update("user_info", contentValues, "user_id" + " = ?", new String[]{id1});
                                    sharedPreferences = getSharedPreferences("com.creation.creative.creativecreation", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("state", "login");
                                    editor.commit();
                                    Intent intent = new Intent(LoginSignup.this, Main2Activity.class);
                                    startActivity(intent);
                                    i=1;
                                    break;
                                }


                            } while (cursor.moveToNext());

                            if(i==0){
                                new AlertDialog.Builder(LoginSignup.this).setCancelable(false).setMessage("User Id or Password does not match!!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        ;
                                    }
                                }).create().show();
                            }

                        }

                    }


                    } catch (SQLiteException sqle) {
                    throw sqle;
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignup.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("com.creation.creative.creativecreation", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("state", "skip");
                editor.commit();
                Intent intent = new Intent(LoginSignup.this, Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    private boolean ForSignUp(String emailText, String passText) {
        boolean result=true;
        if ((emailText.length()==0) && (passText.length() == 0 ) ) {
            result=false;
            new AlertDialog.Builder(LoginSignup.this).setCancelable(false).setMessage("Fields cannot be blank!!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ;
                }
            }).create().show();
        }

        else  if (!emailText.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            result=false;
            new AlertDialog.Builder(LoginSignup.this).setCancelable(false).setMessage("Invalid Email!!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ;
                }
            }).create().show();

        }
        else if (passText.length()<6)
        {
            result=false;
            new AlertDialog.Builder(LoginSignup.this).setCancelable(false).setMessage("Minimum pasword length 6 digits !!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ;
                }
            }).create().show();
        }
        return result;
    }


}




