package com.example.fashionhubadmin.Fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fashionhubadmin.Modal.CategoryModal;
import com.example.fashionhubadmin.Modal.ProductModal;
import com.example.fashionhubadmin.R;
import com.example.fashionhubadmin.databinding.FragmentAddproductBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddProductFragment extends Fragment {
    private FragmentAddproductBinding binding;
    private int Reqcode = 1;
    private Uri Pimguri;
    DatabaseReference databaseReference;
    FirebaseStorage storage ;

    ArrayAdapter adapter;
    List list;
    CategoryModal cmodal ;
    private ProgressDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddproductBinding.inflate(inflater, container, false);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setTitle("Uploading");
        dialog.setMessage("Upload Product Item....");

        binding.addproductImgid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Reqcode);
            }
        });

        binding.addProductbtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addproduct();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList();
        Query query = databaseReference.child("Admin").child("Categories");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    cmodal = snapshot1.getValue(CategoryModal.class);
                    list.add(String.valueOf(cmodal.getcName()));
                }
                adapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                binding.categorySpinnerid.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        return binding.getRoot();
    }

    private void addproduct() {
        String p_name = binding.productnameID.getEditText().getText().toString().trim();
        String p_price = binding.priceID.getEditText().getText().toString().trim();
        String p_discount = binding.discountID.getEditText().getText().toString().trim();
        String p_discription = binding.discriptionID.getEditText().getText().toString().trim();
        String category = binding.categorySpinnerid.getSelectedItem().toString();

        System.out.println("categori--" + category);
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        storage = FirebaseStorage.getInstance();

        if (p_name.isEmpty() || p_price.isEmpty() || p_discount.isEmpty() || p_discription.isEmpty()||category.isEmpty()) {
            Toast.makeText(getContext(), "fill the field", Toast.LENGTH_SHORT).show();
        } else if (Pimguri == null) {
            Toast.makeText(getContext(), "Select Image", Toast.LENGTH_SHORT).show();
        } else {

            long price = Long.parseLong(p_price);
            long discount = Long.parseLong(p_discount);
            long total = 100 - discount;

            long dis_price = (total * price) / 100;

            String Dp = String.valueOf(dis_price);

            dialog.show();
            String pId = databaseReference.push().getKey();

            StorageReference reference = storage.getReference().child("ProductsImage").child(pId);
            reference.putFile(Pimguri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful()){
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String pUri = uri.toString();
                                ProductModal modal = new ProductModal(p_name,p_price,p_discount,p_discription,pUri,category,Dp,pId);
                                databaseReference.child("Products").child(pId)
                                        .setValue(modal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                                Toast.makeText(getContext(), "Product Add", Toast.LENGTH_SHORT).show();
                                                binding.productnameID.getEditText().setText("");
                                                binding.priceID.getEditText().setText("");
                                                binding.discountID.getEditText().setText("");
                                                binding.discriptionID.getEditText().setText("");
                                            }
                                        });

                            }
                        });
                    }
                }
            });

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Reqcode) {
            if (data != null) {
                if (data.getData() != null) {
                    Pimguri = data.getData();
                    binding.addproductImgid.setImageURI(Pimguri);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}