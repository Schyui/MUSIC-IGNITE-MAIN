package com.example.musicignite;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Set;

public class terms extends AppCompatActivity {

    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_terms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.termsAct), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back = findViewById(R.id.backBtn);
        TextView aboutText = findViewById(R.id.aboutText);

        String htmlText = "<b>Terms and Conditions – Music Ignite</b><br><br>" +
                "<b>Effective Date:</b> May 24, 2025<br><br>" +

                "Welcome to <b>Music Ignite</b>, a mobile application developed by <b>General Ignite</b>. By using this app, you agree to the following terms and conditions. Please read them carefully.<br><br>" +

                "<b>1. Use of the App</b><br>" +
                "- You must be at least 13 years old to use Music Ignite.<br>" +
                "- You agree to use the app for learning and personal use only.<br>" +
                "- Do not use the app for illegal or harmful purposes.<br><br>" +

                "<b>2. Accounts</b><br>" +
                "- You are responsible for maintaining the security of your account.<br>" +
                "- Do not share your login credentials with others.<br>" +
                "- We may suspend or terminate accounts that violate these terms.<br><br>" +

                "<b>3. Content and Copyright</b><br>" +
                "- All lessons, music sheets, and tools provided in the app are owned by General Ignite or licensed to us.<br>" +
                "- You may not copy, share, or sell any content from Music Ignite without permission.<br>" +
                "- You retain ownership of content you upload, but you give us permission to display it within the app.<br><br>" +

                "<b>4. Privacy</b><br>" +
                "- We respect your privacy. Your personal information is stored securely and used only to improve your experience.<br>" +
                "- We do not sell or share your data with third parties without your consent.<br>" +
                "- Read our full Privacy Policy for details.<br><br>" +

                "<b>5. Changes to the App</b><br>" +
                "- We may update the app from time to time, including new features or removing old ones.<br>" +
                "- We are not responsible for any loss caused by app updates or downtime.<br><br>" +

                "<b>6. Termination</b><br>" +
                "- You may stop using the app at any time.<br>" +
                "- We may suspend or delete your account if you violate these terms.<br><br>" +

                "<b>7. Limitation of Liability</b><br>" +
                "- Music Ignite is provided “as is.” We do our best to ensure everything works well, but we can’t guarantee it will always be perfect.<br>" +
                "- We are not responsible for any damages or losses caused by using the app.<br><br>" +

                "<b>8. Contact Us</b><br>" +
                "If you have questions about these terms, feel free to contact us at:<br><br>" +
                "📧 support@generalignite.com<br>" +
                "🌐 www.generalignite.com";


        aboutText.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
        back.setOnClickListener(v -> {
            finish();
        });






    }
}