package com.brian.tienda;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Validaciones   extends AppCompatActivity {

    EditText correos, contrasenas, usuarios;


    public Validaciones(EditText correos, EditText contrasenas) {

        String correo = correos.getText().toString();
        String contrasena = contrasenas.getText().toString();


        if (correo.isEmpty() || !correo.contains("@")) {

            showError(correos, "correo invalido");
        } else if (contrasena.isEmpty() || contrasena.length() < 3) {

            showError(contrasenas, "contrasena invalida");


        }
    }

    public Validaciones(EditText usuario, EditText correos, EditText contrasenas) {

        String usuarios = usuario.getText().toString();
        String correo = correos.getText().toString();
        String contrasena = contrasenas.getText().toString();


        if (usuarios.isEmpty() || usuarios.length() < 3) {

            showError(usuario, "usuario invalido");
        } else if (correo.isEmpty() || !correo.contains("@")) {

            showError(correos, "correo invalido");


        } else if (contrasena.isEmpty() || contrasena.length() < 3) {
            showError(contrasenas, "contraseña invalida");

        }
    }


    public void showError(EditText entrada, String texto) {

        entrada.setError(texto);
        entrada.requestFocus();


    }

}