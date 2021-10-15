package com.brian.tienda;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tienda.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity implements Iinten{
    EditText correolog, contralog;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        correolog= findViewById(R.id.correolog);
        contralog= findViewById(R.id.contralog);
        correolog.requestFocus();
        mAuth = FirebaseAuth.getInstance();

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
                if( correo.isEmpty() && contrasena.isEmpty()){


                    showError(contralog, "cotraseña no puede ir vacio");
                    showError(correolog, "correo no puede ir vacio");


            }else if(!m.find()){

                    showError(correolog, "correo invalido");
                    Toast.makeText(this, "verique @, sin espacios y el dominio", Toast.LENGTH_LONG).show();
                }



                else if(contrasena.isEmpty() || contrasena.length() < 8 || Pattern.compile(" {1,}").matcher(contralog.getText().toString()).find() ){


                    showError(contralog, "contrasena invalida");
                    Toast.makeText(this, "almenos un dijito, un caracter espacial @ y sin espacios", Toast.LENGTH_LONG).show();


            }else if (!M.find()){

                    Toast.makeText(this, "almenos un dijito, un caracter espacial @ y sin espacios", Toast.LENGTH_LONG).show();
                    showError(contralog, "contrasena invalida");

                }




                else {



                    mAuth.signInWithEmailAndPassword(correo,contrasena)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(login.this, "Authentication ok.",
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
            }

    }

    public  void showError(EditText entrada, String texto){

        entrada.setError(texto);
        entrada.requestFocus();


    }

}