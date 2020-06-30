package com.example.bollywoodgameapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class PromptGiverStart extends AppCompatDialogFragment {
    EditText editTextEnterName;
    ImageButton imageButtonStart;
    TextView textViewGenCode;
    ImageButton imageButtonCopy;

    String code;
    String name;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();


    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.prompt_giver_start,null);

        textViewGenCode = view.findViewById(R.id.textViewGenCode);
        editTextEnterName = view.findViewById(R.id.editTextEnterName);
        imageButtonStart = view.findViewById(R.id.imageButtonStart);
        imageButtonCopy = view.findViewById(R.id.imageButtonCopy);

        builder.setView(view);
        code = genCode();

        textViewGenCode.setText(code);

        imageButtonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getContext()).getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("code",code);
                clipboard.setPrimaryClip(clip);
            }
        });

        imageButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref=ref.child(code);
                name = editTextEnterName.getText().toString();
                ref.child("giver").setValue(name);
                ref.child("player1").setValue(name);
                ref.child("blankMovie").setValue(getString(R.string.noMovieYet));
                ref.child("chances").setValue("10");
                ref.child("completeMovie").setValue("");
                ref.child("player1score").setValue("0");

                Intent i = new Intent(Objects.requireNonNull(getActivity()), GiverActivity.class);
                i.putExtra("name",name);
                i.putExtra("code",code);
                startActivity(i);
            }
        });

        return builder.create();
    }

    String genCode(){
        Date date = new Date();
        Random random = new Random();
        long timestamp = date.getTime();
        int postfix = random.nextInt(10000);
        return String.valueOf(timestamp)+postfix;
    }
}
