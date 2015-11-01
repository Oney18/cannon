package cs301.cannon;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

/**
 * CannonMainActivity
 * 
 * This is the activity for the cannon animation. It creates a AnimationCanvas
 * containing a particular Animator object
 *
 * TO GRADER: EC Options Included:
 *  	Targets have animated explosion upon hit (5pt)
 *  	Arbitrary number of cannonballs on screen at once (5pt)
 *  	Balls bounce realistically on the ground (5pt)
 *
 * @author Andrew Nuxoll
 * @author Jarrett Oney
 * @version October 2015
 * 
 */
public class CannonMainActivity extends Activity {

	/**
	 * creates an AnimationCanvas containing a CannonAnimator.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cannon_main);

		// Create an animation canvas and place it in the main layout
		Animator canAnim = new CannonAnimator();
		AnimationCanvas myCanvas = new AnimationCanvas(this, canAnim);
		LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.topLevelLayout);
		mainLayout.addView(myCanvas);

	}

	/**
	 * This is the default behavior (empty menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cannon_main, menu);
		return true;
	}
}
