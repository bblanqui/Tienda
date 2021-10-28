package com.brian.tienda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.brian.tienda.Adapter.ProductAdacter;
import com.brian.tienda.entities.ProductEntity;
import com.example.tienda.R;
import com.example.tienda.databinding.ActivityProductListBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class productList extends AppCompatActivity {
    private ActivityProductListBinding productlistbinding;
    private FirebaseFirestore db;
    ProductAdacter productAdacter;

    ArrayList<ProductEntity> product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        productlistbinding = ActivityProductListBinding.inflate(getLayoutInflater());
        View view = productlistbinding.getRoot();
        setContentView(view);
        db=FirebaseFirestore.getInstance();
        product = new ArrayList<>();
        productAdacter = new ProductAdacter(this, product, db);
        productlistbinding.rvProducts.setHasFixedSize(true);
        productlistbinding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        productlistbinding.rvProducts.setAdapter(productAdacter);

        getProducts();

    }

    public void getProducts(){
        db.collection("products").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_LONG).show();
                    return;
                }
                for (DocumentChange dc: value.getDocumentChanges()){
                    if(dc.getType()== DocumentChange.Type.ADDED){
                        product.add(dc.getDocument().toObject(ProductEntity.class));
                    }
                }
                productAdacter.notifyDataSetChanged();
            }
        });
    }
}