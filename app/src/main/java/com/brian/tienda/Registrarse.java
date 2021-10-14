package com.brian.tienda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tienda.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.service.controls.ControlsProviderService.TAG;

public class Registrarse extends AppCompatActivity {
    EditText usuario, correo, pass;
    Spinner roles;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        usuario = findViewById(R.id.usuario);
        correo = findViewById(R.id.correo);
        pass = findViewById(R.id.pass);
        usuario.requestFocus();
        roles=findViewById(R.id.rol);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.rol, android.R.layout.simple_spinner_item);
        roles.setAdapter(adapter);


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

            mAuth.createUserWithEmailAndPassword(correos, contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                               FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                                       .getCurrentUser().getUid()).setValue(usuarios,correos, contrasena)
                                       .addOnCompleteListener(new OnCompleteListener <void>(){});

                                Intent intentdo = new Intent(Registrarse.this, inicio.class);

                                startActivity(intentdo);
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Registrarse.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
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