package com.example.rolex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WomenFragment extends Fragment {

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_women, container, false);

        // Setup individual card clicks
        view.findViewById(R.id.c1).setOnClickListener(v -> openService("Woman", "Facial", R.drawable.w_facial));
        view.findViewById(R.id.c2).setOnClickListener(v -> openService("Woman", "Hair Spa", R.drawable.w_spa));
        view.findViewById(R.id.c3).setOnClickListener(v -> openService("Woman", "Pedicure", R.drawable.w_pedi));
        view.findViewById(R.id.c4).setOnClickListener(v -> openService("Woman", "Manicure", R.drawable.w_meni)); // FIX spelling
        view.findViewById(R.id.c5).setOnClickListener(v -> openService("Woman", "Head Massage", R.drawable.w_head));
        view.findViewById(R.id.c6).setOnClickListener(v -> openService("Woman", "Wax", R.drawable.w_wax));
        return view;
    }

    private void openService(String gender, String serviceName, int imageRes) {
        Intent intent = new Intent(getActivity(), ServiceListActivity.class);
        intent.putExtra("GENDER", gender);
        intent.putExtra("SERVICE_NAME", serviceName);
        intent.putExtra("IMAGE", imageRes);   // ✅ PASS IMAGE
        startActivity(intent);
    }
}