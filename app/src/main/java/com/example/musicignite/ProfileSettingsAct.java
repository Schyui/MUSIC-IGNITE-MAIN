package com.example.musicignite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingsAct extends AppCompatActivity {

    CircleImageView profilePic;
    ImageView changePfp, back;

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_PROFILE_PIC_URI = "profile_pic_uri";

    ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_settings);


        profilePic = findViewById(R.id.profilePic);
        changePfp = findViewById(R.id.changePfp);
        back = findViewById(R.id.backBtn);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImageUri = data.getData();
                            if (selectedImageUri != null) {
                                // No persistable URI permission for ACTION_PICK
                                profilePic.setImageURI(selectedImageUri);
                                getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                                        .edit()
                                        .putString(KEY_PROFILE_PIC_URI, selectedImageUri.toString())
                                        .apply();
                            }
                        }
                    }
                }
        );

        changePfp.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");  // Show only images
            imagePickerLauncher.launch(intent);
        });

        back.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedUriString = prefs.getString(KEY_PROFILE_PIC_URI, null);

        if (savedUriString != null) {
            try {
                Uri savedUri = Uri.parse(savedUriString);
                profilePic.setImageURI(savedUri);
            } catch (Exception e) {
                profilePic.setImageResource(R.drawable.profileicon);
            }
        } else {
            profilePic.setImageResource(R.drawable.profileicon);
        }
    }
}
