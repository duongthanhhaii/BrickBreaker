package CodeGame;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	
	
	private static final String URL = "jdbc:mysql://localhost:3306/gamebb"; 
    private static final String USERNAME = "root"; 
    private static final String SCORE = "";
	
    public static void saveScore(String namePlayer, int score) {
	 	
	    try {
	    	
	        Connection connect = DriverManager.getConnection(URL, USERNAME, SCORE);

	        // Thực hiện truy vấn để lưu kết quả
	        String sql = "INSERT INTO account(name,score) VALUES (?, ?)";
	        PreparedStatement statement = connect.prepareStatement(sql);
	        statement.setString(1, namePlayer);
	        statement.setInt(2, score);
	        statement.execute();

	        // Đóng kết nối
	        connect.close();
	    } catch (SQLException ex) {
	        System.out.println("Lỗi kết nối");
	    }
	}
	
	public static void main(String[] args) {
		
		
		GamePanel thePanel = new GamePanel();
		
		JFrame theFrame =new JFrame("Brick Breaker");
		
		HUD theHUD = new HUD();
		
		Toolkit toolkit = theFrame.getToolkit();
		Dimension dimension = toolkit.getScreenSize();
		theFrame.setBounds((dimension.width - WIDTH)/2, (dimension.height - HEIGHT)/2, WIDTH, HEIGHT);
		theFrame.setLocation(WIDTH, HEIGHT);
		
		theFrame.add(thePanel);
		theFrame.addMouseMotionListener(thePanel);
		
		theFrame.addMouseListener(thePanel);
		
		
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setVisible(true);
		thePanel.run();
		
		try {
            // Tạo kết nối đến database
            Connection conn = DriverManager.getConnection(URL, USERNAME, SCORE);

            // Kiểm tra kết nối
            if (conn != null) {
                System.out.println("Kết nối thành công đến database");
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi kết nối đến database: " + ex.getMessage());
        }
		
		String namePlayer = "";
		namePlayer = JOptionPane.showInputDialog("Nhập tên của bạn:");
		saveScore(namePlayer, thePanel.scr);
		
		

	}
	
}
