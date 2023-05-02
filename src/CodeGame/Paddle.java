package CodeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Paddle {
	private double x;
	private int width,height, startWidth, startHeight;
	private long widthTimer;
	private boolean altWidth;
	private double targetX;
	public final int YPOS = Main.HEIGHT - 100;
	
	public Paddle(int theWidth, int theHeight) {
		
		altWidth = false;
		this.width = theWidth;
		startWidth = theWidth;
		this.height = theHeight;
		startHeight = height;
		
		this.x = Main.WIDTH/2 - width/2;
	}
	
	
	
	public void update() {
		if((System.nanoTime() - widthTimer)/1000 > 4000000) {
			width = startWidth;
			altWidth = false;
		}
		
		x+=(targetX - x)* .3;
	}
	
	public void draw(Graphics2D g) {
		
		int yDraw = YPOS+(startHeight - height)/2;
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) x,yDraw,width,height);
		if(altWidth == true) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Corier New", Font.BOLD,18));
			
		}

	}
	
	public void processMouseMoved(int MouseXPOS) {
			
			targetX = MouseXPOS - width/2;
			if(targetX > Main.WIDTH - width) {
				targetX = Main.WIDTH - width;
			}
			if(targetX<0) {
				targetX = 0;
			}
		}
	
	public Rectangle getRect() {
		return new Rectangle((int)x,YPOS, width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int newWidth) {
		altWidth = true;
		this.width = newWidth;
		setWidthTimer();
	}


	public void setWidthTimer() {
		widthTimer = System.nanoTime();
	}
	
	
}
