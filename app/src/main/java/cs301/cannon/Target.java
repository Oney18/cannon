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
    private Paint whitePaint;
    private Paint blackPaint;
    private int initX;
    private int initY;
    private int xCent;
    private int yCent;
    private int type;
    private int hitCount; //count when hit
    private int velX;
    private int velY;

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
    }

    public void draw(Canvas g, double count) {
        if (type < 3) {
            double targVelX = Math.cos(count / 32);
            double targVelY = Math.sin(count / 32);

            velX = (int) (500 * targVelX);
            velY = (int) (350 * targVelY);
        }

        switch (type) {

            case 1:
                xCent = initX + velX;
                yCent = initY;
                g.drawCircle(xCent, yCent, 70, redPaint);
                g.drawCircle(xCent, yCent, 50, whitePaint);
                g.drawCircle(xCent, yCent, 30, redPaint);
                g.drawCircle(xCent, yCent, 10, whitePaint);
                break;

            case 2:
                xCent = initX;
                yCent = initY + velY;
                g.drawCircle(xCent, yCent, 70, redPaint);
                g.drawCircle(xCent, yCent, 50, whitePaint);
                g.drawCircle(xCent, yCent, 30, redPaint);
                g.drawCircle(xCent, yCent, 10, whitePaint);
                break;

            case 3:
                g.drawCircle(xCent, yCent, 70, redPaint);
                g.drawCircle(xCent, yCent, 50, whitePaint);
                g.drawCircle(xCent, yCent, 30, redPaint);
                g.drawCircle(xCent, yCent, 10, whitePaint);
                g.drawCircle(xCent, yCent, hitCount, blackPaint);
                hitCount++;
                if (hitCount == 101) {
                    type = 4;
                }
                break;

            case 4:
                //this is gone
        }
    }



    public void isHit(CannonBall ball)
    {
        if(ball.getxPos() - xCent <= 30 && ball.getxPos() - xCent >= 30 &&
                ball.getyPos() - yCent <= 30 && ball.getyPos() - yCent >= 30)
        {
          type = 3;
        }
    }
}
