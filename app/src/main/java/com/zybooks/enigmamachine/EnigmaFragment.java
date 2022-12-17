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
    private int priorLetter = 10;
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
        String configuration = sharedPref.getString("configuration", "2,0,1,0,0,0");
        String[] configurationArray = configuration.split(",");

        for (int i = 1; i < keyButton.getChildCount(); i++) {
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

        RotorAL.get(0).setImageResource(rotorLetter((Integer.valueOf(configurationArray[1]))));
        RotorAL.get(1).setImageResource(rotorLetter((Integer.valueOf(configurationArray[3]))));
        RotorAL.get(2).setImageResource(rotorLetter((Integer.valueOf(configurationArray[5]))));

        blink = loadAnimation(getActivity().getApplicationContext(),R.anim.blink);
        LampOnColor = ContextCompat.getColor(this.requireActivity(), R.color.electric_yellow);
        LampOffColor = ContextCompat.getColor(this.requireActivity(), R.color.lamp_gray);

        if(savedInstanceState == null){
            rotor1 = new Rotor((Integer.valueOf(configurationArray[0])+1),(Integer.valueOf(configurationArray[1])) +1);
            rotor2 = new Rotor((Integer.valueOf(configurationArray[2])+1),(Integer.valueOf(configurationArray[3])) +1);
            rotor3 = new Rotor((Integer.valueOf(configurationArray[4])+1),(Integer.valueOf(configurationArray[5])) +1);
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
            RotorAL.get(2).setImageResource(rotorLetter(rotor3.getSetting()));
            RotorAL.get(1).setImageResource(rotorLetter(rotor2.getSetting()));
            RotorAL.get(0).setImageResource(rotorLetter(rotor1.getSetting()));

        } else {
            rotor3.turnRotor();
            if(rotor2.isswitchCase()) {
                rotor2.turnRotor();
                rotor1.turnRotor();
            }
            RotorAL.get(2).setImageResource(rotorLetter(rotor3.getSetting()));
            RotorAL.get(1).setImageResource(rotorLetter(rotor2.getSetting()));
            RotorAL.get(0).setImageResource(rotorLetter(rotor1.getSetting()));
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
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(10).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "a");
                priorLetter = 10;
                break;
            case 1://b
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(23).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "b");
                priorLetter = 23;
                break;
            case 2://c
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(21).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "c");
                priorLetter = 21;
                break;
            case 3://d
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(12).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "d");
                priorLetter = 12;
                break;
            case 4://e
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(2).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "e");
                priorLetter = 2;
                break;
            case 5://f
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(13).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "f");
                priorLetter = 13;
                break;
            case 6://g
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(14).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "g");
                priorLetter = 14;
                break;
            case 7://h
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(15).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "h");
                priorLetter = 15;
                break;
            case 8://i
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(7).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "i");
                priorLetter = 7;
                break;
            case 9://j
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(16).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "j");
                priorLetter = 16;
                break;
            case 10://k
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(17).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "k");
                priorLetter = 17;
                break;
            case 11://l
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(18).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "l");
                priorLetter = 18;
                break;
            case 12://m
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(25).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "m");
                priorLetter = 25;
                break;
            case 13://n
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(24).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "n");
                priorLetter = 24;
                break;
            case 14://0
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(8).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "o");
                priorLetter = 8;
                break;
            case 15://p
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(9).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "p");
                priorLetter = 9;
                break;
            case 16://q
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(0).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "q");
                priorLetter = 0;
                break;
            case 17://r
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(3).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "r");
                priorLetter = 3;
                break;
            case 18://s
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(11).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "s");
                priorLetter = 11;
                break;
            case 19://t
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(4).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "t");
                priorLetter = 4;
                break;
            case 20://u
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(6).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "u");
                priorLetter = 6;
                break;
            case 21://v
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(22).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "v");
                priorLetter = 22;
                break;
            case 22://w
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(1).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "w");
                priorLetter = 1;
                break;
            case 23://x
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(20).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "x");
                priorLetter = 20;
                break;
            case 24:
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(5).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "y");
                priorLetter = 5;
                break;
            case 25:
                lampboardAL.get(priorLetter).setColorFilter(LampOffColor);
                lampboardAL.get(19).setColorFilter(LampOnColor);
                encryptionTape.get(1).setText(encryptionTape.get(1).getText() + "z");
                priorLetter = 19;
                break;
        }

    }
    private int rotorLetter(int i){
        int returnedId;
        switch (i){
            case 0:
                returnedId = R.drawable.square_a;
                break;
            case 1:
                returnedId = R.drawable.square_b;
                break;
            case 2:
                returnedId = R.drawable.square_c;
                break;
            case 3:
                returnedId = R.drawable.square_d;
                break;
            case 4:
                returnedId = R.drawable.square_e;
                break;
            case 5:
                returnedId = R.drawable.square_f;
                break;
            case 6:
                returnedId = R.drawable.square_g;
                break;
            case 7:
                returnedId = R.drawable.square_h;
                break;
            case 8:
                returnedId = R.drawable.square_i;
                break;
            case 9:
                returnedId = R.drawable.square_j;
                break;
            case 10:
                returnedId = R.drawable.square_k;
                break;
            case 11:
                returnedId = R.drawable.square_l;
                break;
            case 12:
                returnedId = R.drawable.square_m;
                break;
            case 13:
                returnedId = R.drawable.square_n;
                break;
            case 14:
                returnedId = R.drawable.square_o;
                break;
            case 15:
                returnedId = R.drawable.square_p;
                break;
            case 16:
                returnedId = R.drawable.square_q;
                break;
            case 17:
                returnedId = R.drawable.square_r;
                break;
            case 18:
                returnedId = R.drawable.square_s;
                break;
            case 19:
                returnedId = R.drawable.square_t;
                break;
            case 20:
                returnedId = R.drawable.square_u;
                break;
            case 21:
                returnedId = R.drawable.square_v;
                break;
            case 22:
                returnedId = R.drawable.square_w;
                break;
            case 23:
                returnedId = R.drawable.square_x;
                break;
            case 24:
                returnedId = R.drawable.square_y;
                break;
            case 25:
                returnedId = R.drawable.square_z;
                break;
            default:
                returnedId = R.drawable.square_a;
        }
        return returnedId;
    }
}