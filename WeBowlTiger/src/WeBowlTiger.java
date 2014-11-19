import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class WeBowlTiger {

	private JFrame frame;
    private JLabel ball;
    private ArrayList<JLabel> pinsLblArray = new ArrayList<JLabel>();
    boolean firstthrow = true;
   
    boolean[] pins = new boolean[]{true,true,true,true,true,true,true,true,true,true};
    
    
    private void drawpins( boolean[] pins) {
    	for( int idx=0; idx<10; idx++) 
    	{
    		if ( pins[idx]==true )
    		{
    			pinsLblArray.get(idx).setVisible(true);
    		} 
    		else 
    		{
    			pinsLblArray.get(idx).setVisible(false);
    		}
    	}
    	Graphics graphics=frame.getContentPane().getGraphics();
    	frame.paint(graphics);
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					WeBowlTiger window = new WeBowlTiger();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public WeBowlTiger() 
	{
		initialize();
	}

	private void resetPins(boolean[] pins) throws InterruptedException
	{
		//Sleep before resetting the pins
		Thread.sleep(4000);
		
		for( int idx=0; idx<10;idx++ ) 
			pins[idx]=true;
		
		drawpins(pins);
	}
	
    public void move() throws InterruptedException  
    {
    	PinDrop pindrop = new PinDrop();
    	assert( pindrop != null );
    	
    	pindrop.getPinsStillStanding(pins);
    	
		MovingBall movingball = new MovingBall(frame,ball,60);
		movingball.draw();
		
		if ( firstthrow == true )
		{
			//We are in our first bowl attempt
			firstthrow=false;
			drawpins(pins);
		} 
		else 
		{
			//We are in our second bowl attempt
			firstthrow=true;			
			drawpins(pins);			
			
			resetPins(pins);			
		}	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setSize(new Dimension(200, 400));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setPreferredSize(new Dimension(40, 40));
		frame.setBounds(100, 100, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setCursor(Cursor.HAND_CURSOR);
		
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
		
		JLabel[] pins = new JLabel[10];
		
		for (int i = 0; i<10; i++)
		{
			pins[i] = new JLabel(Integer.toString(i+1));
			pins[i].setIcon(new ImageIcon(dimgPins));
			pins[i].setSize(new Dimension(Width, Height));
			switch (i)
			{
			//row 1
				case 0: pins[i].setBounds(180, 190, 30, 60);
					break;
			//row 2
				case 1: pins[i].setBounds(130, 140, 30, 60);
					break;
				case 2: pins[i].setBounds(230, 140, 30, 60);
					break;
			//row 3
				case 3: pins[i].setBounds(90, 90, 30, 60);
					break;
				case 4: pins[i].setBounds(180, 90, 30, 60);
					break;
				case 5: pins[i].setBounds(280, 90, 30, 60);
					break;
			//row 4
				case 6: pins[i].setBounds(40, 40, 30, 60);
					break;
				case 7: pins[i].setBounds(130, 40, 30, 60);
					break;
				case 8: pins[i].setBounds(230, 40, 30, 60);
					break;
				case 9: pins[i].setBounds(330, 40, 30, 60);
					break;
					
				default: 
					break;
			}
			
			frame.getContentPane().add(pins[i]);
			pinsLblArray.add(pins[i]);
		}
		
	
		JLabel lblBall = new JLabel("ball");
		lblBall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				try {
					move();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		lblBall.setIcon(new ImageIcon(dimgball));
		lblBall.setBounds(180, 490, 50, 40);
		frame.getContentPane().add(lblBall);
		ball=lblBall;
        
		
		JSeparator separator1 = new JSeparator();
		separator1.setOrientation(SwingConstants.VERTICAL);
		separator1.setForeground(Color.BLACK);
		separator1.setBounds(10, 11, 2, 523);
		frame.getContentPane().add(separator1);

		
		JSeparator separator2 = new JSeparator();
		separator2.setOrientation(SwingConstants.VERTICAL);
		separator2.setForeground(Color.BLACK);
		separator2.setBounds(30, 11, 2, 523);
		frame.getContentPane().add(separator2);
		
		
		JSeparator separator3 = new JSeparator();
		separator3.setOrientation(SwingConstants.VERTICAL);
		separator3.setForeground(Color.BLACK);
		separator3.setBounds(370, 11, 2, 523);
		frame.getContentPane().add(separator3);
		
		JSeparator separator4 = new JSeparator();
		separator4.setOrientation(SwingConstants.VERTICAL);
		separator4.setForeground(Color.BLACK);
		separator4.setBounds(390, 11, 2, 523);
		frame.getContentPane().add(separator4);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(518, 50, 83, 81);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
	
		JPanel panel = new JPanel();
		panel.setBounds(36, 2, 24, 26);
		panel_1.add(panel);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(3, 5, 18, 18);
		panel.add(textPane);
		textPane.setEditable(false);
		textPane.setBackground(Color.WHITE);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBackground(SystemColor.control);
		textPane_2.setBounds(10, 39, 63, 37);
		panel_1.add(textPane_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_2.setBounds(59, 2, 24, 26);
		panel_1.add(panel_2);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setBackground(Color.WHITE);
		textPane_1.setBounds(3, 5, 18, 18);
		panel_2.add(textPane_1);
		
		JLabel lblVittal = new JLabel("Vittal");
		lblVittal.setHorizontalAlignment(SwingConstants.CENTER);
		lblVittal.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblVittal.setBounds(374, 50, 134, 81);
		frame.getContentPane().add(lblVittal);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(603, 50, 83, 81);
		frame.getContentPane().add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_4.setBounds(36, 2, 24, 26);
		panel_3.add(panel_4);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setEditable(false);
		textPane_3.setBackground(Color.WHITE);
		textPane_3.setBounds(3, 5, 18, 18);
		panel_4.add(textPane_3);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setBackground(SystemColor.menu);
		textPane_4.setBounds(10, 39, 63, 37);
		panel_3.add(textPane_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_5.setBounds(59, 2, 24, 26);
		panel_3.add(panel_5);
		
		JTextPane textPane_5 = new JTextPane();
		textPane_5.setEditable(false);
		textPane_5.setBackground(Color.WHITE);
		textPane_5.setBounds(3, 5, 18, 18);
		panel_5.add(textPane_5);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(688, 50, 83, 81);
		frame.getContentPane().add(panel_6);
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_7.setBounds(36, 2, 24, 26);
		panel_6.add(panel_7);
		
		JTextPane textPane_6 = new JTextPane();
		textPane_6.setEditable(false);
		textPane_6.setBackground(Color.WHITE);
		textPane_6.setBounds(3, 5, 18, 18);
		panel_7.add(textPane_6);
		
		JTextPane textPane_7 = new JTextPane();
		textPane_7.setBackground(SystemColor.menu);
		textPane_7.setBounds(10, 39, 63, 37);
		panel_6.add(textPane_7);
		
		JPanel panel_8 = new JPanel();
		panel_8.setLayout(null);
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_8.setBounds(59, 2, 24, 26);
		panel_6.add(panel_8);
		
		JTextPane textPane_8 = new JTextPane();
		textPane_8.setEditable(false);
		textPane_8.setBackground(Color.WHITE);
		textPane_8.setBounds(3, 5, 18, 18);
		panel_8.add(textPane_8);
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_9.setBounds(773, 50, 83, 81);
		frame.getContentPane().add(panel_9);
		
		JPanel panel_10 = new JPanel();
		panel_10.setLayout(null);
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_10.setBounds(36, 2, 24, 26);
		panel_9.add(panel_10);
		
		JTextPane textPane_9 = new JTextPane();
		textPane_9.setEditable(false);
		textPane_9.setBackground(Color.WHITE);
		textPane_9.setBounds(3, 5, 18, 18);
		panel_10.add(textPane_9);
		
		JTextPane textPane_10 = new JTextPane();
		textPane_10.setBackground(SystemColor.menu);
		textPane_10.setBounds(10, 39, 63, 37);
		panel_9.add(textPane_10);
		
		JPanel panel_11 = new JPanel();
		panel_11.setLayout(null);
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_11.setBounds(59, 2, 24, 26);
		panel_9.add(panel_11);
		
		JTextPane textPane_11 = new JTextPane();
		textPane_11.setEditable(false);
		textPane_11.setBackground(Color.WHITE);
		textPane_11.setBounds(3, 5, 18, 18);
		panel_11.add(textPane_11);
		
		JPanel panel_12 = new JPanel();
		panel_12.setLayout(null);
		panel_12.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_12.setBounds(858, 50, 83, 81);
		frame.getContentPane().add(panel_12);
		
		JPanel panel_13 = new JPanel();
		panel_13.setLayout(null);
		panel_13.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_13.setBounds(36, 2, 24, 26);
		panel_12.add(panel_13);
		
		JTextPane textPane_12 = new JTextPane();
		textPane_12.setEditable(false);
		textPane_12.setBackground(Color.WHITE);
		textPane_12.setBounds(3, 5, 18, 18);
		panel_13.add(textPane_12);
		
		JTextPane textPane_13 = new JTextPane();
		textPane_13.setBackground(SystemColor.menu);
		textPane_13.setBounds(10, 39, 63, 37);
		panel_12.add(textPane_13);
		
		JPanel panel_14 = new JPanel();
		panel_14.setLayout(null);
		panel_14.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_14.setBounds(59, 2, 24, 26);
		panel_12.add(panel_14);
		
		JTextPane textPane_14 = new JTextPane();
		textPane_14.setEditable(false);
		textPane_14.setBackground(Color.WHITE);
		textPane_14.setBounds(3, 5, 18, 18);
		panel_14.add(textPane_14);
		
		JPanel panel_15 = new JPanel();
		panel_15.setLayout(null);
		panel_15.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_15.setBounds(943, 50, 83, 81);
		frame.getContentPane().add(panel_15);
		
		JPanel panel_16 = new JPanel();
		panel_16.setLayout(null);
		panel_16.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_16.setBounds(36, 2, 24, 26);
		panel_15.add(panel_16);
		
		JTextPane textPane_15 = new JTextPane();
		textPane_15.setEditable(false);
		textPane_15.setBackground(Color.WHITE);
		textPane_15.setBounds(3, 5, 18, 18);
		panel_16.add(textPane_15);
		
		JTextPane textPane_16 = new JTextPane();
		textPane_16.setBackground(SystemColor.menu);
		textPane_16.setBounds(10, 39, 63, 37);
		panel_15.add(textPane_16);
		
		JPanel panel_17 = new JPanel();
		panel_17.setLayout(null);
		panel_17.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_17.setBounds(59, 2, 24, 26);
		panel_15.add(panel_17);
		
		JTextPane textPane_17 = new JTextPane();
		textPane_17.setEditable(false);
		textPane_17.setBackground(Color.WHITE);
		textPane_17.setBounds(3, 5, 18, 18);
		panel_17.add(textPane_17);
		
		JPanel panel_18 = new JPanel();
		panel_18.setLayout(null);
		panel_18.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_18.setBounds(1027, 50, 83, 81);
		frame.getContentPane().add(panel_18);
		
		JPanel panel_19 = new JPanel();
		panel_19.setLayout(null);
		panel_19.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_19.setBounds(36, 2, 24, 26);
		panel_18.add(panel_19);
		
		JTextPane textPane_18 = new JTextPane();
		textPane_18.setEditable(false);
		textPane_18.setBackground(Color.WHITE);
		textPane_18.setBounds(3, 5, 18, 18);
		panel_19.add(textPane_18);
		
		JTextPane textPane_19 = new JTextPane();
		textPane_19.setBackground(SystemColor.menu);
		textPane_19.setBounds(10, 39, 63, 37);
		panel_18.add(textPane_19);
		
		JPanel panel_20 = new JPanel();
		panel_20.setLayout(null);
		panel_20.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_20.setBounds(59, 2, 24, 26);
		panel_18.add(panel_20);
		
		JTextPane textPane_20 = new JTextPane();
		textPane_20.setEditable(false);
		textPane_20.setBackground(Color.WHITE);
		textPane_20.setBounds(3, 5, 18, 18);
		panel_20.add(textPane_20);
		
		JPanel panel_21 = new JPanel();
		panel_21.setLayout(null);
		panel_21.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_21.setBounds(1112, 50, 83, 81);
		frame.getContentPane().add(panel_21);
		
		JPanel panel_22 = new JPanel();
		panel_22.setLayout(null);
		panel_22.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_22.setBounds(36, 2, 24, 26);
		panel_21.add(panel_22);
		
		JTextPane textPane_21 = new JTextPane();
		textPane_21.setEditable(false);
		textPane_21.setBackground(Color.WHITE);
		textPane_21.setBounds(3, 5, 18, 18);
		panel_22.add(textPane_21);
		
		JTextPane textPane_22 = new JTextPane();
		textPane_22.setBackground(SystemColor.menu);
		textPane_22.setBounds(10, 39, 63, 37);
		panel_21.add(textPane_22);
		
		JPanel panel_23 = new JPanel();
		panel_23.setLayout(null);
		panel_23.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_23.setBounds(59, 2, 24, 26);
		panel_21.add(panel_23);
		
		JTextPane textPane_23 = new JTextPane();
		textPane_23.setEditable(false);
		textPane_23.setBackground(Color.WHITE);
		textPane_23.setBounds(3, 5, 18, 18);
		panel_23.add(textPane_23);
		
		JPanel panel_24 = new JPanel();
		panel_24.setLayout(null);
		panel_24.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_24.setBounds(1197, 50, 83, 81);
		frame.getContentPane().add(panel_24);
		
		JPanel panel_25 = new JPanel();
		panel_25.setLayout(null);
		panel_25.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_25.setBounds(36, 2, 24, 26);
		panel_24.add(panel_25);
		
		JTextPane textPane_24 = new JTextPane();
		textPane_24.setEditable(false);
		textPane_24.setBackground(Color.WHITE);
		textPane_24.setBounds(3, 5, 18, 18);
		panel_25.add(textPane_24);
		
		JTextPane textPane_25 = new JTextPane();
		textPane_25.setBackground(SystemColor.menu);
		textPane_25.setBounds(10, 39, 63, 37);
		panel_24.add(textPane_25);
		
		JPanel panel_26 = new JPanel();
		panel_26.setLayout(null);
		panel_26.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_26.setBounds(59, 2, 24, 26);
		panel_24.add(panel_26);
		
		JTextPane textPane_26 = new JTextPane();
		textPane_26.setEditable(false);
		textPane_26.setBackground(Color.WHITE);
		textPane_26.setBounds(3, 5, 18, 18);
		panel_26.add(textPane_26);
		
		JPanel panel_27 = new JPanel();
		panel_27.setLayout(null);
		panel_27.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_27.setBounds(1282, 50, 106, 81);
		frame.getContentPane().add(panel_27);
		
		JPanel panel_28 = new JPanel();
		panel_28.setLayout(null);
		panel_28.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_28.setBounds(36, 2, 24, 26);
		panel_27.add(panel_28);
		
		JTextPane textPane_27 = new JTextPane();
		textPane_27.setEditable(false);
		textPane_27.setBackground(Color.WHITE);
		textPane_27.setBounds(3, 5, 18, 18);
		panel_28.add(textPane_27);
		
		JTextPane textPane_28 = new JTextPane();
		textPane_28.setBackground(SystemColor.menu);
		textPane_28.setBounds(10, 39, 63, 37);
		panel_27.add(textPane_28);
		
		JPanel panel_29 = new JPanel();
		panel_29.setLayout(null);
		panel_29.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_29.setBounds(59, 2, 24, 26);
		panel_27.add(panel_29);
		
		JTextPane textPane_29 = new JTextPane();
		textPane_29.setEditable(false);
		textPane_29.setBackground(Color.WHITE);
		textPane_29.setBounds(3, 5, 18, 18);
		panel_29.add(textPane_29);
		
		JPanel panel_30 = new JPanel();
		panel_30.setLayout(null);
		panel_30.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_30.setBounds(82, 2, 24, 26);
		panel_27.add(panel_30);
		
		JTextPane textPane_30 = new JTextPane();
		textPane_30.setEditable(false);
		textPane_30.setBackground(Color.WHITE);
		textPane_30.setBounds(3, 5, 18, 18);
		panel_30.add(textPane_30);
		
		JPanel panel_31 = new JPanel();
		panel_31.setLayout(null);
		panel_31.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_31.setBounds(518, 136, 83, 81);
		frame.getContentPane().add(panel_31);
		
		JPanel panel_32 = new JPanel();
		panel_32.setLayout(null);
		panel_32.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_32.setBounds(36, 2, 24, 26);
		panel_31.add(panel_32);
		
		JTextPane textPane_31 = new JTextPane();
		textPane_31.setEditable(false);
		textPane_31.setBackground(Color.WHITE);
		textPane_31.setBounds(3, 5, 18, 18);
		panel_32.add(textPane_31);
		
		JTextPane textPane_32 = new JTextPane();
		textPane_32.setBackground(SystemColor.menu);
		textPane_32.setBounds(10, 39, 63, 37);
		panel_31.add(textPane_32);
		
		JPanel panel_33 = new JPanel();
		panel_33.setLayout(null);
		panel_33.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_33.setBounds(59, 2, 24, 26);
		panel_31.add(panel_33);
		
		JTextPane textPane_33 = new JTextPane();
		textPane_33.setEditable(false);
		textPane_33.setBackground(Color.WHITE);
		textPane_33.setBounds(3, 5, 18, 18);
		panel_33.add(textPane_33);
		
		JLabel lblSerkan = new JLabel("Serkan");
		lblSerkan.setHorizontalAlignment(SwingConstants.CENTER);
		lblSerkan.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblSerkan.setBounds(374, 136, 134, 81);
		frame.getContentPane().add(lblSerkan);
		
		JPanel panel_34 = new JPanel();
		panel_34.setLayout(null);
		panel_34.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_34.setBounds(603, 136, 83, 81);
		frame.getContentPane().add(panel_34);
		
		JPanel panel_35 = new JPanel();
		panel_35.setLayout(null);
		panel_35.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_35.setBounds(36, 2, 24, 26);
		panel_34.add(panel_35);
		
		JTextPane textPane_34 = new JTextPane();
		textPane_34.setEditable(false);
		textPane_34.setBackground(Color.WHITE);
		textPane_34.setBounds(3, 5, 18, 18);
		panel_35.add(textPane_34);
		
		JTextPane textPane_35 = new JTextPane();
		textPane_35.setBackground(SystemColor.menu);
		textPane_35.setBounds(10, 39, 63, 37);
		panel_34.add(textPane_35);
		
		JPanel panel_36 = new JPanel();
		panel_36.setLayout(null);
		panel_36.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_36.setBounds(59, 2, 24, 26);
		panel_34.add(panel_36);
		
		JTextPane textPane_36 = new JTextPane();
		textPane_36.setEditable(false);
		textPane_36.setBackground(Color.WHITE);
		textPane_36.setBounds(3, 5, 18, 18);
		panel_36.add(textPane_36);
		
		JPanel panel_37 = new JPanel();
		panel_37.setLayout(null);
		panel_37.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_37.setBounds(688, 136, 83, 81);
		frame.getContentPane().add(panel_37);
		
		JPanel panel_38 = new JPanel();
		panel_38.setLayout(null);
		panel_38.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_38.setBounds(36, 2, 24, 26);
		panel_37.add(panel_38);
		
		JTextPane textPane_37 = new JTextPane();
		textPane_37.setEditable(false);
		textPane_37.setBackground(Color.WHITE);
		textPane_37.setBounds(3, 5, 18, 18);
		panel_38.add(textPane_37);
		
		JTextPane textPane_38 = new JTextPane();
		textPane_38.setBackground(SystemColor.menu);
		textPane_38.setBounds(10, 39, 63, 37);
		panel_37.add(textPane_38);
		
		JPanel panel_39 = new JPanel();
		panel_39.setLayout(null);
		panel_39.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_39.setBounds(59, 2, 24, 26);
		panel_37.add(panel_39);
		
		JTextPane textPane_39 = new JTextPane();
		textPane_39.setEditable(false);
		textPane_39.setBackground(Color.WHITE);
		textPane_39.setBounds(3, 5, 18, 18);
		panel_39.add(textPane_39);
		
		JPanel panel_40 = new JPanel();
		panel_40.setLayout(null);
		panel_40.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_40.setBounds(773, 136, 83, 81);
		frame.getContentPane().add(panel_40);
		
		JPanel panel_41 = new JPanel();
		panel_41.setLayout(null);
		panel_41.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_41.setBounds(36, 2, 24, 26);
		panel_40.add(panel_41);
		
		JTextPane textPane_40 = new JTextPane();
		textPane_40.setEditable(false);
		textPane_40.setBackground(Color.WHITE);
		textPane_40.setBounds(3, 5, 18, 18);
		panel_41.add(textPane_40);
		
		JTextPane textPane_41 = new JTextPane();
		textPane_41.setBackground(SystemColor.menu);
		textPane_41.setBounds(10, 39, 63, 37);
		panel_40.add(textPane_41);
		
		JPanel panel_42 = new JPanel();
		panel_42.setLayout(null);
		panel_42.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_42.setBounds(59, 2, 24, 26);
		panel_40.add(panel_42);
		
		JTextPane textPane_42 = new JTextPane();
		textPane_42.setEditable(false);
		textPane_42.setBackground(Color.WHITE);
		textPane_42.setBounds(3, 5, 18, 18);
		panel_42.add(textPane_42);
		
		JPanel panel_43 = new JPanel();
		panel_43.setLayout(null);
		panel_43.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_43.setBounds(858, 136, 83, 81);
		frame.getContentPane().add(panel_43);
		
		JPanel panel_44 = new JPanel();
		panel_44.setLayout(null);
		panel_44.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_44.setBounds(36, 2, 24, 26);
		panel_43.add(panel_44);
		
		JTextPane textPane_43 = new JTextPane();
		textPane_43.setEditable(false);
		textPane_43.setBackground(Color.WHITE);
		textPane_43.setBounds(3, 5, 18, 18);
		panel_44.add(textPane_43);
		
		JTextPane textPane_44 = new JTextPane();
		textPane_44.setBackground(SystemColor.menu);
		textPane_44.setBounds(10, 39, 63, 37);
		panel_43.add(textPane_44);
		
		JPanel panel_45 = new JPanel();
		panel_45.setLayout(null);
		panel_45.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_45.setBounds(59, 2, 24, 26);
		panel_43.add(panel_45);
		
		JTextPane textPane_45 = new JTextPane();
		textPane_45.setEditable(false);
		textPane_45.setBackground(Color.WHITE);
		textPane_45.setBounds(3, 5, 18, 18);
		panel_45.add(textPane_45);
		
		JPanel panel_46 = new JPanel();
		panel_46.setLayout(null);
		panel_46.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_46.setBounds(943, 136, 83, 81);
		frame.getContentPane().add(panel_46);
		
		JPanel panel_47 = new JPanel();
		panel_47.setLayout(null);
		panel_47.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_47.setBounds(36, 2, 24, 26);
		panel_46.add(panel_47);
		
		JTextPane textPane_46 = new JTextPane();
		textPane_46.setEditable(false);
		textPane_46.setBackground(Color.WHITE);
		textPane_46.setBounds(3, 5, 18, 18);
		panel_47.add(textPane_46);
		
		JTextPane textPane_47 = new JTextPane();
		textPane_47.setBackground(SystemColor.menu);
		textPane_47.setBounds(10, 39, 63, 37);
		panel_46.add(textPane_47);
		
		JPanel panel_48 = new JPanel();
		panel_48.setLayout(null);
		panel_48.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_48.setBounds(59, 2, 24, 26);
		panel_46.add(panel_48);
		
		JTextPane textPane_48 = new JTextPane();
		textPane_48.setEditable(false);
		textPane_48.setBackground(Color.WHITE);
		textPane_48.setBounds(3, 5, 18, 18);
		panel_48.add(textPane_48);
		
		JPanel panel_49 = new JPanel();
		panel_49.setLayout(null);
		panel_49.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_49.setBounds(1027, 136, 83, 81);
		frame.getContentPane().add(panel_49);
		
		JPanel panel_50 = new JPanel();
		panel_50.setLayout(null);
		panel_50.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_50.setBounds(36, 2, 24, 26);
		panel_49.add(panel_50);
		
		JTextPane textPane_49 = new JTextPane();
		textPane_49.setEditable(false);
		textPane_49.setBackground(Color.WHITE);
		textPane_49.setBounds(3, 5, 18, 18);
		panel_50.add(textPane_49);
		
		JTextPane textPane_50 = new JTextPane();
		textPane_50.setBackground(SystemColor.menu);
		textPane_50.setBounds(10, 39, 63, 37);
		panel_49.add(textPane_50);
		
		JPanel panel_51 = new JPanel();
		panel_51.setLayout(null);
		panel_51.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_51.setBounds(59, 2, 24, 26);
		panel_49.add(panel_51);
		
		JTextPane textPane_51 = new JTextPane();
		textPane_51.setEditable(false);
		textPane_51.setBackground(Color.WHITE);
		textPane_51.setBounds(3, 5, 18, 18);
		panel_51.add(textPane_51);
		
		JPanel panel_52 = new JPanel();
		panel_52.setLayout(null);
		panel_52.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_52.setBounds(1112, 136, 83, 81);
		frame.getContentPane().add(panel_52);
		
		JPanel panel_53 = new JPanel();
		panel_53.setLayout(null);
		panel_53.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_53.setBounds(36, 2, 24, 26);
		panel_52.add(panel_53);
		
		JTextPane textPane_52 = new JTextPane();
		textPane_52.setEditable(false);
		textPane_52.setBackground(Color.WHITE);
		textPane_52.setBounds(3, 5, 18, 18);
		panel_53.add(textPane_52);
		
		JTextPane textPane_53 = new JTextPane();
		textPane_53.setBackground(SystemColor.menu);
		textPane_53.setBounds(10, 39, 63, 37);
		panel_52.add(textPane_53);
		
		JPanel panel_54 = new JPanel();
		panel_54.setLayout(null);
		panel_54.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_54.setBounds(59, 2, 24, 26);
		panel_52.add(panel_54);
		
		JTextPane textPane_54 = new JTextPane();
		textPane_54.setEditable(false);
		textPane_54.setBackground(Color.WHITE);
		textPane_54.setBounds(3, 5, 18, 18);
		panel_54.add(textPane_54);
		
		JPanel panel_55 = new JPanel();
		panel_55.setLayout(null);
		panel_55.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_55.setBounds(1197, 136, 83, 81);
		frame.getContentPane().add(panel_55);
		
		JPanel panel_56 = new JPanel();
		panel_56.setLayout(null);
		panel_56.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_56.setBounds(36, 2, 24, 26);
		panel_55.add(panel_56);
		
		JTextPane textPane_55 = new JTextPane();
		textPane_55.setEditable(false);
		textPane_55.setBackground(Color.WHITE);
		textPane_55.setBounds(3, 5, 18, 18);
		panel_56.add(textPane_55);
		
		JTextPane textPane_56 = new JTextPane();
		textPane_56.setBackground(SystemColor.menu);
		textPane_56.setBounds(10, 39, 63, 37);
		panel_55.add(textPane_56);
		
		JPanel panel_57 = new JPanel();
		panel_57.setLayout(null);
		panel_57.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_57.setBounds(59, 2, 24, 26);
		panel_55.add(panel_57);
		
		JTextPane textPane_57 = new JTextPane();
		textPane_57.setEditable(false);
		textPane_57.setBackground(Color.WHITE);
		textPane_57.setBounds(3, 5, 18, 18);
		panel_57.add(textPane_57);
		
		JPanel panel_58 = new JPanel();
		panel_58.setLayout(null);
		panel_58.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_58.setBounds(1282, 136, 106, 81);
		frame.getContentPane().add(panel_58);
		
		JPanel panel_59 = new JPanel();
		panel_59.setLayout(null);
		panel_59.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_59.setBounds(36, 2, 24, 26);
		panel_58.add(panel_59);
		
		JTextPane textPane_58 = new JTextPane();
		textPane_58.setEditable(false);
		textPane_58.setBackground(Color.WHITE);
		textPane_58.setBounds(3, 5, 18, 18);
		panel_59.add(textPane_58);
		
		JTextPane textPane_59 = new JTextPane();
		textPane_59.setBackground(SystemColor.menu);
		textPane_59.setBounds(10, 39, 63, 37);
		panel_58.add(textPane_59);
		
		JPanel panel_60 = new JPanel();
		panel_60.setLayout(null);
		panel_60.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_60.setBounds(59, 2, 24, 26);
		panel_58.add(panel_60);
		
		JTextPane textPane_60 = new JTextPane();
		textPane_60.setEditable(false);
		textPane_60.setBackground(Color.WHITE);
		textPane_60.setBounds(3, 5, 18, 18);
		panel_60.add(textPane_60);
		
		JPanel panel_61 = new JPanel();
		panel_61.setLayout(null);
		panel_61.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_61.setBounds(82, 2, 24, 26);
		panel_58.add(panel_61);
		
		JTextPane textPane_61 = new JTextPane();
		textPane_61.setEditable(false);
		textPane_61.setBackground(Color.WHITE);
		textPane_61.setBounds(3, 5, 18, 18);
		panel_61.add(textPane_61);
		
		JPanel panel_62 = new JPanel();
		panel_62.setLayout(null);
		panel_62.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_62.setBounds(518, 222, 83, 81);
		frame.getContentPane().add(panel_62);
		
		JPanel panel_63 = new JPanel();
		panel_63.setLayout(null);
		panel_63.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_63.setBounds(36, 2, 24, 26);
		panel_62.add(panel_63);
		
		JTextPane textPane_62 = new JTextPane();
		textPane_62.setEditable(false);
		textPane_62.setBackground(Color.WHITE);
		textPane_62.setBounds(3, 5, 18, 18);
		panel_63.add(textPane_62);
		
		JTextPane textPane_63 = new JTextPane();
		textPane_63.setBackground(SystemColor.menu);
		textPane_63.setBounds(10, 39, 63, 37);
		panel_62.add(textPane_63);
		
		JPanel panel_64 = new JPanel();
		panel_64.setLayout(null);
		panel_64.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_64.setBounds(59, 2, 24, 26);
		panel_62.add(panel_64);
		
		JTextPane textPane_64 = new JTextPane();
		textPane_64.setEditable(false);
		textPane_64.setBackground(Color.WHITE);
		textPane_64.setBounds(3, 5, 18, 18);
		panel_64.add(textPane_64);
		
		JLabel lblLe = new JLabel("Le");
		lblLe.setHorizontalAlignment(SwingConstants.CENTER);
		lblLe.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblLe.setBounds(374, 222, 134, 81);
		frame.getContentPane().add(lblLe);
		
		JPanel panel_65 = new JPanel();
		panel_65.setLayout(null);
		panel_65.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_65.setBounds(603, 222, 83, 81);
		frame.getContentPane().add(panel_65);
		
		JPanel panel_66 = new JPanel();
		panel_66.setLayout(null);
		panel_66.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_66.setBounds(36, 2, 24, 26);
		panel_65.add(panel_66);
		
		JTextPane textPane_65 = new JTextPane();
		textPane_65.setEditable(false);
		textPane_65.setBackground(Color.WHITE);
		textPane_65.setBounds(3, 5, 18, 18);
		panel_66.add(textPane_65);
		
		JTextPane textPane_66 = new JTextPane();
		textPane_66.setBackground(SystemColor.menu);
		textPane_66.setBounds(10, 39, 63, 37);
		panel_65.add(textPane_66);
		
		JPanel panel_67 = new JPanel();
		panel_67.setLayout(null);
		panel_67.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_67.setBounds(59, 2, 24, 26);
		panel_65.add(panel_67);
		
		JTextPane textPane_67 = new JTextPane();
		textPane_67.setEditable(false);
		textPane_67.setBackground(Color.WHITE);
		textPane_67.setBounds(3, 5, 18, 18);
		panel_67.add(textPane_67);
		
		JPanel panel_68 = new JPanel();
		panel_68.setLayout(null);
		panel_68.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_68.setBounds(688, 222, 83, 81);
		frame.getContentPane().add(panel_68);
		
		JPanel panel_69 = new JPanel();
		panel_69.setLayout(null);
		panel_69.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_69.setBounds(36, 2, 24, 26);
		panel_68.add(panel_69);
		
		JTextPane textPane_68 = new JTextPane();
		textPane_68.setEditable(false);
		textPane_68.setBackground(Color.WHITE);
		textPane_68.setBounds(3, 5, 18, 18);
		panel_69.add(textPane_68);
		
		JTextPane textPane_69 = new JTextPane();
		textPane_69.setBackground(SystemColor.menu);
		textPane_69.setBounds(10, 39, 63, 37);
		panel_68.add(textPane_69);
		
		JPanel panel_70 = new JPanel();
		panel_70.setLayout(null);
		panel_70.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_70.setBounds(59, 2, 24, 26);
		panel_68.add(panel_70);
		
		JTextPane textPane_70 = new JTextPane();
		textPane_70.setEditable(false);
		textPane_70.setBackground(Color.WHITE);
		textPane_70.setBounds(3, 5, 18, 18);
		panel_70.add(textPane_70);
		
		JPanel panel_71 = new JPanel();
		panel_71.setLayout(null);
		panel_71.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_71.setBounds(773, 222, 83, 81);
		frame.getContentPane().add(panel_71);
		
		JPanel panel_72 = new JPanel();
		panel_72.setLayout(null);
		panel_72.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_72.setBounds(36, 2, 24, 26);
		panel_71.add(panel_72);
		
		JTextPane textPane_71 = new JTextPane();
		textPane_71.setEditable(false);
		textPane_71.setBackground(Color.WHITE);
		textPane_71.setBounds(3, 5, 18, 18);
		panel_72.add(textPane_71);
		
		JTextPane textPane_72 = new JTextPane();
		textPane_72.setBackground(SystemColor.menu);
		textPane_72.setBounds(10, 39, 63, 37);
		panel_71.add(textPane_72);
		
		JPanel panel_73 = new JPanel();
		panel_73.setLayout(null);
		panel_73.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_73.setBounds(59, 2, 24, 26);
		panel_71.add(panel_73);
		
		JTextPane textPane_73 = new JTextPane();
		textPane_73.setEditable(false);
		textPane_73.setBackground(Color.WHITE);
		textPane_73.setBounds(3, 5, 18, 18);
		panel_73.add(textPane_73);
		
		JPanel panel_74 = new JPanel();
		panel_74.setLayout(null);
		panel_74.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_74.setBounds(858, 222, 83, 81);
		frame.getContentPane().add(panel_74);
		
		JPanel panel_75 = new JPanel();
		panel_75.setLayout(null);
		panel_75.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_75.setBounds(36, 2, 24, 26);
		panel_74.add(panel_75);
		
		JTextPane textPane_74 = new JTextPane();
		textPane_74.setEditable(false);
		textPane_74.setBackground(Color.WHITE);
		textPane_74.setBounds(3, 5, 18, 18);
		panel_75.add(textPane_74);
		
		JTextPane textPane_75 = new JTextPane();
		textPane_75.setBackground(SystemColor.menu);
		textPane_75.setBounds(10, 39, 63, 37);
		panel_74.add(textPane_75);
		
		JPanel panel_76 = new JPanel();
		panel_76.setLayout(null);
		panel_76.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_76.setBounds(59, 2, 24, 26);
		panel_74.add(panel_76);
		
		JTextPane textPane_76 = new JTextPane();
		textPane_76.setEditable(false);
		textPane_76.setBackground(Color.WHITE);
		textPane_76.setBounds(3, 5, 18, 18);
		panel_76.add(textPane_76);
		
		JPanel panel_77 = new JPanel();
		panel_77.setLayout(null);
		panel_77.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_77.setBounds(943, 222, 83, 81);
		frame.getContentPane().add(panel_77);
		
		JPanel panel_78 = new JPanel();
		panel_78.setLayout(null);
		panel_78.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_78.setBounds(36, 2, 24, 26);
		panel_77.add(panel_78);
		
		JTextPane textPane_77 = new JTextPane();
		textPane_77.setEditable(false);
		textPane_77.setBackground(Color.WHITE);
		textPane_77.setBounds(3, 5, 18, 18);
		panel_78.add(textPane_77);
		
		JTextPane textPane_78 = new JTextPane();
		textPane_78.setBackground(SystemColor.menu);
		textPane_78.setBounds(10, 39, 63, 37);
		panel_77.add(textPane_78);
		
		JPanel panel_79 = new JPanel();
		panel_79.setLayout(null);
		panel_79.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_79.setBounds(59, 2, 24, 26);
		panel_77.add(panel_79);
		
		JTextPane textPane_79 = new JTextPane();
		textPane_79.setEditable(false);
		textPane_79.setBackground(Color.WHITE);
		textPane_79.setBounds(3, 5, 18, 18);
		panel_79.add(textPane_79);
		
		JPanel panel_80 = new JPanel();
		panel_80.setLayout(null);
		panel_80.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_80.setBounds(1027, 222, 83, 81);
		frame.getContentPane().add(panel_80);
		
		JPanel panel_81 = new JPanel();
		panel_81.setLayout(null);
		panel_81.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_81.setBounds(36, 2, 24, 26);
		panel_80.add(panel_81);
		
		JTextPane textPane_80 = new JTextPane();
		textPane_80.setEditable(false);
		textPane_80.setBackground(Color.WHITE);
		textPane_80.setBounds(3, 5, 18, 18);
		panel_81.add(textPane_80);
		
		JTextPane textPane_81 = new JTextPane();
		textPane_81.setBackground(SystemColor.menu);
		textPane_81.setBounds(10, 39, 63, 37);
		panel_80.add(textPane_81);
		
		JPanel panel_82 = new JPanel();
		panel_82.setLayout(null);
		panel_82.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_82.setBounds(59, 2, 24, 26);
		panel_80.add(panel_82);
		
		JTextPane textPane_82 = new JTextPane();
		textPane_82.setEditable(false);
		textPane_82.setBackground(Color.WHITE);
		textPane_82.setBounds(3, 5, 18, 18);
		panel_82.add(textPane_82);
		
		JPanel panel_83 = new JPanel();
		panel_83.setLayout(null);
		panel_83.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_83.setBounds(1112, 222, 83, 81);
		frame.getContentPane().add(panel_83);
		
		JPanel panel_84 = new JPanel();
		panel_84.setLayout(null);
		panel_84.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_84.setBounds(36, 2, 24, 26);
		panel_83.add(panel_84);
		
		JTextPane textPane_83 = new JTextPane();
		textPane_83.setEditable(false);
		textPane_83.setBackground(Color.WHITE);
		textPane_83.setBounds(3, 5, 18, 18);
		panel_84.add(textPane_83);
		
		JTextPane textPane_84 = new JTextPane();
		textPane_84.setBackground(SystemColor.menu);
		textPane_84.setBounds(10, 39, 63, 37);
		panel_83.add(textPane_84);
		
		JPanel panel_85 = new JPanel();
		panel_85.setLayout(null);
		panel_85.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_85.setBounds(59, 2, 24, 26);
		panel_83.add(panel_85);
		
		JTextPane textPane_85 = new JTextPane();
		textPane_85.setEditable(false);
		textPane_85.setBackground(Color.WHITE);
		textPane_85.setBounds(3, 5, 18, 18);
		panel_85.add(textPane_85);
		
		JPanel panel_86 = new JPanel();
		panel_86.setLayout(null);
		panel_86.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_86.setBounds(1197, 222, 83, 81);
		frame.getContentPane().add(panel_86);
		
		JPanel panel_87 = new JPanel();
		panel_87.setLayout(null);
		panel_87.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_87.setBounds(36, 2, 24, 26);
		panel_86.add(panel_87);
		
		JTextPane textPane_86 = new JTextPane();
		textPane_86.setEditable(false);
		textPane_86.setBackground(Color.WHITE);
		textPane_86.setBounds(3, 5, 18, 18);
		panel_87.add(textPane_86);
		
		JTextPane textPane_87 = new JTextPane();
		textPane_87.setBackground(SystemColor.menu);
		textPane_87.setBounds(10, 39, 63, 37);
		panel_86.add(textPane_87);
		
		JPanel panel_88 = new JPanel();
		panel_88.setLayout(null);
		panel_88.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_88.setBounds(59, 2, 24, 26);
		panel_86.add(panel_88);
		
		JTextPane textPane_88 = new JTextPane();
		textPane_88.setEditable(false);
		textPane_88.setBackground(Color.WHITE);
		textPane_88.setBounds(3, 5, 18, 18);
		panel_88.add(textPane_88);
		
		JPanel panel_89 = new JPanel();
		panel_89.setLayout(null);
		panel_89.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_89.setBounds(1282, 222, 106, 81);
		frame.getContentPane().add(panel_89);
		
		JPanel panel_90 = new JPanel();
		panel_90.setLayout(null);
		panel_90.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_90.setBounds(36, 2, 24, 26);
		panel_89.add(panel_90);
		
		JTextPane textPane_89 = new JTextPane();
		textPane_89.setEditable(false);
		textPane_89.setBackground(Color.WHITE);
		textPane_89.setBounds(3, 5, 18, 18);
		panel_90.add(textPane_89);
		
		JTextPane textPane_90 = new JTextPane();
		textPane_90.setBackground(SystemColor.menu);
		textPane_90.setBounds(10, 39, 63, 37);
		panel_89.add(textPane_90);
		
		JPanel panel_91 = new JPanel();
		panel_91.setLayout(null);
		panel_91.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_91.setBounds(59, 2, 24, 26);
		panel_89.add(panel_91);
		
		JTextPane textPane_91 = new JTextPane();
		textPane_91.setEditable(false);
		textPane_91.setBackground(Color.WHITE);
		textPane_91.setBounds(3, 5, 18, 18);
		panel_91.add(textPane_91);
		
		JPanel panel_92 = new JPanel();
		panel_92.setLayout(null);
		panel_92.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_92.setBounds(82, 2, 24, 26);
		panel_89.add(panel_92);
		
		JTextPane textPane_92 = new JTextPane();
		textPane_92.setEditable(false);
		textPane_92.setBackground(Color.WHITE);
		textPane_92.setBounds(3, 5, 18, 18);
		panel_92.add(textPane_92);
		
		JPanel panel_93 = new JPanel();
		panel_93.setLayout(null);
		panel_93.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_93.setBounds(518, 308, 83, 81);
		frame.getContentPane().add(panel_93);
		
		JPanel panel_94 = new JPanel();
		panel_94.setLayout(null);
		panel_94.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_94.setBounds(36, 2, 24, 26);
		panel_93.add(panel_94);
		
		JTextPane textPane_93 = new JTextPane();
		textPane_93.setEditable(false);
		textPane_93.setBackground(Color.WHITE);
		textPane_93.setBounds(3, 5, 18, 18);
		panel_94.add(textPane_93);
		
		JTextPane textPane_94 = new JTextPane();
		textPane_94.setBackground(SystemColor.menu);
		textPane_94.setBounds(10, 39, 63, 37);
		panel_93.add(textPane_94);
		
		JPanel panel_95 = new JPanel();
		panel_95.setLayout(null);
		panel_95.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_95.setBounds(59, 2, 24, 26);
		panel_93.add(panel_95);
		
		JTextPane textPane_95 = new JTextPane();
		textPane_95.setEditable(false);
		textPane_95.setBackground(Color.WHITE);
		textPane_95.setBounds(3, 5, 18, 18);
		panel_95.add(textPane_95);
		
		JLabel lblEloy = new JLabel("Eloy");
		lblEloy.setHorizontalAlignment(SwingConstants.CENTER);
		lblEloy.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblEloy.setBounds(374, 308, 134, 81);
		frame.getContentPane().add(lblEloy);
		
		JPanel panel_96 = new JPanel();
		panel_96.setLayout(null);
		panel_96.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_96.setBounds(603, 308, 83, 81);
		frame.getContentPane().add(panel_96);
		
		JPanel panel_97 = new JPanel();
		panel_97.setLayout(null);
		panel_97.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_97.setBounds(36, 2, 24, 26);
		panel_96.add(panel_97);
		
		JTextPane textPane_96 = new JTextPane();
		textPane_96.setEditable(false);
		textPane_96.setBackground(Color.WHITE);
		textPane_96.setBounds(3, 5, 18, 18);
		panel_97.add(textPane_96);
		
		JTextPane textPane_97 = new JTextPane();
		textPane_97.setBackground(SystemColor.menu);
		textPane_97.setBounds(10, 39, 63, 37);
		panel_96.add(textPane_97);
		
		JPanel panel_98 = new JPanel();
		panel_98.setLayout(null);
		panel_98.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_98.setBounds(59, 2, 24, 26);
		panel_96.add(panel_98);
		
		JTextPane textPane_98 = new JTextPane();
		textPane_98.setEditable(false);
		textPane_98.setBackground(Color.WHITE);
		textPane_98.setBounds(3, 5, 18, 18);
		panel_98.add(textPane_98);
		
		JPanel panel_99 = new JPanel();
		panel_99.setLayout(null);
		panel_99.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_99.setBounds(688, 308, 83, 81);
		frame.getContentPane().add(panel_99);
		
		JPanel panel_100 = new JPanel();
		panel_100.setLayout(null);
		panel_100.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_100.setBounds(36, 2, 24, 26);
		panel_99.add(panel_100);
		
		JTextPane textPane_99 = new JTextPane();
		textPane_99.setEditable(false);
		textPane_99.setBackground(Color.WHITE);
		textPane_99.setBounds(3, 5, 18, 18);
		panel_100.add(textPane_99);
		
		JTextPane textPane_100 = new JTextPane();
		textPane_100.setBackground(SystemColor.menu);
		textPane_100.setBounds(10, 39, 63, 37);
		panel_99.add(textPane_100);
		
		JPanel panel_101 = new JPanel();
		panel_101.setLayout(null);
		panel_101.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_101.setBounds(59, 2, 24, 26);
		panel_99.add(panel_101);
		
		JTextPane textPane_101 = new JTextPane();
		textPane_101.setEditable(false);
		textPane_101.setBackground(Color.WHITE);
		textPane_101.setBounds(3, 5, 18, 18);
		panel_101.add(textPane_101);
		
		JPanel panel_102 = new JPanel();
		panel_102.setLayout(null);
		panel_102.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_102.setBounds(773, 308, 83, 81);
		frame.getContentPane().add(panel_102);
		
		JPanel panel_103 = new JPanel();
		panel_103.setLayout(null);
		panel_103.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_103.setBounds(36, 2, 24, 26);
		panel_102.add(panel_103);
		
		JTextPane textPane_102 = new JTextPane();
		textPane_102.setEditable(false);
		textPane_102.setBackground(Color.WHITE);
		textPane_102.setBounds(3, 5, 18, 18);
		panel_103.add(textPane_102);
		
		JTextPane textPane_103 = new JTextPane();
		textPane_103.setBackground(SystemColor.menu);
		textPane_103.setBounds(10, 39, 63, 37);
		panel_102.add(textPane_103);
		
		JPanel panel_104 = new JPanel();
		panel_104.setLayout(null);
		panel_104.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_104.setBounds(59, 2, 24, 26);
		panel_102.add(panel_104);
		
		JTextPane textPane_104 = new JTextPane();
		textPane_104.setEditable(false);
		textPane_104.setBackground(Color.WHITE);
		textPane_104.setBounds(3, 5, 18, 18);
		panel_104.add(textPane_104);
		
		JPanel panel_105 = new JPanel();
		panel_105.setLayout(null);
		panel_105.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_105.setBounds(858, 308, 83, 81);
		frame.getContentPane().add(panel_105);
		
		JPanel panel_106 = new JPanel();
		panel_106.setLayout(null);
		panel_106.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_106.setBounds(36, 2, 24, 26);
		panel_105.add(panel_106);
		
		JTextPane textPane_105 = new JTextPane();
		textPane_105.setEditable(false);
		textPane_105.setBackground(Color.WHITE);
		textPane_105.setBounds(3, 5, 18, 18);
		panel_106.add(textPane_105);
		
		JTextPane textPane_106 = new JTextPane();
		textPane_106.setBackground(SystemColor.menu);
		textPane_106.setBounds(10, 39, 63, 37);
		panel_105.add(textPane_106);
		
		JPanel panel_107 = new JPanel();
		panel_107.setLayout(null);
		panel_107.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_107.setBounds(59, 2, 24, 26);
		panel_105.add(panel_107);
		
		JTextPane textPane_107 = new JTextPane();
		textPane_107.setEditable(false);
		textPane_107.setBackground(Color.WHITE);
		textPane_107.setBounds(3, 5, 18, 18);
		panel_107.add(textPane_107);
		
		JPanel panel_108 = new JPanel();
		panel_108.setLayout(null);
		panel_108.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_108.setBounds(943, 308, 83, 81);
		frame.getContentPane().add(panel_108);
		
		JPanel panel_109 = new JPanel();
		panel_109.setLayout(null);
		panel_109.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_109.setBounds(36, 2, 24, 26);
		panel_108.add(panel_109);
		
		JTextPane textPane_108 = new JTextPane();
		textPane_108.setEditable(false);
		textPane_108.setBackground(Color.WHITE);
		textPane_108.setBounds(3, 5, 18, 18);
		panel_109.add(textPane_108);
		
		JTextPane textPane_109 = new JTextPane();
		textPane_109.setBackground(SystemColor.menu);
		textPane_109.setBounds(10, 39, 63, 37);
		panel_108.add(textPane_109);
		
		JPanel panel_110 = new JPanel();
		panel_110.setLayout(null);
		panel_110.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_110.setBounds(59, 2, 24, 26);
		panel_108.add(panel_110);
		
		JTextPane textPane_110 = new JTextPane();
		textPane_110.setEditable(false);
		textPane_110.setBackground(Color.WHITE);
		textPane_110.setBounds(3, 5, 18, 18);
		panel_110.add(textPane_110);
		
		JPanel panel_111 = new JPanel();
		panel_111.setLayout(null);
		panel_111.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_111.setBounds(1027, 308, 83, 81);
		frame.getContentPane().add(panel_111);
		
		JPanel panel_112 = new JPanel();
		panel_112.setLayout(null);
		panel_112.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_112.setBounds(36, 2, 24, 26);
		panel_111.add(panel_112);
		
		JTextPane textPane_111 = new JTextPane();
		textPane_111.setEditable(false);
		textPane_111.setBackground(Color.WHITE);
		textPane_111.setBounds(3, 5, 18, 18);
		panel_112.add(textPane_111);
		
		JTextPane textPane_112 = new JTextPane();
		textPane_112.setBackground(SystemColor.menu);
		textPane_112.setBounds(10, 39, 63, 37);
		panel_111.add(textPane_112);
		
		JPanel panel_113 = new JPanel();
		panel_113.setLayout(null);
		panel_113.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_113.setBounds(59, 2, 24, 26);
		panel_111.add(panel_113);
		
		JTextPane textPane_113 = new JTextPane();
		textPane_113.setEditable(false);
		textPane_113.setBackground(Color.WHITE);
		textPane_113.setBounds(3, 5, 18, 18);
		panel_113.add(textPane_113);
		
		JPanel panel_114 = new JPanel();
		panel_114.setLayout(null);
		panel_114.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_114.setBounds(1112, 308, 83, 81);
		frame.getContentPane().add(panel_114);
		
		JPanel panel_115 = new JPanel();
		panel_115.setLayout(null);
		panel_115.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_115.setBounds(36, 2, 24, 26);
		panel_114.add(panel_115);
		
		JTextPane textPane_114 = new JTextPane();
		textPane_114.setEditable(false);
		textPane_114.setBackground(Color.WHITE);
		textPane_114.setBounds(3, 5, 18, 18);
		panel_115.add(textPane_114);
		
		JTextPane textPane_115 = new JTextPane();
		textPane_115.setBackground(SystemColor.menu);
		textPane_115.setBounds(10, 39, 63, 37);
		panel_114.add(textPane_115);
		
		JPanel panel_116 = new JPanel();
		panel_116.setLayout(null);
		panel_116.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_116.setBounds(59, 2, 24, 26);
		panel_114.add(panel_116);
		
		JTextPane textPane_116 = new JTextPane();
		textPane_116.setEditable(false);
		textPane_116.setBackground(Color.WHITE);
		textPane_116.setBounds(3, 5, 18, 18);
		panel_116.add(textPane_116);
		
		JPanel panel_117 = new JPanel();
		panel_117.setLayout(null);
		panel_117.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_117.setBounds(1197, 308, 83, 81);
		frame.getContentPane().add(panel_117);
		
		JPanel panel_118 = new JPanel();
		panel_118.setLayout(null);
		panel_118.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_118.setBounds(36, 2, 24, 26);
		panel_117.add(panel_118);
		
		JTextPane textPane_117 = new JTextPane();
		textPane_117.setEditable(false);
		textPane_117.setBackground(Color.WHITE);
		textPane_117.setBounds(3, 5, 18, 18);
		panel_118.add(textPane_117);
		
		JTextPane textPane_118 = new JTextPane();
		textPane_118.setBackground(SystemColor.menu);
		textPane_118.setBounds(10, 39, 63, 37);
		panel_117.add(textPane_118);
		
		JPanel panel_119 = new JPanel();
		panel_119.setLayout(null);
		panel_119.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_119.setBounds(59, 2, 24, 26);
		panel_117.add(panel_119);
		
		JTextPane textPane_119 = new JTextPane();
		textPane_119.setEditable(false);
		textPane_119.setBackground(Color.WHITE);
		textPane_119.setBounds(3, 5, 18, 18);
		panel_119.add(textPane_119);
		
		JPanel panel_120 = new JPanel();
		panel_120.setLayout(null);
		panel_120.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_120.setBounds(1282, 308, 106, 81);
		frame.getContentPane().add(panel_120);
		
		JPanel panel_121 = new JPanel();
		panel_121.setLayout(null);
		panel_121.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_121.setBounds(36, 2, 24, 26);
		panel_120.add(panel_121);
		
		JTextPane textPane_120 = new JTextPane();
		textPane_120.setEditable(false);
		textPane_120.setBackground(Color.WHITE);
		textPane_120.setBounds(3, 5, 18, 18);
		panel_121.add(textPane_120);
		
		JTextPane textPane_121 = new JTextPane();
		textPane_121.setBackground(SystemColor.menu);
		textPane_121.setBounds(10, 39, 63, 37);
		panel_120.add(textPane_121);
		
		JPanel panel_122 = new JPanel();
		panel_122.setLayout(null);
		panel_122.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_122.setBounds(59, 2, 24, 26);
		panel_120.add(panel_122);
		
		JTextPane textPane_122 = new JTextPane();
		textPane_122.setEditable(false);
		textPane_122.setBackground(Color.WHITE);
		textPane_122.setBounds(3, 5, 18, 18);
		panel_122.add(textPane_122);
		
		JPanel panel_123 = new JPanel();
		panel_123.setLayout(null);
		panel_123.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_123.setBounds(82, 2, 24, 26);
		panel_120.add(panel_123);
		
		JTextPane textPane_123 = new JTextPane();
		textPane_123.setEditable(false);
		textPane_123.setBackground(Color.WHITE);
		textPane_123.setBounds(3, 5, 18, 18);
		panel_123.add(textPane_123);

	}
}
