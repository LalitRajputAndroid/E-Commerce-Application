package com.example.fashionhub.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionhub.Activitys.ProductDetail;
import com.example.fashionhub.Modals.ProductModal;
import com.example.fashionhub.R;
import com.example.fashionhub.databinding.ProductSinglrrowBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    Context context;
    ArrayList<ProductModal> productlist;

    public ProductAdapter(Context context, ArrayList<ProductModal> productlist) {
        this.context = context;
        this.productlist = productlist;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_singlrrow, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModal modal = productlist.get(position);

        holder.binding.pNameId.setText(modal.getpName());
        holder.binding.productdiscriptionId.setText(modal.getpDiscription());
        holder.binding.productpriceId.setText(modal.getpPrice());
        holder.binding.disPriceID.setText("R.s" + modal.getpDis_price());
        holder.binding.discountID.setText(modal.getpDicount() + "%off");
        Picasso.get().load(modal.getpImg()).into(holder.binding.productImgid);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra("Pname", modal.getpName());
                intent.putExtra("Pdiscription", modal.getpDiscription());
                intent.putExtra("Pprice", modal.getpPrice());
                intent.putExtra("Pdis_price", modal.getpDis_price());
                intent.putExtra("Pdiscount", modal.getpDicount());
                intent.putExtra("PId", modal.getProductID());
                intent.putExtra("Pimg", modal.getpImg());
                context.startActivity(intent);
            }
        });

        holder.binding.productLikeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.child("LikeProducts").child(FirebaseAuth.getInstance().getUid()).child(modal.getProductID()).setValue(modal).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onSuccess(Void unused) {
                        holder.binding.productLikeID.setBackgroundColor(R.color.teal_200);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ProductSinglrrowBinding binding;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProductSinglrrowBinding.bind(itemView);

        }
    }
}
