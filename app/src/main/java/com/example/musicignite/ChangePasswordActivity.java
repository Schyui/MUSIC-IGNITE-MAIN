package com.example.musicignite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPassword, newPassword, old_password, new_password;
    Button saveBtn;
    ImageView backBtnPass, passwordToggle, passwordToggleNew;
    SharedPreferences prefs;
    boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        backBtnPass = findViewById(R.id.backBtnPass);
        oldPassword = findViewById(R.id.old_password);
        newPassword = findViewById(R.id.new_password);
        saveBtn = findViewById(R.id.save_password_btn);
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        passwordToggle = findViewById(R.id.password_toggle);
        passwordToggleNew= findViewById(R.id.password_toggle_new);
        passwordToggle.setOnClickListener(view -> {
            if (isPasswordVisible) {
                oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordToggle.setImageResource(R.drawable.unhide);
            } else {
                oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordToggle.setImageResource(R.drawable.hide);
            }
            oldPassword.setTypeface(Typeface.DEFAULT);
            oldPassword.setSelection(oldPassword.getText().length());
            isPasswordVisible = !isPasswordVisible;
        });
        passwordToggleNew.setOnClickListener(view -> {
            if (isPasswordVisible) {
                newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordToggleNew.setImageResource(R.drawable.unhide);
            } else {
                newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordToggleNew.setImageResource(R.drawable.hide);
            }
            newPassword.setTypeface(Typeface.DEFAULT);
            newPassword.setSelection(newPassword.getText().length());
            isPasswordVisible = !isPasswordVisible;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        saveBtn.setOnClickListener(v -> {
            String username = prefs.getString("usernameSet", null);
            String oldPass = oldPassword.getText().toString().trim();
            String newPass = newPassword.getText().toString().trim();

            if (username == null || oldPass.isEmpty() || newPass.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPass.length() < 6) {
                Toast.makeText(this, "New password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(username);

            userRef.get().addOnSuccessListener(snapshot -> {
                String currentPass = snapshot.child("password").getValue(String.class);
                String lastChangeDate = snapshot.child("lastPasswordChangeDate").getValue(String.class);
                Long changeCountLong = snapshot.child("passwordChangeCount").getValue(Long.class);
                long changeCount = changeCountLong == null ? 0L : changeCountLong;

                String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Calendar.getInstance().getTime());

                if (!oldPass.equals(currentPass)) {
                    Toast.makeText(this, "Old password is incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Reset count if last change wasn't today
                if (lastChangeDate == null || !lastChangeDate.equals(today)) {
                    changeCount = 0L;
                }

                if (changeCount >= 3) {
                    Toast.makeText(this, "You can only change your password 3 times per day", Toast.LENGTH_SHORT).show();
                    return;
                }

                long newChangeCount = changeCount + 1;

                userRef.child("password").setValue(newPass)
                        .addOnSuccessListener(aVoid -> {
                            userRef.child("lastPasswordChangeDate").setValue(today);
                            userRef.child("passwordChangeCount").setValue(newChangeCount);
                            Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show());

            });
        });

        backBtnPass.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileSettingsAct.class);
            startActivity(intent);
        });
    }
}
