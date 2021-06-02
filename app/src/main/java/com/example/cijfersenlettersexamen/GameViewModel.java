package com.example.cijfersenlettersexamen;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    final public NumberFragment numberFragment = new NumberFragment();
    final public LetterFragment letterFragment = new LetterFragment();
    final public ResultFragment resultFragment = new ResultFragment();
    final public EndscreenFragment endscreenFragment = new EndscreenFragment();

    //Variabelen die observed kunnen worden en een actie kunnen triggeren als ze veranderen
    public MutableLiveData<String> player1Name = new MutableLiveData<String>();
    public MutableLiveData<String> player2Name = new MutableLiveData<String>();
    public MutableLiveData<Integer> player1Score = new MutableLiveData<Integer>(0);
    public MutableLiveData<Integer> player2Score = new MutableLiveData<Integer>(0);
    public MutableLiveData<Integer> currentRound = new MutableLiveData<Integer>(1);
    public MutableLiveData<Boolean> pointAssigned = new MutableLiveData<Boolean>(false);
    public MutableLiveData<Fragment> currentFragment = new MutableLiveData<Fragment>(numberFragment);

    public Fragment currentGame = numberFragment;

    private int numberOfRounds = 0;

    public void setPlayerNames(String firstPlayerName, String secondPlayerName){
        player1Name.setValue(firstPlayerName);
        player2Name.setValue(secondPlayerName);
    }

    public void setNumberOfRounds(int numberOfRounds){
        this.numberOfRounds = numberOfRounds;
    }

    public void nextFragment(){
        //Wanneer we in number of letter fragment zitten, gaan we naar result fragment
        if (currentFragment.getValue() == numberFragment || currentFragment.getValue() == letterFragment){
            currentFragment.setValue(resultFragment);
            return;
        //We blijven spelen als we in result fragment zitten, currentround kleiner is dan totaal aantal rounds
        //en er een score gegeven is
        } else if (currentFragment.getValue() == resultFragment && numberOfRounds > currentRound.getValue() && pointAssigned.getValue()){
            if (currentGame == numberFragment){
                currentFragment.setValue(letterFragment);
                currentGame = letterFragment;
            } else {
                currentFragment.setValue(numberFragment);
                currentGame = numberFragment;
            }
            currentRound.setValue(currentRound.getValue()+1);
            pointAssigned.setValue(false);
        //Wanneer current round gelijk is aan totaal aantal rounds en er is een score gegeven, gaan we naar het eindscherm
        } else if (numberOfRounds == currentRound.getValue() && pointAssigned.getValue()){
            currentFragment.setValue(endscreenFragment);
        }
    }

    //De spelers punten geven
    public void addPoint(int player) {
        if(player == 1 && !pointAssigned.getValue()){
            player1Score.setValue(player1Score.getValue()+1);
            pointAssigned.setValue(true);
        } else if(player == 2 && !pointAssigned.getValue()) {
            player2Score.setValue(player2Score.getValue()+1);
            pointAssigned.setValue(true);
        }
    }

    //Beide spelers krijgen een punt indien gelijkspel
    public void draw() {
        if(!pointAssigned.getValue()) {
            player1Score.setValue(player1Score.getValue() + 1);
            player2Score.setValue(player2Score.getValue() + 1);
            pointAssigned.setValue(true);
        }
    }

    //Bepalen wie de winnaar is of gelijkspel
    public String getWinnerName() {
        if(player1Score.getValue() > player2Score.getValue()){
            return player1Name.getValue();
        } else if(player2Score.getValue() > player1Score.getValue()){
            return player2Name.getValue();
        } else {
            return "It's a draw!";
        }
    }

    //Bepalen wat de winnende score is
    public int getWinnerScore() {
        if(player1Score.getValue() > player2Score.getValue()){
            return player1Score.getValue();
        }
        return player2Score.getValue();
    }
}
