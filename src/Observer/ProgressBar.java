package Observer;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBar extends JProgressBar implements Runnable{

	static JProgressBar  pbar;

	static final int MY_MINIMUM = 0;

	static final int MY_MAXIMUM = 100;

	public ProgressBar() {
	    // initialize Progress Bar
	    pbar = new JProgressBar();
	    pbar.setMinimum(MY_MINIMUM);
	    pbar.setMaximum(MY_MAXIMUM);
	    pbar.setStringPainted(true);
	    // add to JPanel	    
	  }
	
	public static void updateProgressValue(int value){
		pbar.setValue(value);
	}

	@Override
	public void run() {
		add(pbar);	
		updateProgressValue(0);
	}
	  
	  
}
