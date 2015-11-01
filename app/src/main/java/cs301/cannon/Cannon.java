package cs301.cannon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by oney18 on 10/31/2015.
 */
public class Cannon {
    private int degrees;
    private Paint greyPaint;
    private int top;
    private int right;
    private int bottom;
    private int left;
    private boolean destroyed;
    private int hitCount;

    private int height; //height of the canvas

    public Cannon(int height)
    {
        this.height = height;
        top = height - 250;
        bottom = height;
        right = 55;
        left = -55;
        degrees = 45;
        destroyed = false;
        hitCount = 0;

        greyPaint = new Paint();
        greyPaint.setColor(Color.GRAY);

    }

    public int getDegrees()
    {
        return degrees;
    }

    public void setDegrees(int degrees)
    {
        this.degrees = degrees;
    }

    public void draw(Canvas g)
    {
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);

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
            blackPaint.setAlpha(4 * hitCount);
            g.drawCircle(125, height - 125, hitCount*3, blackPaint);
            hitCount++;
        }
        else if(hitCount >= 60 ) //cannon explosion slowing down, no more cannon
        {
            blackPaint.setAlpha(4*(120 - hitCount));
            g.drawCircle(125, height - 125, (120 - hitCount)*3, blackPaint);
            hitCount++;
        }
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    public void checkHit(CannonBall ball)
    {
        if(ball.getxPos() < 100 && ball.getyPos() > height - 55 && ball.getLife() > 20)
        {
            destroyed = true;
        }
    }

}
