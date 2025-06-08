package com.example.musicignite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PerfectPitch extends AppCompatActivity {

    ImageView backBtn;
    Button TimeRushBtn, AllInBtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfect_pitch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        TimeRushBtn = findViewById(R.id.TimeRushBtn);
        AllInBtn = findViewById(R.id.allInBtn);


    }

    @Override
    protected void onStart(){
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Practice_Activity.class);
            startActivity(intent);
        });

        TimeRushBtn.setOnClickListener(v -> timeOptions());

        AllInBtn.setOnClickListener(v -> {
            Intent intent = new Intent (this, AllIn.class);
            startActivity(intent);
        });

    }

    private void timeOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.time_options, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // back btn ng drawable
        ImageView timeOptionsBackBtn = dialogView.findViewById(R.id.timeOptionsBackBtn);
        timeOptionsBackBtn.setOnClickListener(v -> dialog.dismiss());



        Button min1 = dialogView.findViewById(R.id.min1btn);
        Button min3 = dialogView.findViewById(R.id.min3btn);
        Button min5 = dialogView.findViewById(R.id.min5btn);
        Button min10 = dialogView.findViewById(R.id.min10btn);

        // mga click listeners ng btn
        min1.setOnClickListener(v -> {
            startTimeRush(1);
            dialog.dismiss();
        });

        min3.setOnClickListener(v -> {
            startTimeRush(3);
            dialog.dismiss();
        });

        min5.setOnClickListener(v -> {
            startTimeRush(5);
            dialog.dismiss();
        });

        min10.setOnClickListener(v -> {
            startTimeRush(10);
            dialog.dismiss();
        });

        dialog.show();
    }

    // method nung mga timers
    private void startTimeRush(int minutes) {
        Intent intent = new Intent(this, TimeRush.class);
        intent.putExtra("timeLimit", minutes); // pag pasa nung selected time
        startActivity(intent);
    }
}
