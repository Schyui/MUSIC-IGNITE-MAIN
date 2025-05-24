package com.example.musicignite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class SettingsFragment extends Fragment {

    TextView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        logout = view.findViewById(R.id.logoutBtn);
        logout.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Logout!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });
        return view;
    }

}