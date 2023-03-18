package com.example.fashionhub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionhub.Modals.CategoryModal;
import com.example.fashionhub.R;
import com.example.fashionhub.databinding.CategorisSinglerowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    Context context;
    ArrayList<CategoryModal> categorieslist ;

    public CategoryAdapter(Context context, ArrayList<CategoryModal> categorieslist) {
        this.context = context;
        this.categorieslist = categorieslist;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.categoris_singlerow,parent,false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        CategoryModal category = categorieslist.get(position);

        holder.binding.categorinameId.setText(category.getcName());
        Picasso.get().load(category.getcImage()).into(holder.binding.categoriImgId);

//        holder.binding.categoriImgId.setBackgroundColor(Color.parseColor(category.getC_bgcolor()));
    }

    @Override
    public int getItemCount() {
        return categorieslist.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategorisSinglerowBinding binding ;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategorisSinglerowBinding.bind(itemView);
        }
    }
}
