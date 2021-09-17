package com.shado.q_learning.map;
import com.shado.q_learning.map.data.Data;


/**
* Map class represents the map of the robot which contains the different data the robot uses to 
* calculate the Q-vals. it also contains all the print functions to the console.
*
*
*/
public class Map {
    public Data[][] map;
    public final static int ROW_SIZE = 6;
    public final static int COL_SIZE = 7;

    public Map() {
        this.map = new Data[ROW_SIZE][COL_SIZE];
        this.initMap();        
    }

    /**
     * initializes the robot map with a constant map of obstacles and rewards.
     * the map starts at row 0 and col 0 and ends at row 6 and col 7.
     * obstacles are located at (1,1) , (1,4) , (3,1), (3,4).
     * negative rewards are located all in the col 0 and one in (4,6).
     * positive reward is located at (5,6).
     * 
     * @since 1.0
    */    
    public void initMap() {
        Data negRewardData = null;
        Data posRewardData = null;
        Data obstacleData = null;
        Data defaultData = null;
        //initializes the map with the different types based on the map
        for(int row = 0; row < ROW_SIZE; row++) {
            for(int col = 0; col < COL_SIZE; col++) {
                if(col == 0) {
                    negRewardData = new Data("-100", "????", "????");
                    map[row][col] = negRewardData;
                } else if(row == 4 && col == 6) {
                    negRewardData = new Data("-100", "????", "????");
                    map[row][col] = negRewardData;
                } else if(row == 5 && col == 6) {
                    posRewardData = new Data("+100", "????", "????");
                    map[row][col] = posRewardData;
                } else if(row == 1 && col == 1) {
                    obstacleData = new Data("????", "????", "####");
                    map[row][col] = obstacleData;
                } else if(row == 1 && col == 4) {
                    obstacleData = new Data("????", "????", "####");
                    map[row][col] = obstacleData;
                } else if(row == 3 && col == 1) {
                    obstacleData = new Data("????", "????", "####");
                    map[row][col] = obstacleData;
                } else if (row == 3 && col == 4) {
                    obstacleData = new Data("????", "????", "####");
                    map[row][col] = obstacleData;
                } else {
                    defaultData = new Data("????", "Waiting", "????");
                    map[row][col] = defaultData;
                }
            }
        }        
    }
    
    /**
     * updates the optimal path by taking the greatest Q-val at a given state
     * 
     * @since 1.0
    */
    public void updateOptimal() {
        String N = "^^^^";
        String E = ">>>>";
        String S = "VVVV";
        String W = "<<<<";
        int pos = -1;
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
                //if there is a obstacle, skip over it 
                if (map[row][col].GetOptimal().equals("Waiting")) {
                    //finds the greatest values position and sets the optimal 
                    pos = map[row][col].GetMaxQValuePos();
                    if (pos == 0) {
                        map[row][col].SetOptimal(N);
                    }
                    if (pos == 1) {
                       map[row][col].SetOptimal(E);
                    }
                    if (pos == 2) {
                        map[row][col].SetOptimal(S);
                    }
                    if (pos == 3) {
                        map[row][col].SetOptimal(W);
                    }
                }
            }
        }
    }

    /**
     * prints frequency map to the console
     * @since 1.0
    */    
    public void printFreqMap() {
        System.out.println("Table of N(s,a):");
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
                if (map[row][col].GetQValue(0).equals("null")) {
                    if (!map[row][col].GetReward().equals("????")) {
                        System.out.print(map[row][col].GetReward() + " ");
                    } else if (!map[row][col].GetObstacle().equals("????")) {
                        System.out.print(map[row][col].GetObstacle() + " ");
                    }
                } else {
                    for (int pos = 0; pos < 4; pos++) {
                        if (pos == 0) System.out.print("(N: " + map[row][col].GetFreq(pos) + ", ");
                        if (pos == 1) System.out.print("E: " + map[row][col].GetFreq(pos) + ", ");
                        if (pos == 2) System.out.print("S: " + map[row][col].GetFreq(pos) + ", ");
                        if (pos == 3) System.out.print("W: " + map[row][col].GetFreq(pos) + ")  ");
                    }
                }
            }
            System.out.println("");
        }
        System.out.println("\n");
    
    }

    /**
     * prints Q-val map to the console
     * @since 1.0
    */
    public void printQvalMap() {
        System.out.println("Table of Q(s,a):");
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
                if (map[row][col].GetQValue(0).equals("null")) {
                    if (!map[row][col].GetReward().equals("????")) {
                        System.out.print(map[row][col].GetReward() + " ");
                    } else if (!map[row][col].GetObstacle().equals("????")) {
                        System.out.print(map[row][col].GetObstacle() + " ");
                    }
                } else {
                    for (int pos = 0; pos < 4; pos++) {
                        if (pos == 0) System.out.print("(N: " + map[row][col].GetQValue(pos) + ", ");
                        if (pos == 1) System.out.print("E: " + map[row][col].GetQValue(pos) + ", ");
                        if (pos == 2) System.out.print("S: " + map[row][col].GetQValue(pos) + ", ");
                        if (pos == 3) System.out.print("W: " + map[row][col].GetQValue(pos) + ")  ");
                    }
                }
            }
            System.out.println("");
        }
        System.out.println("\n");
    }

    /**
     * Prints optimal map to the console
     * @since 1.0
    */    
    public void printOptimalMap() {
        System.out.println("Table of optimal policy:");
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
                if (map[row][col].GetOptimal().equals("????")) {
                    if (!map[row][col].GetReward().equals("????")) {
                        System.out.print(map[row][col].GetReward() + " ");
                    } else if (!map[row][col].GetObstacle().equals("????")) {
                        System.out.print(map[row][col].GetObstacle() + " ");
                    }
                } else {
                    System.out.print(map[row][col].GetOptimal() + " ");
                }
            }
            System.out.println("");
        }
        System.out.println("\n");
    }
}
