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

    TextView Text1, Text2, Text3, Text4, Text5, Text6, Text7, Text8, Text9, Text10, Text11;
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
        Text1 = findViewById(R.id.text1);
        Text2 = findViewById(R.id.text2);
        Text3 = findViewById(R.id.text3);
        Text4 = findViewById(R.id.text4);
        Text5 = findViewById(R.id.text5);
        Text6 = findViewById(R.id.text6);
        Text7 = findViewById(R.id.text7);
        Text8 = findViewById(R.id.text8);
        Text9 = findViewById(R.id.text9);
        Text10 = findViewById(R.id.text10);
        Text11 = findViewById(R.id.text11);

        String htmlText1 = "• An <b>interval</b> measures the distance between two notes.";
        Text1.setText(Html.fromHtml(htmlText1, Html.FROM_HTML_MODE_LEGACY));
        String htmlText2 = "• We will first discuss <b>generic intervals</b>, which are measured on the staff.";
        Text2.setText(Html.fromHtml(htmlText2, Html.FROM_HTML_MODE_LEGACY));
        String htmlText3 = "• When two notes occupy the same line or space, they are a <b>first</b> (or a <b>prime</b>) apart.";
        Text3.setText(Html.fromHtml(htmlText3, Html.FROM_HTML_MODE_LEGACY));
        String htmlText4 = "• C–C#, D–D♭, and A#–A♭ are still <b>firsts</b>.";
        Text4.setText(Html.fromHtml(htmlText4, Html.FROM_HTML_MODE_LEGACY));
        String htmlText5 = "• C–D, D–E, and E–F are all <b>seconds</b>.";
        Text5.setText(Html.fromHtml(htmlText5, Html.FROM_HTML_MODE_LEGACY));
        String htmlText6 = "• C–E, D–F, and E–G are all <b>thirds</b>.";
        Text6.setText(Html.fromHtml(htmlText6, Html.FROM_HTML_MODE_LEGACY));
        String htmlText7 = "• C–F, D–G, and E–A are all <b>fourths</b>.";
        Text7.setText(Html.fromHtml(htmlText7, Html.FROM_HTML_MODE_LEGACY));
        String htmlText8 = "•  C–G, D–A, and E–B are all <b>fifths</b>.";
        Text8.setText(Html.fromHtml(htmlText8, Html.FROM_HTML_MODE_LEGACY));
        String htmlText9 = "• C–A, D–B, and E–C are all <b>sixths</b>.";
        Text9.setText(Html.fromHtml(htmlText9, Html.FROM_HTML_MODE_LEGACY));
        String htmlText10 = "• C–B, D–C, and E–D are all <b>sevenths</b>.";
        Text10.setText(Html.fromHtml(htmlText10, Html.FROM_HTML_MODE_LEGACY));
        String htmlText11 = "• C–C, D–D, and E–E are all <b>eighths</b>.";
        Text11.setText(Html.fromHtml(htmlText11, Html.FROM_HTML_MODE_LEGACY));
    }

        @Override
        public void onStart() {
            super.onStart();
            backBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, Learn_Rhythm.class);
                startActivity(intent);
            });

            NextBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, Learn_Rhythm_L2.class);
                startActivity(intent);
            });


        }
    }