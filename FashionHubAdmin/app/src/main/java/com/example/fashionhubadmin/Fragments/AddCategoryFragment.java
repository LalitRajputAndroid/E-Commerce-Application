package com.example.fashionhubadmin.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fashionhubadmin.Modal.CategoryModal;
import com.example.fashionhubadmin.R;
import com.example.fashionhubadmin.databinding.FragmentAddcategoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddCategoryFragment extends Fragment {
    private FragmentAddcategoryBinding binding;
    private int Reqcode = 2;
    private Uri imguri;

    FirebaseStorage storage;
    DatabaseReference database;
    private ProgressDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddcategoryBinding.inflate(inflater, container, false);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setTitle("Uploading");
        dialog.setMessage("Upload category Item....");
        binding.addcategoryImgid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Reqcode);
            }
        });

        binding.addCategorybtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategories();
            }
        });


        return binding.getRoot();
    }

    private void addCategories() {
        String name = binding.categorynameID.getEditText().getText().toString().trim();

        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();


        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Enter Category", Toast.LENGTH_SHORT).show();
        } else if (imguri == null) {
            Toast.makeText(getContext(), "Select category Image", Toast.LENGTH_SHORT).show();
        } else {
            String cid = database.push().getKey();
            dialog.show();
            StorageReference reference = storage.getReference().child("CategoryImage").child(cid);
            reference.putFile(imguri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String pimg = uri.toString();
                                        CategoryModal modal = new CategoryModal(name, pimg,cid);

                                        database.child("Admin").child("Categories").child(cid).setValue(modal)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialog.dismiss();
//                                                        FragmentManager manager = getFragmentManager();
//                                                        FragmentTransaction transaction = manager.beginTransaction();
//                                                        transaction.replace(R.id.nav_host_fragment_content_main, new HomeFragment()).commit();
                                                        Toast.makeText(getContext(), "Category Add", Toast.LENGTH_SHORT).show();
                                                        binding.categorynameID.getEditText().setText("");
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialog.dismiss();
                                                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
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
                    imguri = data.getData();
                    binding.addcategoryImgid.setImageURI(imguri);
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