package BreakTheBrick;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener
{
    private boolean play = false;
    private int SCORE = 0;
    private int BRICKS = 21;
    private int BrokenBricks;

    private final Timer time;
    int SPEED = 20;

    private int PaddlePositionX = 310;

    private int BallPositionX = 120;
    private int BallPositionY = 350;
    private int BallDirectionX = -4;
    private int BallDirectionY = -6;

    private MapGenerator MAP;

    public Gameplay()
    {
        MAP = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(SPEED, this);
        time.start();
    }

    public void paint(Graphics g)
    {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);
        // borders
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);
        // score
        g.setColor(Color.PINK);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("" + SCORE, 610, 30);
        // map
        MAP.Draw((Graphics2D) g);
        // paddle
        g.setColor(Color.ORANGE);
        g.fillRect(PaddlePositionX,550,100,8);
        // ball
        g.setColor(Color.WHITE);
        g.fillOval(BallPositionX,BallPositionY,20,20);

        // FOR WINNING ------------------------------------------
        if(BRICKS <= 0)
        {
            // You Win!
            play = false;
            BallDirectionX = 0;
            BallDirectionY = 0;
            g.setColor(Color.BLUE);
            g.setFont(new Font("serif", Font.BOLD, 25));
            String YouWinString = "You Win!";
            g.drawString(YouWinString, 300 - YouWinString.length() / 2, 300);
        }

        // FOR LOOSING ------------------------------------------
        if (BallPositionY > 570)
        {
            play = false;
            BallDirectionX = 0;
            BallDirectionY = 0;
            // Game Over!
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 25));
            String GameOverString = "Game Over!";
            g.drawString(GameOverString, 300 - GameOverString.length() / 2, 300);
            // SCORE:
            g.setColor(Color.PINK);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("SCORE:     " + SCORE, 150, 330);
            g.drawString("BRICKS:    " + BrokenBricks, 150, 360);
            // Press ENTER to restart...
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            String PressEnter = "Press ENTER to restart...";
            g.drawString(PressEnter, 270 - PressEnter.length() / 2, 400);
        }

        g.dispose();
    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        time.start();

        if(play)
        {
            if (new Rectangle(BallPositionX,BallPositionY,20,20).intersects(new Rectangle(PaddlePositionX,550,100,8)))
            {
                BallDirectionY = -BallDirectionY;
            }

            LableA : for (int i = 0; i < MAP.MAP2D.length; i++)
            {
                for (int j = 0; j < MAP.MAP2D[0].length; j++)
                {
                    if (MAP.MAP2D[i][j] > 0)
                    {
                        int BrickPositionX = j * MAP.BRICK_WIDTH + 80;
                        int BrickPositionY = i * MAP.BRICK_HEIGHT + 50;
                        int BrickWidth = MAP.BRICK_WIDTH;
                        int BrickHeight = MAP.BRICK_HEIGHT;

                        Rectangle rect = new Rectangle(BrickPositionX, BrickPositionY, BrickWidth, BrickHeight);
                        Rectangle ballRect = new Rectangle(BallPositionX,BallPositionY,20,20);

                        if (ballRect.intersects(rect))
                        {
                            MAP.setBrickValue(0, i, j);
                            BRICKS --;
                            BrokenBricks ++;
                            SCORE += 5;

                            if (BallPositionX + 19 <= ballRect.x || BrickPositionX + 1 >= ballRect.x + ballRect.width)
                            {
                                BallDirectionX = -BallDirectionX;
                            }
                            else
                            {
                                BallDirectionY = -BallDirectionY;
                            }
                            break LableA;
                        }
                    }
                }
            }

            BallPositionX += BallDirectionX;
            BallPositionY += BallDirectionY;
            if (BallPositionX < 0)
            {
                BallDirectionX = -BallDirectionX;
            }
            if (BallPositionY < 0)
            {
                BallDirectionY = -BallDirectionY;
            }
            if (BallPositionX > 670)
            {
                BallDirectionX = -BallDirectionX;
            }
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
                if (PaddlePositionX >= 600)
                {
                    PaddlePositionX = 600;
                }
                else
                {
                    moveRight();
                }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if (PaddlePositionX < 10)
            {
                PaddlePositionX = 10;
            }
            else
            {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            // FOR LOOSING ------------------------------------------
            if (!play & BRICKS > 0)
            {
                play = true;
                BallPositionX = 120;
                BallPositionY = 350;
                BallDirectionX = -4;
                BallDirectionY = -6;
                PaddlePositionX = 310;
                SCORE = 0;
                SPEED = 1;
                BrokenBricks = 0;
                BRICKS = 21;
                MAP = new MapGenerator(3,7);
                repaint();
            }
            // FOR WINNING ------------------------------------------
            else if (!play & BRICKS == 0)
            {
                play = true;
                BallPositionX = 120;
                BallPositionY = 350;
                BallDirectionX = -4;
                BallDirectionY = -6;
                PaddlePositionX = 310;
                if (SPEED != 4)
                {
                    SPEED = SPEED - 8;
                }
                BRICKS = 21;
                MAP = new MapGenerator(3,7);
                repaint();
            }
        }
    }
    public void moveRight()
    {
        play = true;
        PaddlePositionX += 20;
    }
    public void moveLeft()
    {
        play = true;
        PaddlePositionX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
