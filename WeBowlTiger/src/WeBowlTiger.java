import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class WeBowlTiger {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeBowlTiger window = new WeBowlTiger();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WeBowlTiger() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setSize(new Dimension(200, 400));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setPreferredSize(new Dimension(40, 40));
		frame.setBounds(100, 100, 450, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		int Width = 40;
		int Height = 40;
		
		BufferedImage simgPins = null;
		try {
			simgPins = ImageIO.read(new File("img\\pin-on.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image dimgPins = simgPins.getScaledInstance(Width, Height,Image.SCALE_SMOOTH);
		
		BufferedImage simgball = null;
		try {
			simgball = ImageIO.read(new File("img\\default-ball.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Image dimgball = simgball.getScaledInstance(Width, Height,Image.SCALE_SMOOTH);
		
		JLabel lblPin1 = new JLabel("1");
		lblPin1.setIcon(new ImageIcon(dimgPins));
		lblPin1.setSize(new Dimension(Width, Height));
		lblPin1.setBounds(184, 193, 34, 56);
		frame.getContentPane().add(lblPin1);

		JLabel lblPin2 = new JLabel("2");
		lblPin2.setIcon(new ImageIcon(dimgPins));
		lblPin2.setSize(new Dimension(Width, Height));
		lblPin2.setBounds(135, 142, 34, 56);
		frame.getContentPane().add(lblPin2);

		JLabel lblPin3 = new JLabel("3");
		lblPin3.setIcon(new ImageIcon(dimgPins));
		lblPin3.setSize(new Dimension(Width, Height));
		lblPin3.setBounds(232, 142, 34, 56);
		frame.getContentPane().add(lblPin3);

		JLabel lblPin4 = new JLabel("4");
		lblPin4.setIcon(new ImageIcon(dimgPins));
		lblPin4.setSize(new Dimension(Width, Height));
		lblPin4.setBounds(90, 78, 34, 56);
		frame.getContentPane().add(lblPin4);
		
		JLabel lblPin5 = new JLabel("5");
		lblPin5.setIcon(new ImageIcon(dimgPins));
		lblPin5.setSize(new Dimension(Width, Height));
		lblPin5.setBounds(184, 78, 34, 56);
		frame.getContentPane().add(lblPin5);
		
		JLabel lblPin6 = new JLabel("6");
		lblPin6.setIcon(new ImageIcon(dimgPins));
		lblPin6.setSize(new Dimension(Width, Height));
		lblPin6.setBounds(278, 78, 34, 56);
		frame.getContentPane().add(lblPin6);
		
		JLabel lblPin7 = new JLabel("7");
		lblPin7.setSize(new Dimension(Width, Height));		
		lblPin7.setIcon(new ImageIcon(dimgPins));
		lblPin7.setBounds(42, 11, 34, 56);
		frame.getContentPane().add(lblPin7);
	
		JLabel lblPin8 = new JLabel("8");
		lblPin8.setIcon(new ImageIcon(dimgPins));
		lblPin8.setSize(new Dimension(Width, Height));
		lblPin8.setBounds(153, 20, 34, 39);
		frame.getContentPane().add(lblPin8);
		
		JLabel lblPin9 = new JLabel("9");
		lblPin9.setIcon(new ImageIcon(dimgPins));
		lblPin9.setSize(new Dimension(Width, Height));
		lblPin9.setBounds(232, 11, 34, 56);
		frame.getContentPane().add(lblPin9);
		
		JLabel lblPin10 = new JLabel("10");
		lblPin10.setIcon(new ImageIcon(dimgPins));
		lblPin10.setSize(new Dimension(Width, Height));
		lblPin10.setBounds(330, 16, 34, 46);
		frame.getContentPane().add(lblPin10);
		
		JLabel lblBall = new JLabel("ball");
		lblBall.setIcon(new ImageIcon(dimgball));
		lblBall.setBounds(184, 580, 51, 46);
		frame.getContentPane().add(lblBall);
	}
}
