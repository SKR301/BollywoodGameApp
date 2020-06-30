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

public class GiverActivity extends AppCompatActivity implements PromptInputMovie.dialogListener{

    //declare
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    String name;
    String code;
    String blankMovie;
    String completeMovie;
    String chances;
    String giver;
    String receiver;
    String player1;
    String player2;
    String player1score;
    String player2score;
    String lastMovie;
    boolean isRoundEnd;

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
    Button buttonEnterMovie;
    Handler promptHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giver);
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
        buttonEnterMovie=findViewById(R.id.buttonEnterMovie);
        promptHandler = new Handler();

        isRoundEnd=false;

        //set Activity Data
        setActivityData();

        //set data for new game
        newGame();

        //get movie
        getMovie();

        //Enter movie button
        buttonEnterMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovie();
            }
        });

        //get data from database
        getDataAndDisplay();
    }

    //disabling back button action
    @Override
    public void onBackPressed() {

    }

    //set data for a new game
    void newGame(){
        ref.child("blankMovie").setValue(getString(R.string.noMovieYet));
        ref.child("completeMovie").setValue("");
        ref.child("chances").setValue(10);
        disableAllButton();
    }

    //set name
    void setActivityData(){
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        name = extras.getString("name");
        code = extras.getString("code");
        ref=ref.child(Objects.requireNonNull(code));
        ref.child("giver").setValue(name);
    }

    //get movie
    void getMovie(){
        PromptInputMovie promptInputMovie = new PromptInputMovie();
        promptInputMovie.show(getSupportFragmentManager(),"EnterName");
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
                        player1score = Objects.requireNonNull(dataSnapshot.child("player1score").getValue()).toString();
                        player2score = Objects.requireNonNull(dataSnapshot.child("player2score").getValue()).toString();
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
    void checkLose(){
        try {
                if(completeMovie.equals(blankMovie) &&Integer.parseInt(chances)>0){
                isRoundEnd=true;
                promptHandler.postDelayed(promptGiverLostRunnable,5000);
                PromptGiverLost promptGiverLost = new PromptGiverLost();
                promptGiverLost.show(getSupportFragmentManager(),"GiverWon");
            }
        } catch (Exception e){
            Log.d("Error","ErrorCheck:"+e);
        }
    }

    //check for lose
    void checkWin(){
        try {
                if(Integer.parseInt(chances)<=0){
                isRoundEnd=true;
                promptHandler.postDelayed(promptGiverWonRunnable,5000);
                PromptGiverWon promptGiverWon = new PromptGiverWon();
                promptGiverWon.show(getSupportFragmentManager(),"GiverWon");
            }
        } catch (Exception e){
            Log.d("Error","ErrorCheck:"+e);
        }
    }

    //change Intent on win/lose
    void changeIntent() {
        Intent intent = new Intent(GiverActivity.this, ReceiverActivity.class);
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
            if(!blankMovie.equals(getString(R.string.noMovieYet))){
                buttonEnterMovie.setEnabled(false);
            }

        } catch (Exception e){
            Log.d("Error","ErrorDisplay:"+e);
        }
    }

    //set all button enable/disable
    void disableAllButton(){
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
        buttonE.setEnabled(false);
        buttonF.setEnabled(false);
        buttonG.setEnabled(false);
        buttonH.setEnabled(false);
        buttonI.setEnabled(false);
        buttonJ.setEnabled(false);
        buttonK.setEnabled(false);
        buttonL.setEnabled(false);
        buttonM.setEnabled(false);
        buttonN.setEnabled(false);
        buttonO.setEnabled(false);
        buttonP.setEnabled(false);
        buttonQ.setEnabled(false);
        buttonR.setEnabled(false);
        buttonS.setEnabled(false);
        buttonT.setEnabled(false);
        buttonU.setEnabled(false);
        buttonV.setEnabled(false);
        buttonW.setEnabled(false);
        buttonX.setEnabled(false);
        buttonY.setEnabled(false);
        buttonZ.setEnabled(false);
    }

    //listen for movie entered by user
    @Override
    public void retMovieName(String completeMovie,String blankMovie){
        ref.child("completeMovie").setValue(completeMovie);
        ref.child("blankMovie").setValue(blankMovie);
    }

    //change activity after 2 sec of won prompt display
    Runnable promptGiverWonRunnable = new Runnable() {
        @Override
        public void run() {
            changeIntent();
        }
    };

    //change activity after 2 sec of lost prompt display
    Runnable promptGiverLostRunnable = new Runnable() {
        @Override
        public void run() {
            changeIntent();
        }
    };
}