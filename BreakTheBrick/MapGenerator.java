package BreakTheBrick;

import java.awt.*;

public class MapGenerator
{
    public int[][] MAP2D;
    public int BRICK_WIDTH;
    public int BRICK_HEIGHT;
    public MapGenerator(int ROW, int COLLUM)
    {
        MAP2D = new int[ROW][COLLUM];
        for (int i = 0; i < MAP2D.length; i++)
        {
            for (int j = 0; j < MAP2D[0].length; j++)
            {
                MAP2D[i][j] = 1;
            }
        }

        BRICK_WIDTH = 540/COLLUM;
        BRICK_HEIGHT = 150/ROW;
    }
    public void Draw(Graphics2D g)
    {
        for (int i = 0; i < MAP2D.length; i++)
        {
            for (int j = 0; j < MAP2D[0].length; j++)
            {
                if (MAP2D[i][j] > 0)
                {
                    g.setColor(Color.PINK);
                    g.fillRect(j * BRICK_WIDTH + 80, i * BRICK_HEIGHT + 50, BRICK_WIDTH, BRICK_HEIGHT);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * BRICK_WIDTH + 80, i * BRICK_HEIGHT + 50, BRICK_WIDTH, BRICK_HEIGHT);
                }
            }
        }
    }
    public void setBrickValue(int VALUE, int ROW, int COLLUM)
    {
        MAP2D[ROW][COLLUM] = VALUE;
    }
}
