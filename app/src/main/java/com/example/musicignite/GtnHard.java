package com.example.musicignite;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GtnHard extends AppCompatActivity {
    ImageView backBtn, ImageQuizBtn;
    Button[] choiceButtons = new Button[6];
    GridLayout choicesGrid;

    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean quizStarted = false;
    TextView label;

    private final HashMap<String, Integer> soundMap = new HashMap<String, Integer>() {{
        //GCLEF
        put("A", R.drawable.agclef);
        put("B", R.drawable.bgclef);
        put("C", R.drawable.cgclef);
        put("D", R.drawable.dgclef);
        put("E", R.drawable.egclef);
        put("E", R.drawable.eegclef);
        put("F", R.drawable.fgclef);
        put("F", R.drawable.ffgclef);
        put("G", R.drawable.gclef);

        //BASS CLEF
        put("A", R.drawable.a_bass);
        put("A", R.drawable.aa_bass);
        put("B", R.drawable.b_bass);
        put("B", R.drawable.bb_bass);
        put("C", R.drawable.c_bass);
        put("C", R.drawable.cc_bass);
        put("D", R.drawable.d_bass);
        put("E", R.drawable.e_bass);
        put("F", R.drawable.f_bass);
        put("F", R.drawable.ff_bass);
        put("G", R.drawable.g_bass);
        put("G", R.drawable.gg_bass);

        //GCLEF FLAT
        put("A♭", R.drawable.ab);
        put("B♭", R.drawable.bb);
        put("C♭", R.drawable.cb);
        put("D♭", R.drawable.db);
        put("E♭", R.drawable.eb);
        put("E♭", R.drawable.eeb);
        put("F♭", R.drawable.fb);
        put("F♭", R.drawable.ffb);
        put("G♭", R.drawable.gb);

        //GCLEF SHARP#
        put("A♯", R.drawable.ab);
        put("B♯", R.drawable.bb);
        put("C♯", R.drawable.cb);
        put("D♯", R.drawable.db);
        put("E♯", R.drawable.eb);
        put("E♯", R.drawable.eeb);
        put("F♯", R.drawable.fb);
        put("F♯", R.drawable.ffb);
        put("G♯", R.drawable.gb);

        //TREBLE CLEF NOTES
        put("A", R.drawable.atc);
        put("B", R.drawable.btc);
        put("C", R.drawable.ctc);
        put("C", R.drawable.cctc);
        put("D", R.drawable.dtc);
        put("D", R.drawable.ddtc);
        put("E", R.drawable.etc);
        put("E", R.drawable.eetc);
        put("F", R.drawable.ftc);
        put("F", R.drawable.fftc);
        put("G", R.drawable.gtc);
        put("G", R.drawable.ggtc);



    }};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtn_easy);

        backBtn = findViewById(R.id.backBtn);
        ImageQuizBtn = findViewById(R.id.ImageQuizNote_btn);
        choicesGrid = findViewById(R.id.choicesGrid);
        label = findViewById(R.id.label);

        choiceButtons[0] = findViewById(R.id.choice1);
        choiceButtons[1] = findViewById(R.id.choice2);
        choiceButtons[2] = findViewById(R.id.choice3);
        choiceButtons[3] = findViewById(R.id.choice4);
        choiceButtons[4] = findViewById(R.id.choice5);
        choiceButtons[5] = findViewById(R.id.choice6);

        backBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GtnHard.this);
            builder.setTitle("Warning!")
                    .setMessage("Are you sure you want to go back? Your progress will be lost.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent backIntent = new Intent(this, GuessTheNote.class);
                        startActivity(backIntent);
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        ImageQuizBtn.setOnClickListener(v -> {
            if (!quizStarted) {
                generateQuestions();
                currentQuestionIndex = 0;
                score = 0;
                quizStarted = true;
                playQuestion(currentQuestionIndex);
                label.setVisibility(INVISIBLE);
            }
        });

        for (Button button : choiceButtons) {
            button.setOnClickListener(this::handleAnswer);
        }
    }

    private void generateQuestions() {
        List<String> notes = new ArrayList<>(soundMap.keySet());
        Collections.shuffle(notes);
        questions.clear();

        for (int i = 0; i < 10; i++) {
            String correct = notes.get(i % notes.size());
            List<String> choices = new ArrayList<>(notes);
            choices.remove(correct);
            Collections.shuffle(choices);
            while (choices.size() > 5) {
                choices.remove(choices.size() - 1);
            }
            choices.add(correct);
            Collections.shuffle(choices);
            questions.add(new Question(correct, choices));
        }
    }

    private void playQuestion(int index) {
        if (index >= questions.size()) {
            showQuizEndDialog();
            return;
        }

        Question q = questions.get(index);
        ImageQuizBtn.setImageResource(soundMap.get(q.correctAnswer));

        for (int i = 0; i < choiceButtons.length; i++) {
            choiceButtons[i].setText(q.choices.get(i));
            choiceButtons[i].setEnabled(true);
            choiceButtons[i].setBackgroundResource(R.drawable.btn_border);
        }
    }

    private void handleAnswer(View view) {
        if (!quizStarted) {
            Toast.makeText(this, "Click the image to start the quiz!", Toast.LENGTH_SHORT).show();
            return;
        }

        Button selected = (Button) view;
        String answer = selected.getText().toString();
        Question q = questions.get(currentQuestionIndex);

        for (Button btn : choiceButtons) {
            btn.setEnabled(false);
            if (btn.getText().toString().equals(q.correctAnswer)) {
                btn.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            } else if (btn == selected) {
                btn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }
        }

        if (answer.equals(q.correctAnswer)) {
            score++;
        }

        new android.os.Handler().postDelayed(() -> {
            currentQuestionIndex++;
            playQuestion(currentQuestionIndex);
        }, 1000);
    }

    private void showQuizEndDialog() {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GtnHard.this);
            builder.setTitle("Quiz Finished!")
                    .setMessage("Your score: " + score + "/10\nDo you want to take the quiz again?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        quizStarted = false;
                        score = 0;
                        currentQuestionIndex = 0;
                        generateQuestions();
                        quizStarted = true;
                        playQuestion(currentQuestionIndex);
                    })
                    .setNegativeButton("No", (dialog, which) -> finish())
                    .show();
        });
    }

    private static class Question {
        String correctAnswer;
        List<String> choices;

        Question(String correctAnswer, List<String> choices) {
            this.correctAnswer = correctAnswer;
            this.choices = choices;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        label.setVisibility(VISIBLE);
    }
}
