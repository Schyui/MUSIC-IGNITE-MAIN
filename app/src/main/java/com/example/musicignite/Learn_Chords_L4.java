package com.example.musicignite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class Learn_Chords_L4 extends AppCompatActivity {
    TextView[] textViews = new TextView[4];
    Button NextBtn, PrevBtn;
    ImageView backBtn;
    List<PlayerView> playerViews = new ArrayList<>();
    List<ImageView> playButtons = new ArrayList<>();
    List<ImageView> thumbnailViews = new ArrayList<>();
    List<Uri> videoUris = new ArrayList<>();
    ExoPlayer sharedPlayer;
    PlayerView currentPlayerView;
    Uri currentUri;
    int currentIndex = -1;
    int[] Text = {
            R.id.text1, R.id.text2, R.id.text3, R.id.text4
    };
    int[] playerViewIds = {
            R.id.vid1, R.id.vid2, R.id.vid3, R.id.vid4, R.id.vid5
    };
    int[] playButtonIds = {
            R.id.playButton1, R.id.playButton2, R.id.playButton3, R.id.playButton4, R.id.playButton5
    };
    int[] thumbnailIds = {
            R.id.thumb1, R.id.thumb2, R.id.thumb3, R.id.thumb4, R.id.thumb5
    };
    int[] rawIds = {
            R.raw.chords_l3_vid01, R.raw.chords_l3_vid02, R.raw.chords_l3_vid03, R.raw.chords_l3_vid04, R.raw.chords_l3_vid05
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_chords_l4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        PrevBtn = findViewById(R.id.prevBtn);

        String[] htmlTexts = {
                "• <b>Root position</b> is the same as a triad – the root is the lowest (bass) note.",
                "• <b>First inversion</b> is also the same – the third is the lowest note.",
                "• <b>Second inversion</b> is also the same – the fifth is the lowest note.",
                "• This is called <b>third inversion</b>.",
        };

        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = findViewById(Text[i]);
            textViews[i].setText(Html.fromHtml(htmlTexts[i], Html.FROM_HTML_MODE_LEGACY));
        }
        for (int rawId : rawIds) {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + rawId);
            videoUris.add(uri);
        }
        for (int i = 0; i < 5; i++) {
            playerViews.add(findViewById(playerViewIds[i]));
            playButtons.add(findViewById(playButtonIds[i]));
            thumbnailViews.add(findViewById(thumbnailIds[i]));
        }
    }

    @Override
    public void onStart () {
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Chords.class);
            startActivity(intent);
        });

        PrevBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Chords_L3.class);
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