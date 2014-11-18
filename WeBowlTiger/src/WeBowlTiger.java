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
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setSize(new Dimension(40, 40));
		
		BufferedImage simg = null;
		try {
			simg = ImageIO.read(new File("C:\\sprint\\WeBowlTiger\\images\\pin-on.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image dimg = simg.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(),Image.SCALE_SMOOTH);
		
		BufferedImage simgball = null;
		try {
			simgball = ImageIO.read(new File("C:\\sprint\\WeBowlTiger\\images\\default-ball.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		Image dimgball = simgball.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(),Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(dimg));
		lblNewLabel.setBounds(42, 11, 34, 56);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("New label");
		label.setIcon(new ImageIcon(dimg));
		label.setSize(new Dimension(40, 40));
		label.setBounds(153, 20, 34, 39);
		frame.getContentPane().add(label);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(dimgball));
		lblNewLabel_1.setBounds(184, 580, 51, 46);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel label_1 = new JLabel("New label");
		label_1.setIcon(new ImageIcon(dimg));
		label_1.setSize(new Dimension(40, 40));
		label_1.setBounds(232, 11, 34, 56);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("New label");
		label_2.setIcon(new ImageIcon(dimg));
		label_2.setSize(new Dimension(40, 40));
		label_2.setBounds(330, 16, 34, 46);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("New label");
		label_3.setIcon(new ImageIcon(dimg));
		label_3.setSize(new Dimension(40, 40));
		label_3.setBounds(90, 78, 34, 56);
		frame.getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("New label");
		label_4.setIcon(new ImageIcon(dimg));
		label_4.setSize(new Dimension(40, 40));
		label_4.setBounds(184, 78, 34, 56);
		frame.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("New label");
		label_5.setIcon(new ImageIcon(dimg));
		label_5.setSize(new Dimension(40, 40));
		label_5.setBounds(278, 78, 34, 56);
		frame.getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("New label");
		label_6.setIcon(new ImageIcon(dimg));
		label_6.setSize(new Dimension(40, 40));
		label_6.setBounds(135, 142, 34, 56);
		frame.getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel("New label");
		label_7.setIcon(new ImageIcon(dimg));
		label_7.setSize(new Dimension(40, 40));
		label_7.setBounds(232, 142, 34, 56);
		frame.getContentPane().add(label_7);
		
		JLabel label_8 = new JLabel("New label");
		label_8.setIcon(new ImageIcon(dimg));
		label_8.setSize(new Dimension(40, 40));
		label_8.setBounds(184, 193, 34, 56);
		frame.getContentPane().add(label_8);
	}
}
