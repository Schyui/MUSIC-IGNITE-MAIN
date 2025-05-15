package com.example.musicignite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

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



public class Learn_Basics_L4 extends AppCompatActivity {

    ImageView backBtn;
    PlayerView playerView1, playerView2;
    ExoPlayer player1, player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_basics_l4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        playerView1 = findViewById(R.id.vid1);
        playerView2 = findViewById(R.id.vid2);


    }

    @Override
    public void onStart() {
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Learn_Basics_L4.this, Learn_Basics.class);
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

        Uri videoUri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fixed2);
        player1.setMediaItem(MediaItem.fromUri(videoUri1));
        player1.prepare();

        // Player 2
        player2 = new ExoPlayer.Builder(this).build();
        playerView2.setPlayer(player2);
        playerView2.setControllerShowTimeoutMs(500);
        playerView2.setControllerAutoShow(true);

        Uri videoUri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fixed1);
        player2.setMediaItem(MediaItem.fromUri(videoUri2));
        player2.prepare();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (player1 != null) {
            player1.release();
            player1 = null;
        }
        if (player2 != null) {
            player2.release();
            player2 = null;
        }
    }
}