package com.example.musicignite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class SettingsFragment extends Fragment {

    TextView logout, terms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        logout = view.findViewById(R.id.logoutBtn);
        terms = view.findViewById(R.id.terms);
        logout.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Logout!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        terms.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), terms.class);
            startActivity(intent);
        });

    }
}