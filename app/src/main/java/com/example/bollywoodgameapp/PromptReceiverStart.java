package com.example.bollywoodgameapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class PromptReceiverStart extends AppCompatDialogFragment {
    EditText editTextEnterName;
    ImageButton imageButtonStart;
    EditText editTextEnterCode;

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
        View view = layoutInflater.inflate(R.layout.prompt_receiver_start,null);

        editTextEnterCode = view.findViewById(R.id.editTextEnterCode);
        editTextEnterName = view.findViewById(R.id.editTextEnterName);
        imageButtonStart = view.findViewById(R.id.imageButtonStart);

        builder.setView(view);


        imageButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = editTextEnterCode.getText().toString();
                ref = ref.child(code);
                name = editTextEnterName.getText().toString();
                ref.child("receiver").setValue(name);
                ref.child("player2").setValue(name);
                ref.child("player2score").setValue("0");

                Intent i = new Intent(Objects.requireNonNull(getActivity()), ReceiverActivity.class);
                i.putExtra("name",name);
                i.putExtra("code",code);
                startActivity(i);
            }
        });

        return builder.create();
    }
}
