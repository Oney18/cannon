package cs301.cannon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * CannonBall
 *
 * This is the class responsible for the upkeep of the CannonBalls
 * created within the animation, drawing the balls as well as calculating
 * their next positions
 *
 * @author Jarrett Oney
 * @version October 2015
 *
 */
public class CannonBall {
    private double xVel; //x velocity of ball
    private double yVel; //y velocity of ball
    private double xPos; //x pos of ball
    private double yPos; //y pos of ball
    private int bottomOfScreen; //height of the canvas
    private Paint blackPaint; //paint used to create the ball
    private int life; //amount of ticks the ball has been alive

    private static double wind = 0; //the wind affecting all the balls
    private static final int rad = 40; //the radius of all balls
    private static final int power = 80; //the total velocity of the ball when launched

    /**
     * Creates a new cannonball object at the bottom left corner of the canvas
     *
     * @param deg Current degree of the angle the cannon makes to the ground
     * @param yBottom Bottom of the canvas
     */
    public CannonBall(int deg, int yBottom)
    {
        xPos = 0;
        yPos = yBottom;

        //Calculates velocity vectors
        xVel = (int) (power*Math.sin(Math.toRadians(deg)));
        yVel = (int) -(power*Math.cos(Math.toRadians(deg)));

        //Inits paint
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);

        //Ball begins its life
        life = 0;

        bottomOfScreen = yBottom;
    }

    /**
     * Draws the cannonball and then calculates the position for the next frame
     * Takes into account the wind
     *
     * @param g The canvas the ball is to be drawn on
     */
    public void draw(Canvas g)
    {
        g.drawCircle((float) xPos, (float) yPos, rad, blackPaint);

        //Calculates next position
        xPos += xVel;
        yPos += yVel;


        //Bounces if hits the bottom of the screen
        if(yPos >= bottomOfScreen - rad && life != 0)
        {
            yVel *= -0.7;
            yPos = bottomOfScreen - rad;
        }

        //Gravity effects
        yVel += 2;

        //Wind effects
        xVel += wind;

        life++;
    }

    public double getxPos()
    {
        return xPos;
    }

    public double getyPos()
    {
        return yPos;
    }

    public static void setWind(double newWind)
    {
        wind = newWind;
    }

    public int getLife()
    {
        return life;
    }
}
