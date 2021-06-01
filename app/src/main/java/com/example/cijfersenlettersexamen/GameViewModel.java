package com.example.cijfersenlettersexamen;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private int numberOfRounds = 0;
    private boolean pointAssigned = false;

    final NumberFragment numberFragment = new NumberFragment();
    final LetterFragment letterFragment = new LetterFragment();
    final ResultFragment resultFragment = new ResultFragment();
    final EndscreenFragment endscreenFragment = new EndscreenFragment();

    public MutableLiveData<String> player1Name = new MutableLiveData<String>();
    public MutableLiveData<String> player2Name = new MutableLiveData<String>();
    public MutableLiveData<Integer> player1Score = new MutableLiveData<Integer>(0);
    public MutableLiveData<Integer> player2Score = new MutableLiveData<Integer>(0);
    public MutableLiveData<Integer> currentRound = new MutableLiveData<Integer>(1);
    public MutableLiveData<Fragment> currentFragment = new MutableLiveData<Fragment>(numberFragment);
    public Fragment currentGame = numberFragment;

    public void setPlayerNames(String firstPlayerName, String secondPlayerName){
        player1Name.setValue(firstPlayerName);
        player2Name.setValue(secondPlayerName);
    }

    public void setNumberOfRounds(int numberOfRounds){
        this.numberOfRounds = numberOfRounds;
    }

    public void nextFragment(){
        if (currentFragment.getValue() == numberFragment || currentFragment.getValue() == letterFragment){
            currentFragment.setValue(resultFragment);
            return;
        } else if (currentFragment.getValue() == resultFragment && numberOfRounds > currentRound.getValue() && pointAssigned){
            if (currentGame == numberFragment){
                currentFragment.setValue(letterFragment);
                currentGame = letterFragment;
            } else {
                currentFragment.setValue(numberFragment);
                currentGame = numberFragment;
            }
            currentRound.setValue(currentRound.getValue()+1);
            pointAssigned = false;
        } else if (numberOfRounds == currentRound.getValue() && pointAssigned){
            currentFragment.setValue(endscreenFragment);
        }
    }

    public void addPoint(int player) {
        if(player == 1 && !pointAssigned){
            player1Score.setValue(player1Score.getValue()+1);
            pointAssigned = true;
        } else if(player == 2 && !pointAssigned) {
            player2Score.setValue(player2Score.getValue()+1);
            pointAssigned = true;
        }
    }

    public String getWinnerName() {
        if(player1Score.getValue() > player2Score.getValue()){
            return player1Name.getValue();
        } else if(player2Score.getValue() > player1Score.getValue()){
            return player2Name.getValue();
        } else {
            return "It's a draw!";
        }

    }

    public int getWinnerScore() {
        if(player1Score.getValue() > player2Score.getValue()){
            return player1Score.getValue();
        }
        return player2Score.getValue();
    }

    public void draw() {
        if(!pointAssigned) {
            player1Score.setValue(player1Score.getValue() + 1);
            player2Score.setValue(player2Score.getValue() + 1);
            pointAssigned = true;
        }
    }
}
