package com.battleship;

import com.battleship.models.AI;
import com.battleship.models.Board;


public class GameStageController {
    public boolean turnFlag = true;
    public boolean usersetupcompleted = false;
    public boolean gamecompletion = false;

    public void gameExecution(Board userboard, Board aiboard, AI ai){

        while (usersetupcompleted == false){
            usersetupcompleted = userboard.areAllShipsPlaced();
            //implement status banner
        }

        do { 
            if (turnFlag == false){
                ai.attack(userboard);
                turnFlag = true;
            }

            if (userboard.allShipsSunk() == true || aiboard.allShipsSunk() == true ){
                //implement banner write game completed 
            }

        } while (gamecompletion == false);



    }
}
