package com.brian.tienda;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tienda.R;
import com.example.tienda.databinding.ActivityAddProductBinding;

public class AddProduct extends AppCompatActivity {
    private ActivityAddProductBinding addbinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addbinding= ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = addbinding.getRoot();
        setContentView(view);

    }


    public  void pickimage(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);

    }
    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();//obtiene la data
                        Uri ImageUri = data.getData();
                        if (ImageUri != null){
                            addbinding.ivProduct.setImageURI(ImageUri);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "no jala", Toast.LENGTH_LONG).show();
                    }
                }
            }

    );
}