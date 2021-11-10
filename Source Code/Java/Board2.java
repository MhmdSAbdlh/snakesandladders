import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Board2 extends JFrame implements KeyListener{
	
	//Variables
	private Random random = new Random();
	private boolean blueTurn = true;
	private int index, rollN = 0, blueD = 1, redD = 1;
	private int blueX = -80, blueY = 720, redX = -80, redY = 720;
	private String[] messArr = {"RESTART GAME", "MAIN MENU", "EXIT"};
	
	//Images
	private URL url = getClass().getResource("board2.jpg");
	private ImageIcon icon = new ImageIcon(url);
	Image image = icon.getImage();
	private URL player1 = getClass().getResource("player1-b.png");
	private ImageIcon p1Icon = new ImageIcon(player1);
	private URL player2 = getClass().getResource("player2-b.png");
	private ImageIcon p2Icon = new ImageIcon(player2);
	private URL dice = getClass().getResource("dice.png");
	private ImageIcon diceIcon = new ImageIcon(dice);
	private URL logo = getClass().getResource("icon.png");
	private ImageIcon logoIcon = new ImageIcon(logo);
	private URL dice1 = getClass().getResource("1.png");
	private ImageIcon dice1Icon = new ImageIcon(dice1);
	private URL dice2 = getClass().getResource("2.png");
	private ImageIcon dice2Icon = new ImageIcon(dice2);
	private URL dice3 = getClass().getResource("3.png");
	private ImageIcon dice3Icon = new ImageIcon(dice3);
	private URL dice4 = getClass().getResource("4.png");
	private ImageIcon dice4Icon = new ImageIcon(dice4);
	private URL dice5 = getClass().getResource("5.png");
	private ImageIcon dice5Icon = new ImageIcon(dice5);
	private URL dice6 = getClass().getResource("6.png");
	private ImageIcon dice6Icon = new ImageIcon(dice6);
	private URL startDice = getClass().getResource("0.png");
	private ImageIcon currentDice = new ImageIcon(startDice);
	
	private JLabel board = new JLabel(icon);
	private JLabel playerTurn = new JLabel("Blue Turn");
	private JLabel blueNaPo = new JLabel(Intro.userX.toUpperCase()+" ("+playerLocation(blueX,blueY)+")");
	private JLabel redNaPo = new JLabel(Intro.userO.toUpperCase()+" ("+playerLocation(redX,redY)+")");
	private JLabel playerBlue = new JLabel(p1Icon);
	private JLabel playerRed = new JLabel(p2Icon);
	private JLabel diceNumber = new JLabel(currentDice);
	private JButton diceBtn = new JButton(diceIcon);
	private Thread moveThread;
	
	Board2(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 840);
		this.setTitle("Snakes & Ladders");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setIconImage(logoIcon.getImage());
		this.getContentPane().setBackground(Main.darkC);
		this.addKeyListener(this);
		
		//Board
		board.setBounds(0, 0, 800, 800);
		board.setHorizontalAlignment(JLabel.LEFT);
		board.setVerticalAlignment(JLabel.CENTER);
		
		//Add Players
		playerBlue.setBounds(blueX, blueY, 80, 80);
		playerBlue.setHorizontalAlignment(JLabel.LEFT);
		playerBlue.setVerticalAlignment(JLabel.CENTER);
		playerRed.setBounds(redX, redY, 80, 80);
		playerRed.setHorizontalAlignment(JLabel.LEFT);
		playerRed.setVerticalAlignment(JLabel.CENTER);
		
		//Button
		diceBtn.setBackground(Main.darkC);
		diceBtn.setBounds(850, 130, 100, 100);
		diceBtn.setFocusable(false);
		diceBtn.setBorderPainted(false);
		diceBtn.addActionListener(e -> {
			rollN = roleDice();
			diceNumber.setIcon(currentDice);
			if(blueTurn) {
				blueMove();
				blueTurn=false;
				playerTurn.setForeground(Main.playerX);
				playerTurn.setText("Red Turn");
			}
			else {
				redMove();
				blueTurn=true;
				playerTurn.setForeground(Main.playerY);
				playerTurn.setText("Blue Turn");
				}
		});
		
		//Label
		JLabel diceRLabel = new JLabel("Tap the dice");
		diceRLabel.setBounds(800,30,200,100);
		diceRLabel.setForeground(Main.ligthC);
		diceRLabel.setHorizontalAlignment(0);
		diceRLabel.setFont(Main.smallF);
		JLabel diceResult = new JLabel("Dice number");
		diceResult.setBounds(800,300,200,100);
		diceResult.setForeground(Main.ligthC);
		diceResult.setHorizontalAlignment(0);
		diceResult.setFont(Main.smallF);
		playerTurn.setBounds(800, 550, 200, 100);
		playerTurn.setForeground(Main.playerY);
		playerTurn.setHorizontalAlignment(0);
		playerTurn.setFont(Main.medF);
		blueNaPo.setBounds(800, 650, 200, 100);
		blueNaPo.setForeground(Main.playerY);
		blueNaPo.setHorizontalAlignment(0);
		blueNaPo.setFont(Main.smallF);
		redNaPo.setBounds(800, 700, 200, 100);
		redNaPo.setForeground(Main.playerX);
		redNaPo.setHorizontalAlignment(0);
		redNaPo.setFont(Main.smallF);
		diceNumber.setBounds(800,400,200,100);
		diceNumber.setHorizontalAlignment(0);
		
		this.add(diceResult);
		this.add(diceRLabel);
		this.add(diceNumber);
		this.add(playerTurn);
		this.add(blueNaPo);
		this.add(redNaPo);
		this.add(diceBtn);
		this.add(playerBlue);
		this.add(playerRed);
		this.add(board);
		this.getRootPane().setDefaultButton(diceBtn);
		this.setVisible(true);
	}
	
	private int playerLocation(int x, int y) {
			return xDirection(y)?((720-x)/80 + ((720-y)/8) + 1)
					:(x/80 + ((720-y)/8) + 1);
	}
	
	private void laddersCases(){
		URL ladder = getClass().getResource("ladder.wav");
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(ladder);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			
			//LADDER 1
			if(blueX == 0 && blueY == 720) {
				clip.start();
		    	blueX = 160;
				blueY = 480;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 0 && redY == 720) {
				clip.start();
		    	redX = 160;
				redY = 480;
				playerRed.setLocation(redX, redY);
			}
			//LADDER 2
			if(blueX == 640 && blueY == 720) {
				clip.start();
		    	blueX = 720;
				blueY = 480;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 640 && redY == 720) {
				clip.start();
		    	redX = 720;
				redY = 480;
				playerRed.setLocation(redX, redY);
			}
			//LADDER 3
			if(blueX == 240 && blueY == 720) {
				clip.start();
		    	blueX = 480;
				blueY = 640;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 240 && redY == 720) {
				clip.start();
		    	redX = 480;
				redY = 640;
				playerRed.setLocation(redX, redY);
			}
			//LADDER 4
			if(blueX == 560 && blueY == 560) {
				clip.start();
		    	blueX = 240;
				blueY = 80;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 560 && redY == 560) {
				clip.start();
		    	redX = 240;
				redY = 80;
				playerRed.setLocation(redX, redY);
			}
			//LADDER 5
			if(blueX == 0 && blueY == 560) {
				clip.start();
		    	blueX = 80;
				blueY = 400;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 0 && redY == 560) {
				clip.start();
		    	redX = 80;
				redY = 400;
				playerRed.setLocation(redX, redY);
			}
			//LADDER 6
			if(blueX == 720 && blueY == 320) {
				clip.start();
		    	blueX = 480;
				blueY = 240;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 720 && redY == 320) {
				clip.start();
		    	redX = 480;
				redY = 240;
				playerRed.setLocation(redX, redY);
			}
			//LADDER 7
			if(blueX == 0 && blueY == 160) {
				clip.start();
				blueX = 80;
				blueY = 0;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 0 && redY == 160) {
				clip.start();
		    	redX = 80;
				redY = 0;
				playerRed.setLocation(redX, redY);
			}
			//LADDER 8
			if(blueX == 640 && blueY == 160) {
				clip.start();
		    	blueX= 720;
				blueY = 0;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 640 && redY == 160) {
				clip.start();
		    	redX = 720;
				redY = 0;
				playerRed.setLocation(redX, redY);
			}
		} catch (Exception e) {};
		}
	
	private void snakesCases() {
		//Music
		URL snake1 = getClass().getResource("snake1.wav");
		URL snake2 = getClass().getResource("snake3.wav");
		URL snake3 = getClass().getResource("snake4.wav");
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(snake1);
			Clip clip = AudioSystem.getClip();
			AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(snake2);
			Clip clip2 = AudioSystem.getClip();
			AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(snake3);
			Clip clip3 = AudioSystem.getClip();
			clip.open(audioStream);
			clip2.open(audioStream2);
			clip3.open(audioStream3);
			
			//SNAKE 1
			if(blueX == 240 && blueY == 640) {
				clip3.start();
		    	blueX = 480;
				blueY = 720;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 240 && redY == 640) {
				clip3.start();
		    	redX = 480;
				redY = 720;
				playerRed.setLocation(redX, redY);
			}
			//SNAKE 2
			if(blueX == 80 && blueY == 240) {
				clip2.start();
		    	blueX = 80;
				blueY = 640;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 80 && redY == 240) {
				clip2.start();
		    	redX = 80;
				redY = 640;
				playerRed.setLocation(redX, redY);
			}
			//SNAKE 3
			if(blueX == 240 && blueY == 240) {
				clip3.start();
		    	blueX = 0;
				blueY = 320;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 240 && redY == 240) {
				clip3.start();
		    	redX = 0;
				redY = 320;
				playerRed.setLocation(redX, redY);
			}
			//SNAKE 4
			if(blueX == 160 && blueY == 0) {
				clip.start();
		    	blueX = 80;
				blueY = 160;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 160 && redY == 0) {
				clip.start();
		    	redX = 80;
				redY = 160;
				playerRed.setLocation(redX, redY);
			}
			//SNAKE 5
			if(blueX == 400 && blueY == 0) {
				clip.start();
		    	blueX = 400;
				blueY = 160;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 400 && redY == 0) {
				clip.start();
		    	redX = 400;
				redY = 160;
				playerRed.setLocation(redX, redY);
			}
			//SNAKE 6
			if(blueX == 480 && blueY == 80) {
				clip2.start();
		    	blueX = 320;
				blueY = 480;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 480 && redY == 80) {
				clip2.start();
		    	redX = 320;
				redY = 480;
				playerRed.setLocation(redX, redY);
			}
			//SNAKE 7
			if(blueX == 560 && blueY == 0) {
				clip3.start();
		    	blueX = 560;
				blueY = 160;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 560 && redY == 0) {
				clip3.start();
		    	redX = 560;
				redY = 160;
				playerRed.setLocation(redX, redY);
			}
			//SNAKE 8
			if(blueX == 480 && blueY == 320) {
				clip.start();
		    	blueX = 480;
				blueY = 480;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 480 && redY == 320) {
				clip.start();
				redX = 480;
				redY = 480;
				playerRed.setLocation(redX, redY);
			}
		} catch (Exception e) {};
	}
	
	private void blueMove() {
		moveThread = new Thread(()-> {
			diceBtn.setEnabled(false);
			int i=1;
			while(i<=rollN) {
				if(xDirection(blueY))
					blueD = -1;
				else
					blueD = +1;
				blueX+=(80*blueD);
				if(blueY==0 && blueX == 0) {
					playerBlue.setLocation(blueX, blueY);
					blueNaPo.setText(Intro.userX.toUpperCase()+
							" ("+playerLocation(blueX,blueY)+")");
					endGame(blueX, blueY);
					break;
				}
				else {
					if(blueX == 800) {
						blueX = 720;
						blueY-=80;
						playerBlue.setLocation(blueX, blueY);			
						}
					else
						if(blueX == -80) {
							blueX = 0;
							blueY-=80;
							playerBlue.setLocation(blueX, blueY);
						}
						else
							playerBlue.setLocation(blueX, blueY);
					i++;
					blueNaPo.setText(Intro.userX.toUpperCase()+
							" ("+playerLocation(blueX,blueY)+")");
					try {
						Thread.sleep(200);
						} catch (InterruptedException e) {}
					}
				}
			snakesCases();
			laddersCases();
			blueNaPo.setText(Intro.userX.toUpperCase()+
					" ("+playerLocation(blueX,blueY)+")");	
			diceBtn.setEnabled(true);
		});
	moveThread.start();
	}
	
	private void redMove() {
		moveThread = new Thread(()-> {	
			diceBtn.setEnabled(false);
			int i=1;
			while(i<=rollN) {
				if(xDirection(redY))
					redD = -1;
				else
					redD = +1;
				redX+=(80*redD);
				if(redY==0 && redX == 0) {
					playerRed.setLocation(redX, redY);
					redNaPo.setText(Intro.userO.toUpperCase()+
							" ("+playerLocation(redX,redY)+")");
					endGame(redX, redY);
					break;
				}
				else {
					if(redX == 800) {
						redX = 720;
						redY-=80;
						playerRed.setLocation(redX, redY);			
						}
					else
						if(redX == -80) {
							redX = 0;
							redY-=80;
							playerRed.setLocation(redX, redY);	
						}
						else
							playerRed.setLocation(redX, redY);
					i++;
					redNaPo.setText(Intro.userO.toUpperCase()+
							" ("+playerLocation(redX,redY)+")");
					try {
						Thread.sleep(200);
						} catch (InterruptedException e) {}
					}
				}
			snakesCases();
			laddersCases();
			redNaPo.setText(Intro.userO.toUpperCase()+
					" ("+playerLocation(redX,redY)+")");	
			diceBtn.setEnabled(true);
			});
		moveThread.start();
		}

	private void endGame(int x, int y) {
		URL win = getClass().getResource("win.wav");
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(win);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			int result;
			if(x == blueX && y == blueY)
				result = JOptionPane.showOptionDialog(null, Intro.userX.toUpperCase()+" win, while "+Intro.userO.toUpperCase()+" still in "+playerLocation(redX,redY), "GAME OVER", JOptionPane.YES_NO_OPTION, 1, null, messArr, 1);
			else
				result = JOptionPane.showOptionDialog(null, Intro.userO.toUpperCase()+" win, while "+Intro.userX.toUpperCase()+" still in "+playerLocation(blueX,blueY), "GAME OVER", JOptionPane.YES_NO_OPTION, 1, null, messArr, 1);
			
			if(result == 1) {
				this.dispose();
				new Intro();
				}
			else
				if(result == 0) {
					this.dispose();
					new Board2();
				}
				else
					System.exit(0);
			} catch (Exception e) {};
	}
	
	private int roleDice() {
		index = random.nextInt(6)+1;
		switch (index) {
		case 1:
			currentDice = dice1Icon;
			break;
		case 2:
			currentDice = dice2Icon;
			break;
		case 3:
			currentDice = dice3Icon;
			break;
		case 4:
			currentDice = dice4Icon;
			break;
		case 5:
			currentDice = dice5Icon;
			break;
		case 6:
			currentDice = dice6Icon;
			break;

		default:
			break;
		}
		return index;
	}

	private boolean xDirection(int y) {
		return (y == 0 || y == 160 || y == 320 || y == 480 || y == 640);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getExtendedKeyCode() == KeyEvent.VK_PAGE_UP) {
			blueX = 80;
			blueY = 0;
			playerBlue.setLocation(blueX, blueY);
			}
		if(e.getExtendedKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			blueX = 80;
			blueY = 720;
			playerBlue.setLocation(blueX, blueY);
			}
		if(e.getExtendedKeyCode() == KeyEvent.VK_END) {
			redX = 80;
			redY = 0;
			playerRed.setLocation(redX, redY);
			}
		if(e.getExtendedKeyCode() == KeyEvent.VK_HOME) {
			redX = 80;
			redY = 720;
			playerRed.setLocation(redX, redY);
			}
		}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
}
