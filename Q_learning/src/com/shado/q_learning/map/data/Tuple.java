package com.shado.q_learning.map.data;

/**
 * Tuple class represents the robots state before and after an action. Contains
 * the robots action, reward, current row and column, and next row and col. Used
 * in calculating the Q-value for a given state.
 */
public class Tuple {

    private char action; // the direction the robot moves
    private int reward; //the reward from an action
    private int row; //actual location
    private int rowMod; //next square movement
    private int col; //actual location
    private int colMod; //next square movement

    /**
     * Assigns the second tuples data to the first tuples data
     *
     * @param firstTuple the robots data before an action has taken place
     * @param secondTuple the robots data after an action has taken place
     * @since 1.0
     */
    public void assign(Tuple firstTuple, Tuple secondTuple) {
        //assigns the second Tuple to the first to continue the trajectory and also frees up space
        firstTuple.row = secondTuple.row;
        firstTuple.rowMod = secondTuple.rowMod;
        firstTuple.col = secondTuple.col;
        firstTuple.colMod = secondTuple.colMod;
        firstTuple.reward = secondTuple.reward;
        firstTuple.action = secondTuple.action;
    }

    /**
     * @return An Action
     * @since 1.0
     */
    public char getAction() {
        return action;
    }

    /**
     * @return A Reward
     * @since 1.0
     */
    public int getReward() {
        return reward;
    }

    /**
     * @return Current Row coordinate
     * @since 1.0
     */
    public int getRow() {
        return row;
    }

    /**
     * @return Next Row coordinate
     * @since 1.0
     */
    public int getRowMod() {
        return rowMod;
    }

    /**
     * @return Current Column coordinate
     * @since 1.0
     */
    public int getCol() {
        return col;
    }

    /**
     * @return Next Column Coordinate
     * @since 1.0
     */
    public int getColMod() {
        return colMod;
    }

    /**
     * @param action a character that represents four possible movements: N for
     * North, E for East, S for South, and W for West.
     * @since 1.0
     */
    public void setAction(char action) {
        this.action = action;
    }

    /**
     * @param reward a integer that represents reward for the robots action.
     * @since 1.0
     */
    public void setReward(int reward) {
        this.reward = reward;
    }

    /**
     * @param row the current row coordinate of the robot
     * @since 1.0
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @param rowMod the robots row coordinate after an action has taken place
     * @since 1.0
     */
    public void setRowMod(int rowMod) {
        this.rowMod = rowMod;
    }

    /**
     * @param col the current column coordinate of the robot
     * @since 1.0
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @param colMod the robots column coordinate after an action has taken
     * place
     * @since 1.0
     */
    public void setColMod(int colMod) {
        this.colMod = colMod;
    }
}
