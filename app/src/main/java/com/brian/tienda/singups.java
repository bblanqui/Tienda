package com.brian.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tienda.R;

public class singups extends AppCompatActivity implements Iinten{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singups);
    }

    @Override
    public void views(View view) {
        Intent intentdo = new Intent(this, getstart.class);

        startActivity(intentdo);
    }

    @Override
    public void viewsL(View view) {
        Intent intentdo = new Intent(this, login.class);

        startActivity(intentdo);
    }

    @Override
    public void viewsR(View view) {
        Intent intentdo = new Intent(this, Registrarse.class);

        startActivity(intentdo);
    }
}