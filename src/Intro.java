import java.awt.*;
import java.net.URL;

import javax.swing.*;

@SuppressWarnings("serial")
public class Intro extends JFrame{
	
	JLabel gameName = new JLabel("Snakes & Ladders");
	JButton onePlayer = new JButton("SINGLE");
	JButton twoPlayer = new JButton("MULTIPLAYER");
	static String userX;
	static String userO;
	URL dice = getClass().getResource("logo.png");
	ImageIcon diceIcon = new ImageIcon(dice);
	
	Intro(){
		//Frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 800);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Snakes & Ladders");
		this.getContentPane().setBackground(Main.darkC);
		this.getRootPane().setDefaultButton(twoPlayer);
		this.setIconImage(diceIcon.getImage());
		
		//Labels
		JLabel logo = new JLabel(diceIcon);
		logo.setBounds(280, 80, 100, 100);
		logo.setHorizontalAlignment(0);
		gameName.setBounds(0, 200, 640, 100);
		gameName.setHorizontalAlignment(0);
		gameName.setFont(Main.largeF);
		gameName.setForeground(Main.ligthC);
		Main.credit.setHorizontalAlignment(0);
		Main.credit.setBounds(0, 730, 640, 20);
		Main.credit.setForeground(Color.white);
		
		//Buttons
		onePlayer.setFont(Main.medF);
		onePlayer.setBounds(180, 350, 300, 50);
		onePlayer.setFocusable(false);
		onePlayer.setBackground(Main.ligthC);
		onePlayer.setForeground(Main.darkC);
		onePlayer.addActionListener(e-> {
			this.dispose();
			new BoardOP();
			});
		
		twoPlayer.setFont(Main.medF);
		twoPlayer.setBounds(180, 450, 300, 50);
		twoPlayer.setFocusable(false);
		twoPlayer.setBackground(Main.ligthC);
		twoPlayer.setForeground(Main.darkC);
		twoPlayer.addActionListener(e-> {
			userX = JOptionPane.showInputDialog("Player 1, Please enter your name:");
			userO = JOptionPane.showInputDialog("Player 2, Please enter your name:");
			this.dispose();
			new Board();
			});
		JButton Exit = new JButton("EXIT GAME");
		Exit.setFont(Main.medF);
		Exit.setBounds(180, 550, 300, 50);
		Exit.setFocusable(false);
		Exit.setBackground(Main.ligthC);
		Exit.setForeground(Main.darkC);
		Exit.addActionListener(e-> System.exit(0));
				
		//Add to frame
		this.add(logo);
		this.add(gameName);
		this.add(onePlayer);
		this.add(twoPlayer);
		this.add(Exit);
		this.add(Main.credit);
		
		//Show frame
		this.setVisible(true);
	}	
}
