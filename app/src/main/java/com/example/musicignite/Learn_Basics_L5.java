package com.example.musicignite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.Glide;

public class Learn_Basics_L5 extends AppCompatActivity {
    Button NextBtn, PreviousBtn;
    ImageView backBtn;
    PlayerView playerView1;
    ExoPlayer player1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_basics_l5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        playerView1 = findViewById(R.id.vid1);
        NextBtn = findViewById(R.id.nextBtn);
        PreviousBtn = findViewById(R.id.previousBtn);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView gif = findViewById(R.id.gif1);
        Glide.with(this)
                .load(R.drawable.basics_l5_gif1)
                .into(gif);
    }
    @Override
    public void onStart() {
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Learn_Basics_L5.this, Learn_Basics.class);
            startActivity(intent);
        });

        NextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Learn_Basics_L5.this, Learn_Basics_L6.class);
            startActivity(intent);
        });

        PreviousBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Learn_Basics_L5.this, Learn_Basics_L4.class);
            startActivity(intent);
        });

        initializePlayer();
    }
    @OptIn(markerClass = UnstableApi.class)
    private void initializePlayer() {
        // Player 1
        player1 = new ExoPlayer.Builder(this).build();
        playerView1.setPlayer(player1);
        playerView1.setControllerShowTimeoutMs(500);
        playerView1.setControllerAutoShow(true);

        Uri videoUri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.basics_lesson5_vid2);
        player1.setMediaItem(MediaItem.fromUri(videoUri1));
        player1.prepare();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (player1 != null) {
            player1.release();
            player1 = null;
        }
    }
}