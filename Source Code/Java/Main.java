import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class Main {
	
	public static Color darkC = new Color(34, 40, 49);
	public static Color ligthC = new Color(238, 238, 238);
	public static Color playerX = new Color(123, 17, 58);
	public static Color playerY = new Color(21, 151, 187);
	public static Font medF = new Font("Tahoma", Font.BOLD, 24);
	public static Font smallF = new Font("Tahoma", Font.BOLD, 22);
	public static Font largeF = new Font("Tahoma", Font.BOLD, 36);
		
	public static JLabel credit = new JLabel("Created and Designed by MhmdSAbdlh");
	
	public static void main(String[] args) {
		new Intro();
	}

}
