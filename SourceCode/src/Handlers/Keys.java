package Handlers;



import java.awt.event.KeyEvent;


// this class contains a boolean array of current and previous key states
// for the 10 keys that are used for this game.
// a key k is down when keyState[k] is true.
public class Keys {

    public static final int NUM_KEYS = 16;

    public static boolean keyState[] = new boolean[NUM_KEYS];
    public static boolean prevKeyState[] = new boolean[NUM_KEYS];

    public static int UP = 0;
    public static int LEFT = 1;
    public static int DOWN = 2;
    public static int RIGHT = 3;
    public static int BUTTON1 = 4;
    public static int BUTTON2 = 5;
    public static int BUTTON3 = 6;
    public static int BUTTON4 = 7;
    public static int ENTER = 8;
    public static int ESCAPE = 9;
    public static int ok = 10;
    public static int LEN = 11;
    public static int XUONG = 12;
    public static int TRAI = 13;
    public static int PHAI = 14;
     public static int U = 15;
    public static void keySet(int i, boolean b) {
        if (i == KeyEvent.VK_UP) {
            keyState[UP] = b;
        } else if (i == KeyEvent.VK_A) {
            keyState[LEFT] = b;
        } else if (i == KeyEvent.VK_DOWN) {
            keyState[DOWN] = b;
        } else if (i == KeyEvent.VK_D) {
            keyState[RIGHT] = b;
        } else if (i == KeyEvent.VK_W) {
            keyState[BUTTON1] = b;
        } else if (i == KeyEvent.VK_F24) {
            keyState[BUTTON2] = b;
        } else if (i == KeyEvent.VK_SPACE) {
            keyState[BUTTON3] = b;
        } else if (i == KeyEvent.VK_F24) {
            keyState[BUTTON4] = b;
        } else if (i == KeyEvent.VK_ENTER) {
            keyState[ENTER] = b;
        } else if (i == KeyEvent.VK_ESCAPE) {
            keyState[ESCAPE] = b;
        } else if(i == KeyEvent.VK_Z){
            keyState[ok] = b;
        }
        else if(i == KeyEvent.VK_UP){
            keyState[LEN] = b;
        }
        else if(i == KeyEvent.VK_DOWN){
            keyState[XUONG] = b;
        }
        else if(i == KeyEvent.VK_LEFT){
            keyState[TRAI] = b;
        }
        else if(i == KeyEvent.VK_RIGHT){
            keyState[PHAI] = b;
        }
         else if(i == KeyEvent.VK_U){
            keyState[U] = b;
        }
    }

    public static void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            prevKeyState[i] = keyState[i];
        }
    }

    public static boolean isPressed(int i) {
        return keyState[i] && !prevKeyState[i];
    }

    public static boolean anyKeyPress() {
        for (int i = 0; i < NUM_KEYS; i++) {
            if (keyState[i]) {
                return true;
            }
        }
        return false;
    }
   
}
