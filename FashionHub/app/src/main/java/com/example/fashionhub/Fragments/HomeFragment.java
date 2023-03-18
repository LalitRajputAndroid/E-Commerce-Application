package com.example.fashionhub.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fashionhub.Adapters.CategoryAdapter;
import com.example.fashionhub.Adapters.ProductAdapter;
import com.example.fashionhub.Modals.CategoryModal;
import com.example.fashionhub.Modals.ProductModal;
import com.example.fashionhub.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    FragmentHomeBinding binding ;
    CategoryAdapter adapter ;
    ProductAdapter productAdapter ;
    ArrayList<CategoryModal> arrayList ;
    ArrayList<ProductModal> productModals ;
    DatabaseReference reference ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentHomeBinding.inflate(inflater, container, false);

        categories();
        products();
        slideresAdd();



       return binding.getRoot();
    }

    private void slideresAdd() {

        binding.sliderId.addData(new CarouselItem("https://jssors8.azureedge.net/demos/image-slider/img/px-beach-daylight-fun-1430675-image.jpg","caption here"));
        binding.sliderId.addData(new CarouselItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdUmK6fRPfPQIMyOxUOjiTnR2VYizvkkW-UxLhGoVJNobm5Id3fpRhsIGtPj3H06ujPb4&usqp=CAU","caption here"));
        binding.sliderId.addData(new CarouselItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSMCrmxaENxz2260ZzCIAJ_UAyjTzXK8EnetYoa4jRK_9Hx49BdmIE7RmHv5F3i8M4RR_U&usqp=CAU","caption here"));
        binding.sliderId.addData(new CarouselItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoHX4_cEgpm26cHWKMn6t__E_ruzVLr0x-vyl2OV3dmZLQjTjD3szpHqbPIgw01tlOQUc&usqp=CAU","caption here"));

    }

    public void categories(){

        GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        binding.categoryRecyclerId.setLayoutManager(manager);
        arrayList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Admin");
        reference.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    CategoryModal modal = snapshot1.getValue(CategoryModal.class);
                    arrayList.add(modal);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        adapter = new CategoryAdapter(getContext(),arrayList);
        binding.categoryRecyclerId.setAdapter(adapter);
    }

    public void products(){
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        binding.productRecyclerId.setLayoutManager(manager);
        productModals = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Admin");
        reference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productModals.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    ProductModal modal = snapshot1.getValue(ProductModal.class);
                    productModals.add(modal);
                }
                productAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        productAdapter = new ProductAdapter(getContext(),productModals);
        binding.productRecyclerId.setAdapter(productAdapter);
    }
}