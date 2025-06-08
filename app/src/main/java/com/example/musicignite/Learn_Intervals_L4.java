package com.example.musicignite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class Learn_Intervals_L4 extends AppCompatActivity {
    TextView Text1;
    Button PrevBtn;
    ImageView backBtn;
    List<PlayerView> playerViews = new ArrayList<>();
    List<ImageView> playButtons = new ArrayList<>();
    List<ImageView> thumbnailViews = new ArrayList<>();
    List<Uri> videoUris = new ArrayList<>();
    ExoPlayer sharedPlayer;
    PlayerView currentPlayerView;
    Uri currentUri;
    int currentIndex = -1;
    int[] playerViewIds = {
            R.id.vid1, R.id.vid2, R.id.vid3, R.id.vid4, R.id.vid5, R.id.vid6,
            R.id.vid7, R.id.vid8, R.id.vid9, R.id.vid10, R.id.vid11, R.id.vid12
    };
    int[] playButtonIds = {
            R.id.playButton1, R.id.playButton2, R.id.playButton3, R.id.playButton4, R.id.playButton5, R.id.playButton6,
            R.id.playButton7, R.id.playButton8, R.id.playButton9, R.id.playButton10, R.id.playButton11, R.id.playButton12
    };
    int[] thumbnailIds = {
            R.id.thumb1, R.id.thumb2, R.id.thumb3, R.id.thumb4, R.id.thumb5, R.id.thumb6,
            R.id.thumb7, R.id.thumb8, R.id.thumb9, R.id.thumb10, R.id.thumb11, R.id.thumb12
    };
    int[] rawIds = {
            R.raw.interval_lesson4_vid01, R.raw.interval_lesson4_vid02, R.raw.interval_lesson4_vid03, R.raw.interval_lesson4_vid04,
            R.raw.interval_lesson4_vid05, R.raw.interval_lesson4_vid06, R.raw.interval_lesson4_vid07, R.raw.interval_lesson4_vid08,
            R.raw.interval_lesson4_vid09, R.raw.interval_lesson4_vid10, R.raw.interval_lesson4_vid11, R.raw.interval_lesson4_vid12
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_intervals_l4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        PrevBtn = findViewById(R.id.prevBtn);
        Text1 = findViewById(R.id.text1);

        String htmlText1 = "• In music, the verb <b>invert</b> means to move the lowest note in a group an octave higher.";
        Text1.setText(Html.fromHtml(htmlText1, Html.FROM_HTML_MODE_LEGACY));

        new Thread(() -> {
            for (int rawId : rawIds) {
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + rawId);
                videoUris.add(uri);
            }

            // Back to UI thread after processing
            new Handler(Looper.getMainLooper()).post(() -> {
                // Start animation or enable buttons here
            });
        }).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            for (int i = 0; i < 12; i++) {
                playerViews.add(findViewById(playerViewIds[i]));
                playButtons.add(findViewById(playButtonIds[i]));
                thumbnailViews.add(findViewById(thumbnailIds[i]));
            }
            initializePlayer(); // Move this here instead of onStart
        }, 100);
    }

    @Override
    protected void onStart() {
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Intervals.class);
            startActivity(intent);
        });

        PrevBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Intervals_L3.class);
            startActivity(intent);
        });
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