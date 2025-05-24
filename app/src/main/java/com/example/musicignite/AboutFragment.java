package com.example.musicignite;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView aboutText = view.findViewById(R.id.aboutText);
        String htmlText = "<b>Welcome to Music Ignite – your gateway to mastering music, one note at a time!</b><br><br>"
                + "At General Ignite, we believe that music is for everyone. Whether you're just starting out or already on your musical journey, "
                + "Music Ignite is here to guide, teach, and inspire you. Our mission is to make learning music fun, easy, and accessible for all.<br><br>"
                + "Through interactive lessons, engaging practice tools, and a growing library of music sheets, Music Ignite empowers you to learn at your own pace, anytime, anywhere.<br><br>"
                + "We’re passionate about sparking creativity and helping you unlock your full musical potential.<br><br>"
                + "Our team is made up of musicians, educators, and tech experts who are united by a shared belief: that music can change lives. We've worked hard to build an app that meets learners where they are—whether you're a complete beginner, a casual player, or a future performer.<br><br>"
                + "Music Ignite includes structured lessons, goal tracking, and personalized learning paths to help you stay motivated. We also regularly update the platform with fresh content, including new sheet music, tutorials, and feature improvements.<br><br>"
                + "We value community, creativity, and lifelong learning. As we grow, we're committed to listening to our users and building a musical experience that supports every step of your journey.<br><br>"
                + "Thank you for being part of the Music Ignite family.<br><br>"
                + "Let’s ignite your passion for music — together.";

        // Set text with HTML formatting
        aboutText.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));

        return view;
    }
}
