package com.battleship;

import com.battleship.mainStageModules.LogBox;
import com.battleship.models.AI;
import com.battleship.models.Board;



public class GameStageController {
    public boolean turnFlag = true;
    public boolean usersetupcompleted = false;
    public boolean gamecompletion = false;
    private LogBox logBox;


    public GameStageController() {
        this.logBox = new LogBox(); // Initialize LogBox
    }

    public void gameExecution(Board userboard, Board aiboard, AI ai){

        while (usersetupcompleted == false){
            usersetupcompleted = userboard.areAllShipsPlaced();
            logBox.updateMessage("Please place all ships for the game to start.");
        }

        do { 
            if (turnFlag == false){
                ai.attack(userboard);
                turnFlag = true;
            }

            if (userboard.allShipsSunk() == true || aiboard.allShipsSunk() == true ){
                logBox.updateMessage(" Game completed.");
            }

        } while (gamecompletion == false);

        //if out of do while display game completed and winner based on userboard.allshipsunk

    }
}
