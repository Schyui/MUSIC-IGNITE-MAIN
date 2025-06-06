package com.example.musicignite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class Learn_Intervals_L3 extends AppCompatActivity {
    Button NextBtn, PreviousBtn;
    ImageView backBtn, playButton1, playButton2, playButton3;
    PlayerView playerView1, playerView2, playerView3;
    List<PlayerView> playerViews = new ArrayList<>();
    List<Uri> videoUris = new ArrayList<>();
    List<ImageView> playButtons = new ArrayList<>();
    List<ImageView> thumbnailViews = new ArrayList<>();
    ExoPlayer sharedPlayer;
    PlayerView currentPlayerView;
    Uri currentUri;
    int currentIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_intervals_l3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        NextBtn = findViewById(R.id.nextBtn);
        PreviousBtn = findViewById(R.id.prevBtn);
        playButton1 = findViewById(R.id.playButton1);
        playButton2 = findViewById(R.id.playButton2);
        playButton3 = findViewById(R.id.playButton3);

        playerView1 = findViewById(R.id.vid1);
        playerView2 = findViewById(R.id.vid2);
        playerView3 = findViewById(R.id.vid3);

        thumbnailViews.add(findViewById(R.id.thumb1));
        thumbnailViews.add(findViewById(R.id.thumb2));
        thumbnailViews.add(findViewById(R.id.thumb3));

        playerViews.add(playerView1);
        playerViews.add(playerView2);
        playerViews.add(playerView3);

        playButtons.add(playButton1);
        playButtons.add(playButton2);
        playButtons.add(playButton3);

        videoUris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intervals_lesson3_vid1));
        videoUris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intervals_lesson3_vid2));
        videoUris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intervals_lesson3_vid3));
    }
    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Intervals.class);
            startActivity(intent);
        });

        NextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Intervals_L4.class);
            startActivity(intent);
        });

        PreviousBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Intervals_L2.class);
            startActivity(intent);
        });

        initializePlayer();

    }

    @OptIn(markerClass = UnstableApi.class)
    private void initializePlayer () {
        sharedPlayer = new ExoPlayer.Builder(this).build();

        for (int i = 0; i < playerViews.size(); i++) {
            final PlayerView pvCopy = playerViews.get(i);
            final ImageView playBtn = playButtons.get(i);
            final Uri uri = videoUris.get(i);

            pvCopy.setTag(uri);
            pvCopy.setControllerAutoShow(false);
            pvCopy.setControllerShowTimeoutMs(500);
            playBtn.setVisibility(View.VISIBLE);

            // Clicking play button
            playBtn.setOnClickListener(v -> playInView(pvCopy, uri));

            // Clicking the video
            pvCopy.setOnClickListener(v -> playInView(pvCopy, uri));
        }

        // Show/hide play button depending on state
        sharedPlayer.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                for (int i = 0; i < playerViews.size(); i++) {
                    if (playerViews.get(i) == currentPlayerView) {
                        playButtons.get(i).setVisibility(isPlaying ? View.GONE : View.VISIBLE);
                    } else {
                        playButtons.get(i).setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED && currentPlayerView != null) {
                    int idx = playerViews.indexOf(currentPlayerView);
                    if (idx != -1) {
                        playButtons.get(idx).setVisibility(View.VISIBLE);
                        thumbnailViews.get(idx).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void playInView (PlayerView playerView, Uri uri){

        int newIndex = playerViews.indexOf(playerView);

        if (currentPlayerView == playerView && uri.equals(currentUri)) {
            if (sharedPlayer.getPlaybackState() == Player.STATE_ENDED) {
                sharedPlayer.seekTo(0);
                sharedPlayer.play();
                thumbnailViews.get(newIndex).setVisibility(View.GONE);
                playButtons.get(newIndex).setVisibility(View.GONE);
            } else if (sharedPlayer.isPlaying()) {
                sharedPlayer.pause();
            } else {
                sharedPlayer.play();
            }
            return;
        }

        // Stop and detach previous player view
        if (currentPlayerView != null && currentPlayerView != playerView) {
            if (currentIndex != -1) {
                thumbnailViews.get(currentIndex).setVisibility(View.VISIBLE);
                playButtons.get(currentIndex).setVisibility(View.VISIBLE);
                playerViews.get(currentIndex).setPlayer(null);
            }
        }

        currentPlayerView = playerView;
        currentUri = uri;
        currentIndex = newIndex;

        playerView.setPlayer(sharedPlayer);
        thumbnailViews.get(currentIndex).setVisibility(View.GONE);
        playButtons.get(currentIndex).setVisibility(View.GONE);

        sharedPlayer.setMediaItem(MediaItem.fromUri(uri));
        sharedPlayer.prepare();
        sharedPlayer.play();
    }

    @Override
    protected void onPause () {
        super.onPause();
        if (sharedPlayer != null && sharedPlayer.isPlaying()) {
            sharedPlayer.pause();
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (sharedPlayer != null) {
            sharedPlayer.release();
        }
    }
}