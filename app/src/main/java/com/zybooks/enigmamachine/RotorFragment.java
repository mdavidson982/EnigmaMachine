package com.zybooks.enigmamachine;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */


public class RotorFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private ConstraintLayout keyButton;
    private ArrayList<Spinner> spinnerAL = new ArrayList<>();
    private ArrayList<ImageView> RotorAL = new ArrayList<>();
    private String[] returnArray = {"2","0","1","0","0","0"};
    private String returnString = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rotor, container, false);

        // Add the same click handler to all grid buttons


        keyButton = rootView.findViewById(R.id.parent);
        Log.i("onCreateView", "This is the onCreateView");
        SharedPreferences sharedPref = this.requireActivity().getPreferences(Context.MODE_PRIVATE);

        for (int i = 1; i < keyButton.getChildCount(); i++) {
            //Log.i("OnKeyboardClick", String.valueOf(keyButton.getChildAt(i)));
            if(keyButton.getChildAt(i) instanceof AppCompatImageView && i <= 3) {
                ImageView rotorImage = (ImageView) keyButton.getChildAt(i);
                RotorAL.add(rotorImage);
            }
            if(keyButton.getChildAt(i) instanceof AppCompatSpinner && i > 3) {
                Spinner spinner = (Spinner) keyButton.getChildAt(i);
                spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
            }
        }
        return rootView;
        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        returnString = "";
        if(adapterView.getId() == R.id.Rotor1_rotors){
            returnArray[0] = String.valueOf(i);
        }
        if(adapterView.getId() == R.id.Rotor2_rotors){
            returnArray[2] = String.valueOf(i);
        }
        if(adapterView.getId() == R.id.Rotor3_rotors){
            returnArray[4] = String.valueOf(i);
        }
        if(adapterView.getId() == R.id.Rotor1_settings){
            returnArray[1] = String.valueOf(i);
            RotorAL.get(0).setImageResource(rotorLetter(i));
        }
        if(adapterView.getId() == R.id.Rotor2_settings){
            returnArray[3] = String.valueOf(i);
            RotorAL.get(1).setImageResource(rotorLetter(i));
        }
        if(adapterView.getId() == R.id.Rotor3_settings){
            returnArray[5] = String.valueOf(i);
            RotorAL.get(2).setImageResource(rotorLetter(i));
        }

        for(int y = 0; y<returnArray.length; y++){

            if(y == returnArray.length -1){
                returnString += returnArray[y];
            }else{
                returnString += returnArray[y] + ",";
            }

        }
        Log.i("returnString", returnString);

        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("configuration", returnString);
        editor.apply();

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

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}