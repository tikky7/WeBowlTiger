import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class MovingBall {
	private JLabel label = null;
	private JFrame frame = null;
	private int speed = 20;
	private final int boundary=360;
	private Graphics graphics = null;
	public MovingBall( JFrame refframe, JLabel reflabel, int refspeed ) {
		frame=refframe;
		label=reflabel;
		speed=refspeed;
	}
	public void draw( ) {
		assert( label != null);
		int steps=boundary/speed;
		assert(steps>0);
		graphics=frame.getContentPane().getGraphics();
		label.setVisible(false);

		frame.repaint();
		frame.setVisible(true);

		frame.paint(graphics);
		
		for( int idx=0; idx<steps; idx++ ) {
			label.setBounds(184, 610-idx*speed, 51, 46);
			label.setVisible(true);

			frame.repaint();
			frame.setVisible(true);
			frame.paint(graphics);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}
		label.setBounds(184, 610, 51, 46);
		label.setVisible(true);

		frame.repaint();
		frame.setVisible(true);
		frame.paint(graphics);
	}

}
