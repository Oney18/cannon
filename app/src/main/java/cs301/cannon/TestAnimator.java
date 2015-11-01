package cs301.cannon;

import android.graphics.*;
import android.view.MotionEvent;

import java.util.ArrayList;


/**
 * class that animates a ball repeatedly moving diagonally on
 * simple background
 * 
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @version September 2012
 */
public class TestAnimator implements Animator {

	// instance variables
	private double count = 0; // counts the number of logical clock ticks
	private boolean goBackwards = false; // whether clock is ticking backwards

	private int fireX; //coords for the fire button
	private int fireY;
	private int xSize;
	private int ySize;
    private int xBarL;
    private int xBarR;
	private int shots = 0;
    private int wind;

    private int windRectFillL = xSize/2;
    private int windRectFillR = xSize/2;

	private Target topTarg;
	private Target sideTarg;
	private Cannon cannon;
	private ArrayList<CannonBall> balls;
    private CannonBall masterBall;
	
	/**
	 * Interval between animation frames: .03 seconds (i.e., about 33 times
	 * per second).
	 * 
	 * @return the time interval between frames, in milliseconds.
	 */
	public int interval() {
		return 30;
	}
	
	/**
	 * The background color: a light blue.
	 * 
	 * @return the background color onto which we will draw the image.
	 */
	public int backgroundColor() {
		// create/return the background color
		return Color.rgb(180, 200, 255);
	}
	
	/**
	 * Action to perform on clock tick
	 * 
	 * @param g the graphics object on which to draw
	 */
	public void tick(Canvas g) {
		// bump our count either up or down by one, depending on whether
		// we are in "backwards mode".

		count++; //used for targets' pos

		//used for fire button
		Paint redPaint = new Paint();
		redPaint.setColor(Color.RED);

        //used for wind bar
        Paint bluePaint = new Paint();
        Paint greyPaint = new Paint();
        bluePaint.setColor(Color.CYAN);
        bluePaint.setAlpha(150);
        greyPaint.setColor(Color.GRAY);


		//used for magnitude display
		if(balls != null) {
			Paint textPaint = new Paint();
			textPaint.setColor(Color.BLACK);
			textPaint.setTextSize(45);
			String pow = "Shots: " + shots;
			g.drawText(pow, xSize - 300, 100, textPaint);
		}

		//used throughout class
		xSize = g.getWidth();
		ySize = g.getHeight();
        xBarL = xSize/2 - 500;
        xBarR = xSize/2 + 500;



		//draw fire button
		g.drawOval(xSize - 200, ySize - 200, xSize, ySize, redPaint);

        //draw wind bar
        g.drawRect(xBarL, ySize - 150, xBarR, ySize - 50, greyPaint);
        g.drawRect(windRectFillL, ySize - 150, windRectFillR, ySize-50, bluePaint);

		//used to determine if the button is pressed
		this.fireX = xSize - 300;
		this.fireY = ySize - 300;

		//set targs
		if(topTarg == null) { //everything set at once so if oen is null, all are null
			topTarg = new Target(xSize - xSize / 2, 100, 1);
			sideTarg = new Target(xSize - 300, ySize - ySize / 2, 2);
			cannon = new Cannon(ySize);
			balls = new ArrayList<CannonBall>();
            masterBall = new CannonBall(0,0); //used to change wind universally
		}

        topTarg.draw(g, count);
        sideTarg.draw(g, count);

		for(int i = 0; i < balls.size(); i++)
		{
			if(balls.get(i).getxPos() > xSize)
			{
				balls.remove(i);
				i--;
			}

			if(i < balls.size() && i >= 0) {
				balls.get(i).draw(g);
                topTarg.checkHit(balls.get(i));
                sideTarg.checkHit(balls.get(i));
			}

		}

		cannon.draw(g);

	}

	/**
	 * Tells that we never pause.
	 * 
	 * @return indication of whether to pause
	 */
	public boolean doPause() {
		return false;
	}

	/**
	 * Tells that we never stop the animation.
	 * 
	 * @return indication of whether to quit.
	 */
	public boolean doQuit() {
		return false;
	}
	
	/**
	 * reverse the ball's direction when the screen is tapped
	 */
	public void onTouch(MotionEvent event)
	{
		if(event.getX() >= fireX && event.getY() >= fireY )
		{
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				CannonBall ball = new CannonBall(cannon.getDegrees(), ySize);
				balls.add(ball);
				shots++;
			}
		}
        else if(event.getX() >= xBarL && event.getX() <= xBarR && event.getY() >= ySize - 150)
        {
            wind = (int) (((double) (event.getX() - xSize/2)/500)*3);

            if(event.getX() <= xSize/2)
            {
                windRectFillL = (int) event.getX();
                windRectFillR = xSize/2;
            }
            else
            {
                windRectFillL = xSize/2;
                windRectFillR = (int) event.getX();
            }

            masterBall.setWind(wind);
        }
		else
		{
			int deg = (int) Math.toDegrees(Math.atan(((double) (ySize - event.getY())) / event.getX()));
			cannon.setDegrees(90 - deg);
		}
	}
	
	

}//class TextAnimator
