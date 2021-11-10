package com.mhmdsabdlh.snakesandladders;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Map2_PC extends AppCompatActivity {

    private ImageView player1, player2, map;
    private Button diceNum;
    private final Random random = new Random();
    private int rollN = 0;
    private int blueD = 1;
    private int redD = 1;
    private final String p1Name = "YOU";
    private final String p2Name = "PC";
    private float blueX, blueY, redX, redY, squareX;
    private TextView playerBlue;
    private TextView playerRed;
    private MediaPlayer ladder, snake1, snake2, snake3, win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2_pc);

        //Definition of Views
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        playerBlue = findViewById(R.id.playerBlue);
        playerBlue.setText(p1Name + ": 0");
        playerRed = findViewById(R.id.playerRed);
        playerRed.setText(p2Name + ": 0");
        map = findViewById(R.id.map);
        diceNum = findViewById(R.id.diceNum);

        //Hack
        TextView credit = findViewById(R.id.credit);
        credit.setOnLongClickListener(view -> {
            blueX = squareX;
            blueY = 0;
            setLocation(blueX, blueY, player1);
            return true;
        });

        //Sound Effect
        ladder = MediaPlayer.create(getApplicationContext(), R.raw.ladder);
        snake1 = MediaPlayer.create(getApplicationContext(), R.raw.snake1);
        snake2 = MediaPlayer.create(getApplicationContext(), R.raw.snake2);
        snake3 = MediaPlayer.create(getApplicationContext(), R.raw.snake3);
        win = MediaPlayer.create(getApplicationContext(), R.raw.win);

        //Get Height and Width of map
        map.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //noinspection deprecation
                map.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                squareX = (float) map.getWidth() / 10;
                blueX = -squareX;
                blueY = squareX * 9;
                redX = -squareX;
                redY = squareX * 9;
            }
        });
    }

    //Dice click function
    public void diceClick(View view) {
        //PC TURN
        Thread move = new Thread(() -> {
            diceNum.setClickable(false);
            rollN = roleDice();
            int i = 1;
            while (i <= rollN) {
                if (xDirection(blueY))
                    blueD = -1;
                else
                    blueD = +1;
                blueX += (squareX * blueD);
                if (playerLocation(blueX, blueY) == 100) {
                    setLocation(blueX, blueY, player1);
                    endGame(p1Name + " (BLUE) win, while " + p2Name + " (RED) still in " + playerLocation(redX, redY) + ".");
                    break;
                } else {
                    if (blueX == squareX * 10) {
                        blueX = squareX * 9;
                        blueY -= squareX;
                    } else if (blueX < 0) {
                        blueX = 0;
                        blueY -= squareX;
                    }
                    setLocation(blueX, blueY, player1);
                    i++;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }
            }
            snakesCases();
            laddersCases();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            if (playerLocation(blueX, blueY) != 100) {
                //PC TURN
                rollN = roleDice();
                i = 1;
                while (i <= rollN) {
                    if (xDirection(redY))
                        redD = -1;
                    else
                        redD = +1;
                    redX += (squareX * redD);
                    if (playerLocation(redX, redY) == 100) {
                        setLocation(redX, redY, player2);
                        endGame(p2Name + " (RED) win, while " + p1Name + " (BLUE) still in " + playerLocation(blueX, blueY) + ".");
                        break;
                    } else {
                        if (redX == squareX * 10) {
                            redX = squareX * 9;
                            redY -= squareX;
                        } else if (redX < 0) {
                            redX = 0;
                            redY -= squareX;
                        }
                        setLocation(redX, redY, player2);
                        i++;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                snakesCases();
                laddersCases();
                diceNum.setClickable(true);
            }
        });
        move.start();
    }

    //get the dice number
    private int roleDice() {
        int index = random.nextInt(6) + 1;
        switch (index) {
            case 1:
                diceNum.setBackground(getDrawable(R.drawable.dice1));
                break;
            case 2:
                diceNum.setBackground(getDrawable(R.drawable.dice2));
                break;
            case 3:
                diceNum.setBackground(getDrawable(R.drawable.dice3));
                break;
            case 4:
                diceNum.setBackground(getDrawable(R.drawable.dice4));
                break;
            case 5:
                diceNum.setBackground(getDrawable(R.drawable.dice5));
                break;
            case 6:
                diceNum.setBackground(getDrawable(R.drawable.dice6));
                break;
            default:
                break;
        }
        return index;
    }

    //Get the location in map of players
    private int playerLocation(float x, float y) {
        return xDirection(y) ?
                (int) ((squareX * 9 - x) / squareX + ((squareX * 9 - y) / squareX) * 10 + 1)
                : (int) (x / squareX + ((squareX * 9 - y) / squareX) * 10 + 1);
    }

    //Know the x direction to move
    private boolean xDirection(float y) {
        return y == 0 || y == squareX * 2 || y == squareX * 4 || y == squareX * 6 || y == squareX * 8;
    }

    //Set the position of players in map
    private void setLocation(float x, float y, ImageView player) {
        player.setX(x);
        player.setY(y);
        Map2_PC.this.runOnUiThread(() -> {
            if (player == player1)
                playerBlue.setText(p1Name + ": " + playerLocation(x, y));
            else
                playerRed.setText(p2Name + ": " + playerLocation(x, y));
        });
    }

    //Ladders cases
    private void laddersCases() {
        if (blueX == 0 && blueY == squareX * 9) {
            ladder.start();
            blueX = squareX * 2;
            blueY = squareX * 6;
            setLocation(blueX, blueY, player1);
        }
        if (redX == 0 && redY == squareX * 9) {
            ladder.start();
            redX = squareX * 2;
            redY = squareX * 6;
            setLocation(redX, redY, player2);
        }
        //LADDER 2
        if (blueX == squareX * 8 && blueY == squareX * 9) {
            ladder.start();
            blueX = squareX * 9;
            blueY = squareX * 6;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 8 && redY == squareX * 9) {
            ladder.start();
            redX = squareX * 9;
            redY = squareX * 6;
            setLocation(redX, redY, player2);
        }
        //LADDER 3
        if (blueX == squareX * 3 && blueY == squareX * 9) {
            ladder.start();
            blueX = squareX * 6;
            blueY = squareX * 8;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 3 && redY == squareX * 9) {
            ladder.start();
            redX = squareX * 6;
            redY = squareX * 8;
            setLocation(redX, redY, player2);
        }
        //LADDER 4
        if (blueX == squareX * 7 && blueY == squareX * 7) {
            ladder.start();
            blueX = squareX * 3;
            blueY = squareX;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 7 && redY == squareX * 7) {
            ladder.start();
            redX = squareX * 3;
            redY = squareX;
            setLocation(redX, redY, player2);
        }
        //LADDER 5
        if (blueX == 0 && blueY == squareX * 7) {
            ladder.start();
            blueX = squareX;
            blueY = squareX * 5;
            setLocation(blueX, blueY, player1);
        }
        if (redX == 0 && redY == squareX * 7) {
            ladder.start();
            redX = squareX;
            redY = squareX * 5;
            setLocation(redX, redY, player2);
        }
        //LADDER 6
        if (blueX == squareX * 9 && blueY == squareX * 4) {
            ladder.start();
            blueX = squareX * 6;
            blueY = squareX * 3;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 9 && redY == squareX * 4) {
            ladder.start();
            redX = squareX * 6;
            redY = squareX * 3;
            setLocation(redX, redY, player2);
        }
        //LADDER 7
        if (blueX == 0 && blueY == squareX * 2) {
            ladder.start();
            blueX = squareX;
            blueY = 0;
            setLocation(blueX, blueY, player1);
        }
        if (redX == 0 && redY == squareX * 2) {
            ladder.start();
            redX = squareX;
            redY = 0;
            setLocation(redX, redY, player2);
        }
        //LADDER 8
        if (blueX == squareX * 8 && blueY == squareX * 2) {
            ladder.start();
            blueX = squareX * 9;
            blueY = 0;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 8 && redY == squareX * 2) {
            ladder.start();
            redX = squareX * 9;
            redY = 0;
            setLocation(redX, redY, player2);
        }
    }

    //Snakes cases
    private void snakesCases() {
        //SNAKE 1
        if (blueX == squareX * 3 && blueY == squareX * 8) {
            snake3.start();
            blueX = squareX * 6;
            blueY = squareX * 9;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 3 && redY == squareX * 8) {
            snake3.start();
            redX = squareX * 6;
            redY = squareX * 9;
            setLocation(redX, redY, player2);
        }
        //SNAKE 2
        if (blueX == squareX && blueY == squareX * 3) {
            snake2.start();
            blueX = squareX;
            blueY = squareX * 8;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX && redY == squareX * 3) {
            snake2.start();
            redX = squareX;
            redY = squareX * 8;
            setLocation(redX, redY, player2);
        }
        //SNAKE 3
        if (blueX == squareX * 3 && blueY == squareX * 3) {
            snake3.start();
            blueX = 0;
            blueY = squareX * 4;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 3 && redY == squareX * 3) {
            snake3.start();
            redX = 0;
            redY = squareX * 4;
            setLocation(redX, redY, player2);
        }
        //SNAKE 4
        if (blueX == squareX * 2 && blueY == 0) {
            snake1.start();
            blueX = squareX;
            blueY = squareX * 2;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 2 && redY == 0) {
            snake1.start();
            redX = squareX;
            redY = squareX * 2;
            setLocation(redX, redY, player2);
        }
        //SNAKE 5
        if (blueX == squareX * 5 && blueY == 0) {
            snake1.start();
            blueX = squareX * 5;
            blueY = squareX * 2;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 5 && redY == 0) {
            snake1.start();
            redX = squareX * 5;
            redY = squareX * 2;
            setLocation(redX, redY, player2);
        }
        //SNAKE 6
        if (blueX == squareX * 6 && blueY == squareX) {
            snake2.start();
            blueX = squareX * 4;
            blueY = squareX * 6;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 6 && redY == squareX) {
            snake2.start();
            redX = squareX * 4;
            redY = squareX * 6;
            setLocation(redX, redY, player2);
        }
        //SNAKE 7
        if (blueX == squareX * 7 && blueY == 0) {
            snake3.start();
            blueX = squareX * 7;
            blueY = squareX * 2;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 7 && redY == 0) {
            snake3.start();
            redX = squareX * 7;
            redY = squareX * 2;
            setLocation(redX, redY, player2);
        }
        //SNAKE 8
        if (blueX == squareX * 6 && blueY == squareX * 4) {
            snake1.start();
            blueX = squareX * 6;
            blueY = squareX * 6;
            setLocation(blueX, blueY, player1);
        }
        if (redX == squareX * 6 && redY == squareX * 4) {
            snake1.start();
            redX = squareX * 6;
            redY = squareX * 6;
            setLocation(redX, redY, player2);
        }
    }

    //Alert at the end
    private void endGame(String s) {
        win.start();
        Map2_PC.this.runOnUiThread(() -> new AlertDialog.Builder(Map2_PC.this).setTitle("Game Over")
                .setMessage(s)
                .setPositiveButton("Restart?", (dialogInterface, i1) -> {
                    blueX = -squareX;
                    blueY = squareX * 9;
                    redX = -squareX;
                    redY = squareX * 9;
                    setLocation(blueX, blueY, player1);
                    setLocation(redX, redY, player2);
                    diceNum.setBackground(getDrawable(R.drawable.dice0));
                    diceNum.setClickable(true);
                })
                .setNeutralButton("Main Menu!", (dialogInterface, i1) -> finish())
                .setNegativeButton("Exit", (dialogInterface, i1) -> this.finishAffinity())
                .show());
    }

}