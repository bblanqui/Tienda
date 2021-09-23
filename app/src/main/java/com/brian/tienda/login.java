package com.brian.tienda;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tienda.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity implements Iinten{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void views(View view) {
        Intent intentdo = new Intent(this, singups.class);

        startActivity(intentdo);
    }
}