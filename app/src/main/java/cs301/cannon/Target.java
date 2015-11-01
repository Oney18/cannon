package cs301.cannon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Target
 *
 * This is the class for the various target animations
 * It holds the methods required to paint them as well as their logic
 *
 * @author Jarrett Oney
 * @version October 2015
 *
 */
public class Target {

    private Paint redPaint;
    private Paint whitePaint; //paints used for the targets
    private Paint expPaint;

    private double initX; //starting x pos
    private double initY; //starting y pos
    private float xCent; //x pos of target
    private float yCent; //y pos of target
    private int type; //the kind/phase of the target
    private int hitCount; //count when hit
    private double velX; //speed of movement in X
    private double velY; //speed of movement in Y

    private boolean hit; //was the target hit?

    /**
     * Creates a new target object
     *
     * @param initX Initial x position of target
     * @param initY Initial y position of target
     * @param type The type of target being made
     */
    public Target(double initX, double initY, int type)
    {
        //inits the paints used for the target
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        expPaint = new Paint();
        expPaint.setARGB(0, 119, 55, 55);


        this.initX = initX;
        this.initY = initY;
        this.type = type;

        hit = false; //is not hit initially
    }

    /**
     * Calculates the target's velocity and draws the target
     *
     * @param g Canvas target is drawn on
     * @param count The current time in ticks the animation has been running
     */
    public void draw(Canvas g, double count) {

        if (type < 3) { //no need to check velocity for a destroyed target
            double targVelX = Math.cos(count / 32);
            double targVelY = Math.sin(count / 32);

            velX = 500 * targVelX;
            velY = 350 * targVelY;
        }

        switch (type) {

            case 1: //target goes to left and right
                xCent = (float) (initX + velX);
                yCent = (float) initY;
                g.drawCircle(xCent, yCent, 70, redPaint);
                g.drawCircle(xCent, yCent, 50, whitePaint);
                g.drawCircle(xCent, yCent, 30, redPaint);
                g.drawCircle(xCent, yCent, 10, whitePaint);
                break;

            case 2: //target goes up and down
                xCent = (float) initX;
                yCent = (float) (initY + velY);
                g.drawCircle(xCent, yCent, 70, redPaint);
                g.drawCircle(xCent, yCent, 50, whitePaint);
                g.drawCircle(xCent, yCent, 30, redPaint);
                g.drawCircle(xCent, yCent, 10, whitePaint);
                break;

            case 3: //explosion growing
                g.drawCircle(xCent, yCent + 2*hitCount, 70, redPaint);
                g.drawCircle(xCent, yCent + 2*hitCount, 50, whitePaint);
                g.drawCircle(xCent, yCent + 2*hitCount, 30, redPaint);
                g.drawCircle(xCent, yCent + 2*hitCount, 10, whitePaint);
                g.drawCircle(xCent, yCent + 2*hitCount, hitCount, expPaint);
                expPaint.setAlpha((int)(2.5*hitCount)); //gets darker
                hitCount++;

                if (hitCount >= 101) { //begins second phase of explosion
                    yCent += 2*hitCount;
                    type = 4;
                }
                break;

            case 4: //explosion leaving
                g.drawCircle(xCent, yCent + 2*(101 - hitCount), hitCount, expPaint);
                expPaint.setAlpha((int)(2.5*hitCount)); //gets lighter
                hitCount--;
                if(hitCount <= 1) //explosion concluded
                {
                    type = 5;
                }
                break;

            case 5: //blank space
                break; //this target is destroyed
        }
    }

    public boolean isHit()
    {
        return hit;
    }

    /**
     * Checks to see if a target is hit based on a ball's position
     *
     * @param ball The ball that might hit the target
     */
    public void checkHit(CannonBall ball)
    {
        //There is a 50x50 pixel square around the center of the target that denotes the hit zone
        if( type < 3 && ball.getxPos() - xCent <= 50 && ball.getxPos() - xCent >= -50 &&
                ball.getyPos() - yCent <= 50 && ball.getyPos() - yCent >= -50)
        {
            type = 3; //begins the explosion
            hit = true;
        }
    }
}
