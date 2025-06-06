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

public class Learn_Intervals_L2 extends AppCompatActivity {
    TextView[] textViews = new TextView[12];
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
            R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6,
            R.id.text7, R.id.text8, R.id.text9, R.id.text10, R.id.text11, R.id.text12
    };
    int[] playerViewIds = {
            R.id.vid1, R.id.vid2, R.id.vid3, R.id.vid4, R.id.vid5, R.id.vid6,
            R.id.vid7, R.id.vid8, R.id.vid9, R.id.vid10, R.id.vid11, R.id.vid12,
            R.id.vid13, R.id.vid14, R.id.vid15, R.id.vid16, R.id.vid17, R.id.vid18,
            R.id.vid19, R.id.vid20, R.id.vid21, R.id.vid22
    };
    int[] playButtonIds = {
            R.id.playButton1, R.id.playButton2, R.id.playButton3, R.id.playButton4, R.id.playButton5, R.id.playButton6,
            R.id.playButton7, R.id.playButton8, R.id.playButton9, R.id.playButton10, R.id.playButton11, R.id.playButton12,
            R.id.playButton13, R.id.playButton14, R.id.playButton15, R.id.playButton16, R.id.playButton17, R.id.playButton18,
            R.id.playButton19, R.id.playButton20, R.id.playButton21, R.id.playButton22
    };
    int[] thumbnailIds = {
            R.id.thumb1, R.id.thumb2, R.id.thumb3, R.id.thumb4, R.id.thumb5, R.id.thumb6,
            R.id.thumb7, R.id.thumb8, R.id.thumb9, R.id.thumb10, R.id.thumb11, R.id.thumb12,
            R.id.thumb13, R.id.thumb14, R.id.thumb15, R.id.thumb16, R.id.thumb17, R.id.thumb18,
            R.id.thumb19, R.id.thumb20, R.id.thumb21, R.id.thumb22
    };
    int[] rawIds = {
            R.raw.intervals_lesson2_vid01, R.raw.intervals_lesson2_vid02, R.raw.intervals_lesson2_vid03,
            R.raw.intervals_lesson2_vid04, R.raw.intervals_lesson2_vid05, R.raw.intervals_lesson2_vid06,
            R.raw.intervals_lesson2_vid07, R.raw.intervals_lesson2_vid08, R.raw.intervals_lesson2_vid09,
            R.raw.intervals_lesson2_vid10, R.raw.intervals_lesson2_vid11, R.raw.intervals_lesson2_vid12,
            R.raw.intervals_lesson2_vid13, R.raw.intervals_lesson2_vid14, R.raw.intervals_lesson2_vid15,
            R.raw.intervals_lesson2_vid16, R.raw.intervals_lesson2_vid17, R.raw.intervals_lesson2_vid18,
            R.raw.intervals_lesson2_vid19, R.raw.intervals_lesson2_vid20, R.raw.intervals_lesson2_vid21,
            R.raw.intervals_lesson2_vid22
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_intervals_l2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        NextBtn = findViewById(R.id.nextBtn);
        PrevBtn = findViewById(R.id.prevBtn);

        String[] htmlTexts = {
                "• <b>Specific intervals</b> are measured both on the staff and in half steps on the keyboard.",
                "• A <b>major second</b> is made up of two half steps.",
                "•  A <b>major third</b> is made up of four half steps.",
                "• A <b>perfect fourth</b> is made up of five half steps.",
                "• A <b>perfect fifth</b> is made up of seven half steps.",
                "• A <b>major sixth</b> is made up of nine half steps.",
                "• A <b>major seventh</b> is made up of eleven half steps.",
                "• Finally, a <b>perfect eighth</b> (or <b>perfect octave</b>) is made up of twelve half steps.",
                "• The terms \"major\" and \"perfect\" refer to the interval's <b>quality</b>.",
                "• Next, let's discuss <b>minor</b> intervals.",
                "• An <b>augmented</b> interval has one more half step than a perfect interval.",
                "• A <b>diminished</b> interval has one less half step than a perfect interval."
        };

        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = findViewById(Text[i]);
            textViews[i].setText(Html.fromHtml(htmlTexts[i], Html.FROM_HTML_MODE_LEGACY));
        }
        for (int rawId : rawIds) {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + rawId);
            videoUris.add(uri);
        }
        for (int i = 0; i < 22; i++) {
            playerViews.add(findViewById(playerViewIds[i]));
            playButtons.add(findViewById(playButtonIds[i]));
            thumbnailViews.add(findViewById(thumbnailIds[i]));
        }
    }

    @Override
    public void onStart () {
        super.onStart();
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Intervals.class);
            startActivity(intent);
        });

        NextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Intervals_L3.class);
            startActivity(intent);
        });

        PrevBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Learn_Intervals_L1.class);
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