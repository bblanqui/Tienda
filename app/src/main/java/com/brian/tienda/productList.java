package com.brian.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.brian.tienda.entities.ProductEntity;
import com.example.tienda.R;
import com.example.tienda.databinding.ActivityProductListBinding;

import java.util.ArrayList;
import java.util.List;

public class productList extends AppCompatActivity {
    private ActivityProductListBinding productlistbinding;

    List<ProductEntity> product = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productlistbinding = ActivityProductListBinding.inflate(getLayoutInflater());
        View view = productlistbinding.getRoot();
        setContentView(view);
    }
}