package com.example.cijfersenlettersexamen;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import be.bluebanana.zakisolver.LetterSolver;

public class LetterViewModel extends AndroidViewModel {
    final int limit = 9;
    final int solutionLimit = 20;

    public MutableLiveData<Boolean> lettersLimitReached = new MutableLiveData<Boolean>();
    public MutableLiveData<Integer> timerNumber = new MutableLiveData<Integer>(0); //Timer number
    public MutableLiveData<Integer> timerLimit  = new MutableLiveData<Integer>(2); //The amount the timer will go for in seconds, 60s is the default time
    public MutableLiveData<Boolean> timerLimitReached = new MutableLiveData<Boolean>();
    public MutableLiveData<LinkedList<String>> resultString = new MutableLiveData<LinkedList<String>>();

    private final Character[] availableConsonants = {'b', 'c', 'd','f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};
    private final Character[] availableVowels = {'a', 'e', 'i', 'o', 'u'};
    private LinkedList<Character> generatedLetters = new LinkedList<Character>();
    private int counter = 0;
    private Context context;

    Random random = new Random();
    Timer timer = new Timer();

    public LetterViewModel(@NonNull Application application) {
        super(application);
    }

    public Character getConsonant() {
        int number;
        char letter;
        number = random.nextInt(availableConsonants.length);
        letter = availableConsonants[number];
        this.generatedLetters.add(letter);
        Log.d("TAG", String.valueOf(this.generatedLetters));
        this.counter++;
        if(counter == limit){
            lettersLimitReached.setValue(true);
        }
        return letter;
    }

    public Character getVowel() {
        int number;
        char letter;
        number = random.nextInt(availableVowels.length);
        letter = availableVowels[number];
        this.generatedLetters.add(letter);
        Log.d("TAG", String.valueOf(this.generatedLetters));
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

    public void getSolutions() {
        LetterSolver solver = new LetterSolver();
        LinkedList<String> x = new LinkedList<String>();
        solver.loadDictionary(getApplication(), R.raw.dictionary_nl);
        solver.setInput(this.generatedLetters,
                results -> {
                    if (results.size() == 0) {
                        x.add("No solutions found.");
                    } else {
                        results.stream()
                                .limit(solutionLimit)
                                .forEach(result ->
                                        x.add(result)
                                );
                    }
                    this.resultString.postValue(x);
                }
        );
        // Start the solver
        new Thread(solver).start();
    }

    public void reset() {
        this.generatedLetters.clear();
        this.lettersLimitReached.setValue(false);
        this.timerLimitReached.setValue(false);
        this.counter = 0;
        //LinkedList<String> empty = new LinkedList<String>();
        //this.resultString.setValue(empty);
    }
}
