package com.battleship;

import com.battleship.models.Board;


public class GameStageController {
    public boolean turnFlag = true;
    public boolean usersetupcompleted = false;
    public boolean gamecompletion = false;

    public void gameExecution(Board userboard, Board aiboard){

        while (usersetupcompleted == false){
            usersetupcompleted = userboard.isboardplaced();
            //implement status banner
        }

        do { 
            
            if (userboard.allShipsSunk() == true || aiboard.allShipsSunk() == true ){
                
            }

        } while (gamecompletion == false);



    }
}
