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

public class Learn_ScalesAndSignatures_L4 extends AppCompatActivity {
    Button NextBtn, PreviousBtn;
    ImageView backBtn;
    TextView text, text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_scales_l4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        NextBtn = findViewById(R.id.nextBtn);
        PreviousBtn = findViewById(R.id.previousBtn);
        text = findViewById(R.id.l1_text11);
        text2 = findViewById(R.id.modified_text17);

        String htmlText = "• You can remember this order by using the following saying: &quot;<b>B</b>attle <b>E</b>nds <b>A</b>nd <b>D</b>own <b>G</b>oes <b>C</b>harles' <b>F</b>ather&quot;.";
        text.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
        String htmlText2 = "• You can remember this order by using the following saying: &quot;<b>F</b>ather <b>C</b>harles <b>A</b>nd <b>G</b>oes <b>D</b>own <b>A</b>nd <b>E</b>nds <b>B</b>attle&quot;.";
        text2.setText(Html.fromHtml(htmlText2, Html.FROM_HTML_MODE_LEGACY));
    }

    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures.class);
            startActivity(intent);
        });

        NextBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures_L5.class);
            startActivity(intent);
        });

        PreviousBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Learn_ScalesAndSignatures_L3.class);
            startActivity(intent);
        });


    }
}