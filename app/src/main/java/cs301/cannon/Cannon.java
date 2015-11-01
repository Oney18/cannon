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

    private int height; //height of the canvas

    public Cannon(int height)
    {
        this.height = height;
        top = height - 250;
        bottom = height;
        right = 55;
        left = -55;
        degrees = 45;

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
        RectF barrel = new RectF(left, top, right, bottom);
        g.save();
        g.rotate(degrees, 0, height);
        g.drawRect(barrel, greyPaint);
        g.restore();

    }
}
