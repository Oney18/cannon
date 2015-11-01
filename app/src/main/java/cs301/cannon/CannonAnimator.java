package cs301.cannon;

import android.graphics.*;
import android.view.MotionEvent;

import java.util.ArrayList;


/**
 * CannonAnimator
 *
 * Class that represents the animation of the cannon game
 *
 * TO GRADER: EC Options Included:
 *      Targets have animated explosion upon hit (5pt)
 *      Targets Move (5pt)
 *      Arbitrary number of cannonballs on screen at once (5pt)
 *      Balls bounce realistically on the ground (5pt)
 * 
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @author Jarrett Oney
 *
 * @version October 2015
 */
public class CannonAnimator implements Animator {

	// instance variables
	private double count = 0; // counts the number of logical clock ticks

	private int fireX; //left x coord of fire button
	private int fireY; //top y coord of fire button
	private int xSize; //width of canvas
	private int ySize; //height of canvas
    private int xBarL; //left x coord of the wind bar
    private int xBarR; //right x coord of the wind bar
	private int shots = 0; //shot count
    private double wind; //wind for the balls

    //the fill rectangle left and right coords for widn bar
    private int windRectFillL = xSize/2;
    private int windRectFillR = xSize/2;

    //the objects animated
	private Target topTarg;
	private Target sideTarg;
	private Cannon cannon;
	private ArrayList<CannonBall> balls;
	
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

        //used throughout class
        xSize = g.getWidth();
        ySize = g.getHeight();

        //used to draw wind bar
        xBarL = xSize/2 - 500;
        xBarR = xSize/2 + 500;

		//used to determine if the fire button is pressed
		this.fireX = xSize - 300;
		this.fireY = ySize - 300;

        //set targs
        if(topTarg == null) { //everything set at once so if one is null, all are null
            topTarg = new Target(xSize - xSize / 2, 100, 1);
            sideTarg = new Target(xSize - 300, ySize - ySize / 2, 2);
            cannon = new Cannon(ySize);
            balls = new ArrayList<CannonBall>();
			wind = 0;
			CannonBall.setWind(wind);
        }

        //used for text display display
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(45);

        //displays number fo shots taken, quasi-score
        g.drawText("Shots: " + shots, xSize - 325, 100, textPaint);

        //displays which targets are hit
        g.drawText("Hit:", xSize - 275, 170, textPaint);

        if(topTarg.isHit()) //displays the top target if is hit
        {
            g.drawText("Top Target", xSize - 345, 310, textPaint);
        }

        if(sideTarg.isHit()) //displays the side target if is hit
        {
            g.drawText("Side Target", xSize - 350, 240, textPaint);
        }

		//draw fire button
		g.drawOval(xSize - 205, ySize - 205, xSize - 5, ySize - 5, redPaint);
		g.drawText("FIRE!", xSize - 148, ySize - 92, textPaint);

        //draw wind bar
        g.drawRect(xBarL, ySize - 150, xBarR, ySize - 50, greyPaint);
        g.drawRect(windRectFillL, ySize - 150, windRectFillR, ySize - 50, bluePaint);
		g.drawText("Wind Bar", xSize / 2 - 90, ySize - 10, textPaint);

		//used to determine if the fire button is pressed
		this.fireX = xSize - 300;
		this.fireY = ySize - 300;

        //draw the targets
        topTarg.draw(g, count);
        sideTarg.draw(g, count);

        //draws the balls
		for(int i = 0; i < balls.size(); i++)
		{
			if(balls.get(i).getxPos() > xSize + 40 || balls.get(i).getxPos() < -40)
			{ //removes balls if off the screen
				balls.remove(i);
				i--;
			}

			if(i < balls.size() && i >= 0) { //checks all hittable objects for each ball
				balls.get(i).draw(g);
                topTarg.checkHit(balls.get(i));
                sideTarg.checkHit(balls.get(i));
                cannon.checkHit(balls.get(i));
			}
		}

        //draw the cannon
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
     * Controls the app depending on where the user touches
     *
     * @param event a MotionEvent describing the touch
     */
	public void onTouch(MotionEvent event)
	{
		if(event.getX() >= fireX && event.getY() >= fireY && !cannon.isDestroyed()) //fire button, cannon exists
		{
			if(event.getAction() == MotionEvent.ACTION_DOWN) { //prevents moving cannon when shooting
				CannonBall ball = new CannonBall(cannon.getDegrees(), ySize);
				balls.add(ball);
				shots++;
			}
		}
        else if(event.getX() >= xBarL && event.getX() <= xBarR && event.getY() >= ySize - 150) //change wind
        {
            wind = (((double) (event.getX() - xSize/2)/500)*2);

            if(event.getX() <= xSize/2) //left side touched
            {
                windRectFillL = (int) event.getX();
                windRectFillR = xSize/2;
            }
            else //right side touched
            {
                windRectFillL = xSize/2;
                windRectFillR = (int) event.getX();
            }

            CannonBall.setWind(wind); //wind set for all balls
        }
		else //change angle of cannon, no visible change if cannon destroyed
		{
			int deg = (int) Math.toDegrees(Math.atan(((double) (ySize - event.getY())) / event.getX()));
			cannon.setDegrees(90 - deg);
		}
	}
}
