import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MovingBall extends JPanel {
	private JLabel label = null;
	private JFrame frame = null;
	private int speed = 20;
	private final int boundary=300;
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

		frame.paintComponents(graphics);
		
		for( int idx=0; idx<steps; idx++ ) {
			label.setBounds(180, 490-idx*speed, 50, 40);
			label.setVisible(true);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.repaint();
			frame.setVisible(true);
			frame.paintComponents(graphics);
			
					
		}
		
		label.setBounds(180, 490, 50, 40);
		label.setVisible(true);

		frame.repaint();
		frame.setVisible(true);
		frame.paintComponents(graphics);
	}

}
