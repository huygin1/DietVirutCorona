package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;
import Handlers.Keys;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
double width1 = screenSize.getWidth();
double height1 = screenSize.getHeight();
    
                
	// dimensions
	public static  float WIDTH ;
	public static  float HEIGHT;

	public static  float SCALE = 1f;
	
	// game thread
	private Thread thread;
	public boolean running;
	private int FPS = 60;
	private long targetTime = 1000 /FPS ;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	public boolean  checksc = false;
	// game state manager
	private GameStateManager gsm;
	
	// other
	private boolean recording = false;
	private int recordingCount = 0;
	private boolean screenshot;

	public GamePanel() {
		super();
                if(width1 == 1920){
                    WIDTH = 1920;
                    HEIGHT = 1080;
                     
                }else{
                    WIDTH = 1280;
                    HEIGHT = 720;
                    SCALE = 1;
                    checksc = true;
                }
		setPreferredSize(new Dimension((int)(WIDTH * SCALE ), (int)(HEIGHT * SCALE ) ));
		setFocusable(true);
		requestFocus();
                Toolkit.getDefaultToolkit().sync();
	}
	
	public void addNotify() {
       
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
                 Toolkit.getDefaultToolkit().sync();
	}
	
	private void init() {
		
		image = new BufferedImage((int)WIDTH, (int)HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		/*g.setRenderingHint(
			RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON
		);*/
		
		
                    running = true;
               
		
		gsm = new GameStateManager();
		 Toolkit.getDefaultToolkit().sync();
	}
	public long c;
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
			 
			update();
			draw();
			drawToScreen();
			                 try {
                       
                    } catch (Exception e) {
                    }
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000 ;
                        c = wait;
                        
			if(wait < 0) wait = 0;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		 Toolkit.getDefaultToolkit().sync();
	}
	
	private void update() {
          
		gsm.update();
		Keys.update();
                Toolkit.getDefaultToolkit().sync();
	}
	private void draw() {
		gsm.draw(g);
                
                  Toolkit.getDefaultToolkit().sync();
	}
        
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,(int)(WIDTH * SCALE) , (int)(HEIGHT * SCALE), null);
		g2.dispose();
              
		if(screenshot) {
			screenshot = false;
			try {
				java.io.File out = new java.io.File("screenshot " + System.nanoTime() + ".gif");
				javax.imageio.ImageIO.write(image, "gif", out);
			}
			catch(Exception e) {}
		}
		if(!recording) return;
		try {
			java.io.File out = new java.io.File("C:\\out\\frame" + recordingCount + ".gif");
			javax.imageio.ImageIO.write(image, "gif", out);
			recordingCount++;
		}
		catch(Exception e) {}
                 Toolkit.getDefaultToolkit().sync();
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		if(key.isControlDown()) {
			if(key.getKeyCode() == KeyEvent.VK_R) {
				recording = !recording;
				return;
			}
			if(key.getKeyCode() == KeyEvent.VK_S) {
				screenshot = true;
				return;
			}
		}
		Keys.keySet(key.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}
       
        

}