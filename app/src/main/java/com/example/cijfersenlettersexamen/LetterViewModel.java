package com.example.cijfersenlettersexamen;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LetterViewModel extends ViewModel {
    final int maxSmallNumber = 9;
    final int minSmallNumber = 1;
    final int maxBigNumber = 4;
    final int minBigNumber = 1;
    final int limit = 9;

    public MutableLiveData<Boolean> lettersLimitReached = new MutableLiveData<Boolean>();
    public MutableLiveData<Integer> timerNumber = new MutableLiveData<Integer>(0); //Timer number
    public MutableLiveData<Integer> timerLimit  = new MutableLiveData<Integer>(7); //The amount the timer will go for in seconds, 60s is the default time
    public MutableLiveData<Boolean> timerLimitReached = new MutableLiveData<Boolean>();

    private final String[] availableConsonants = {"b", "c", "d","f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "y", "z"};
    private final String[] availableVowels = {"a", "e", "i", "o", "u"};
    private LinkedList<String> generatedLetters = new LinkedList<String>();
    private int counter = 0;

    Random random = new Random();
    Timer timer = new Timer();

    public String getConsonant() {
        int number;
        String letter;
        number = random.nextInt(availableConsonants.length);
        letter = availableConsonants[number];
        this.generatedLetters.add(letter);
        this.counter++;
        if(counter == limit){
            lettersLimitReached.setValue(true);
        }
        return letter;
    }

    public String getVowel() {
        int number;
        String letter;
        number = random.nextInt(availableVowels.length);
        letter = availableVowels[number];
        this.generatedLetters.add(letter);
        this.counter++;
        if(counter == limit){
            lettersLimitReached.setValue(true);
        }
        return letter;
    }

    public void startTimer() {
        long startTime = System.currentTimeMillis(); //Get the current time
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - startTime <= timerLimit.getValue() * 1000 /*x1000 for seconds*/) {
                    //timer is een background thread activiteit en we gebruiken postvalue omdat het an bij de eerstvolgende kans aangepast zal worden
                    timerNumber.postValue(timerNumber.getValue() + 1);
                } else {
                    timerNumber.postValue(0);
                    timerLimitReached.postValue(true);
                    cancel();
                }

            }
        }, 500, 1000);
    }
}
