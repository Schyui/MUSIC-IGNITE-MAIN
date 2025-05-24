package com.example.musicignite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private Button learnBtn, practiceBtn, musicSheetBtn;
    private TextView username;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        learnBtn = view.findViewById(R.id.learnBtn);
        practiceBtn = view.findViewById(R.id.practiceBtn);
        musicSheetBtn = view.findViewById(R.id.musicSheetBtn);

        // Set username from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        // Set button listeners
        learnBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), Learn_Activity.class);
            startActivity(intent);
        });

        practiceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), Practice_Activity.class);
            startActivity(intent);
        });

        musicSheetBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MusicSheets_Activity.class);
            startActivity(intent);
        });

        return view;
    }
}
