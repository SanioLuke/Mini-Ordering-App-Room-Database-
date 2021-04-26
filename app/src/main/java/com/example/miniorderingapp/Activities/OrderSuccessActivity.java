package com.example.miniorderingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.miniorderingapp.R;

public class OrderSuccessActivity extends AppCompatActivity {

    ImageView ordersuccess_anim;
    TextView ordersuccess_homepage;
    Button ordersuccess_vieworders_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        ordersuccess_anim = findViewById(R.id.ordersuccess_anim);
        ordersuccess_homepage = findViewById(R.id.ordersuccess_homepage);
        ordersuccess_vieworders_btn = findViewById(R.id.ordersuccess_vieworders_btn);

        Glide.with(getApplicationContext()).load(R.raw.ordered_anim).into(ordersuccess_anim);
        ordersuccess_homepage.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        ordersuccess_vieworders_btn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AllOrdersActivity.class)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}