package com.brian.tienda;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tienda.R;
import com.example.tienda.databinding.ActivityAddProductBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddProduct extends AppCompatActivity {
    private ActivityAddProductBinding addbinding;
    StorageReference storageReference;
    ProgressDialog prograssDialog;
    private Uri downloadUrl;
    private Uri imageUri;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addbinding= ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = addbinding.getRoot();
        setContentView(view);
        uploadImage();

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
                        Uri uri = data.getData();
                        if (uri != null){
                            addbinding.ivProduct.setImageURI(uri);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "no jala", Toast.LENGTH_LONG).show();
                    }
                }
            }

    );

    public void uploadImage(){
       prograssDialog = new ProgressDialog(this);
       prograssDialog.setTitle("agregamdo prodcto");
       prograssDialog.show();

       // obtener fecha  y organizar el formato
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/_MM_dd_HH_mm_ss",
                Locale.US
        );
        //OBTENER LA FECHA
        Date dateNow = new Date();
        String filenameImage = dateFormat.format(dateNow);
        storageReference = FirebaseStorage.getInstance().getReference(
                "products/"+filenameImage);
    }
    UploadTask uploadTask = storageReference.putFile(imageUri);
     Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
         @Override
         public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
             if (!task.isSuccessful()){
                 throw task.getException();
             }
             return  storageReference.getDownloadUrl();
         }
     }).addOnCompleteListener(new OnCompleteListener<Uri>() {
         @Override
         public void onComplete(@NonNull Task<Uri> task) {
             if (task.isSuccessful()){
                 downloadUrl= task.getResult();
                 addProduct();
             }
         }
     });
     public void addProduct(){
         String productName = addbinding.Pnames.getText().toString();
         String productPrice = addbinding.Pprice.getText().toString();
         String productStock = addbinding.Pstock.getText().toString();
         String productProducto = addbinding.Pproducto.getText().toString();
         Map<String, Object> product = new HashMap<>();
         product.put("name", productName);
         product.put("price", Double.parseDouble(productPrice));
         product.put("stock", Double.parseDouble(productStock));
         product.put("producto",productProducto);
         product.put("imagen",downloadUrl);

         db.collection("imagen")
                 .add(product)
                 .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                     @Override
                     public void onSuccess(DocumentReference documentReference) {
                         prograssDialog.dismiss();

                     }
                 });


     }
}