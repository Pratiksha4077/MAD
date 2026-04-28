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

public class MenFragment extends Fragment {

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_men, container, false);

        // Click listeners for Men's services
        view.findViewById(R.id.c1).setOnClickListener(v -> openService("Man", "Haircut", R.drawable.m_haircut));
        view.findViewById(R.id.c2).setOnClickListener(v -> openService("Man", "Beard Shaving", R.drawable.m_shav));
        view.findViewById(R.id.c3).setOnClickListener(v -> openService("Man", "Hair Styling", R.drawable.m_style));
        view.findViewById(R.id.c4).setOnClickListener(v -> openService("Man", "Hair Spa", R.drawable.m_spa));
        view.findViewById(R.id.c5).setOnClickListener(v -> openService("Man", "Head Massage", R.drawable.m_massage));
        view.findViewById(R.id.c6).setOnClickListener(v -> openService("Man", "Hair Coloring", R.drawable.m_color));
        return view;
    }

    private void openService(String gender, String serviceName, int imageRes) {
        Intent intent = new Intent(getActivity(), ServiceListActivity.class);
        intent.putExtra("GENDER", gender);
        intent.putExtra("SERVICE_NAME", serviceName);
        intent.putExtra("IMAGE", imageRes); // ✅ PASS IMAGE
        startActivity(intent);
    }
}