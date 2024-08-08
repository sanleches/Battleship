package com.battleship.view;

import com.battleship.model.AI;
import com.battleship.model.Board;

import javafx.scene.control.Label;



public class GameStageController {
    public boolean turnFlag = true;
    public boolean usersetupcompleted = false;
    public boolean gamecompletion = false;




    public void gameExecution(Board userboard, Board aiboard, AI ai, Label statusLabel){

        while (usersetupcompleted == false){
            usersetupcompleted = userboard.areAllShipsPlaced();
            statusLabel.setText("Please place all ships for the game to start.");
        }

        do { 
            if (turnFlag == false){
                ai.attack(userboard);
                turnFlag = true;
            }
            else{
                
            }

            if (userboard.allShipsSunk() == true || aiboard.allShipsSunk() == true ){
                if(userboard.allShipsSunk()){
                    statusLabel.setText(" Game completed. Winner AI");
                }
                else if (aiboard.allShipsSunk()) {
                    statusLabel.setText(" Game completed. You WON!");

                }
                gamecompletion = true;
            }

        } while (gamecompletion == false);


    }
}
