package com.example.bollywoodgameapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ReceiverActivity extends AppCompatActivity {

    //declare
    String name;
    String blankMovie;
    String completeMovie;
    String chances;
    String giver;
    String receiver;
    String player1;
    String player2;
    String lastMovie;
    String code;
    boolean isRoundEnd;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    TextView textViewChancesLeft;
    ProgressBar progressBarChancesLeft;
    TextView textViewMovieBlank;
    TextView textViewBy;
    Button buttonA;
    Button buttonB;
    Button buttonC;
    Button buttonD;
    Button buttonE;
    Button buttonF;
    Button buttonG;
    Button buttonH;
    Button buttonI;
    Button buttonJ;
    Button buttonK;
    Button buttonL;
    Button buttonM;
    Button buttonN;
    Button buttonO;
    Button buttonP;
    Button buttonQ;
    Button buttonR;
    Button buttonS;
    Button buttonT;
    Button buttonU;
    Button buttonV;
    Button buttonW;
    Button buttonX;
    Button buttonY;
    Button buttonZ;
    Handler promptHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        //initialize
        textViewChancesLeft=findViewById(R.id.textViewChancesLeft);
        progressBarChancesLeft=findViewById(R.id.progressBarChancesLeft);
        textViewMovieBlank=findViewById(R.id.textViewMovieBlank);
        textViewBy=findViewById(R.id.textViewBy);
        buttonA=findViewById(R.id.buttonA);
        buttonB=findViewById(R.id.buttonB);
        buttonC=findViewById(R.id.buttonC);
        buttonD=findViewById(R.id.buttonD);
        buttonE=findViewById(R.id.buttonE);
        buttonF=findViewById(R.id.buttonF);
        buttonG=findViewById(R.id.buttonG);
        buttonH=findViewById(R.id.buttonH);
        buttonI=findViewById(R.id.buttonI);
        buttonJ=findViewById(R.id.buttonJ);
        buttonK=findViewById(R.id.buttonK);
        buttonL=findViewById(R.id.buttonL);
        buttonM=findViewById(R.id.buttonM);
        buttonN=findViewById(R.id.buttonN);
        buttonO=findViewById(R.id.buttonO);
        buttonP=findViewById(R.id.buttonP);
        buttonQ=findViewById(R.id.buttonQ);
        buttonR=findViewById(R.id.buttonR);
        buttonS=findViewById(R.id.buttonS);
        buttonT=findViewById(R.id.buttonT);
        buttonU=findViewById(R.id.buttonU);
        buttonV=findViewById(R.id.buttonV);
        buttonW=findViewById(R.id.buttonW);
        buttonX=findViewById(R.id.buttonX);
        buttonY=findViewById(R.id.buttonY);
        buttonZ=findViewById(R.id.buttonZ);
        isRoundEnd=false;
        promptHandler = new Handler();

        //set Activity Data
        setActivityData();

        //set data for new game
        newGame();

        //get data from database
        getDataAndDisplay();
    }

    //disabling back button action
    @Override
    public void onBackPressed() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //set data for a new game
    void newGame(){
        ref.child("blankMovie").setValue(getString(R.string.noMovieYet));
        ref.child("completeMovie").setValue("");
        ref.child("chances").setValue(10);
        disableButton();
    }

    //set name
    void setActivityData(){
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        name = extras.getString("name");
        code = extras.getString("code");
        ref=ref.child(Objects.requireNonNull(code));
        ref.child("receiver").setValue(name);
    }

    //get data from database and display
    void getDataAndDisplay(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!isRoundEnd){
                    try{
                        chances = Objects.requireNonNull(dataSnapshot.child("chances").getValue()).toString();
                        blankMovie = Objects.requireNonNull(dataSnapshot.child("blankMovie").getValue()).toString();
                        completeMovie = Objects.requireNonNull(dataSnapshot.child("completeMovie").getValue()).toString();
                        giver = Objects.requireNonNull(dataSnapshot.child("giver").getValue()).toString();
                        receiver = Objects.requireNonNull(dataSnapshot.child("receiver").getValue()).toString();
                        player1 = Objects.requireNonNull(dataSnapshot.child("player1").getValue()).toString();
                        player2 = Objects.requireNonNull(dataSnapshot.child("player2").getValue()).toString();
                        lastMovie = Objects.requireNonNull(dataSnapshot.child("lastMovie").getValue()).toString();
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    checkWin();
                    checkLose();
                    displayChances();
                    displayMovie();
                    displayBy();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //check for Win
    void checkWin(){
        try {
            if(completeMovie.equals(blankMovie) &&Integer.parseInt(chances)>0){
                isRoundEnd=true;
                ref.child("lastMovie").setValue(completeMovie);
                promptHandler.postDelayed(promptReceiverWonRunnable,3000);
                PromptReceiverWon promptReceiverWon = new PromptReceiverWon();
                promptReceiverWon.show(getSupportFragmentManager(),"ReceiverWon");
            }
        } catch (Exception e){
            Log.d("Error","ErrorCheck:"+e);
        }
    }

    //check for lose
    void checkLose(){
        try {
            if(Integer.parseInt(chances)<=0){
                isRoundEnd=true;
                ref.child("lastMovie").setValue(completeMovie);
                promptHandler.postDelayed(promptReceiverLostRunnable,3000);
                PromptReceiverLost promptReceiverLost = new PromptReceiverLost();
                promptReceiverLost.show(getSupportFragmentManager(),"ReceiverLost");
            }
        } catch (Exception e){
            Log.d("Error","ErrorCheck:"+e);
        }
    }

    //change Intent on win/lose
    void changeIntent() {
        Intent intent = new Intent(ReceiverActivity.this, GiverActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("code",code);
        startActivity(intent);
    }

    //display chance
    void displayChances(){
        try {
            textViewChancesLeft.setText(String.format("%s%s%s", getString(R.string.chances_left), chances, getString(R.string.by10)));
            progressBarChancesLeft.setProgress((10-Integer.parseInt(chances))*10);
        } catch (Exception e){
            Log.d("Error","ErrorDisplay:"+e);
        }
    }

    //display by
    void displayBy(){
        try{
            textViewBy.setText(String.format("%s%s", getString(R.string.givenBy), giver));
        }  catch (Exception e){
            Log.d("Error","ErrorDisplay:"+e);
        }
    }

    //display Movie
    void displayMovie(){
        try {
            StringBuilder movie = new StringBuilder();

            for (char c: blankMovie.toCharArray()) {
                movie.append(c).append(" ");
            }

            textViewMovieBlank.setText(movie.toString().trim());

        } catch (Exception e){
            Log.d("Error","ErrorDisplay:"+e);
        }
    }

    //on press of button
    public void buttonClick(View view) {
        Button buttonPressed = (Button) view;
        buttonPressed.setEnabled(false);

        boolean isPresent = false;
        char ch = buttonPressed.getText().charAt(0);
        StringBuilder movie = new StringBuilder();
        movie.append(blankMovie);

        for(int a=0;a<completeMovie.length();a++){
            if(completeMovie.charAt(a)==ch){
                isPresent = true;
                movie.setCharAt(a,ch);
                ref.child("blankMovie").setValue(movie.toString());
            }
        }

        if(!isPresent){
            ref.child("chances").setValue(Integer.parseInt(chances)-1);
        }
    }

    //disable vowel buttons
    void disableButton(){
        buttonA.setEnabled(false);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
        buttonE.setEnabled(false);
        buttonF.setEnabled(true);
        buttonG.setEnabled(true);
        buttonH.setEnabled(true);
        buttonI.setEnabled(false);
        buttonJ.setEnabled(true);
        buttonK.setEnabled(true);
        buttonL.setEnabled(true);
        buttonM.setEnabled(true);
        buttonN.setEnabled(true);
        buttonO.setEnabled(false);
        buttonP.setEnabled(true);
        buttonQ.setEnabled(true);
        buttonR.setEnabled(true);
        buttonS.setEnabled(true);
        buttonT.setEnabled(true);
        buttonU.setEnabled(false);
        buttonV.setEnabled(true);
        buttonW.setEnabled(true);
        buttonX.setEnabled(true);
        buttonY.setEnabled(true);
        buttonZ.setEnabled(true);
    }

    //change activity after 2 sec of won prompt display
    Runnable promptReceiverWonRunnable = new Runnable() {
        @Override
        public void run() {
            changeIntent();
        }
    };

    //change activity after 2 sec of lost prompt display
    Runnable promptReceiverLostRunnable = new Runnable() {
        @Override
        public void run() {
            changeIntent();
        }
    };
}