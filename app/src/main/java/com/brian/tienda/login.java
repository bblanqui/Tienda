package com.brian.tienda;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tienda.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity implements Iinten{
    EditText correolog, contralog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        correolog= findViewById(R.id.correolog);
        contralog= findViewById(R.id.contralog);
        correolog.requestFocus();


    }

    public void views(View view) {
        Intent intentdo = new Intent(this, singups.class);

        startActivity(intentdo);
    }

    public void  validators (View view){
            Pattern p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
            Pattern c = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
            String correo = correolog.getText().toString();
            String contrasena = contralog.getText().toString();
            Matcher m = p.matcher(correo);
            Matcher M = c.matcher(contrasena);
                if( correo.isEmpty()){

                showError(correolog, "correo no puede ir vacio");

            }else if(!m.find()){

                    showError(correolog, "correo invalido");

                }



                else if(contrasena.isEmpty() || contrasena.length() < 8 || Pattern.compile(" {1,}").matcher(contralog.getText().toString()).find() ){


                    showError(contralog, "contrasena invalida");
                    Toast.makeText(this, "almenos un dijito, un caracter espacial @ y sin espacios", Toast.LENGTH_LONG);


            }else if (!M.find()){

                    Toast.makeText(this, "almenos un dijito, un caracter espacial @ y sin espacios", Toast.LENGTH_LONG);
                    showError(contralog, "contrasena invalida");

                }




                else {

                Intent intentdo = new Intent(this, inicio.class);

                startActivity(intentdo);

            }

    }

    public  void showError(EditText entrada, String texto){

        entrada.setError(texto);
        entrada.requestFocus();


    }

}