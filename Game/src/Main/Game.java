package Main;

import javax.swing.JFrame;

public class Game {
   
    
    public static void main(String[] args) {

        JFrame window = new JFrame("Do an");
          JFrame a = new JFrame("Do an");
        window.add(new GamePanel());
     
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setResizable(false);
        
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
    }

}
