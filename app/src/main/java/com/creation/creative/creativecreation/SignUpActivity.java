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
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    UserDataHelper dataHelper;

    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    InputFilter[] filters;
    Button in, up, upin;
    EditText email, pass, phn;
    private SharedPreferences sharedPreferences;
    private SQLiteDatabase sqlite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(10);
        in = (Button) findViewById(R.id.Sign_In_Log);
        up = (Button) findViewById(R.id.Sign_Up_Log);
        upin = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.Mail_Log);
        pass = (EditText) findViewById(R.id.Password_Log);
        phn = (EditText) findViewById(R.id.Phone_Log);
        pass.setFilters(filters);
        phn.setFilters(filters);

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginSignup.class);
                startActivity(intent);
            }
        });


        upin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_id = email.getText().toString();

                String pwdd = pass.getText().toString();
                String phn_no = phn.getText().toString();

               boolean result= ForSignUp(email_id, pwdd, phn_no);
                if(result==true){
                    method(email_id, pwdd, phn_no);
                }
            }
        });
    }

    boolean ForSignUp(String eid, String pswd, String phone) {
        boolean result = true;
        if ((eid.length() == 0) && (pswd.length() == 0) && (phone.length() == 0)) {
            result = false;
            new AlertDialog.Builder(SignUpActivity.this).setCancelable(false).setMessage("Fields cannot be blank!!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ;
                }
            }).create().show();
        } else if (!eid.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            result = false;
            new AlertDialog.Builder(SignUpActivity.this).setCancelable(false).setMessage("Invalid Email!!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ;
                }
            }).create().show();

        }


        else if (phone.length() < 10) {
            result = false;
            new AlertDialog.Builder(SignUpActivity.this).setCancelable(false).setMessage("Phone number not be less than 10 digits !!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ;
                }
            }).create().show();
        } else if (pswd.length() < 6) {
            result = false;
            new AlertDialog.Builder(SignUpActivity.this).setCancelable(false).setMessage("Minimum pasword length 6 digits !!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ;
                }
            }).create().show();
        }
        else {
            UserDataHelper myDbHelper;
            myDbHelper = new UserDataHelper(SignUpActivity.this);
            try {
                myDbHelper.createDataBase();

            } catch (IOException ioe) {
                throw new Error("Unable to create database");
            }
            try {
                myDbHelper.openDataBase();
                sqlite = myDbHelper.getWritableDatabase();
                Cursor cursor = sqlite.rawQuery("SELECT * FROM user_info", null);
                if (cursor.moveToFirst()) {
                    do {
                        String login = cursor.getString(0);
                        if (login.equals(eid)) {
                            result = false;
                            new AlertDialog.Builder(SignUpActivity.this).setCancelable(false).setMessage(" User Id Already  Exist !!!").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ;
                                }
                            }).create().show();
                            // login="nsdjd";
                            break;
                        }

                    } while (cursor.moveToNext());

                }
            } catch (SQLiteException sqle) {
                throw sqle;
            }
        }
        return result;
    }

    void method(String eid, String pswd, String phone) {
        // Toast.makeText(this,"Good to go",Toast.LENGTH_LONG).show();
        UserDataHelper myDbHelper;
        myDbHelper = new UserDataHelper(this);

        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
            SQLiteDatabase sqlite = myDbHelper.getWritableDatabase();


            ContentValues contentValues = new ContentValues();
            //  values.put(user_id, label);


            //ContentValues contentValues = new ContentValues();
            //int phn = Integer.parseInt(phone);
            long phn = Long.parseLong(phone);

            contentValues.put("user_id", eid);
            contentValues.put("user_pass", pswd);
            contentValues.put("user_phn", phn);
            contentValues.put("user_login", eid);


            sqlite.insert("user_info", null, contentValues);
            sharedPreferences = getSharedPreferences("com.creation.creative.creativecreation", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("state", "login");
            editor.commit();
           /* cursor = sqLiteDatabase.rawQuery("SELECT * FROM user_info", null);
            if (cursor.moveToFirst()) {
                do {
                    String id1 = cursor.getString(0);
                    String pass1 = cursor.getString(1);
                    String login = cursor.getString(3);
                    Log.e("id1", id1);
                    Log.e("pass1", pass1);
                    Log.e("id1", id1);
                } while (cursor.moveToNext());
            }*/
            //Toast.makeText(this,"inserted",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SignUpActivity.this, Main2Activity.class);
            startActivity(intent);

        } catch (SQLiteException sqle) {
            throw sqle;
        }
    }


}








