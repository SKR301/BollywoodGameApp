package com.example.bollywoodgameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonReceiveMovie;
    Button buttonGiveMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonReceiveMovie = findViewById(R.id.buttonReceiveMovie);
        buttonGiveMovie = findViewById(R.id.buttonGiveMovie);

        buttonReceiveMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptReceiverStart promptReceiverStart = new PromptReceiverStart();
                promptReceiverStart.show(getSupportFragmentManager(),"Start");
            }
        });

        buttonGiveMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptGiverStart promptGiverStart = new PromptGiverStart();
                promptGiverStart.show(getSupportFragmentManager(),"Start");
            }
        });
    }

}
