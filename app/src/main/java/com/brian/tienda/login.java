package com.brian.tienda;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.brian.tienda.entities.ProductEntity;
import com.brian.tienda.entities.ShareEntity;
import com.example.tienda.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity implements Iinten{
    EditText correolog, contralog;
    ShareEntity users;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = FirebaseFirestore.getInstance();
        users = new ShareEntity();
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

            String correo = correolog.getText().toString();
            String contrasena = contralog.getText().toString();
            Matcher m = p.matcher(correo);

                if( correo.isEmpty() && contrasena.isEmpty()){


                    showError(contralog, "cotraseña no puede ir vacio");
                    showError(correolog, "correo no puede ir vacio");


            }else if(!m.find()){

                    showError(correolog, "correo invalido");
                    Toast.makeText(this, "verique @, sin espacios y el dominio", Toast.LENGTH_LONG).show();
                }



                else if(contrasena.isEmpty() || contrasena.length() < 8 ){


                    showError(contralog, "contrasena invalida");
                    Toast.makeText(this, "almenos 8 caracteres y  sin espacios", Toast.LENGTH_LONG).show();


            }

                else {



                    mAuth.signInWithEmailAndPassword(correo,contrasena)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        db.collection("dbcomputec").whereEqualTo("correo", correo )
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()){
                                                            for (QueryDocumentSnapshot documentSnapshots: task.getResult()){
                                                                users= documentSnapshots.toObject(ShareEntity.class);
                                                                break;
                                                            }
                                                            saveUserPreferences(getApplicationContext());
                                                            Intent intentdo = new Intent(getApplicationContext(), productList.class);

                                                            startActivity(intentdo);


                                                        }

                                                    }
                                                });


                                    } else {
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(login.this, "valide usuario y contraseña",
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


















    public void saveUserPreferences(Context context){



        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        // permite escribir data en las shared preferences
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("status", true);
        editor.putString("correo", users.getCorreo());
        editor.putString("rol", users.getRol());
        editor.putString("usuario", users.getUsuario());
        editor.commit();
    }




}