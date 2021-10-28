package com.brian.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.brian.tienda.entities.ProductEntity;
import com.example.tienda.R;
import com.example.tienda.databinding.ActivityEditarBinding;
import com.example.tienda.databinding.ProductItemBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editar extends AppCompatActivity {
    private ProductEntity product;
    private FirebaseFirestore db;
    private ActivityEditarBinding editarbindig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editarbindig = ActivityEditarBinding.inflate(getLayoutInflater());
        View view =editarbindig.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        product= (ProductEntity) intent.getSerializableExtra("product");
        editarbindig.names.setText(product.getName());
        editarbindig.stocks.setText(String.valueOf(product.getStock()));
        editarbindig.prices.setText(String.valueOf(product.getPrice()));
        editarbindig.descripcions.setText(product.getDescripcion());

    }
    public  void updatep (View view){
        Map<String, Object> dataProduct = new HashMap<>();
        dataProduct.put("name", editarbindig.names.getText().toString());
        dataProduct.put("descripcion", editarbindig.descripcions.getText().toString());
        dataProduct.put("stock", Double.parseDouble(editarbindig.stocks.getText().toString()));
        dataProduct.put("price", Double.parseDouble(editarbindig.prices.getText().toString()));
          if(view.getId() == editarbindig.update.getId()){
              db.collection("products").document(product.getId()).update(dataProduct)
                      .addOnSuccessListener(unused -> {
                          Toast.makeText(getApplicationContext(), "Producto Actualizado", Toast.LENGTH_LONG).show();
                          Intent intent = new Intent(getApplicationContext(), productList.class);
                          startActivity(intent);
                          finish();
                      })
                      .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Actualizacion Fallida", Toast.LENGTH_LONG).show());
          }

          }
    }
