package com.zybooks.enigmamachine;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private final String ROTOR_STATE = "rotorState";
    private String rotorString;
    private ArrayList<ImageView> lampboardAL = new ArrayList<>();
    private ArrayList<ImageView> RotorAL = new ArrayList<>();
    private ImageView lampboard;
    private Rotor rotor1, rotor2, rotor3, reflect;
    private Animation blink;
    private int LampOnColor, LampOffColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int buttonID;
        View rootView = inflater.inflate(R.layout.fragment_enigma, container, false);
        RelativeLayout keyButton = rootView.findViewById(R.id.parent);

        for (int i = 0; i < keyButton.getChildCount(); i++) {
            //Log.i("OnKeyboardClick", String.valueOf(keyButton.getChildAt(i)));
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
        blink = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.blink);
        LampOnColor = ContextCompat.getColor(this.requireActivity(), R.color.electric_yellow);

        if(savedInstanceState == null){
            rotor1 = new Rotor(1,1);
            rotor2 = new Rotor(2,1);
            rotor3 = new Rotor(3,1);
            reflect = new Rotor(4,1);
        }else{
            String rotorState = savedInstanceState.getString(ROTOR_STATE);
            rotor1 = new Rotor(Character.getNumericValue(rotorState.charAt(0)),Character.getNumericValue(rotorState.charAt(1)));
            rotor2 = new Rotor(Character.getNumericValue(rotorState.charAt(2)),Character.getNumericValue(rotorState.charAt(3)));
            rotor3 = new Rotor(Character.getNumericValue(rotorState.charAt(4)),Character.getNumericValue(rotorState.charAt(5)));
            reflect = new Rotor(Character.getNumericValue(rotorState.charAt(6)),Character.getNumericValue(rotorState.charAt(7)));
        }


        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        rotorString = String.valueOf(rotor1.getOption()) + String.valueOf(rotor1.getSetting());
        rotorString += String.valueOf(rotor2.getOption()) + String.valueOf(rotor2.getSetting());
        rotorString += String.valueOf(rotor3.getOption()) + String.valueOf(rotor3.getSetting());
        rotorString += String.valueOf(reflect.getOption()) + String.valueOf(reflect.getSetting());
        outState.putString(ROTOR_STATE, rotorString);
    }


    private void onKeyboardClick(@NonNull View view){
        Log.i("OnKeyboardClick", String .valueOf(view.getId()));
        if(view.getId() == R.id.key_a){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('a');
        }
        if(view.getId() == R.id.key_b){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('b');
        }
        if(view.getId() == R.id.key_c){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('c');
        }
        if(view.getId() == R.id.key_d){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('d');
        }
        if(view.getId() == R.id.key_e){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('e');
        }
        if(view.getId() == R.id.key_f){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('f');
        }
        if(view.getId() == R.id.key_g){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('g');
        }
        if(view.getId() == R.id.key_h){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('h');
        }
        if(view.getId() == R.id.key_i){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('i');
        }
        if(view.getId() == R.id.key_j){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('j');
        }
        if(view.getId() == R.id.key_k){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('k');
        }
        if(view.getId() == R.id.key_l){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('l');
        }
        if(view.getId() == R.id.key_m){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('m');
        }
        if(view.getId() == R.id.key_n){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('n');
        }
        if(view.getId() == R.id.key_o){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('o');
        }
        if(view.getId() == R.id.key_p){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('p');
        }
        if(view.getId() == R.id.key_q){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('q');
        }
        if(view.getId() == R.id.key_r){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('r');
        }
        if(view.getId() == R.id.key_s){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('s');
        }
        if(view.getId() == R.id.key_t){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('t');
        }
        if(view.getId() == R.id.key_u){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('u');
        }
        if(view.getId() == R.id.key_v){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('v');
        }
        if(view.getId() == R.id.key_w){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('w');
        }
        if(view.getId() == R.id.key_x){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('x');
        }
        if(view.getId() == R.id.key_y){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('y');
        }
        if(view.getId() == R.id.key_z){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encodeLetter('z');
        }


    }

    private void encodeLetter(char letter){
        if (rotor3.isswitchCase()) {
            rotor3.turnRotor();
            if(rotor2.isswitchCase()) {
                rotor2.turnRotor();
                rotor1.turnRotor();
            }else {
                rotor2.turnRotor();
            }
        } else {
            rotor3.turnRotor();
            if(rotor2.isswitchCase()) {
                rotor2.turnRotor();
                rotor1.turnRotor();
            }
        }

        letter = rotor3.encode(letter);
        Log.i("R3", String.valueOf(letter));
        letter = rotor2.encode(letter);
        Log.i("R2", String.valueOf(letter));
        letter = rotor1.encode(letter);
        Log.i("R1", String.valueOf(letter));
        letter = reflect.encode(letter);
        Log.i("RR", String.valueOf(letter));
        letter = rotor1.reverseEncode(letter);
        Log.i("R1R", String.valueOf(letter));
        letter = rotor2.reverseEncode(letter);
        Log.i("R2R", String.valueOf(letter));
        letter = rotor3.reverseEncode(letter);
        Log.i("R3R", String.valueOf(letter));

        int asciiletter = letter;
        asciiletter -= 65;
        Log.i("EncodeLetter", String.valueOf(asciiletter));
        switch (asciiletter){
            case 0://a
                lampboardAL.get(10).setColorFilter(LampOnColor);
                break;
            case 1://b
                lampboardAL.get(23).setColorFilter(LampOnColor);
                break;
            case 2://c
                lampboardAL.get(21).setColorFilter(LampOnColor);
                break;
            case 3://d
                lampboardAL.get(12).setColorFilter(LampOnColor);
                break;
            case 4://e
                lampboardAL.get(2).setColorFilter(LampOnColor);
                break;
            case 5://f
                lampboardAL.get(13).setColorFilter(LampOnColor);
                break;
            case 6://g
                lampboardAL.get(14).setColorFilter(LampOnColor);
                break;
            case 7://h
                lampboardAL.get(15).setColorFilter(LampOnColor);
                break;
            case 8://i
                lampboardAL.get(7).setColorFilter(LampOnColor);
                break;
            case 9://j
                lampboardAL.get(16).setColorFilter(LampOnColor);
                break;
            case 10://k
                lampboardAL.get(17).setColorFilter(LampOnColor);
                break;
            case 11://l
                lampboardAL.get(18).setColorFilter(LampOnColor);
                break;
            case 12://m
                lampboardAL.get(25).setColorFilter(LampOnColor);
                break;
            case 13://n
                lampboardAL.get(24).setColorFilter(LampOnColor);
                break;
            case 14://0
                lampboardAL.get(8).setColorFilter(LampOnColor);
                break;
            case 15://p
                lampboardAL.get(9).setColorFilter(LampOnColor);
                break;
            case 16://q
                lampboardAL.get(0).setColorFilter(LampOnColor);
                break;
            case 17://r
                lampboardAL.get(3).setColorFilter(LampOnColor);
                break;
            case 18://s
                lampboardAL.get(11).setColorFilter(LampOnColor);
                break;
            case 19://t
                lampboardAL.get(4).setColorFilter(LampOnColor);
                break;
            case 20://u
                lampboardAL.get(6).setColorFilter(LampOnColor);
                break;
            case 21://v
                lampboardAL.get(22).setColorFilter(LampOnColor);
                break;
            case 22://w
                lampboardAL.get(1).setColorFilter(LampOnColor);
                break;
            case 23://x
                lampboardAL.get(20).setColorFilter(LampOnColor);
                break;
            case 24:
                lampboardAL.get(5).setColorFilter(LampOnColor);
                break;
            case 25:
                lampboardAL.get(19).setColorFilter(LampOnColor);
                break;
        }

    }
}