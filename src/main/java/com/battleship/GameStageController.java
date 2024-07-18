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
                if(userboard.allShipsSunk()){
                    logBox.updateMessage(" Game completed. Winner AI");
                }
                else if (aiboard.allShipsSunk()) {
                    logBox.updateMessage(" Game completed. You WON!");

                }
            }

        } while (gamecompletion == false);


    }
}
