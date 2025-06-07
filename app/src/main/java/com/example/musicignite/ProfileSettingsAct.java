package com.example.musicignite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingsAct extends AppCompatActivity {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_PROFILE_PIC_URI = "profile_pic_uri";

    private CircleImageView profilePic;
    private ImageView changePfp, back;
    private SharedPreferences prefs;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_settings);

        // Now get shared prefs inside onCreate
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

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
                                // Take persistable URI permission
                                try {
                                    getContentResolver().takePersistableUriPermission(
                                            selectedImageUri,
                                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    );
                                } catch (Exception e) {
                                    e.printStackTrace(); // Optional for debugging
                                }

                                // Save to SharedPreferences
                                prefs.edit().putString(KEY_PROFILE_PIC_URI, selectedImageUri.toString()).apply();
                                profilePic.setImageURI(selectedImageUri);
                            }
                        }
                    }
                }
        );


        changePfp.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);


        });

        back.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfilePic();
 
    }

    private void loadProfilePic() {

        String savedUriString = prefs.getString(KEY_PROFILE_PIC_URI, null);

        if (savedUriString != null && !savedUriString.isEmpty()) {
            try {
                Uri savedUri = Uri.parse(savedUriString);
                profilePic.setImageURI(savedUri);
            } catch (Exception e) { 
                profilePic.setImageResource(R.drawable.profileicon);
                Toast.makeText(this, "Error loading profile picture", Toast.LENGTH_SHORT).show();
            }
        } else {
            profilePic.setImageResource(R.drawable.profileicon);
        }
    }
}
