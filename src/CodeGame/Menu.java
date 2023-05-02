package CodeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {

	public static Rectangle playRect = new Rectangle(Main.WIDTH/2 - 50 ,120,160,50 );
	
	public Menu() { }
	
	
	public void draw(Graphics2D g) {
		
		g.setColor(Color.BLACK); 
		g.setFont(new Font("Courier New", Font.BOLD , 50));
		g.drawString("BRICK BREAKER", 150, 80);
		
		g.fill(playRect) ;
		
		g.setColor(Color.WHITE); 
		g.setFont(new Font("Arial", Font.BOLD , 30));
		g.drawString("Start game", Main.WIDTH/2 - 50 , 150);
			
	}
	public void release(Graphics2D g) {
		g.dispose();
	}
	
}
