package cs301.cannon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by oney18 on 10/31/2015.
 * 2100
 * 1400
 */
public class Target {

    private Paint redPaint;
    private Paint whitePaint; //paints used for the targets
    private Paint blackPaint;

    private int initX; //starting x pos
    private int initY; //starting y pos
    private int xCent; //x pos of target
    private int yCent; //y pos of target
    private int type; //the kind/phase of the target
    private int hitCount; //count when hit
    private int velX; //speed of movement in X
    private int velY; //speed of movement in Y

    private boolean hit;

    public Target(int initX, int initY, int type)
    {
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setAlpha(150);


        this.initX = initX;
        this.initY = initY;

        this.type = type;
        hit = false;
    }

    public void draw(Canvas g, double count) {
        if (type < 3) {
            double targVelX = Math.cos(count / 32);
            double targVelY = Math.sin(count / 32);

            velX = (int) (500 * targVelX);
            velY = (int) (350 * targVelY);
        }

        switch (type) {

            case 1: //target goes to left and right
                xCent = initX + velX;
                yCent = initY;
                g.drawCircle(xCent, yCent, 70, redPaint);
                g.drawCircle(xCent, yCent, 50, whitePaint);
                g.drawCircle(xCent, yCent, 30, redPaint);
                g.drawCircle(xCent, yCent, 10, whitePaint);
                break;

            case 2: //target goes up and down
                xCent = initX;
                yCent = initY + velY;
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
                g.drawCircle(xCent, yCent + 2*hitCount, hitCount, blackPaint);
                blackPaint.setAlpha((int)(2.5*hitCount)); //gets darker
                hitCount++;

                if (hitCount >= 101) { //begins second phase of explosion
                    yCent += 2*hitCount;
                    type = 4;
                }
                break;

            case 4: //explosion leaving
                g.drawCircle(xCent, yCent + 2*(101 - hitCount), hitCount, blackPaint);
                blackPaint.setAlpha((int)(2.5*hitCount)); //gets lighter
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

    public void checkHit(CannonBall ball)
    {
        if( type < 3 && ball.getxPos() - xCent <= 50 && ball.getxPos() - xCent >= -50 &&
                ball.getyPos() - yCent <= 50 && ball.getyPos() - yCent >= -50)
        {
          type = 3;
            hit = true;
        }
    }
}
