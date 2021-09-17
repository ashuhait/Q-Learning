package com.shado.q_learning.gui;

import com.shado.q_learning.map.Map;
import com.shado.q_learning.Q_learning;
import com.shado.q_learning.map.data.Data;
import com.shado.q_learning.map.data.Tuple;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* MyFrame class is the GUI for the application. Receives data from the Q_learning class
* and displays it to the GUI. The MyFrame thread will keep running until the restart button is pressed.
*
* @author Ali Shuhait  
* @since 1.0
*/
public class MyFrame extends JFrame implements ActionListener{
    private final static int ROW_SIZE = Map.ROW_SIZE;
    private final static int COL_SIZE = Map.COL_SIZE;
    private final static int MIN_FILLER_WIDTH = 65;
    private final static int MIN_FILLER_HEIGHT = 15;
    private final static int HORIZ_GAP = 5;  //horizontal gap for each square in the GridLayout
    private final static int VERT_GAP = 5;   //vertical gap for each square in the GridLayout
    private final static Dimension vertFiller = new Dimension(0,MIN_FILLER_HEIGHT);    
    private final static Dimension horizFiller = new Dimension(MIN_FILLER_WIDTH,0);
    private static DecimalFormat df = new DecimalFormat("#.##");

    
    private Thread thread;
    
    private static boolean isFastForward = false;
    private static boolean running = false;
    
    //Buttons for the button panel
    private static JButton[] buttons = new JButton[3];  
    private static JButton restartBtn = new JButton("restart");
    private static JButton playBtn = new JButton("play");
    private static JButton fastForwardBtn = new JButton("fast forward");
    
    //The gridlayout with 5 by 5 gap and 6 by 7 matrix
    private static GridLayout robotMap = new GridLayout(ROW_SIZE, COL_SIZE, HORIZ_GAP, VERT_GAP);
    private static JPanel[][] mapTiles = new JPanel[ROW_SIZE][COL_SIZE];
    
    private static JPanel[][] nPanel = new JPanel[ROW_SIZE][COL_SIZE];
    private static JPanel[][] ePanel = new JPanel[ROW_SIZE][COL_SIZE];
    private static JPanel[][] sPanel = new JPanel[ROW_SIZE][COL_SIZE];
    private static JPanel[][] wPanel = new JPanel[ROW_SIZE][COL_SIZE];    
    
    private static JLabel[][] Nlabel = new JLabel[ROW_SIZE][COL_SIZE];
    private static JLabel[][] Elabel = new JLabel[ROW_SIZE][COL_SIZE];
    private static JLabel[][] Slabel = new JLabel[ROW_SIZE][COL_SIZE];
    private static JLabel[][] Wlabel = new JLabel[ROW_SIZE][COL_SIZE];

    private static JLabel[][] Nvalues = new JLabel[ROW_SIZE][COL_SIZE];
    private static JLabel[][] Evalues = new JLabel[ROW_SIZE][COL_SIZE];
    private static JLabel[][] Svalues = new JLabel[ROW_SIZE][COL_SIZE];
    private static JLabel[][] Wvalues = new JLabel[ROW_SIZE][COL_SIZE];
    
    private Q_learning qlearn;

    public MyFrame() {
        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Q-LEARNING");
        setMaximumSize(DimMax);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /**
     * starts the MyFrame GUI and adds all the components to the GUI
     * @since 1.0
    */
    public static void startFrame() {
        //FIXME: move some code into a new method
        //FIXME: resize images correctly and add a better design for the map
        //FIXME: add a moving robot to the map
        MyFrame f = new MyFrame();
        
        JPanel mapHolder;
        
        mapHolder = new JPanel();
        
        mapHolder.setLayout(robotMap);
        
        //Adds the direction labels, frequency, and Q-value to each of the squares in the GridLayout.
        //Also formats each square in the GridLayout using a boxfiller  
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                
                if(j == 0 || (j == 6 && i == 4)) {
                    mapTiles[i][j] = new JPanel();
                    JLabel imgLabel = new JLabel();
                    ImageIcon fireTrapIcon = new ImageIcon("fireTrap.png");
                    Image fireTrapImg = fireTrapIcon.getImage();
                    Image scaledFireTrapImg = fireTrapImg.getScaledInstance(250, 170, Image.SCALE_SMOOTH);
                    fireTrapIcon = new ImageIcon(scaledFireTrapImg);
                    imgLabel.setIcon(fireTrapIcon);
                    mapTiles[i][j].add(imgLabel);
                    mapHolder.add(mapTiles[i][j]);
                    continue;
                } else if(j == 6 && i == 5) {
                    mapTiles[i][j] = new JPanel();
                    JLabel imgLabel = new JLabel();
                    ImageIcon bagOfMoneyIcon = new ImageIcon("bagOfMoney.png");
                    Image bagOfMoneyImg = bagOfMoneyIcon.getImage();
                    Image scaledBagOfMoneyImg = bagOfMoneyImg.getScaledInstance(250, 170, Image.SCALE_SMOOTH);
                    bagOfMoneyIcon = new ImageIcon(scaledBagOfMoneyImg);
                    imgLabel.setIcon(bagOfMoneyIcon);
                    mapTiles[i][j].add(imgLabel);
                    mapHolder.add(mapTiles[i][j]);
                    continue;
                } else if (i == 1 && (j == 1 || j == 4)) {
                    mapTiles[i][j] = new JPanel();
                    JLabel imgLabel = new JLabel();
                    ImageIcon rockIcon = new ImageIcon("rock.png");
                    Image rockImg = rockIcon.getImage();
                    Image scaledRockImg = rockImg.getScaledInstance(250, 170, Image.SCALE_SMOOTH);
                    rockIcon = new ImageIcon(scaledRockImg);
                    imgLabel.setIcon(rockIcon);
                    mapTiles[i][j].add(imgLabel);
                    mapHolder.add(mapTiles[i][j]);
                    continue;
                } else if (i == 3 && (j == 1 || j == 4)) {
                    mapTiles[i][j] = new JPanel();
                    JLabel imgLabel = new JLabel();
                    ImageIcon rockIcon = new ImageIcon("rock.png");
                    Image rockImg = rockIcon.getImage();
                    Image scaledRockImg = rockImg.getScaledInstance(250, 170, Image.SCALE_SMOOTH);
                    rockIcon = new ImageIcon(scaledRockImg);
                    imgLabel.setIcon(rockIcon);
                    mapTiles[i][j].add(imgLabel);
                    mapHolder.add(mapTiles[i][j]);
                    continue;
                }
                
                mapTiles[i][j] = new JPanel(new BorderLayout());
                
                nPanel[i][j] = new JPanel(); 
                Nlabel[i][j] = new JLabel("NORTH");
                Nvalues[i][j] = new JLabel("(0, 0)");
                nPanel[i][j].add(new Box.Filler(horizFiller,horizFiller,horizFiller));
                nPanel[i][j].setLayout(new BoxLayout(nPanel[i][j], BoxLayout.PAGE_AXIS));
                nPanel[i][j].add(Nlabel[i][j]);
                nPanel[i][j].add(Nvalues[i][j]);
                
                ePanel[i][j] = new JPanel();
                Elabel[i][j] = new JLabel("EAST");
                Evalues[i][j] = new JLabel("(0, 0)");
                ePanel[i][j].add(new Box.Filler(vertFiller,vertFiller,vertFiller));
                ePanel[i][j].setLayout(new BoxLayout(ePanel[i][j], BoxLayout.PAGE_AXIS));
                ePanel[i][j].add(Elabel[i][j]);
                ePanel[i][j].add(Evalues[i][j]);

                sPanel[i][j] = new JPanel();
                Slabel[i][j] = new JLabel("SOUTH");
                Svalues[i][j] = new JLabel("(0, 0)");
                sPanel[i][j].setLayout(new BoxLayout(sPanel[i][j], BoxLayout.PAGE_AXIS));
                sPanel[i][j].add(new Box.Filler(horizFiller,horizFiller,horizFiller));
                sPanel[i][j].add(Slabel[i][j]);
                sPanel[i][j].add(Svalues[i][j]);

                wPanel[i][j] = new JPanel();
                Wlabel[i][j] = new JLabel("WEST");
                Wvalues[i][j] = new JLabel("(0, 0)");
                wPanel[i][j].add(new Box.Filler(vertFiller,vertFiller,vertFiller));
                wPanel[i][j].setLayout(new BoxLayout(wPanel[i][j], BoxLayout.PAGE_AXIS));
                wPanel[i][j].add(Wlabel[i][j]);
                wPanel[i][j].add(Wvalues[i][j]);

                mapTiles[i][j].setBorder(new LineBorder(Color.BLACK));
                mapTiles[i][j].add(nPanel[i][j], BorderLayout.NORTH);
                mapTiles[i][j].add(ePanel[i][j], BorderLayout.EAST);
                mapTiles[i][j].add(sPanel[i][j], BorderLayout.SOUTH);
                mapTiles[i][j].add(wPanel[i][j], BorderLayout.WEST);

                mapHolder.add(mapTiles[i][j]);
            }
        }
        JPanel buttonPanel = new JPanel();
        
        buttons[0] = restartBtn;
        buttons[1] = playBtn;
        buttons[2] = fastForwardBtn;
        
       
        for(int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(f);
            buttons[i].setFocusable(false);
            buttonPanel.add(buttons[i]);
        }
        
        f.add(mapHolder, BorderLayout.CENTER);
        f.add(buttonPanel, BorderLayout.SOUTH);
        f.setVisible(true);
    }
    
    /**
     * Receives the updated map from Q_learning class and then updates the GUI
     * @param map  the updated robot map from Q_learning class
     * @param firstTuple the robots data before an action has taken place
     * @param secondTuple the robots data after an action has taken place
     * @since 1.0
    */
    public static void sendMapToGUI(Data[][] map, Tuple firstTuple, Tuple secondTuple) { 
        //resets the strings after it has been used in the for loop  
        String newNorthValue = "(";
        String newEastValue = "(";
        String newSouthValue = "(";
        String newWestValue = "(";
        
        
        //adds the Q-value and frequenecy from the updated map and adds it to its respective JLabel
        //FIXME: needs to be optimized
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if(j == 0 || (j == 6 && i == 4) || (j == 6 && i == 5)) {
                    continue;
                } else if (i == 1 && (j == 1 || j == 4)) {
                    continue; 
                } else if (i == 3 && (j == 1 || j == 4)) {
                    continue;
                }
                newNorthValue += df.format(map[i][j].GetQValueDouble(0));
                newEastValue += df.format(map[i][j].GetQValueDouble(1));
                newSouthValue += df.format(map[i][j].GetQValueDouble(2));
                newWestValue += df.format(map[i][j].GetQValueDouble(3));
                
                newNorthValue += ", ";
                newEastValue += ", ";
                newSouthValue += ", ";
                newWestValue += ", ";
                
                newNorthValue += Integer.toString(map[i][j].GetFreq(0));
                newEastValue += Integer.toString(map[i][j].GetFreq(1));
                newSouthValue += Integer.toString(map[i][j].GetFreq(2));
                newWestValue += Integer.toString(map[i][j].GetFreq(3));
                
                newNorthValue += ")";
                newEastValue += ")";
                newSouthValue += ")";
                newWestValue += ")";
                
                Nvalues[i][j].setText(newNorthValue);
                Evalues[i][j].setText(newEastValue);
                Svalues[i][j].setText(newSouthValue);
                Wvalues[i][j].setText(newWestValue);

                if (i == firstTuple.getRow() && j == firstTuple.getCol()) {
                    if (firstTuple.getAction() == 'N'){
                        Nlabel[i][j].setForeground(Color.red);
                        try {
                            Thread.sleep(700);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Nlabel[i][j].setForeground(Color.black);
                    } else if (firstTuple.getAction() == 'E') {
                        Elabel[i][j].setForeground(Color.red);
                        try {
                            Thread.sleep(700);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Elabel[i][j].setForeground(Color.black);
                    } else if (firstTuple.getAction() == 'S') {
                        Slabel[i][j].setForeground(Color.red);
                        try {
                            Thread.sleep(700);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Slabel[i][j].setForeground(Color.black);
                    } else if (firstTuple.getAction() == 'W') {
                        Wlabel[i][j].setForeground(Color.red);
                        try {
                            Thread.sleep(700);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Wlabel[i][j].setForeground(Color.black);
                    }
                }
                
                    
                newNorthValue = "(";
                newEastValue = "(";
                newSouthValue = "(";
                newWestValue = "(";
            }
        }
    }
    
    /**
     * Receives the updated optimal path map from Q_learning class and then updates the GUI
     * @param map  the updated robot map from Q_learning class
     * @since 1.0
    */
    public static void sendOptimalPathToGUI(Data[][] map) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if(j == 0 || (j == 6 && i == 4) || (j == 6 && i == 5)) {
                    continue;
                } else if (i == 1 && (j == 1 || j == 4)) {
                    continue;
                } else if (i == 3 && (j == 1 || j == 4)) {
                    continue;
                }
                if ("^^^^".equals(map[i][j].GetOptimal())) {
                    Nlabel[i][j].setForeground(Color.green);
                } else if (">>>>".equals(map[i][j].GetOptimal())) {
                    Elabel[i][j].setForeground(Color.green);
                } else if ("VVVV".equals(map[i][j].GetOptimal())) {
                    Slabel[i][j].setForeground(Color.green);
                } else if ("<<<<".equals(map[i][j].GetOptimal())) {
                    Wlabel[i][j].setForeground(Color.green);
                }
            }
        }
    }
    
    /**
     * Waits for a button to be pressed by the user then calls its respective method 
     * @param e the ActionEvent object that is used to get the source of which button was called
     * @since 1.0
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {  // restart   
            restart();
        } else if (e.getSource() == buttons[1]) {  // play     
            play();
        } else if (e.getSource() == buttons[2]) {  // fastforward   
            fastForward();
        }
    }
    
    /**
     * Starts the simulation for the user
     * @since 1.0
    */
    public void play() {
        if (running) return;
        running = true;
        qlearn = new Q_learning();
        thread = new Thread(qlearn);
        thread.start();
    }
   
    /**
     * Forwards the simulation to the final result
     * @since 1.0
    */
    private void fastForward() {
        if (!running) return;
        isFastForward = true;
    }
    
    /**
     * Restarts the simulation for the user 
     * @since 1.0
    */
    private void restart() {
        if(!running) return;
        running = false;
        isFastForward = true;
        try {
            thread.join();
            qlearn = null;
            resetMap();
            removeTracker();
        } catch (InterruptedException e) {
            
        }
        isFastForward = false;
    }

    /**
     * resets the Map for the user. called by the restart method. 
     * @since 1.0
    */
    public static void resetMap() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if(j == 0 || (j == 6 && i == 4) || (j == 6 && i == 5)) {
                    continue;
                } else if (i == 1 && (j == 1 || j == 4)) {
                    continue;
                } else if (i == 3 && (j == 1 || j == 4)) {
                    continue;
                }
                Nvalues[i][j].setText("(0, 0)");
                Evalues[i][j].setText("(0, 0)");
                Svalues[i][j].setText("(0, 0)");
                Wvalues[i][j].setText("(0, 0)");
            }
        }
    }
    
    /**
     * resets the red labels on the Map for the user. called by the restart method. 
     * @since 1.0
    */
    public static void removeTracker() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if(j == 0 || (j == 6 && i == 4) || (j == 6 && i == 5)) {
                    continue;
                } else if (i == 1 && (j == 1 || j == 4)) {
                    continue;
                } else if (i == 3 && (j == 1 || j == 4)) {
                    continue;
                }
                Nlabel[i][j].setForeground(Color.black);
                Elabel[i][j].setForeground(Color.black);
                Slabel[i][j].setForeground(Color.black);
                Wlabel[i][j].setForeground(Color.black);
            }
        }
    }
    
    /**
     * @return a Boolean that represents whether the thread is running
     * @since 1.0
    */
    public static boolean getPlayStatus() {
        return running;
    }
    
    /**
     * @return a Boolean that represents whether the forward button has been pressed
     * @since 1.0
    */
    public static boolean getFastForwardStatus() {
        return isFastForward;
    }  
    
    
} 
