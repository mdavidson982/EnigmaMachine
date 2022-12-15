package com.zybooks.enigmamachine;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class EnigmaFragment extends Fragment {

    private ImageButton keyboard;
    private ArrayList<ImageView> lampboardAL = new ArrayList<>();
    private ArrayList<ImageView> RotorAL = new ArrayList<>();
    private ImageView lampboard;

    private int LampOnColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int buttonID;
        View rootView = inflater.inflate(R.layout.fragment_enigma, container, false);
        RelativeLayout keyButton = rootView.findViewById(R.id.parent);

        for (int i = 0; i < keyButton.getChildCount(); i++) {
            Log.i("OnKeyboardClick", String.valueOf(keyButton.getChildAt(i)));
            if(keyButton.getChildAt(i) instanceof AppCompatImageView && i <= 3) {
                ImageView rotorImage = (ImageView) keyButton.getChildAt(i);
                RotorAL.add(rotorImage);
            }
            if(keyButton.getChildAt(i) instanceof AppCompatImageView && i > 3){
                ImageView lampboard = (ImageView) keyButton.getChildAt(i);
                lampboardAL.add(lampboard);
            }else if(keyButton.getChildAt(i) instanceof AppCompatImageButton){
                ImageButton keyboard = (ImageButton) keyButton.getChildAt(i);
                keyboard.setOnClickListener(this::onKeyboardClick);
            }
        }
        LampOnColor = ContextCompat.getColor(this.requireActivity(), R.color.electric_yellow);
        return rootView;
    }

    private void onKeyboardClick(View view){
        Log.i("OnKeyboardClick", String.valueOf(view.getId()));
        if(view.getId() == R.id.key_q){
            lampboardAL.get(0).setColorFilter(LampOnColor);
        }

    }

}