package com.example.bollywoodgameapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class PromptInputMovie  extends AppCompatDialogFragment {
    EditText editTextInputMovie;
    String completeMovie;
    ImageButton imageButtonStart;
    StringBuilder blankMovie;
    dialogListener movieInputListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.prompt_enter_movie,null);

        imageButtonStart = view.findViewById(R.id.imageButtonStart);
        builder.setView(view);


        imageButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeMovie = editTextInputMovie.getText().toString().toUpperCase();
                if(!completeMovie.equals("")) {
                    blankMovie.append(completeMovie);

                    for (int a = 0; a < blankMovie.length(); a++) {
                        if (blankMovie.charAt(a) != 'A' && blankMovie.charAt(a) != 'E' && blankMovie.charAt(a) != 'I' && blankMovie.charAt(a) != 'O' && blankMovie.charAt(a) != 'U' && blankMovie.charAt(a) != ' ') {
                            blankMovie.setCharAt(a, '_');
                        }
                    }

                    movieInputListener.retMovieName(completeMovie,blankMovie.toString());
                    Objects.requireNonNull(getDialog()).dismiss();
                } else {
                    Toast.makeText(getContext(), "Movie name Cannot be EMPTY", Toast.LENGTH_SHORT).show();
                }
            }
        });


        editTextInputMovie = view.findViewById(R.id.editTextInputMovie);
        blankMovie = new StringBuilder();

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        try {
            movieInputListener = (dialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" must be implemented");
        }
    }

    public interface dialogListener{
        void retMovieName(String completeMovie,String blankMovie);
    }
}
