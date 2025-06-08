package com.example.musicignite;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {

    TextView logout, terms, profileSettings, displayName, help;
    CircleImageView profilePic;

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_PROFILE_PIC_URI = "profile_pic_uri";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        logout = view.findViewById(R.id.logoutBtn);
        terms = view.findViewById(R.id.terms);
        profileSettings = view.findViewById(R.id.profileSettings);
        profilePic = view.findViewById(R.id.profilePic); // connect the image view
        displayName = view.findViewById(R.id.displayName);
        help = view.findViewById(R.id.help);

        loadProfilePic(); // Load initially when view is created

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefName = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String name = prefName.getString("nameSet", "defaultName");
        displayName.setText(name);
        logout.setOnClickListener(v -> {
            Context context = getContext();
            if (context != null) {
                SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                prefs.edit().putBoolean("is_logged_in", true).apply();

            }

            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });

        profileSettings.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProfileSettingsAct.class);
            startActivity(intent);
        });

        terms.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), terms.class);
            startActivity(intent);
        });
        help.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), help.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfilePic();
    }
    public void loadProfilePic() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String uriString = prefs.getString(KEY_PROFILE_PIC_URI, null);

        if (uriString != null) {
            try {
                Uri imageUri = Uri.parse(uriString);
                profilePic.setImageURI(null); // clear cache
                profilePic.setImageURI(imageUri);
            } catch (Exception e) {
                profilePic.setImageResource(R.drawable.profileicon);
            }
        } else {
            profilePic.setImageResource(R.drawable.profileicon);
        }
    }

}
