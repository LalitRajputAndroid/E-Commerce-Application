package com.example.fashionhub.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fashionhub.Activitys.MainActivity;
import com.example.fashionhub.Modals.UserModal;
import com.example.fashionhub.R;
import com.example.fashionhub.databinding.FragmentSingupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SingupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SingupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingupFragment newInstance(String param1, String param2) {
        SingupFragment fragment = new SingupFragment();
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

    FragmentSingupBinding binding ;
    FirebaseAuth auth ;
    DatabaseReference reference ;
    ProgressDialog dialog ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSingupBinding.inflate(inflater, container, false);
        SharedPreferences preferences = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        boolean check = preferences.getBoolean("flag",false);
        if (check){
            Toast.makeText(getContext(), "MAin", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class));
        }

        binding.signinbuttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupauth();
            }
        });


        binding.logintextId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.authFramelayout_ID,new LoginFragment()).commit();
            }
        });
        return binding.getRoot();
    }

    public void signupauth(){
        String name = binding.signinFullnameId.getText().toString().trim();
        String email = binding.signinEmailId.getText().toString().trim();
        String password = binding.signinPasswordId.getText().toString().trim();

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setTitle("Register");
        dialog.setMessage("Creating a Account...");

        if (name.isEmpty()||email.isEmpty()||password.isEmpty()){
            binding.signinFullnameId.setError("Enter a Name");
            binding.signinEmailId.setError("Emter a Email");
            binding.signinPasswordId.setError("Enter a Password");
        }else {

            auth = FirebaseAuth.getInstance();
            reference = FirebaseDatabase.getInstance().getReference("Users");

            dialog.show();
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    dialog.dismiss();
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Signup Success", Toast.LENGTH_SHORT).show();

                        String uid = auth.getUid();
                        UserModal modal = new UserModal(name,email,password,uid);
                        reference.child("LoginUser").child(uid).setValue(modal);

                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.authFramelayout_ID,new LoginFragment()).commit();

                        SharedPreferences preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("flag",true);
                        editor.apply();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Error","exception"+e);
                }
            });
        }
    }
}