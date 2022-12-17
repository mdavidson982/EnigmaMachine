package com.zybooks.enigmamachine;

import static android.view.animation.AnimationUtils.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
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
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private final ArrayList<ImageView> RotorAL = new ArrayList<>();
    private ImageView lampboard;
    private Rotor rotor1, rotor2, rotor3, reflect;
    private Animation blink;
    private int LampOnColor, LampOffColor;
    private ArrayList<TextView> encryptionTape = new ArrayList<>();
    private RelativeLayout keyButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lampboardAL.clear();
        RotorAL.clear();
        encryptionTape.clear();
        View rootView = inflater.inflate(R.layout.fragment_enigma, container, false);
        keyButton = rootView.findViewById(R.id.parent);
        Log.i("onCreateView", "This is the onCreateView");
        SharedPreferences sharedPref = this.requireActivity().getPreferences(Context.MODE_PRIVATE);

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
            if(keyButton.getChildAt(i) instanceof AppCompatTextView){
                TextView tape = (TextView) keyButton.getChildAt(i);
                encryptionTape.add(tape);
            }
        }
        blink = loadAnimation(getActivity().getApplicationContext(),R.anim.blink);
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
    public void onResume(){
        super.onResume();


        //Log.i("OnResume", "OnResume running.");
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
        //Log.i("OnKeyboardClick", String .valueOf(view.getId()));
        if(view.getId() == R.id.key_a){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "a");
            encodeLetter('a');
        }
        if(view.getId() == R.id.key_b){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "b");
            encodeLetter('b');
        }
        if(view.getId() == R.id.key_c){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "c");
            encodeLetter('c');
        }
        if(view.getId() == R.id.key_d){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "d");
            encodeLetter('d');
        }
        if(view.getId() == R.id.key_e){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "e");
            encodeLetter('e');
        }
        if(view.getId() == R.id.key_f){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "f");
            encodeLetter('f');
        }
        if(view.getId() == R.id.key_g){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "g");
            encodeLetter('g');
        }
        if(view.getId() == R.id.key_h){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "h");
            encodeLetter('h');
        }
        if(view.getId() == R.id.key_i){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "i");
            encodeLetter('i');
        }
        if(view.getId() == R.id.key_j){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "j");
            encodeLetter('j');
        }
        if(view.getId() == R.id.key_k){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "k");
            encodeLetter('k');
        }
        if(view.getId() == R.id.key_l){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "l");
            encodeLetter('l');
        }
        if(view.getId() == R.id.key_m){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "m");
            encodeLetter('m');
        }
        if(view.getId() == R.id.key_n){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "n");
            encodeLetter('n');
        }
        if(view.getId() == R.id.key_o){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "o");
            encodeLetter('o');
        }
        if(view.getId() == R.id.key_p){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "p");
            encodeLetter('p');
        }
        if(view.getId() == R.id.key_q){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "q");
            encodeLetter('q');
        }
        if(view.getId() == R.id.key_r){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "r");
            encodeLetter('r');
        }
        if(view.getId() == R.id.key_s){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "s");
            encodeLetter('s');
        }
        if(view.getId() == R.id.key_t){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "t");
            encodeLetter('t');
        }
        if(view.getId() == R.id.key_u){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "u");
            encodeLetter('u');
        }
        if(view.getId() == R.id.key_v){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "v");
            encodeLetter('v');
        }
        if(view.getId() == R.id.key_w){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "w");
            encodeLetter('w');
        }
        if(view.getId() == R.id.key_x){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "x");
            encodeLetter('x');
        }
        if(view.getId() == R.id.key_y){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "y");
            encodeLetter('y');
        }
        if(view.getId() == R.id.key_z){
            //Log.i("AfterKeyboardClick", String.valueOf(lampboardAL.get(0)));
            encryptionTape.get(0).setText(encryptionTape.get(0).getText() + "z");
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
        //Log.i("R3", String.valueOf(letter));
        letter = rotor2.encode(letter);
        //Log.i("R2", String.valueOf(letter));
        letter = rotor1.encode(letter);
        //Log.i("R1", String.valueOf(letter));
        letter = reflect.encode(letter);
        //Log.i("RR", String.valueOf(letter));
        letter = rotor1.reverseEncode(letter);
        //Log.i("R1R", String.valueOf(letter));
        letter = rotor2.reverseEncode(letter);
        //Log.i("R2R", String.valueOf(letter));
        letter = rotor3.reverseEncode(letter);
        //Log.i("R3R", String.valueOf(letter));

        int asciiletter = letter;
        asciiletter -= 65;
        //Log.i("EncodeLetter", String.valueOf(asciiletter));
        switch (asciiletter){
            case 0://a
                lampboardAL.get(10).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "a");
                break;
            case 1://b
                lampboardAL.get(23).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "b");
                break;
            case 2://c
                lampboardAL.get(21).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "c");
                break;
            case 3://d
                lampboardAL.get(12).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "d");
                break;
            case 4://e
                lampboardAL.get(2).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "e");
                break;
            case 5://f
                lampboardAL.get(13).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "f");
                break;
            case 6://g
                lampboardAL.get(14).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "g");
                break;
            case 7://h
                lampboardAL.get(15).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "h");
                break;
            case 8://i
                lampboardAL.get(7).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "i");
                break;
            case 9://j
                lampboardAL.get(16).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "j");
                break;
            case 10://k
                lampboardAL.get(17).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "k");
                break;
            case 11://l
                lampboardAL.get(18).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "l");
                break;
            case 12://m
                lampboardAL.get(25).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "m");
                break;
            case 13://n
                lampboardAL.get(24).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "n");
                break;
            case 14://0
                lampboardAL.get(8).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "o");
                break;
            case 15://p
                lampboardAL.get(9).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "p");
                break;
            case 16://q
                lampboardAL.get(0).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "q");
                break;
            case 17://r
                lampboardAL.get(3).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "r");
                break;
            case 18://s
                lampboardAL.get(11).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "s");
                break;
            case 19://t
                lampboardAL.get(4).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "t");
                break;
            case 20://u
                lampboardAL.get(6).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "u");
                break;
            case 21://v
                lampboardAL.get(22).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "v");
                break;
            case 22://w
                lampboardAL.get(1).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "w");
                break;
            case 23://x
                lampboardAL.get(20).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "x");
                break;
            case 24:
                lampboardAL.get(5).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "y");
                break;
            case 25:
                lampboardAL.get(19).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "z");
                break;
        }

    }
}