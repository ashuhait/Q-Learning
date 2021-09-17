package com.shado.q_learning.map.environment;

import com.shado.q_learning.map.data.Data;
import com.shado.q_learning.map.Map;
import com.shado.q_learning.map.data.Tuple;
import java.util.Random;

/**
* Simulate class represents the different effects that can happen in the robots map environment.
* These effects include: random drift chance, a random action the robot performs, and a random chance
* to pick the highest value. 
*
*/
public class Simulate {
    public Map dataMap = new Map();
    private Random rand;
    private int e_greedyChance;
    private int randDrift;
    private int randAction;
    
    public Simulate() {
        this.rand = new Random();
    }

    /**
    * @param row a integer that represents the current row the robot is located
    * @param col a integer that represents the current col the robot is located 
    * @return A Tuple populated with data
    */
    public Tuple getNextTuple(int row, int col) {
        // cant land on a obstacle or any of the rewards
        Data[][] map = dataMap.map;
        Tuple temp = new Tuple();
        e_greedyChance = rand.nextInt(100) + 1;
        randDrift = rand.nextInt(10) + 1 ;
        //if we run into any of the terminal rewards then assign the reward and return the tuple
        if(col == 0 || (col ==6 && row == 4)) {
            temp.setReward(-100);
            return temp;
        } else if(col == 6 && row == 5) {
            temp.setReward(100);
            return temp;
        }
        
        if (e_greedyChance >= 1 && e_greedyChance <= 10) {//10 percent chance to choose random action

            randAction = rand.nextInt(4); //random four numbers: N = 0, E = 1, S = 2, W = 3 
            
            if (randAction == 0) MoveNorth(row, col, randDrift, temp, map);
            if (randAction == 1) MoveEast(row, col, randDrift, temp, map);
            if (randAction == 2) MoveSouth(row, col, randDrift, temp, map);
            if (randAction == 3) MoveWest(row, col, randDrift, temp, map);
        } else { //90 percent chance to pick the greatest Qvalue
            //assigs the four values and picks the greatest one, then calls the move function
            double northVal = map[row][col].GetQValueDouble(0);
            double eastVal = map[row][col].GetQValueDouble(1);
            double southVal = map[row][col].GetQValueDouble(2);
            double westVal = map[row][col].GetQValueDouble(3);
            if ((northVal >= eastVal) && (northVal >= southVal) && (northVal >= westVal)) {
                MoveNorth(row, col, randDrift, temp, map);
            } else if ((eastVal >= southVal) && (eastVal >= westVal)) {
                MoveEast(row, col, randDrift, temp, map);
            } else if ((southVal >= westVal)) {
                MoveSouth(row, col, randDrift, temp, map);
            } else {
                MoveWest(row, col, randDrift, temp, map);
            }
        }
        return temp;
    }

    /**
    * MoveNorth method goes North with 80% chance, and drifts West or East with 10% chance each.
    * 
    * @param row a integer that represents the current row the robot is located
    * @param col a integer that represents the current col the robot is located 
    * @param randDrift a integer that represents a probability of drifting to the left or right of the desired direction
    * @param temp a Tuple that represents the current tuple of data
    * @param map a Data[][] array that represents the robots map
    */
    public void MoveNorth(int row, int col, int randDrift, Tuple temp, Data[][] map) {
        if (randDrift > 2) { //going straight
            modifyTuple(temp, row, col, -1, 0, 'N', -2);
        } else if (randDrift == 1) { //going left
            modifyTuple(temp, row, col, 0, -1, 'W', -1);
        } else if (randDrift == 2) { //going right
            modifyTuple(temp, row, col, 0, 1, 'E', -3);
        }
    }

    /**
    * MoveEast method goes East with 80% chance, and drifts North or South with 10% chance each.
    * 
    * @param row a integer that represents the current row the robot is located
    * @param col a integer that represents the current col the robot is located 
    * @param randDrift a integer that represents a probability of drifting to the left or right of the desired direction
    * @param temp a Tuple that represents the current tuple of data
    * @param map a Data[][] array that represents the robots map
    */
    public void MoveEast(int row, int col, int randDrift, Tuple temp, Data[][] map) {
        if (randDrift > 2) { //going straight
            modifyTuple(temp, row, col, 0, 1, 'E', -3);
        } else if (randDrift == 1) { //going left
            modifyTuple(temp, row, col, -1, 0, 'N', -2);
        } else if (randDrift == 2) { //going right
            modifyTuple(temp, row, col, 1, 0, 'S', -2);
        }
    }

    /**
    * MoveSouth method goes South with 80% chance, and drifts East or West with 10% chance each.
    * 
    * @param row a integer that represents the current row the robot is located
    * @param col a integer that represents the current col the robot is located 
    * @param randDrift a integer that represents a probability of drifting to the left or right of the desired direction
    * @param temp a Tuple that represents the current tuple of data
    * @param map a Data[][] array that represents the robots map
    */
    public void MoveSouth(int row, int col, int randDrift, Tuple temp, Data[][] map) {
        if (randDrift > 2) { //going straight
            modifyTuple(temp, row, col, 1, 0, 'S', -2);
        } else if (randDrift == 1) { //going left
            modifyTuple(temp, row, col, 0, 1, 'E', -3);
        } else if (randDrift == 2) { //going right
            modifyTuple(temp, row, col, 0, -1, 'W', -1);
        }
    }

    /**
    * MoveWest method goes West with 80% chance, and drifts North or south with 10% chance each.
    * 
    * @param row a integer that represents the current row the robot is located
    * @param col a integer that represents the current col the robot is located 
    * @param randDrift a integer that represents a probability of drifting to the left or right of the desired direction
    * @param temp a Tuple that represents the current tuple of data
    * @param map a Data[][] array that represents the robots map
    */
    public void MoveWest(int row, int col, int randDrift, Tuple temp, Data[][] map) {
        if (randDrift > 2) { //going straight
            modifyTuple(temp, row, col, 0, -1, 'W', -1);
        } else if (randDrift == 1) { //going left
            modifyTuple(temp, row, col, 1, 0, 'S', -2);
        } else if (randDrift == 2) { //going right
            modifyTuple(temp, row, col, -1, 0, 'N', -2);
        }
    }
    
    /**
    * modifyTuple method moves the robot to the next state by setting the row and col to the new values
    * depending on the action that was taken
    * 
    * @param temp a Tuple that represents the current tuple of data
    * @param row a integer that represents the current row the robot is located
    * @param col a integer that represents the current col the robot is located
    * @param rowMod a integer that represents the row move taken by the robot: 0 if it does not move,
    * -1 if it moves north and 1 if it moves south    * 
    * @param colMod a integer that represents the col move taken by the robot: 0 if it does not move,
    * -1 if it moves west and 1 if it moves east 
    * @param action a character that represents four possible movements: N for
    * @param reward a integer that represents reward for the robots action.
    */
    public void modifyTuple(Tuple temp, int row, int col, int rowMod, int colMod, char action, int reward) {
        temp.setAction(action);
        temp.setReward(reward);
        temp.setRow(row);
        temp.setCol(col);
        if(row + rowMod < 0 || row + rowMod >= dataMap.ROW_SIZE){
             ; //stay in the same state
        } else if(col + colMod < 0 || col + colMod >= dataMap.COL_SIZE) {
            ; //stay in the same state
        } else if (dataMap.map[row + rowMod][col + colMod].GetObstacle().equals("####")) {
            ; //stay in the same state
        } else {
            if(rowMod != 0) temp.setRowMod(rowMod);
            if(colMod != 0) temp.setColMod(colMod);
        }
    }

    /**
    * returnRandCoord method is used giving the robot a starting position on the map
    * 
    * @return A int[] array that contains a random row and col pair that is not a obstacle state or in any of the reward states
    */
    public int[] returnRandCoord() {
        int randCol;
        int randRow;
        while (true) {
            randCol = rand.nextInt(7);
            if (randCol == 0) continue;  
            
            randRow = rand.nextInt(6);

            //obstacle checking
            if (randCol == 1 && randRow == 1) continue;            
            if (randCol == 1 && randRow == 3) continue;
            if (randCol == 4 && randRow == 1) continue;
            if (randCol == 4 && randRow == 3) continue;

            //reward checking
            if (randCol == 6 && randRow == 4) continue;
            if (randCol == 6 && randRow == 5) continue;
            //if it passes the checks then leave the while loop and assign the values to a temporary array and returns it
            break;
        }
        int temp[] = {randRow, randCol};
        
        return temp;
    }
}
