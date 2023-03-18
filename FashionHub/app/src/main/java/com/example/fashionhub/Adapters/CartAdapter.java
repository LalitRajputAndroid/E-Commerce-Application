package com.example.fashionhub.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionhub.Modals.CartModal;
import com.example.fashionhub.Modals.ProductModal;
import com.example.fashionhub.R;
import com.example.fashionhub.databinding.CartSinglerowBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<CartModal> list ;

    public CartAdapter(Context context, ArrayList<CartModal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_singlerow,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CartModal modal = list.get(position);

        Picasso.get().load(modal.getPimg()).into(holder.binding.productImgid);
        holder.binding.productdiscriptionId.setText(modal.getPdis());
        holder.binding.priceID.setText(modal.getPrice());
        holder.binding.pNameId.setText(modal.getPname());
        holder.binding.quntityId.setText(modal.getQuntity());

        holder.binding.increaseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int cout= Integer.parseInt(holder.binding.quntityId.getText().toString());
                int price= Integer.parseInt(holder.binding.priceID.getText().toString());
                cout++;
                holder.binding.quntityId.setText(String.valueOf(cout));
                int c= Integer.parseInt(holder.binding.quntityId.getText().toString());
//                int tp=price*c;
//                holder.plprice.setText(String.valueOf(tp));
            }
        });

        holder.binding.decreaseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cout = Integer.parseInt(holder.binding.quntityId.getText().toString());
                int price= Integer.parseInt(holder.binding.priceID.getText().toString());
                if (cout != 1) {
                    cout--;
                    holder.binding.quntityId.setText(String.valueOf(cout));
                    int c= Integer.parseInt(holder.binding.quntityId.getText().toString());
//                    int tp=price*c;
//                    holder.plprice.setText(String.valueOf(tp));
                }
                else
                {
//                    holder.plprice.setText(cartModel.getProductDiscountPrice());
                }
            }
        });

        holder.binding.deletetocartId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                reference.child("CartProducts").child(modal.getUId()).child(modal.getProduct_Id()).removeValue();
                notifyDataSetChanged();
                list.remove(position);
                list.clear();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        CartSinglerowBinding binding ;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CartSinglerowBinding.bind(itemView);
        }
    }
}
