package com.example.musicignite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingsAct extends AppCompatActivity {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_PROFILE_PIC_URI = "profile_pic_uri";

    private CircleImageView profilePic;
    private ImageView changePfp, back;
    private SharedPreferences prefs;

    TextView usernameDisplay, emailDisplay;
    EditText EditDisplay, EditUsername;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_settings);

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        profilePic = findViewById(R.id.profilePic);
        changePfp = findViewById(R.id.changePfp);
        back = findViewById(R.id.backBtn);
        EditDisplay = findViewById(R.id.EditDisplay);
        usernameDisplay = findViewById(R.id.usernameDisplay);
        emailDisplay = findViewById(R.id.emailDisplay);
        EditDisplay.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String newName = EditDisplay.getText().toString().trim();
                if (!newName.isEmpty()) {
                    updateDisplayNameInFirebase(newName);
                }
            }
        });
        EditDisplay.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String newName = EditDisplay.getText().toString().trim();
                if (!newName.isEmpty()) {
                    updateDisplayNameInFirebase(newName); // your function to update Firebase
                } else {
                    Toast.makeText(this, "Display name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                return true; // handled
            }
            return false;
        });


        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImageUri = data.getData();
                            if (selectedImageUri != null) {
                                try {
                                    getContentResolver().takePersistableUriPermission(
                                            selectedImageUri,
                                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    );
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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

        back.setOnClickListener(v -> {

            Intent intent = new Intent(ProfileSettingsAct.this, MainActivity.class);
            intent.putExtra("open_fragment", "my_fragment");
            startActivity(intent);

        });

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

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String name = prefs.getString("nameSet", "defaultName");
        String uname = prefs.getString("usernameSet", "defaultUname");
        String email = prefs.getString("emailSet","defaultEmail");
        usernameDisplay.setText("@" + uname);
        EditDisplay.setText(name);
        emailDisplay.setText("Email: "+email);
        ImageView changePassword = findViewById(R.id.ChangePassword);
        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileSettingsAct.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

    }

    private void updateDisplayNameInFirebase(String newName) {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = prefs.getString("usernameSet", null);

        if (username == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(username);
        userRef.child("name").setValue(newName)
                .addOnSuccessListener(aVoid -> {
                    prefs.edit().putString("nameSet", newName).apply();
                    Toast.makeText(ProfileSettingsAct.this, "Display name updated", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ProfileSettingsAct.this, "Failed to update display name", Toast.LENGTH_SHORT).show();
                });
    }
}