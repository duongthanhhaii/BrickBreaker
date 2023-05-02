package CodeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class HUD {
	private int score;
	
	
	public HUD() {  }
	public void draw(Graphics2D g) {
		g.setFont(new Font("CourierNew", Font.PLAIN , 15));
		g.setColor(Color.RED);
		g.drawString("Score: "+ score, 20,20);
	}
	public int getScore() {
		return score;
	}
	public void addScore(int scoreToAdd) {
		score = score + scoreToAdd;
	}
	private static final String URL = "jdbc:mysql://localhost:3306/gamebb"; 
    private static final String USERNAME = "root"; 
    private static final String SCORE = "";
    
	/*public void drawScore(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		try {
			Connection connect = DriverManager.getConnection(URL, USERNAME, SCORE);
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM account ORDER BY score DESC");
			
			int x = 150;
			int y = 200;
			
			g.setFont(new Font("Georgia", Font.PLAIN, 16));
			g.setColor(Color.WHITE);
			while (rs.next()) {
				String name = rs.getString("name");
				int score = rs.getInt("score");
				g.drawString( name , x,y);
				g.drawString(String.valueOf(score), x + 200, y);
				
				y = y + 50;
			}	rs.close();		
		} catch (SQLException e) {
			System.out.println("Lỗi kết nối ghi");
		}
		
	}*/
}