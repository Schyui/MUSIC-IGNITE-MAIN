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

public class help extends AppCompatActivity {

    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help);



        back = findViewById(R.id.backBtn);
        TextView aboutText = findViewById(R.id.aboutText);

        String htmlText = "<b>Help – Music Ignite</b><br><br>" +

                "Welcome to <b>Music Ignite</b>, your companion for learning music sheets and notes effectively. This section provides guidance on how to get the most out of the app.<br><br>" +

                "<b>Getting Started</b><br>" +
                "- Navigate through lessons using the menu at the bottom.<br>" +
                "- Use the quiz feature to test your knowledge and track progress.<br>" +
                "- Access your profile to review completed lessons and scores.<br><br>" +

                "<b>Features</b><br>" +
                "- Interactive music sheets with note highlighting.<br>" +
                "- Step-by-step tutorials for beginners and advanced learners.<br>" +
                "- Timed quizzes to challenge your reading skills.<br>" +
                "- Option to toggle sound on/off for practice.<br><br>" +

                "<b>Troubleshooting</b><br>" +
                "- If the app crashes or freezes, try restarting your device.<br>" +
                "- Ensure you have a stable internet connection for content updates.<br>" +
                "- For login or account issues, use the 'Forgot Password' option or contact support.<br><br>" +

                "<b>Feedback & Support</b><br>" +
                "- We welcome your feedback to improve Music Ignite.<br>" +
                "- Report bugs or suggest features by contacting us.<br><br>" +

                "<b>Contact Us</b><br>" +
                "📧 support@generalignite.com<br>" +
                "🌐 www.generalignite.com";



        aboutText.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
        back.setOnClickListener(v -> {
            finish();
        });






    }
}