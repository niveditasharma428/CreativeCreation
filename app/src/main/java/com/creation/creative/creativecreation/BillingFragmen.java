package com.creation.creative.creativecreation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class BillingFragmen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_billing_fragmen);

          BillingFragment f = new BillingFragment();
                                                FrameLayout frame1 = (FrameLayout) findViewById(R.id.fragment);
                                                frame1.setBackgroundColor(Color.WHITE);
                                                FragmentManager fragmentManager = getSupportFragmentManager();
                                                fragmentManager .beginTransaction().replace(R.id.fragment, f).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BillingFragmen.this, Main2Activity.class);
        startActivity(intent);
    }
}
