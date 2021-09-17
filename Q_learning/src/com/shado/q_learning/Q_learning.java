package com.shado.q_learning;
import com.shado.q_learning.gui.MyFrame;
import com.shado.q_learning.map.environment.Simulate;
import com.shado.q_learning.map.data.Tuple;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*Q_learning class is the main class that starts the GUI and runs the loop for a constant of 50,000 times.
*It also calculates the Qval and freq value for each state and stores it in the map. 
*
*@author Ali Shuhait
*@since 1.0
*/
public class Q_learning implements Runnable{
    public static void main(String[] args) {
        MyFrame.startFrame();
    }
    
    /**
     * @param firstTuple  the robots data before an action has taken place
     * @param secondTuple  the robots data after an action has taken place
     * @param sim  a simulate object that contains the map of data for the robot
     * @since 1.0
    */
    private static void calculateQVal(Tuple firstTuple, Tuple secondTuple, Simulate sim) { 
        int pos = -404;
        //finds the position using the action of the firstTuple
        if(firstTuple.getAction() == 'N') pos = 0;
        else if(firstTuple.getAction() == 'E') pos = 1;
        else if(firstTuple.getAction() == 'S') pos = 2;
        else if(firstTuple.getAction() == 'W') pos = 3;
        
        int currentFreq = sim.dataMap.map[firstTuple.getRow()][firstTuple.getCol()].GetFreq(pos);
        sim.dataMap.map[firstTuple.getRow()][firstTuple.getCol()].SetFreq(pos, currentFreq + 1);
        double currFreq = (double) sim.dataMap.map[firstTuple.getRow()][firstTuple.getCol()].GetFreq(pos);
        
        double gamma = 0.9;
        double Q = sim.dataMap.map[firstTuple.getRow()][firstTuple.getCol()].GetQValueDouble(pos);
        double Freq = 1.0 / currFreq;
        double reward = (double) firstTuple.getReward();
        double Qmax = -404;
        //if seocndTuple equals the terminal reward then assign it as the Qman otherwise assign the maximum value from secondTuple location
        if(secondTuple.getReward() == -100 || secondTuple.getReward() == 100) {
             Qmax = secondTuple.getReward();
        } else {
             Qmax = sim.dataMap.map[secondTuple.getRow()][secondTuple.getCol()].GetMaxQValue();
        } 
        double finalQvalue = Q + (Freq * (reward + (gamma * Qmax) - Q));
        sim.dataMap.map[firstTuple.getRow()][firstTuple.getCol()].SetQValue(pos, finalQvalue);
    }
    
    /**
     * loops for a constant of 50,000 times. during each loop, we assign a first and second tuple 
     * then calculate the qval and freq for each state. it also sends data to the Myframe class to display the information to the GUI 
     * 
     * @since 1.0
    */
    @Override
    public void run() {
        int stop = 50000;
        Simulate sim = new Simulate();
        int looper = 1;
        int cnt = 0;
        Tuple firstTuple = null;
        Tuple secondTuple = null;
        while (looper != stop) {
            int rowCol[] = sim.returnRandCoord();
            firstTuple = sim.getNextTuple(rowCol[0], rowCol[1]);
            while (cnt != 100) {
                secondTuple = sim.getNextTuple(firstTuple.getRow() + firstTuple.getRowMod(), firstTuple.getCol() + firstTuple.getColMod());
                calculateQVal(firstTuple, secondTuple, sim);
                
                if(!MyFrame.getFastForwardStatus()) {
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Q_learning.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    MyFrame.sendMapToGUI(sim.dataMap.map, firstTuple, secondTuple);
                }
               
                
                //leave the while loop when secondTuple hit a terminal state
                if(secondTuple.getReward() == -100 || secondTuple.getReward() == 100) {
                   break;
                } 
                //moves the values of the secondTuple to the firstTuple and repeats this cycle until 100 tuples have been created
                //or we hit a terminal reward state
                firstTuple.assign(firstTuple, secondTuple);
                cnt++;
            }
            MyFrame.removeTracker();
            looper++;
            cnt = 0;
        }
        sim.dataMap.updateOptimal();
        MyFrame.sendMapToGUI(sim.dataMap.map, firstTuple, secondTuple);
        MyFrame.sendOptimalPathToGUI(sim.dataMap.map);
    }
}