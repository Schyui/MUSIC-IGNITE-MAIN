package com.example.musicignite;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Learn_Intervals_L1 extends AppCompatActivity {
    Button NextBtn;
    ImageView backBtn;
    TextView[] textViews = new TextView[11];
    int[] Text = {
            R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6,
            R.id.text7, R.id.text8, R.id.text9, R.id.text10, R.id.text11
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_intervals_l1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        NextBtn = findViewById(R.id.nextBtn);
        String[] htmlTexts = {

                "• An <b>interval</b> measures the distance between two notes.",
                "• We will first discuss <b>generic intervals</b>, which are measured on the staff.",
                "• When two notes occupy the same line or space, they are a <b>first</b> (or a <b>prime</b>) apart.",
                "• C–C#, D–D♭, and A#–A♭ are still <b>firsts</b>.",
                "• C–D, D–E, and E–F are all <b>seconds</b>.",
                "• C–E, D–F, and E–G are all <b>thirds</b>.",
                "• C–F, D–G, and E–A are all <b>fourths</b>.",
                "•  C–G, D–A, and E–B are all <b>fifths</b>.",
                "• C–A, D–B, and E–C are all <b>sixths</b>.",
                "• C–B, D–C, and E–D are all <b>sevenths</b>.",
                "• C–C, D–D, and E–E are all <b>eighths</b>."
        };

        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = findViewById(Text[i]);
            textViews[i].setText(Html.fromHtml(htmlTexts[i], Html.FROM_HTML_MODE_LEGACY));
    }
}
        @Override
        public void onStart() {
            super.onStart();
            backBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, Learn_Intervals.class);
                startActivity(intent);
            });

            NextBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, Learn_Intervals_L2.class);
                startActivity(intent);
            });


        }
    }