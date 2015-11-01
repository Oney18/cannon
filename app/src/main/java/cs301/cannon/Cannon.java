package cs301.cannon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Cannon
 *
 * This is the class that represents the cannon in the animation.
 * It draws the cannon as well as the logic for if the cannon is hit.
 *
 * @author Jarrett Oney
 * @version October 2015
 *
 */
public class Cannon {
    private int degrees; //degrees of the angle made by the cannon and the ground
    private Paint greyPaint; //paint for the cannon
    private Paint expPaint; //paint for cannon's explosion
    private int top; //top pixel of the cannon
    private int right; //right pixel of the cannon
    private int bottom; //bottom pixel of the cannon
    private int left; //left pixel of the cannon
    private boolean destroyed; //is the cannon destroyed?
    private int hitCount; //the amount of ticks since the cannon was hit
    private int height; //height of the canvas

    /**
     * Creates the cannon object
     *
     * @param height The height of the canvas
     */
    public Cannon(int height)
    {
        //inits the cannons dimensions
        this.height = height;
        top = height - 250;
        bottom = height;
        right = 55;
        left = -55;
        degrees = 45;
        destroyed = false;
        hitCount = 0;

        //inits the paints used to draw the cannon
        greyPaint = new Paint();
        greyPaint.setColor(Color.GRAY);
        expPaint = new Paint();
        expPaint.setARGB(0, 119, 55, 55);

    }

    public int getDegrees()
    {
        return degrees;
    }

    public void setDegrees(int degrees)
    {
        this.degrees = degrees;
    }

    /**
     * Draws the cannon or the cannon exploding
     *
     * @param g The canvas to draw on
     */
    public void draw(Canvas g)
    {
        if(hitCount < 60) //cannon exists and not destroyed
        {
            RectF barrel = new RectF(left, top, right, bottom);
            g.save();
            g.rotate(degrees, 0, height);
            g.drawRect(barrel, greyPaint);
            g.restore();
        }

        if(destroyed && hitCount < 60) //cannon exploding
        {
            expPaint.setAlpha(4 * hitCount);
            g.drawCircle(0, height, hitCount * 5, expPaint);
            hitCount++;
        }
        else if(hitCount >= 60 ) //cannon explosion slowing down, no more cannon
        {
            expPaint.setAlpha(4*(120 - hitCount));
            g.drawCircle(0, height, (120 - hitCount)*5, expPaint);
            hitCount++;
        }
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    /**
     * Checks to see if a given cannonball hits the cannon
     *
     * @param ball The cannonball which might hit the cannon
     */
    public void checkHit(CannonBall ball)
    {
        //the cannonball must be in the air where the cannon is
        //must also not have just been created
        if(ball.getxPos() < 200 && ball.getyPos() > height - 225 &&
                ball.getyPos() < height - 50 && ball.getLife() > 20)
        {
            destroyed = true;
        }
    }

}
