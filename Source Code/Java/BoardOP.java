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
public class BoardOP extends JFrame implements KeyListener{
	
	//Variables
	private Random random = new Random();
	private int index, rollN = 0;
	private int blueX = 0, blueY = 720, redX = 0, redY = 720;
	private String[] messArr = {"RESTART GAME", "MAIN MENU", "EXIT"};
	
	//Images
	private URL url = getClass().getResource("board.jpg");
	private ImageIcon icon = new ImageIcon(url);
	Image image = icon.getImage();
	private URL player1 = getClass().getResource("player1-a.png");
	private ImageIcon p1Icon = new ImageIcon(player1);
	private URL player2 = getClass().getResource("player2-a.png");
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
	private ImageIcon currentDice;
	
	private JLabel board = new JLabel(icon);
	private JLabel blueNaPo = new JLabel("YOU ("+playerLocation(blueX,blueY)+")");
	private JLabel redNaPo = new JLabel("PC ("+playerLocation(redX,redY)+")");
	private JLabel playerBlue = new JLabel(p1Icon);
	private JLabel playerRed = new JLabel(p2Icon);
	private JLabel diceNumber = new JLabel();
	private JButton diceBtn = new JButton(diceIcon);
	private Thread moveThread = new Thread();
	
	BoardOP(){
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
		diceBtn.addActionListener(e->{
			rollN = roleDice();
			diceNumber.setIcon(currentDice);
			blueMove();
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
		return(x/80 + ((720-y)/8) + 1);
	}

	private void laddersCases() {
		URL ladder = getClass().getResource("ladder.wav");
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(ladder);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			
			//LADDER 1
			if(blueX == 160 && blueY == 240) {
				clip.start();
				blueX = 320;
				blueY = 0;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 160 && redY == 240) {
				clip.start();
				redX = 320;
				redY = 0;
				playerRed.setLocation(redX, redY);
			}
			//X=560	 Y=240
			if(blueX == 560 && blueY == 240) {
				clip.start();
				blueX = 560;
				blueY = 0;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 560 && redY == 240) {
				clip.start();
				redX = 560;
				redY = 0;
				playerRed.setLocation(redX, redY);
			}
			//X=400 Y=480
			if(blueX == 400 && blueY == 480) {
				clip.start();
				blueX = 320;
				blueY = 320;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 400 && redY == 480) {
				clip.start();
				redX = 320;
				redY = 320;
				playerRed.setLocation(redX, redY);
			}
			//X=160 Y=720
			if(blueX == 160 && blueY == 720) {
				clip.start();
				blueX = 0;
				blueY = 320;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 160 && redY == 720) {
				clip.start();
				redX = 0;
				redY = 320;
				playerRed.setLocation(redX, redY);
			}
			//X=400 Y=720
			if(blueX == 400 && blueY == 720) {
				clip.start();
				blueX = 480;
				blueY = 560;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 400 && redY == 720) {
				clip.start();
				redX = 480;
				redY = 560;
				playerRed.setLocation(redX, redY);
			}
			//X=720 Y=640
			if(blueX == 720 && blueY == 640) {
				clip.start();
				blueX = 720;
				blueY = 240;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 720 && redY == 640) {
				clip.start();
				redX = 720;
				redY = 240;
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
			
			//X=0 Y=0
			if(blueX == 0 && blueY == 0) {
				clip2.start();
				blueX = 0;
				blueY = 240;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 0 && redY == 0) {
				clip2.start();
				redX = 0;
				redY = 240;
				playerRed.setLocation(redX, redY);
			}
			//X=64 Y=0
			if(blueX == 640 && blueY == 0) {
				clip2.start();
				blueX = 640;
				blueY = 240;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 640 && redY == 0) {
				clip2.start();
				redX = 640;
				redY = 240;
				playerRed.setLocation(redX, redY);
			}
			//X=480 Y=80
			if(blueX == 480 && blueY == 80) {
				clip2.start();
				blueX = 480;
				blueY = 320;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 480 && redY == 80) {
				clip2.start();
				redX = 480;
				redY = 320;
				playerRed.setLocation(redX, redY);
			}
			//X=320 Y=240
			if(blueX == 320 && blueY == 240) {
				clip3.start();
				blueX = 80;
				blueY = 320;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 320 && redY == 240) {
				clip3.start();
				redX = 80;
				redY = 320;
				playerRed.setLocation(redX, redY);
			}
			//X=480 Y=400
			if(blueX == 480 && blueY == 400) {
				clip.start();
				blueX = 640;
				blueY = 640;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 480 && redY == 400) {
				clip.start();
				redX = 640;
				redY = 640;
				playerRed.setLocation(redX, redY);
			}
			//X=240 Y=480
			if(blueX == 240 && blueY == 480) {
				clip.start();
				blueX = 0;
				blueY = 720;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 240 && redY == 480) {
				clip.start();
				redX = 0;
				redY = 720;
				playerRed.setLocation(redX, redY);
			}
			//X=320 Y=560
			if(blueX == 320 && blueY == 560) {
				clip3.start();
				blueX = 320;
				blueY = 720;
				playerBlue.setLocation(blueX, blueY);
			}
			if(redX == 320 && redY == 560) {
				clip3.start();
				redX = 320;
				redY = 720;
				playerRed.setLocation(redX, redY);
			}
		} catch (Exception e) {};
	}
	
	private void blueMove() {
		moveThread = new Thread(()-> {
			diceBtn.setEnabled(false);
			int i=1;
			while(i<=rollN) {
				blueX+=80;
				if(blueY==0 && blueX == 720) {
					playerBlue.setLocation(blueX, blueY);
					blueNaPo.setText("YOU ("+playerLocation(blueX,blueY)+")");
					endGame(blueX, blueY);
					break;
				}
				if(blueX == 800) {
					blueX = 0;
					blueY-=80;
					playerBlue.setLocation(blueX, blueY);			
					}
				else
					playerBlue.setLocation(blueX, blueY);
				i++;
				blueNaPo.setText("YOU ("+playerLocation(blueX,blueY)+")");
				try {
					Thread.sleep(200);
					} catch (InterruptedException e) {}
				}
			snakesCases();
			laddersCases();
			blueNaPo.setText("YOU ("+playerLocation(blueX,blueY)+")");
			try {
				Thread.sleep(500);
				} catch (InterruptedException e) {}
			//PC TURN
			rollN = roleDice();
			diceNumber.setIcon(currentDice);
			i=1;
			while(i<=rollN) {
				redX+=80;
				if(redY==0 && redX == 720) {
					playerRed.setLocation(redX, redY);
					redNaPo.setText("PC ("+playerLocation(redX,redY)+")");
					endGame(redX, redY);
					break;
				}
				if(redX == 800) {
					redX = 0;
					redY-=80;
					playerRed.setLocation(redX, redY);			
					}
				else
					playerRed.setLocation(redX, redY);
				i++;
				redNaPo.setText("PC ("+playerLocation(redX,redY)+")");
				try {Thread.sleep(200);
					} catch (InterruptedException e) {}
				}
			snakesCases();
			laddersCases();
			redNaPo.setText("PC ("+playerLocation(redX,redY)+")");
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
				result = JOptionPane.showOptionDialog(null, "You win, while 'PC' still in "+playerLocation(redX,redY), "GAME OVER", JOptionPane.YES_NO_OPTION, 1, null, messArr, 1);
			else
				result = JOptionPane.showOptionDialog(null, "You loss, <LOSER> and you still in "+playerLocation(blueX,blueY), "GAME OVER", JOptionPane.YES_NO_OPTION, 1, null, messArr, 1);
			if(result == 0) {
				this.dispose();
				new BoardOP();
				}
			else
				if(result == 1) {
					this.dispose();
					new Intro();
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

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getExtendedKeyCode() == KeyEvent.VK_PAGE_UP) {
			blueX = 560;
			blueY = 0;
			playerBlue.setLocation(blueX, blueY);
			}
		if(e.getExtendedKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			blueX = 0;
			blueY = 720;
			playerBlue.setLocation(blueX, blueY);
			}
		if(e.getExtendedKeyCode() == KeyEvent.VK_END) {
			redX = 560;
			redY = 0;
			playerRed.setLocation(redX, redY);
			}
		if(e.getExtendedKeyCode() == KeyEvent.VK_HOME) {
			redX = 0;
			redY = 720;
			playerRed.setLocation(redX, redY);
			}
		}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
}
