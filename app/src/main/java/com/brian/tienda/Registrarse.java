package com.brian.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tienda.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrarse extends AppCompatActivity {
    EditText usuario, correo, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        usuario = findViewById(R.id.usuario);
        correo = findViewById(R.id.correo);
        pass = findViewById(R.id.pass);
        usuario.requestFocus();
    }

    public void views(View view) {
        Intent intentdo = new Intent(this, singups.class);

        startActivity(intentdo);
    }

    public void validaciones(View view) {
        Pattern p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        Pattern c = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

        String usuarios = usuario.getText().toString();
        String correos = correo.getText().toString();
        String contrasena = pass.getText().toString();
        Matcher m = p.matcher(correos);
        Matcher M = c.matcher(contrasena);


        if (usuarios.isEmpty() || usuarios.length() < 3 || Pattern.compile(" {1,}").matcher(usuario.getText().toString()).find()) {

            showError(usuario, "usuario invalido");
        } else if (correos.isEmpty() || !correos.contains("@") || correo.length() < 3 || Pattern.compile(" {1,}").matcher(correo.getText().toString()).find()){

            showError(correo, "correo invalido");


        }else if(!m.find()){

            showError(correo, "correo invalido");

        } else if (contrasena.isEmpty() || contrasena.length() < 8 || Pattern.compile(" {1,}").matcher(pass.getText().toString()).find())  {
            showError(pass, "contraseña invalida");

        }else if (!M.find()){

            Toast.makeText(this, "almenos un dijito, un caracter espacial @ y sin espacios", Toast.LENGTH_LONG);
            showError(pass, "contrasena invalida");

        }else {

            Intent intentdo = new Intent(this, inicio.class);

            startActivity(intentdo);
        }


    }


    public  void showError(EditText entrada, String texto){

        entrada.setError(texto);
        entrada.requestFocus();


    }

}