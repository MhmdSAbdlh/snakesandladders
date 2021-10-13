import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class BoardOP extends JFrame implements ActionListener{
	
	//Variables
	Random random;
	boolean blueTurn = true;
	boolean endGame = false;
	int rollN = 0;
	int blueX = 0, blueY = 720, redX = 0, redY = 720;
	String[] messArr = {"RESTART GAME", "MAIN MENU", "EXIT"};
	
	//Images
	URL url = getClass().getResource("board.jpg");
	ImageIcon icon = new ImageIcon(url);
	Image image = icon.getImage();
	URL player1 = getClass().getResource("player1.png");
	ImageIcon p1Icon = new ImageIcon(player1);
	URL player2 = getClass().getResource("player2.png");
	ImageIcon p2Icon = new ImageIcon(player2);
	URL dice = getClass().getResource("dice.png");
	ImageIcon diceIcon = new ImageIcon(dice);
	URL logo = getClass().getResource("logo.png");
	ImageIcon logoIcon = new ImageIcon(logo);
	URL dice1 = getClass().getResource("1.png");
	ImageIcon dice1Icon = new ImageIcon(dice1);
	URL dice2 = getClass().getResource("2.png");
	ImageIcon dice2Icon = new ImageIcon(dice2);
	URL dice3 = getClass().getResource("3.png");
	ImageIcon dice3Icon = new ImageIcon(dice3);
	URL dice4 = getClass().getResource("4.png");
	ImageIcon dice4Icon = new ImageIcon(dice4);
	URL dice5 = getClass().getResource("5.png");
	ImageIcon dice5Icon = new ImageIcon(dice5);
	URL dice6 = getClass().getResource("6.png");
	ImageIcon dice6Icon = new ImageIcon(dice6);
	ImageIcon currentDice;
	
	JLabel board = new JLabel(icon);
	JLabel playerTurn = new JLabel("Blue Turn");
	JLabel blueNaPo = new JLabel("YOU ("+playerLocation(blueX,blueY)+")");
	JLabel redNaPo = new JLabel("PC ("+playerLocation(redX,redY)+")");
	JLabel playerBlue = new JLabel(p1Icon);
	JLabel playerRed = new JLabel(p2Icon);
	JLabel diceNumber = new JLabel();
	JButton diceBtn = new JButton(diceIcon);
	Thread delay = new Thread();
	
	BoardOP(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 840);
		this.setTitle("Snakes & Ladders");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setIconImage(logoIcon.getImage());
		this.getContentPane().setBackground(Main.darkC);
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getExtendedKeyCode() == KeyEvent.VK_END) {
					blueX = 560;
					blueY = 0;
					playerBlue.setLocation(blueX, blueY);
				}}
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
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
		diceBtn.addActionListener(this);
		
		//Label
		JLabel diceRLabel = new JLabel("Press the dice");
		diceRLabel.setBounds(800,30,200,100);
		diceRLabel.setForeground(Main.ligthC);
		diceRLabel.setHorizontalAlignment(0);
		diceRLabel.setFont(Main.smallF);
		JLabel diceResult = new JLabel("Dice number");
		diceResult.setBounds(800,300,200,100);
		diceResult.setForeground(Main.ligthC);
		diceResult.setHorizontalAlignment(0);
		diceResult.setFont(Main.smallF);
		playerTurn.setBounds(840, 550, 120, 100);
		playerTurn.setForeground(Main.playerY);
		playerTurn.setHorizontalAlignment(0);
		playerTurn.setFont(Main.medF);
		blueNaPo.setBounds(810, 650, 180, 100);
		blueNaPo.setForeground(Main.playerY);
		blueNaPo.setHorizontalAlignment(0);
		blueNaPo.setFont(Main.smallF);
		redNaPo.setBounds(810, 700, 180, 100);
		redNaPo.setForeground(Main.playerX);
		redNaPo.setHorizontalAlignment(0);
		redNaPo.setFont(Main.smallF);
		diceNumber.setBounds(850,400,100,100);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == diceBtn) {
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
		}
		//PC TURN
		Timer pc = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		pc.start();
		pc.setRepeats(false);
	}
	
	private int playerLocation(int x, int y) {
		return(x/80 + ((720-y)/8) + 1);
	}

	private void laddersCases() {
		//X=160 Y=240
		if(blueX == 160 && blueY == 240) {
			blueX = 320;
			blueY = 0;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 160 && redY == 240) {
			redX = 320;
			redY = 0;
			playerRed.setLocation(redX, redY);
		}
		//X=560	 Y=240
		if(blueX == 560 && blueY == 240) {
			blueX = 560;
			blueY = 0;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 560 && redY == 240) {
			redX = 560;
			redY = 0;
			playerRed.setLocation(redX, redY);
		}
		//X=400 Y=480
		if(blueX == 400 && blueY == 480) {
			blueX = 320;
			blueY = 320;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 400 && redY == 480) {
			redX = 320;
			redY = 320;
			playerRed.setLocation(redX, redY);
		}
		//X=160 Y=720
		if(blueX == 160 && blueY == 720) {
			blueX = 0;
			blueY = 320;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 160 && redY == 720) {
			redX = 0;
			redY = 320;
			playerRed.setLocation(redX, redY);
		}
		//X=400 Y=720
		if(blueX == 400 && blueY == 720) {
			blueX = 480;
			blueY = 560;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 400 && redY == 720) {
			redX = 480;
			redY = 560;
			playerRed.setLocation(redX, redY);
		}
		//X=720 Y=640
		if(blueX == 720 && blueY == 640) {
			blueX = 720;
			blueY = 240;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 720 && redY == 640) {
			redX = 720;
			redY = 240;
			playerRed.setLocation(redX, redY);
			}
		}
	
	private void snakesCases() {
		//X=0 Y=0
		if(blueX == 0 && blueY == 0) {
			blueX = 0;
			blueY = 240;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 0 && redY == 0) {
			redX = 0;
			redY = 240;
			playerRed.setLocation(redX, redY);
		}
		//X=64 Y=0
		if(blueX == 640 && blueY == 0) {
			blueX = 640;
			blueY = 240;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 640 && redY == 0) {
			redX = 640;
			redY = 240;
			playerRed.setLocation(redX, redY);
		}
		//X=480 Y=80
		if(blueX == 480 && blueY == 80) {
			blueX = 480;
			blueY = 320;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 480 && redY == 80) {
			redX = 480;
			redY = 320;
			playerRed.setLocation(redX, redY);
		}
		//X=320 Y=240
		if(blueX == 320 && blueY == 240) {
			blueX = 80;
			blueY = 320;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 320 && redY == 240) {
			redX = 80;
			redY = 320;
			playerRed.setLocation(redX, redY);
		}
		//X=480 Y=400
		if(blueX == 480 && blueY == 400) {
			blueX = 640;
			blueY = 640;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 480 && redY == 400) {
			redX = 640;
			redY = 640;
			playerRed.setLocation(redX, redY);
		}
		//X=240 Y=480
		if(blueX == 240 && blueY == 480) {
			blueX = 0;
			blueY = 720;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 240 && redY == 480) {
			redX = 0;
			redY = 720;
			playerRed.setLocation(redX, redY);
		}
		//X=320 Y=560
		if(blueX == 320 && blueY == 560) {
			blueX = 320;
			blueY = 720;
			playerBlue.setLocation(blueX, blueY);
		}
		if(redX == 320 && redY == 560) {
			redX = 320;
			redY = 720;
			playerRed.setLocation(redX, redY);
		}
	}
	
	private void blueMove() {
		Thread blueT = new Thread(new Runnable() {
		@Override
		public void run() {
			int i=1;
			while(i<=rollN) {
				blueX+=80;
				if(blueY==0 && blueX == 720) {
					playerBlue.setLocation(blueX, blueY);
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
					Thread.sleep(300);
					} catch (InterruptedException e) {}
				}
			i=10;
			snakesCases();
			laddersCases();
			blueNaPo.setText("YOU ("+playerLocation(blueX,blueY)+")");			
		}
	});
	blueT.start();
	}

	private void redMove() {
			Thread redT = new Thread(new Runnable() {
				@Override
				public void run() {
					int i=1;
					while(i<=rollN) {
						redX+=80;
						if(redY==0 && redX == 720) {
							playerRed.setLocation(redX, redY);
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
						try {Thread.sleep(300);
							} catch (InterruptedException e) {}
						}
					i=10;
					snakesCases();
					laddersCases();
					redNaPo.setText("PC ("+playerLocation(redX,redY)+")");
				}
			});
			redT.start();
			}

	private void endGame(int x, int y) {
		if(playerLocation(x,y) == 100) {
			int result;
			if(x == blueX && y == blueY)
				result = JOptionPane.showOptionDialog(null, "You win, while 'PC' still in "+playerLocation(redX,redY), "GAME OVER", JOptionPane.YES_NO_OPTION, 1, null, messArr, 1);
			else
				result = JOptionPane.showOptionDialog(null, "You loss, <LOSER> and you still in "+playerLocation(blueX,blueY), "GAME OVER", JOptionPane.YES_NO_OPTION, 1, null, messArr, 1);
			endGame = true;
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
			}
	}
	
	private int roleDice() {
		random = new Random();
		int index = random.nextInt(6)+1;
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

}
