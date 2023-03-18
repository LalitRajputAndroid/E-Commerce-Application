package com.example.fashionhub.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fashionhub.Adapters.CartAdapter;
import com.example.fashionhub.Modals.CartModal;
import com.example.fashionhub.Modals.ProductModal;
import com.example.fashionhub.R;
import com.example.fashionhub.databinding.FragmentCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentCartBinding binding ;
    CartAdapter cartAdapter ;
    ArrayList<CartModal> arrayList ;
    DatabaseReference reference ;
    CartModal modal;

    private int product_price,product_quntity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);

        arrayList = new ArrayList<>();

        Cart cart = TinyCartHelper.getCart();

//        for (Map.Entry<Item,Integer> entry : cart.)

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child("CartProducts").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    modal = snapshot1.getValue(CartModal.class);
                    arrayList.add(modal);

                    product_price = Integer.parseInt(modal.getPrice());
                    product_quntity = Integer.parseInt(modal.getQuntity());
                    double total = product_price*product_quntity;

                    System.out.println("total------"+total);

                    binding.carttotalId.setText("R.s "+String.valueOf(total));
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cartAdapter = new CartAdapter(getContext(),arrayList);
        binding.cartrecyclerViewId.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.cartrecyclerViewId.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        return binding.getRoot();
    }
}