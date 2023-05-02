package CodeGame;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GamePanel extends JPanel implements Runnable,MouseMotionListener,MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//fields
	private boolean running;
	private BufferedImage images;
	private Graphics2D g;
	int mouseX;
	
	Ball theBall;
	Paddle thePaddle;
	Map theMap;
	HUD theHud;
	Menu menu;
	private ArrayList<PowerUp> powerUps;
	private ArrayList<BrickSplotion> brickSplotions;
	
	//enum
	private enum MENU{
		Menu,
		Game
	}
	
	public Graphics2D getG() {
		return g;
	}

	private MENU menu1 = MENU.Menu;
	
	public int scr;
	public GamePanel() {
		init();
		
	}

	public void init() {
		theBall = new Ball(200,200,1,4,20);
		thePaddle = new Paddle(100,20);
		theMap = new Map(4,10);
		theHud = new HUD();
		
		menu = new Menu();
		
		running = true;
		mouseX = 0;
		
		powerUps = new ArrayList<PowerUp>();
		brickSplotions = new ArrayList<BrickSplotion>();
		
		 images = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);
		 g = (Graphics2D) images.getGraphics();
		 
		 
		 g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 
	}
	
	@Override
	public void run() {
		
		while(running) {
			
			
			update();
			
			draw();
			
			repaint();

			try {
				Thread.sleep(12);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void checkCollisions() {
		Rectangle ballRect = theBall.getRect();
		Rectangle paddleRect = thePaddle.getRect();
		
		for(int i=0;i<powerUps.size();i++) {
			Rectangle puRect = powerUps.get(i).getRect();
			
			if(paddleRect.intersects(puRect)) {
				/*drawLoser();
				running = false;*/
				if(powerUps.get(i).getType()==PowerUp.WIDEPADDLE && !powerUps.get(i).getWasUsed()) {
					thePaddle.setWidth(thePaddle.getWidth() *2);
					powerUps.get(i).setWasUsed(true);
				}
			}
			
		}
		
		if(ballRect.intersects(paddleRect)) {
			theBall.setDY(-theBall.getDY());
			
			if(theBall.getX() < mouseX + thePaddle.getWidth()/4) {
				theBall.setDx(theBall.getDx() - 0.5);
			}
			if(theBall.getX() < mouseX + thePaddle.getWidth() && theBall.getX() > mouseX + thePaddle.getWidth()/4)  {
				theBall.setDx(theBall.getDx() + 0.5);
			}
		}
			
		
		
		A: for(int row = 0; row < theMap.getMapArray().length; row++) {
		 	for(int col = 0; col < theMap.getMapArray()[0].length;col++) {
				if(theMap.getMapArray()[row][col] > 0) {
					int brickx = col * theMap.getBrickWidth() + theMap.HOR_PAD;
					int bricky = row * theMap.getBrickHeight()+ theMap.VERT_PAD;
					int brickWidth = theMap.getBrickWidth();
					int brickHeight = theMap.getBrickHeight();
					
					Rectangle brickRect = new Rectangle(brickx, bricky, brickWidth, brickHeight);
					if(ballRect.intersects(brickRect)) {
						
						if(theMap.getMapArray()[row][col] == 1) {
							brickSplotions.add(new BrickSplotion(brickx, bricky, theMap));
						}
						PowerUp p;
						if(theMap.getMapArray()[row][col]>3) {
							p = new PowerUp(brickx, bricky, theMap.getMapArray()[row][col], brickWidth, brickHeight);
							powerUps.add(p);
							theMap.setBrick(row, col, 3);
						}
						else {
							theMap.hitBrick(row, col);
						}	
						
						theMap.hitBrick(row, col);
						theBall.setDY( -theBall.getDY());
						theHud.addScore(10);
						scr = theHud.getScore();
						break A;
					}
				}
			}
		}
	}

	public void update() {
		this.checkCollisions();;
		theBall.update();
		
		thePaddle.update();
		
		for(PowerUp pu : powerUps) {
			pu.update();
		}
		
		for(int i=0;i < brickSplotions.size();i++) {
			brickSplotions.get(i).update();
			if(!brickSplotions.get(i).getIsActive()) {
				brickSplotions.remove(i);
			}
		}
	}
	
	
	public void draw() {
		//background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		menu.draw(g);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		if(menu1 == MENU.Game) {
			
			theMap.draw(g);
			
			theBall.draw(g);
			
			thePaddle.draw(g);
			
			theHud.draw(g);
			
			drawPowerUps();
			
			for(BrickSplotion bs: brickSplotions) {
				bs.draw(g);
			}
			if(theMap.isThereAWin() == true) {
				drawWin();

				running = false;
			}
			if(theBall.youLose()) {
				
				drawLoser();
				running = false;
			}
		}
		else if(menu1 == MENU.Menu) {
			menu.draw(g);	
		}	
	}
	
	
	
	public void drawWin() {
		g.setColor(Color.RED);
		g.setFont(new Font("CourierNew", Font.BOLD , 50));
		g.drawString("Winner", Main.WIDTH/2, Main.HEIGHT/2);
	}
	
	public void drawLoser() {
		g.setColor(Color.RED);
		g.setFont(new Font("Courier New", Font.BOLD , 50));
		g.drawString("Loser", 200, 200);
	}
	
	public void drawPowerUps() {
		for(PowerUp pu : powerUps) {
			pu.draw(g);
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(images, 0, 0, Main.WIDTH, Main.HEIGHT, null);
		g2.dispose();
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		thePaddle.processMouseMoved(mouseX);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {	
		int mx = e.getX();
		int my = e.getY();
		
		if( mx > Main.WIDTH/2 - 50 && mx < Main.WIDTH/2 - 50 + 160) {
			if( my > 150 && my < 200)
				this.menu1 = MENU.Game;
		}
	/*	if( mx > Main.WIDTH/2 - 30  && mx < Main.WIDTH/2 - 30  + 90) {
			if( my > 250 && my < 300)
				this.menu1 = MENU.Score;
		}*/
	}	
	

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	
      
}
