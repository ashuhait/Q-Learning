package com.shado.q_learning.map.data;

   /**
    * Data class is the information found in each state of the Map class. it includes
    * reward, optimal path, and if there is an obstacle in that state. there is also a 
    * frequency and a q-value for each direction the robot travels. the Map class creates
    * new instances of Data class depending on the size of the maps row and column size.
    * 
    * @author Ali Shuhait
    * @since 1.0
   */
public class Data {

    private int[] Freq;
    private double[] qValues;
    private String optimal;
    private String reward;
    private String obstacle;
    
    /** 
     * 
     * @param reward a string that represents a positive or negative reward in a given state. either -100, 100, or ???? for no reward.  
     * @param optimal a string that represents the direction of the optimal path which is one of the four constants: "^^^^", ">>>>", "VVVV", "<<<<"
     * @param obstacle a String that represents a state that the robot cannot enter 
     * 
     * @since 1.0
    */
    public Data(String reward, String optimal, String obstacle) {
        //the "####" string is for obstacles, the "Waiting" is for the default spaces, and "-100" or "100" is the terminal reward space
        this.reward = reward;
        this.optimal = optimal;
        this.obstacle = obstacle;
        if(reward.equals("????") && obstacle.equals("????")) {
            this.Freq = new int[] {0, 0, 0, 0};
            this.qValues = new double[] {0.0, 0.0, 0.0, 0.0};
        } else {
            this.Freq = null;
            this.qValues = null;            
        }
    }
    
    /**
     * @return A frequency at the given position and 
     *         a -404 error if Freq is not initialized
     * @since 1.0
    */
    public int GetFreq(int pos) {
        if(Freq == null) return -404;
        return Freq[pos];
    }
    
    /**
     * @param pos  a integer that represents the four directions.
     *             0 for North, 1 for East, 2 for West, 3 for South
     * @param val  a integer for the access frequency of a given state    
     * @since 1.0
    */
    public void SetFreq(int pos, int val) {
        Freq[pos] = val;
    }

    /**
     * @return the max Q-value for the four direction and 
     *         a -404 error if qValues is not initialized
     * @since 1.0
    */
    public double GetMaxQValue() {        
       if(qValues == null) return -404;
        double max = qValues[0];
        for (int i = 1; i < qValues.length; i++) {
            max = Math.max(max, qValues[i]);
        }
        return max;
    }
    
    /**
     * @return the max Q-value's index in the qValue array and
     *         a -404 error if qValues is not initialized
     * @since 1.0
    */
    public int GetMaxQValuePos() {
       if(qValues == null) return -404;
       double max = qValues[0];
       int index = 0;
       for(int i = 1; i < qValues.length; i++) {
           if(qValues[i] >= max) {
               max = qValues[i];
               index = i;
           }
       }
       return index;
    }
    
    /**
     * @param pos  a integer that represents the four directions.
     *             0 for North, 1 for East, 2 for West, 3 for South
     * @return a String representation of the Q-value at the 
     *         given position
     * @since 1.0
    */
    public String GetQValue(int pos) {
        if(qValues == null) return "null";
        return Double.toString(qValues[pos]);
    }
    
    /**
     * @param pos  a integer that represents the four directions.
     *             0 for North, 1 for East, 2 for West, 3 for South     * 
     * @return A double representation of the Q-value at a given position
     * @since 1.0
    */
    public double GetQValueDouble(int pos) {
        if(qValues == null) return -404;
        return qValues[pos];
    }
    
    /**
     * @param pos  a integer that represents the four directions.
     *             0 for North, 1 for East, 2 for West, 3 for South
     * @param val  a double for the Q-value of a given state    
     * @since 1.0
    */
    public void SetQValue(int pos, double val) {
        //if the Q-value has not been initialized print an error message
        if(qValues == null) {
            System.out.println("Error");
            return;
        }
        qValues[pos] = val;
    }
    
    /**
     * @param val a String for the optimal direction. 
     *            Can only be one of four constants: "^^^^", ">>>>", "VVVV", "<<<<"
     * @since 1.0
    */
    public void SetOptimal(String val) {
        optimal = val;
    }
    
    /**
     * @return the optimal path for a given state
     * @since 1.0
    */
    public String GetOptimal() {
        return optimal;
    }
    
    /**
     * @param val a String for the reward 
     * @since 1.0
    */
    public void SetReward(String val) {
        reward = val;
    }
    
    /**
     * @return the reward for a given state
     * @since 1.0
    */
    public String GetReward() {
        return reward;
    }
    
    /**
     * @return the obstacle for a given state. "####" for a obstacle string and "????" for a open space
     * @since 1.0
    */
    public String GetObstacle() {
        return obstacle;
    }

    /**
     * @param obstacle a String that represents a state that the robot cannot enter 
     * @since 1.0
    */
    public void setObstacle(String obstacle) {
        this.obstacle = obstacle;
    }
}
