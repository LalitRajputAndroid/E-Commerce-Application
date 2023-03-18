package com.example.fashionhub.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fashionhub.Fragments.CartFragment;
import com.example.fashionhub.Modals.CartModal;
import com.example.fashionhub.Modals.ProductModal;
import com.example.fashionhub.R;
import com.example.fashionhub.databinding.ActivityProductDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    private ActivityProductDetailBinding binding ;
    DatabaseReference reference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toobarid);
        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String name = getIntent().getStringExtra("Pname");
        String discription = getIntent().getStringExtra("Pdiscription");
        String price = getIntent().getStringExtra("Pprice");
        String dis_price = getIntent().getStringExtra("Pdis_price");
        String discount = getIntent().getStringExtra("Pdiscount");
        String Pid = getIntent().getStringExtra("PId");
        String image = getIntent().getStringExtra("Pimg");

        Picasso.get().load(image).into(binding.singleproductImgId);
        binding.productDiscritionId.setText(discription);
        binding.nameID.setText(name);
        binding.productpriceId.setPaintFlags(binding.productpriceId.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        binding.productpriceId.setText(price);
        binding.disPriceID.setText("R.s"+dis_price);
        binding.discountID.setText(discount+"off");

        binding.addtocartBtnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uId = FirebaseAuth.getInstance().getUid();
                reference = FirebaseDatabase.getInstance().getReference("Users");
                CartModal modal = new CartModal(name,discription,price,Pid,uId,image,"1");
                reference.child("CartProducts").child(uId).child(Pid).setValue(modal).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ProductDetail.this, "Product Add", Toast.LENGTH_SHORT).show();
                        binding.addtocartBtnid.setText("Go to Cart");
                    }
                });
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}