package com.brian.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.tienda.R;

public class Registrarse extends AppCompatActivity {
    EditText usuario, correo, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        usuario = findViewById(R.id.usuario);
        correo = findViewById(R.id.correo);
        pass = findViewById(R.id.pass);
    }

    public void views(View view) {
        Intent intentdo = new Intent(this, singups.class);

        startActivity(intentdo);
    }

    public  void validaciones (View view){

        Validaciones Validacion = new Validaciones(usuario, correo, pass);
    }


}