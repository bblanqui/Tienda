package com.brian.tienda.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brian.tienda.editar;
import com.brian.tienda.entities.ProductEntity;
import com.brian.tienda.splash;
import com.bumptech.glide.Glide;
import com.example.tienda.R;
import com.example.tienda.databinding.ProductItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductAdacter extends RecyclerView.Adapter<ProductAdacter.ProductViewHolder> {

    private Context context;
    private ProductItemBinding productItemBinding;
    private ArrayList<ProductEntity> products;
    private FirebaseFirestore db;
    private splash roles;
   public ProductAdacter(Context context, ArrayList<ProductEntity> products, FirebaseFirestore db){

       this.context = context;
       this.products= products;
       this.db = db;
   }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
      productItemBinding = ProductItemBinding.inflate(LayoutInflater.from(context));
        return new ProductViewHolder(productItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ProductAdacter.ProductViewHolder holder, int position) {
      ProductEntity product = products.get(position);
      holder.itemBinding.name.setText(product.getName());
      holder.itemBinding.prices.setText(String.valueOf(product.getPrice()));
      holder.itemBinding.stock.setText(String.valueOf(product.getStock()));
      holder.itemBinding.descri.setText(product.getDescripcion());
      Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_images_rafiki)
                .error(R.drawable.imagen)
                .circleCrop()
                .into(holder.itemBinding.imageView18);







      splash r = new splash();

        if(r.rol(context).equals("cliente")){
            holder.itemBinding.delete.setVisibility(View.INVISIBLE);
            holder.itemBinding.edit.setVisibility(View.INVISIBLE);
            holder.itemBinding.imageView15.setVisibility(View.INVISIBLE);
        }

      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
              db.collection("products").document(product.getId()).delete().addOnSuccessListener(
                      new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void unused) {
                              Toast.makeText(context, "eliminado correctamente", Toast.LENGTH_LONG).show();
                              products.remove(holder.getAdapterPosition());
                              notifyDataSetChanged();
                          }
                      }
              ).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull @NotNull Exception e) {
                      Toast.makeText(context, "fallo eliminar",Toast.LENGTH_LONG).show();
                  }
              });
          }
      });
      builder.setNegativeButton("calcelar", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {

          }
      });
      holder.itemBinding.delete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            builder.setMessage("desea eliminar el producto?");
            builder.create().show();

          }
      });

      holder.itemBinding.edit.setOnClickListener(view -> {
          Intent intent = new Intent(context, editar.class);
          intent.putExtra("product", product);
          context.startActivity(intent);
      });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
       ProductItemBinding itemBinding;
        public ProductViewHolder(@NonNull ProductItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
