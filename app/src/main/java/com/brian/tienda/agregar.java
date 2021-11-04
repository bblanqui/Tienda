package com.brian.tienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.brian.tienda.entities.ProductEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.tienda.databinding.ActivityAgregarBinding;

import java.util.HashMap;
import java.util.Map;

import static android.service.controls.ControlsProviderService.TAG;

public class agregar extends AppCompatActivity {
    private FirebaseFirestore db;
    private ActivityAgregarBinding agregarBinding;
    Map<String, Object> user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agregarBinding = ActivityAgregarBinding.inflate(getLayoutInflater());
        View view =agregarBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
        user = new HashMap<>();



    }





    public  void  add (View view){

        if( agregarBinding.names.getText().toString().isEmpty()){

            showError(agregarBinding.names, "producto no puede ir vacio");

        }else if( agregarBinding.prices.getText().toString().isEmpty()){
            showError(agregarBinding.prices, "precio no puede ir vacio");

        }else if( agregarBinding.stocks.getText().toString().isEmpty()){
            showError(agregarBinding.stocks, "stock no puede ir vacio");

        }else  if( agregarBinding.descripcions.getText().toString().isEmpty()){
            showError(agregarBinding.descripcions, "descricion no puede ir vacia");
        }else {
            user.put("descripcion", agregarBinding.descripcions.getText().toString());
            user.put("name", agregarBinding.names.getText().toString());
            user.put("price",  Double.parseDouble(agregarBinding.prices.getText().toString()));
            user.put("stock", Double.parseDouble(agregarBinding.stocks.getText().toString()));
            db.collection("products")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Producto agregado", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), productList.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });

        }







    }

    public  void showError(EditText entrada, String texto){

        entrada.setError(texto);
        entrada.requestFocus();


    }
}