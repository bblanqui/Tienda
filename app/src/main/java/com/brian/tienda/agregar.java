package com.brian.tienda;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.brian.tienda.entities.ProductEntity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.tienda.databinding.ActivityAgregarBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.service.controls.ControlsProviderService.TAG;

public class agregar extends AppCompatActivity {
    private FirebaseFirestore db;
    private ActivityAgregarBinding agregarBinding;
    Map<String, Object> user;
    StorageReference storageReference;
    ProgressDialog prograssDialog;
    private Uri downloadUrl, imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agregarBinding = ActivityAgregarBinding.inflate(getLayoutInflater());
        View view =agregarBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
        user = new HashMap<>();



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
                            imageUri = uri;
                            agregarBinding.imgProduct.setImageURI(uri);
                        }
                    }else {

                        Toast.makeText(getApplicationContext(), "imagen no seleccionada", Toast.LENGTH_LONG).show();
                    }
                }
            }

    );

    public void uploadImage(View view){


        if( agregarBinding.names.getText().toString().isEmpty()){

            showError(agregarBinding.names, "producto no puede ir vacio");

        }else if( agregarBinding.prices.getText().toString().isEmpty()){
            showError(agregarBinding.prices, "precio no puede ir vacio");

        }else if( agregarBinding.stocks.getText().toString().isEmpty()){
            showError(agregarBinding.stocks, "stock no puede ir vacio");

        }else  if( agregarBinding.descripcions.getText().toString().isEmpty()){
            showError(agregarBinding.descripcions, "descricion no puede ir vacia");
        }else if(imageUri==null){

            Toast.makeText(getApplicationContext(), "por favor seleccionar una imagen", Toast.LENGTH_LONG).show();
        }else {
            prograssDialog = new ProgressDialog(this);
            prograssDialog.setTitle("agregando producto");
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

                           user.put("descripcion", agregarBinding.descripcions.getText().toString());
                           user.put("name", agregarBinding.names.getText().toString());
                           user.put("price",  Double.parseDouble(agregarBinding.prices.getText().toString()));
                           user.put("stock", Double.parseDouble(agregarBinding.stocks.getText().toString()));
                           user.put("imageUrl",downloadUrl.toString());
                           db.collection("products")
                                   .add(user)
                                   .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                       @Override
                                       public void onSuccess(DocumentReference documentReference) {
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

            });

        }




    }




    public  void showError(EditText entrada, String texto){

        entrada.setError(texto);
        entrada.requestFocus();


    }
}