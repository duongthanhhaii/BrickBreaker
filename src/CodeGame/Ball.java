package CodeGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball {
	private double x,y,dx,dy;
	private int ballSize ;
	
	public Ball(double x, double y, double dx, double dy, int ballSize) {
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.ballSize = ballSize;
	}
	
	public void update() {
		setPositon();
		
	}
	
	private void setPositon() {
		x += dx;
		y += dy;
		
		if(x < 0)
			dx = -dx;
		if(y < 0)
			dy = -dy;
		if(x > Main.WIDTH - ballSize)
			dx = -dx;
		if(y > Main.HEIGHT - ballSize)
			dy = -dy;
		
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.setStroke(new BasicStroke(4));
		g.drawOval((int)x, (int)y, ballSize, ballSize);
	}
	
	public Rectangle getRect() {
		return new Rectangle((int)x,(int)y,ballSize,ballSize); 
	}
	
	public void setDY(double theDY) {
		dy = theDY;
	}
	public double getDY() {
		return dy;
	}

	public double getX() {
		return x;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double theDx) {
		this.dx = theDx;
	}
	public boolean youLose() {
		boolean loser = false;
		if(y > Main.HEIGHT - ballSize*2) {
			loser = true;
		}
		return loser;
	}
}
