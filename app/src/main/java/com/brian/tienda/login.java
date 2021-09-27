package com.brian.tienda;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tienda.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity implements Iinten{
    EditText correolog, contralog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correolog= findViewById(R.id.correolog);
        contralog= findViewById(R.id.contralog);
    }

    public void views(View view) {
        Intent intentdo = new Intent(this, singups.class);

        startActivity(intentdo);
    }

    public void  validators (View view){



            Validaciones validacion = new Validaciones(correolog, contralog);


    }



}