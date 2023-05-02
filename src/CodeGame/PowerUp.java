package CodeGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PowerUp {
	private int x, y , dy, type,width, height;
	private boolean isOnScreen;
	private boolean wasUsed;
	
	private Color color;
	
	public final static int WIDEPADDLE = 4 ;
	public final static int FASTBALL = 5 ;
	public final static Color WIDECOLOR = Color.GREEN;
	public final static Color FASTCOLOR = Color.RED;
	
	public PowerUp(int xStart, int yStart, int theType, int theWidth, int theHeight) {
		
		x = xStart;
		y = yStart;
		type = theType;
		width = theWidth;
		height = theHeight;
		
		if(type < 4) { type = 4; }
		if(type > 5) { type = 5; }
		if(type == WIDEPADDLE) {
			color = WIDECOLOR;
		}
		if(type == FASTBALL) {
			color = FASTCOLOR;
		}
		dy = (int) (Math.random()*6 + 1); 
		
		wasUsed = false;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
	}
	
	public void update() {
		y += dy;
		if( y > Main.HEIGHT)
			isOnScreen = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public int getY() {
		return y;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int newDY) {   this.dy = newDY; }
	public int getType() {	return type; }

	public boolean isOnScreen() {	return isOnScreen; }
	public void setOnScreen(boolean onScreen) { this.isOnScreen = onScreen; }
	
	
	public boolean getWasUsed() {	return wasUsed; }

	public void setWasUsed(boolean used) {	wasUsed = used; }

	public Rectangle getRect() {
		
		return new Rectangle(x, y, width, height);
	}
	
	
}
