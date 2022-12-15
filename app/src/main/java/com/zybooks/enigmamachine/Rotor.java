package com.zybooks.enigmamachine;

public class Rotor{
    // This class is the rotor object in the enigma machine program

    // option is the selected rotor, there are eight possible options
    private int option;

    // Which value the difference array will start at
    private int setting;

    // Which point in the array will spin the proceeding rotor, each option has a
    // unique switch case
    private int switchCase;

    private final int[] rotor1 = { 4, 9, 10, 2, 7, 1, 23, 9, 13, 16, 3, 8, 2, 9, 10, 18, 7, 3, 0, 22, 6, 13, 5, 20, 4,
            10 };

    private final int[] rotor2 = { 0, 8, 1, 7, 14, 3, 11, 13, 15, 18, 1, 22, 10, 6, 24, 13, 0, 15, 7, 20, 21, 3, 9, 24,
            16, 5 };

    private final int[] rotor3 = { 1, 2, 3, 4, 5, 6, 22, 8, 9, 10, 13, 10, 13, 0, 10, 15, 18, 5, 14, 7, 16, 17, 24, 21,
            18, 15 };

    // Make mirror/reflection rotor here.
    private final int[] reflectionrotorB = { 24, 16, 18, 4, 12, 13, 5, 22, 7, 14, 3, 21, 2, 23, 24, 19, 14, 10, 13, 6, 8,
            1, 25, 12, 2, 20 };

    private int[] difference;
    private int[] reverse;

    public Rotor() {
        option = 1;
        setting = 0;
        difference = rotor1;

        switchCase = 16;
    }

    public Rotor(int option, int setting) {

        switch (option) {

            case 1:
                this.option = option;
                this.setting = setting - 1;
                difference = rotor1;
                reverse = inverseKey(difference);
                switchCase = 16;
                break;
            case 2:
                this.option = option;
                this.setting = setting - 1;
                difference = rotor2;
                reverse = inverseKey(difference);
                switchCase = 4;
                break;
            case 3:
                this.option = option;
                this.setting = setting - 1;
                difference = rotor3;
                reverse = inverseKey(difference);
                switchCase = 21;
                break;
            case 4:
                this.option = option;
                this.setting = setting - 1;
                difference = reflectionrotorB;
                switchCase = 0;
                break;
            // There will be 8 total cases in the future, this is just a test. Bet (Future Matt)
            default:
                this.option = 1;
                this.setting = 0;
                difference = rotor1;
                reverse = inverseKey(difference);
                switchCase = 16;
        }
    }

    // Get current objects setting
    public int getSetting() {
        return this.setting;
    }

    // Set current objects setting
    public void setSetting(int setting) {
        this.setting = setting - 1;
    }

    // Get current rotor selection
    public int getOption() {
        return this.option;
    }

    // Set option changes the current rotor it also changes the setting to the
    // default
    public void setOption(int option) {
        switch (option) {

            case 1:
                this.option = option;
                this.setting = 0;
                difference = rotor1;
                reverse = inverseKey(difference);
                switchCase = 16;
                break;
            case 2:
                this.option = option;
                this.setting = 0;
                difference = rotor2;
                reverse = inverseKey(difference);
                switchCase = 4;
                break;
            case 3:
                this.option = option;
                this.setting = 0;
                difference = rotor3;
                reverse = inverseKey(difference);
                switchCase = 21;
                break;
            // There will be 8 total cases in the future, this is just a test.
            default:
                this.option = 1;
                this.setting = 0;
                difference = rotor1;
                reverse = inverseKey(difference);
                switchCase = 16;
        }
    }

    // gets current objects switch case
    public int getswitchCase() {
        return this.switchCase;
    }

    // This should never have to be used in the final program, it's just good for
    // testing.
    public void setswitchCase(int switchCase) {
        this.switchCase = switchCase;
    }

    public char encode(char letter) {
        if (Character.isLetter(letter)) {
            letter = Character.toUpperCase(letter);
            int ascii = (int) letter;

            if (ascii < 65 || ascii > 90) {
                System.out.println("Out of range");
                return 'A';
            } else {
                int diff = setting + (ascii - 65);
                if (diff > 25)
                    diff = diff % 26;

                ascii += difference[diff];

                if (ascii > 90) {
                    ascii = ascii % 90;
                    ascii += 64;
                    return (char) ascii;
                } else {
                    return (char) ascii;
                }

            }
        } else {
            return 'A';
        }
    }

    public char reverseEncode(char letter) {
        if (Character.isLetter(letter)) {
            letter = Character.toUpperCase(letter);
            int ascii = (int) letter;
            int i = (ascii - 65) + setting;
            //System.out.println(i);
            if (i > 25)
                i = i%26;
            //System.out.println(reverse[i]);

            ascii += reverse[i];


            // System.out.print(ascii + ",");
            // System.out.print(i);

            // System.out.println(ascii);

            if (ascii > 90) {
                ascii = ascii % 90;
                ascii += 64;
                return (char) ascii;
            } else if (ascii < 65) {
                ascii = 91 - (65 - ascii);

                return (char) ascii;
            } else {
                return (char) ascii;
            }

        }

        return 'A';
    }

    private int[] inverseKey(int[] difference) {
        int[] inverse = new int[26];
        int i = 0;
        for (int x : difference) {
            int ascii = (i + 65) + x;
            if (ascii > 90) {
                ascii = (ascii % 90) + 64;
            }

            int diff = (i + 65) - ascii;

            if (diff < 0)
                diff = diff + 26;

            inverse[ascii-65] = diff;
            i++;

        }

        return inverse;

    }

    public void turnRotor() {
        setting += 1;
        if(setting > 25) {
            this.setting = 0;
        }
    }

    public void letterToSetting(char letter) {
        if (Character.isLetter(letter)) {
            letter = Character.toUpperCase(letter);
            int ascii = (int) letter;
            ascii = ascii -65;
            this.setting = ascii;
        }
    }

    public boolean isswitchCase() {
        if (this.setting == this.switchCase) {
            return true;
        } else {
            return false;
        }
    }



}
