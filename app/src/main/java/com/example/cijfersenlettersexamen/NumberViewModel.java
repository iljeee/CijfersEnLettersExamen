package com.example.cijfersenlettersexamen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import be.bluebanana.zakisolver.NumberSolver;

public class NumberViewModel extends ViewModel {

    final int maxTargetNumber = 1000;
    final int minTargetNumber = 100;
    final int maxSmallNumber = 9;
    final int minSmallNumber = 1;
    final int maxBigNumber = 4;
    final int minBigNumber = 1;
    final int limit = 6;
    final int solutionLimit = 20;

    public MutableLiveData<Boolean> numbersLimitReached = new MutableLiveData<Boolean>(false);
    public MutableLiveData<Integer> timerNumber = new MutableLiveData<Integer>(0); //Timer number
    public MutableLiveData<Integer> timerLimit  = new MutableLiveData<Integer>(2); //The amount the timer will go for in seconds, 60s is the default time
    public MutableLiveData<Boolean> timerLimitReached = new MutableLiveData<Boolean>(false);
    public MutableLiveData<LinkedList<String>> resultString = new MutableLiveData<LinkedList<String>>();
    public int counter = 0;

    private Random random = new Random();
    private Timer timer = new Timer();
    private int targetNumber = 1;
    private LinkedList<Integer> generatedNumbers = new LinkedList<Integer>();

    //Genereert een willekeurig getal dat de spelers en de solver moeten proberen te benaderen
    public int getTargetNumber() {
        if(this.targetNumber == 1){
            this.targetNumber = random.nextInt(maxTargetNumber + 1 - minTargetNumber) + minTargetNumber;
        }
        return this.targetNumber;
    }

    //Een willekeurig groot getal genereren, toevoegen aan de generatedNumbers list en
    //numbersLimitReached op true zetten wanneer counter gelijk is aan limit
    public int getBigNumber(){
        int number;
        number = random.nextInt(maxBigNumber + 1 - minBigNumber) + minBigNumber;
        switch(number) {
            case 1:
                number = 10;
                break;
            case 2:
                number = 25;
                break;
            case 3:
                number = 50;
                break;
            case 4:
                number = 100;
                break;
            default:
        }
        this.generatedNumbers.add(number);
        this.counter++;
        if(this.counter == this.limit){
            numbersLimitReached.setValue(true);
        }
        return number;
    }

    //Een willekeurig klein getal genereren, toevoegen aan de generatedNumbers list en
    //numbersLimitReached op true zetten wanneer counter gelijk is aan limit
    public int getSmallNumber(){

        int number;
        number = random.nextInt(maxSmallNumber + 1 - minSmallNumber) + minSmallNumber;
        this.generatedNumbers.add(number);
        this.counter++;
        if(this.counter == this.limit){
            numbersLimitReached.setValue(true);
        }
        return number;
    }

    //Timer starten met een period van 1 seconde
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

    //De solver zoekt een formule die gebruikt kan worden om met behulp van de gegenereerde
    //getallen zo dicht mogelijk bij het target getal te komen
    public void getSolutions() {
        NumberSolver solver = new NumberSolver();
        LinkedList<String> x = new LinkedList<String>();
        solver.setInput(this.generatedNumbers, this.targetNumber,
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
        this.targetNumber = 1;
        this.generatedNumbers.clear();
        this.numbersLimitReached.setValue(false);
        this.timerLimitReached.setValue(false);
        this.counter = 0;
        LinkedList<String> empty = new LinkedList<String>();
        this.resultString.setValue(empty);
    }
}
