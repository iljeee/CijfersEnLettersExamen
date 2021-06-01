package com.example.cijfersenlettersexamen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NumberViewModel extends ViewModel {

    final int maxTargetNumber = 1000;
    final int minTargetNumber = 100;
    final int maxSmallNumber = 9;
    final int minSmallNumber = 1;
    final int maxBigNumber = 4;
    final int minBigNumber = 1;
    final int limit = 6;

    public MutableLiveData<Boolean> numbersLimitReached = new MutableLiveData<Boolean>();
    public MutableLiveData<Integer> timerNumber = new MutableLiveData<Integer>(0); //Timer number
    public MutableLiveData<Integer> timerLimit  = new MutableLiveData<Integer>(2); //The amount the timer will go for in seconds, 60s is the default time
    public MutableLiveData<Boolean> timerLimitReached = new MutableLiveData<Boolean>();

    Random random = new Random();
    Timer timer = new Timer();

    private int targetNumber = 1;
    private LinkedList<Integer> generatedNumbers = new LinkedList<Integer>();
    private int counter = 0;

    public int getTargetNumber() {
        if(this.targetNumber == 1){
            this.targetNumber = random.nextInt(maxTargetNumber + 1 - minTargetNumber) + minTargetNumber;
        }
        return this.targetNumber;
    }

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
        if(counter == limit){
            numbersLimitReached.setValue(true);
        }
        return number;
    }

    public int getSmallNumber(){

        int number;
        number = random.nextInt(maxSmallNumber + 1 - minSmallNumber) + minSmallNumber;
        this.generatedNumbers.add(number);
        this.counter++;
        if(counter == limit){
            numbersLimitReached.setValue(true);
        }
        return number;
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
