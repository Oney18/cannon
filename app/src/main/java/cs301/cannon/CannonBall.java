package cs301.cannon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by oney18 on 10/31/2015.
 */
public class CannonBall {
    private int xVel;
    private int yVel;
    private int xPos;
    private int yPos;
    private int bottomOfScreen;
    private Paint blackPaint;
    private int life;

    private static int wind = 0;
    private static int rad = 40;
    private static int power = 75;

    public CannonBall(int deg, int yBottom)
    {
        xPos = 0;
        yPos = yBottom;
        xVel = (int) (power*Math.sin(Math.toRadians(deg)));
        yVel = (int) -(power*Math.cos(Math.toRadians(deg)));
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);

        life = 0;

        bottomOfScreen = yBottom;
    }

    public void draw(Canvas g)
    {
        g.drawCircle(xPos, yPos, rad, blackPaint);

        xPos += xVel;
        yPos += yVel;

        yVel += 2;

        if(yPos >= bottomOfScreen && life != 0)
        {
            yVel *= -0.8;
            yPos = bottomOfScreen;
        }

        xVel += wind;

        life++;
    }

    public int getxPos()
    {
        return xPos;
    }

    public int getyPos()
    {
        return yPos;
    }

    public void setWind(int wind)
    {
        this.wind = wind;
    }

    public int getLife()
    {
        return life;
    }
}
