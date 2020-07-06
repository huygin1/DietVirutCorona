package GameState;

import Main.GamePanel;
import Sever.*;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class GameStateManager {

    public boolean checkloginadmin;
    private GameState[] gameStates;
    private int currentState;

    private PauseState pauseState;
    private boolean paused;

    public static final int NUMGAMESTATES = 16;
    public static final int MENUSTATE = 0;
    public static final int GUIDESTATE = 2;
    public static final int LEVEL1 = 3;
    public static final int LEVEL2 = 4;
    public static final int TEST = 5;
    public static final int DB = 6;
    public static final int IF = 7;
    public static final int ACIDSTATE = 15;

//  
    public GameStateManager() {

//		prepareGUI();
//         showTextFieldDemo();
        gameStates = new GameState[NUMGAMESTATES];

        pauseState = new PauseState(this);
        paused = false;

        currentState = DB;

        loadState(currentState);

    }

    private void loadState(int state) {
        if (state == MENUSTATE) {

            gameStates[state] = new MenuState(this);

        } else if (state == GUIDESTATE) {
            gameStates[state] = new GuideState(this);
        } else if (state == LEVEL1) {
            gameStates[state] = new Level1(this);
        } else if (state == LEVEL2) {
            gameStates[state] = new Level2(this);
        } else if (state == ACIDSTATE) {
            gameStates[state] = new AcidState(this);
        } else if (state == TEST) {
            gameStates[state] = new TestState(this);
        } else if (state == DB) {
            gameStates[state] = new DB(this);
        } else if (state == IF) {
            gameStates[state] = new info(this);
        }

    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);

    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public void update() {

        if (paused) {
            pauseState.update();
            return;
        }
        if (gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    public void draw(java.awt.Graphics2D g) {
        Toolkit.getDefaultToolkit().sync();
        if (paused) {
            pauseState.draw(g);
            Toolkit.getDefaultToolkit().sync();
            return;
        }
        if (gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
            Toolkit.getDefaultToolkit().sync();
        } else {
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0, 0, (int) GamePanel.WIDTH, (int) GamePanel.HEIGHT);
        }
        Toolkit.getDefaultToolkit().sync();
    }

}
