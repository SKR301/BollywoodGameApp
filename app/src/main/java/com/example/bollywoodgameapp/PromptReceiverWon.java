package com.example.bollywoodgameapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class PromptReceiverWon extends AppCompatDialogFragment {

    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.prompt_receiver_won,null);

        builder.setView(view);

        return builder.create();
    }
}
