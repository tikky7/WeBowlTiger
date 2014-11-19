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
import java.lang.reflect.Field;
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
    	for( int idx=0; idx<10; idx++) {
    		
    		if ( pins[idx]==true ) {
    			pinsLblArray.get(idx).setVisible(true);
    		} else {
    			pinsLblArray.get(idx).setVisible(false);
    		}
    	}
    	Graphics graphics=frame.getContentPane().getGraphics();
    	frame.paint(graphics);
    }

    static int[][][] allFrames;
    static int currFrame = 1;
    private JTextPane user1Frame1Score;
    private JTextPane user1Frame1BallTxt1;
    private JTextPane user1Frame1BallTxt2;
    private JTextPane user1Frame2BallTxt1;
    private JTextPane user1Frame2BallTxt2;
    private JTextPane user1Frame3BallTxt1;
    private JTextPane user1Frame3BallTxt2;
    private JTextPane user1Frame4BallTxt1;
    private JTextPane user1Frame4BallTxt2;
    private JTextPane user1Frame5BallTxt1;
    private JTextPane user1Frame5BallTxt2;
    private JTextPane user1Frame6BallTxt1;
    private JTextPane user1Frame6BallTxt2;
    private JTextPane user1Frame7BallTxt1;
    private JTextPane user1Frame7BallTxt2;
    private JTextPane user1Frame8BallTxt1;
    private JTextPane user1Frame8BallTxt2;
    private JTextPane user1Frame9BallTxt1;
    private JTextPane user1Frame9BallTxt2;
    private JTextPane user1Frame10BallTxt1;
    private JTextPane user1Frame10BallTxt2;
    private JTextPane user1Frame10BallTxt3;
    private JTextPane user1Frame10Score;
    private JTextPane user1Frame9Score;
    private JTextPane user1Frame8Score;
    private JTextPane user1Frame7Score;
    private JTextPane user1Frame6Score;
    private JTextPane user1Frame5Score;
    private JTextPane user1Frame4Score;
    private JTextPane user1Frame3Score;
    private JTextPane user1Frame2Score;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		allFrames = new int[4][10][3];
		for (int i = 0; i < 10; i++) {
			allFrames[0][i] = new int[]{-1, -1, -1};
			allFrames[1][i] = new int[]{-1, -1, -1};
			allFrames[2][i] = new int[]{-1, -1, -1};
			allFrames[3][i] = new int[]{-1, -1, -1};
		}
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

     public void move( ) throws InterruptedException {
    	PinDrop pindrop = new PinDrop();
    	assert( pindrop != null );
    	
    	pindrop.getPinsStillStanding(pins);
    	int count = 0;
		for (int i = 0; i < 10; i++) {
			if (!pins[i]) {
				count++;
			}
		}
		MovingBall movingball = new MovingBall(frame,ball,60);
		
		movingball.draw();
		if ( firstthrow == false )
		{
			firstthrow=true;			
			drawpins(pins);
			Thread.sleep(4000);
			for( int idx=0; idx<10;idx++ ) 
				pins[idx]=true;
		} 
		else {
			firstthrow=false;
		}
		
		drawpins(pins);
		playerRolledBall(0, count);
	}
     
     
     public void playerRolledBall(int playerId, int numOfPins) {
    	 // Apply the strike/spare/last frame rules
    	 int f = findCurrentFrame(playerId);
    	 if (f != -1) {
    		 // Check if it's the first or second roll
    		 int firstRoll = allFrames[playerId][f][0];
    		 if (firstRoll == -1) {
    			 // First roll
    			 allFrames[playerId][f][0] = numOfPins;
    		 } else if (firstRoll == 10) {
    			 // spare: No second roll needed
    			 allFrames[playerId][++f][0] = numOfPins;
    		 } else if (allFrames[playerId][f][1] == -1){
    			 allFrames[playerId][f][1] = numOfPins;
    		 } else {
    			 allFrames[playerId][f][2] = numOfPins;
    		 }
    		 updateScore();
    	 }
     }
     
     private int findCurrentFrame(int playerId) {
    	 int frame = -1;
    	 if (allFrames[playerId][0][0] == -1 ||
    		 (allFrames[playerId][0][0] != -1 && allFrames[playerId][0][0] != 10 && allFrames[playerId][0][1] == -1)) {
    		 frame = 0;
    	 } else {
    		 int i = 1;
	    	 for (; i < 10; i++) {
	    		 if (((allFrames[playerId][i][0] == -1 || allFrames[playerId][i][1] == -1) || (i == 9 && allFrames[playerId][i][2] == -1)) &&
	    			 allFrames[playerId][i - 1][0] != -1) {
	    			 frame = i;
	    			 break;
	    		 }
	    	 }
    	 }
    	 return frame;
     }
     private void updateScore() {
    	 Class<?> that = this.getClass();
    	 for (int user = 0; user < 4; user++) {
    		 int userScore[] = calculateScore(allFrames[user]);
    		 String u = "user" + (user + 1);
    		 for (int i = 0; i < 10; i++) {
        		 String frame = "Frame" + (i + 1);
        		 Field f = null;
        		 Field s = null;
        		 for (int ball = 0; ball < 3; ball++) {
        			 if (allFrames[user][i][ball] == -1) {
        				 continue;
        			 }
        			 String txtName = u + frame  + "BallTxt" + (ball + 1);
        			 try {
        				 f = that.getDeclaredField(txtName);
        				 s = that.getDeclaredField(u + frame + "Score");
        			 } catch (Exception e) {
        				 String dummy = "";
        			 }
        			 if (f != null) {
        				 JTextPane txt = null;
        				 JTextPane scoreTxt = null;
        				 try {
        					 txt = (JTextPane)f.get(this);
        					 scoreTxt = (JTextPane)s.get(this);
        				 } catch (Exception e) {
        					 String dummy = "";
        				 }
        				 if (txt != null) {
        					 int value = allFrames[user][i][ball];
        					 if (value == 10) {
        						 if (ball == 0) {
        							 txt.setText("X");
        						 } else {
        							 txt.setText("/");
        						 }
        					 } else {
        						 txt.setText(String.valueOf(value));
        					 }
        				 }
        				 if (scoreTxt != null) {
        					 scoreTxt.setText(String.valueOf(userScore[i]));
        				 }
        			 }
        		 }
        	 }
    	 }
     }
     
     public int[] calculateScore(int input[][]) {
 		int[] score = new int[10];
 		int total = 0;
 		for (int i = 0; i < 9; i++) {
 			if (input[i][0] == 10) {
 				score[i] = total + 10 + input[i+1][0] + input[i+1][1];
 			} else if (input[i][1] == 10) {
 				score[i] = total + 10 + input[i+1][0];
 			} else {
 				score[i] = total + input[i][0] + input[i][1];
 			}
 			total = score[i];
 		}
 		if (input[9][0] == 10) {
 			score[9] = total + 10 + input[9][1] + input[9][2];
 		} else if (input[9][1] == 10){			
 			score[9] = total + 10 + input[9][2];
 		} else {
 			score[9] = total + input[9][0] + input[9][1];
 		}
 		return score;
 	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setSize(new Dimension(200, 400));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setPreferredSize(new Dimension(40, 40));
		frame.setBounds(100, 100, 1450, 640);
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
		lblBall.setBounds(184, 488, 51, 46);
		frame.getContentPane().add(lblBall);
		ball=lblBall;
        
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.BLACK);
		separator.setBounds(380, 11, 2, 523);
		frame.getContentPane().add(separator);
		
		JPanel user1Frame1 = new JPanel();
		user1Frame1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame1.setBounds(518, 50, 83, 81);
		frame.getContentPane().add(user1Frame1);
		user1Frame1.setLayout(null);
		
		JPanel user1Frame1Ball1 = new JPanel();
		user1Frame1Ball1.setBounds(36, 2, 24, 26);
		user1Frame1.add(user1Frame1Ball1);
		user1Frame1Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame1Ball1.setLayout(null);
		
		user1Frame1BallTxt1 = new JTextPane();
		user1Frame1BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame1Ball1.add(user1Frame1BallTxt1);
		user1Frame1BallTxt1.setEditable(false);
		user1Frame1BallTxt1.setBackground(Color.WHITE);
		
		user1Frame1Score = new JTextPane();
		user1Frame1Score.setBackground(SystemColor.control);
		user1Frame1Score.setBounds(10, 39, 63, 37);
		user1Frame1.add(user1Frame1Score);
		
		JPanel user1Frame1Ball2 = new JPanel();
		user1Frame1Ball2.setLayout(null);
		user1Frame1Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame1Ball2.setBounds(59, 2, 24, 26);
		user1Frame1.add(user1Frame1Ball2);
		
		user1Frame1BallTxt2 = new JTextPane();
		user1Frame1BallTxt2.setEditable(false);
		user1Frame1BallTxt2.setBackground(Color.WHITE);
		user1Frame1BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame1Ball2.add(user1Frame1BallTxt2);
		
		JLabel lblVittal = new JLabel("Vittal");
		lblVittal.setHorizontalAlignment(SwingConstants.CENTER);
		lblVittal.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblVittal.setBounds(374, 50, 134, 81);
		frame.getContentPane().add(lblVittal);
		
		JPanel user1Frame2 = new JPanel();
		user1Frame2.setLayout(null);
		user1Frame2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame2.setBounds(603, 50, 83, 81);
		frame.getContentPane().add(user1Frame2);
		
		JPanel user1Frame2Ball1 = new JPanel();
		user1Frame2Ball1.setLayout(null);
		user1Frame2Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame2Ball1.setBounds(36, 2, 24, 26);
		user1Frame2.add(user1Frame2Ball1);
		
		user1Frame2BallTxt1 = new JTextPane();
		user1Frame2BallTxt1.setEditable(false);
		user1Frame2BallTxt1.setBackground(Color.WHITE);
		user1Frame2BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame2Ball1.add(user1Frame2BallTxt1);
		
		user1Frame2Score = new JTextPane();
		user1Frame2Score.setBackground(SystemColor.menu);
		user1Frame2Score.setBounds(10, 39, 63, 37);
		user1Frame2.add(user1Frame2Score);
		
		JPanel user1Frame2Ball2 = new JPanel();
		user1Frame2Ball2.setLayout(null);
		user1Frame2Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame2Ball2.setBounds(59, 2, 24, 26);
		user1Frame2.add(user1Frame2Ball2);
		
		user1Frame2BallTxt2 = new JTextPane();
		user1Frame2BallTxt2.setEditable(false);
		user1Frame2BallTxt2.setBackground(Color.WHITE);
		user1Frame2BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame2Ball2.add(user1Frame2BallTxt2);
		
		JPanel user1Frame3 = new JPanel();
		user1Frame3.setLayout(null);
		user1Frame3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame3.setBounds(688, 50, 83, 81);
		frame.getContentPane().add(user1Frame3);
		
		JPanel user1Frame3Ball1 = new JPanel();
		user1Frame3Ball1.setLayout(null);
		user1Frame3Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame3Ball1.setBounds(36, 2, 24, 26);
		user1Frame3.add(user1Frame3Ball1);
		
		user1Frame3BallTxt1 = new JTextPane();
		user1Frame3BallTxt1.setEditable(false);
		user1Frame3BallTxt1.setBackground(Color.WHITE);
		user1Frame3BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame3Ball1.add(user1Frame3BallTxt1);
		
		user1Frame3Score = new JTextPane();
		user1Frame3Score.setBackground(SystemColor.menu);
		user1Frame3Score.setBounds(10, 39, 63, 37);
		user1Frame3.add(user1Frame3Score);
		
		JPanel user1Frame3Ball2 = new JPanel();
		user1Frame3Ball2.setLayout(null);
		user1Frame3Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame3Ball2.setBounds(59, 2, 24, 26);
		user1Frame3.add(user1Frame3Ball2);
		
		user1Frame3BallTxt2 = new JTextPane();
		user1Frame3BallTxt2.setEditable(false);
		user1Frame3BallTxt2.setBackground(Color.WHITE);
		user1Frame3BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame3Ball2.add(user1Frame3BallTxt2);
		
		JPanel user1Frame4 = new JPanel();
		user1Frame4.setLayout(null);
		user1Frame4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame4.setBounds(773, 50, 83, 81);
		frame.getContentPane().add(user1Frame4);
		
		JPanel user1Frame4Ball1 = new JPanel();
		user1Frame4Ball1.setLayout(null);
		user1Frame4Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame4Ball1.setBounds(36, 2, 24, 26);
		user1Frame4.add(user1Frame4Ball1);
		
		user1Frame4BallTxt1 = new JTextPane();
		user1Frame4BallTxt1.setEditable(false);
		user1Frame4BallTxt1.setBackground(Color.WHITE);
		user1Frame4BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame4Ball1.add(user1Frame4BallTxt1);
		
		user1Frame4Score = new JTextPane();
		user1Frame4Score.setBackground(SystemColor.menu);
		user1Frame4Score.setBounds(10, 39, 63, 37);
		user1Frame4.add(user1Frame4Score);
		
		JPanel user1Frame4Ball2 = new JPanel();
		user1Frame4Ball2.setLayout(null);
		user1Frame4Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame4Ball2.setBounds(59, 2, 24, 26);
		user1Frame4.add(user1Frame4Ball2);
		
		user1Frame4BallTxt2 = new JTextPane();
		user1Frame4BallTxt2.setEditable(false);
		user1Frame4BallTxt2.setBackground(Color.WHITE);
		user1Frame4BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame4Ball2.add(user1Frame4BallTxt2);
		
		JPanel user1Frame5 = new JPanel();
		user1Frame5.setLayout(null);
		user1Frame5.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame5.setBounds(858, 50, 83, 81);
		frame.getContentPane().add(user1Frame5);
		
		JPanel user1Frame5Ball1 = new JPanel();
		user1Frame5Ball1.setLayout(null);
		user1Frame5Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame5Ball1.setBounds(36, 2, 24, 26);
		user1Frame5.add(user1Frame5Ball1);
		
		user1Frame5BallTxt1 = new JTextPane();
		user1Frame5BallTxt1.setEditable(false);
		user1Frame5BallTxt1.setBackground(Color.WHITE);
		user1Frame5BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame5Ball1.add(user1Frame5BallTxt1);
		
		user1Frame5Score = new JTextPane();
		user1Frame5Score.setBackground(SystemColor.menu);
		user1Frame5Score.setBounds(10, 39, 63, 37);
		user1Frame5.add(user1Frame5Score);
		
		JPanel user1Frame5Ball2 = new JPanel();
		user1Frame5Ball2.setLayout(null);
		user1Frame5Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame5Ball2.setBounds(59, 2, 24, 26);
		user1Frame5.add(user1Frame5Ball2);
		
		user1Frame5BallTxt2 = new JTextPane();
		user1Frame5BallTxt2.setEditable(false);
		user1Frame5BallTxt2.setBackground(Color.WHITE);
		user1Frame5BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame5Ball2.add(user1Frame5BallTxt2);
		
		JPanel user1Frame6 = new JPanel();
		user1Frame6.setLayout(null);
		user1Frame6.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame6.setBounds(943, 50, 83, 81);
		frame.getContentPane().add(user1Frame6);
		
		JPanel user1Frame6Ball1 = new JPanel();
		user1Frame6Ball1.setLayout(null);
		user1Frame6Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame6Ball1.setBounds(36, 2, 24, 26);
		user1Frame6.add(user1Frame6Ball1);
		
		user1Frame6BallTxt1 = new JTextPane();
		user1Frame6BallTxt1.setEditable(false);
		user1Frame6BallTxt1.setBackground(Color.WHITE);
		user1Frame6BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame6Ball1.add(user1Frame6BallTxt1);
		
		user1Frame6Score = new JTextPane();
		user1Frame6Score.setBackground(SystemColor.menu);
		user1Frame6Score.setBounds(10, 39, 63, 37);
		user1Frame6.add(user1Frame6Score);
		
		JPanel user1Frame6Ball2 = new JPanel();
		user1Frame6Ball2.setLayout(null);
		user1Frame6Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame6Ball2.setBounds(59, 2, 24, 26);
		user1Frame6.add(user1Frame6Ball2);
		
		user1Frame6BallTxt2 = new JTextPane();
		user1Frame6BallTxt2.setEditable(false);
		user1Frame6BallTxt2.setBackground(Color.WHITE);
		user1Frame6BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame6Ball2.add(user1Frame6BallTxt2);
		
		JPanel user1Frame7 = new JPanel();
		user1Frame7.setLayout(null);
		user1Frame7.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame7.setBounds(1027, 50, 83, 81);
		frame.getContentPane().add(user1Frame7);
		
		JPanel user1Frame7Ball1 = new JPanel();
		user1Frame7Ball1.setLayout(null);
		user1Frame7Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame7Ball1.setBounds(36, 2, 24, 26);
		user1Frame7.add(user1Frame7Ball1);
		
		user1Frame7BallTxt1 = new JTextPane();
		user1Frame7BallTxt1.setEditable(false);
		user1Frame7BallTxt1.setBackground(Color.WHITE);
		user1Frame7BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame7Ball1.add(user1Frame7BallTxt1);
		
		user1Frame7Score = new JTextPane();
		user1Frame7Score.setBackground(SystemColor.menu);
		user1Frame7Score.setBounds(10, 39, 63, 37);
		user1Frame7.add(user1Frame7Score);
		
		JPanel user1Frame7Ball2 = new JPanel();
		user1Frame7Ball2.setLayout(null);
		user1Frame7Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame7Ball2.setBounds(59, 2, 24, 26);
		user1Frame7.add(user1Frame7Ball2);
		
		user1Frame7BallTxt2 = new JTextPane();
		user1Frame7BallTxt2.setEditable(false);
		user1Frame7BallTxt2.setBackground(Color.WHITE);
		user1Frame7BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame7Ball2.add(user1Frame7BallTxt2);
		
		JPanel user1Frame8 = new JPanel();
		user1Frame8.setLayout(null);
		user1Frame8.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame8.setBounds(1112, 50, 83, 81);
		frame.getContentPane().add(user1Frame8);
		
		JPanel user1Frame8Ball1 = new JPanel();
		user1Frame8Ball1.setLayout(null);
		user1Frame8Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame8Ball1.setBounds(36, 2, 24, 26);
		user1Frame8.add(user1Frame8Ball1);
		
		user1Frame8BallTxt1 = new JTextPane();
		user1Frame8BallTxt1.setEditable(false);
		user1Frame8BallTxt1.setBackground(Color.WHITE);
		user1Frame8BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame8Ball1.add(user1Frame8BallTxt1);
		
		user1Frame8Score = new JTextPane();
		user1Frame8Score.setBackground(SystemColor.menu);
		user1Frame8Score.setBounds(10, 39, 63, 37);
		user1Frame8.add(user1Frame8Score);
		
		JPanel user1Frame8Ball2 = new JPanel();
		user1Frame8Ball2.setLayout(null);
		user1Frame8Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame8Ball2.setBounds(59, 2, 24, 26);
		user1Frame8.add(user1Frame8Ball2);
		
		user1Frame8BallTxt2 = new JTextPane();
		user1Frame8BallTxt2.setEditable(false);
		user1Frame8BallTxt2.setBackground(Color.WHITE);
		user1Frame8BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame8Ball2.add(user1Frame8BallTxt2);
		
		JPanel user1Frame9 = new JPanel();
		user1Frame9.setLayout(null);
		user1Frame9.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame9.setBounds(1197, 50, 83, 81);
		frame.getContentPane().add(user1Frame9);
		
		JPanel user1Frame9Ball1 = new JPanel();
		user1Frame9Ball1.setLayout(null);
		user1Frame9Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame9Ball1.setBounds(36, 2, 24, 26);
		user1Frame9.add(user1Frame9Ball1);
		
		user1Frame9BallTxt1 = new JTextPane();
		user1Frame9BallTxt1.setEditable(false);
		user1Frame9BallTxt1.setBackground(Color.WHITE);
		user1Frame9BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame9Ball1.add(user1Frame9BallTxt1);
		
		user1Frame9Score = new JTextPane();
		user1Frame9Score.setBackground(SystemColor.menu);
		user1Frame9Score.setBounds(10, 39, 63, 37);
		user1Frame9.add(user1Frame9Score);
		
		JPanel user1Frame9Ball2 = new JPanel();
		user1Frame9Ball2.setLayout(null);
		user1Frame9Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame9Ball2.setBounds(59, 2, 24, 26);
		user1Frame9.add(user1Frame9Ball2);
		
		user1Frame9BallTxt2 = new JTextPane();
		user1Frame9BallTxt2.setEditable(false);
		user1Frame9BallTxt2.setBackground(Color.WHITE);
		user1Frame9BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame9Ball2.add(user1Frame9BallTxt2);
		
		JPanel user1Frame10 = new JPanel();
		user1Frame10.setLayout(null);
		user1Frame10.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user1Frame10.setBounds(1282, 50, 106, 81);
		frame.getContentPane().add(user1Frame10);
		
		JPanel user1Frame10Ball1 = new JPanel();
		user1Frame10Ball1.setLayout(null);
		user1Frame10Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame10Ball1.setBounds(36, 2, 24, 26);
		user1Frame10.add(user1Frame10Ball1);
		
		user1Frame10BallTxt1 = new JTextPane();
		user1Frame10BallTxt1.setEditable(false);
		user1Frame10BallTxt1.setBackground(Color.WHITE);
		user1Frame10BallTxt1.setBounds(3, 5, 18, 18);
		user1Frame10Ball1.add(user1Frame10BallTxt1);
		
		user1Frame10Score = new JTextPane();
		user1Frame10Score.setBackground(SystemColor.menu);
		user1Frame10Score.setBounds(10, 39, 63, 37);
		user1Frame10.add(user1Frame10Score);
		
		JPanel user1Frame10Ball2 = new JPanel();
		user1Frame10Ball2.setLayout(null);
		user1Frame10Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame10Ball2.setBounds(59, 2, 24, 26);
		user1Frame10.add(user1Frame10Ball2);
		
		user1Frame10BallTxt2 = new JTextPane();
		user1Frame10BallTxt2.setEditable(false);
		user1Frame10BallTxt2.setBackground(Color.WHITE);
		user1Frame10BallTxt2.setBounds(3, 5, 18, 18);
		user1Frame10Ball2.add(user1Frame10BallTxt2);
		
		JPanel user1Frame10Ball3 = new JPanel();
		user1Frame10Ball3.setLayout(null);
		user1Frame10Ball3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user1Frame10Ball3.setBounds(82, 2, 24, 26);
		user1Frame10.add(user1Frame10Ball3);
		
		user1Frame10BallTxt3 = new JTextPane();
		user1Frame10BallTxt3.setEditable(false);
		user1Frame10BallTxt3.setBackground(Color.WHITE);
		user1Frame10BallTxt3.setBounds(3, 5, 18, 18);
		user1Frame10Ball3.add(user1Frame10BallTxt3);
		
		JPanel user2Frame1 = new JPanel();
		user2Frame1.setLayout(null);
		user2Frame1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame1.setBounds(518, 136, 83, 81);
		frame.getContentPane().add(user2Frame1);
		
		JPanel user2Frame1Ball1 = new JPanel();
		user2Frame1Ball1.setLayout(null);
		user2Frame1Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame1Ball1.setBounds(36, 2, 24, 26);
		user2Frame1.add(user2Frame1Ball1);
		
		JTextPane user2Frame1BallTxt1 = new JTextPane();
		user2Frame1BallTxt1.setEditable(false);
		user2Frame1BallTxt1.setBackground(Color.WHITE);
		user2Frame1BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame1Ball1.add(user2Frame1BallTxt1);
		
		JTextPane user2Frame1Score = new JTextPane();
		user2Frame1Score.setBackground(SystemColor.menu);
		user2Frame1Score.setBounds(10, 39, 63, 37);
		user2Frame1.add(user2Frame1Score);
		
		JPanel user2Frame1Ball2 = new JPanel();
		user2Frame1Ball2.setLayout(null);
		user2Frame1Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame1Ball2.setBounds(59, 2, 24, 26);
		user2Frame1.add(user2Frame1Ball2);
		
		JTextPane user2Frame1BallTxt2 = new JTextPane();
		user2Frame1BallTxt2.setEditable(false);
		user2Frame1BallTxt2.setBackground(Color.WHITE);
		user2Frame1BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame1Ball2.add(user2Frame1BallTxt2);
		
		JLabel lblSerkan = new JLabel("Serkan");
		lblSerkan.setHorizontalAlignment(SwingConstants.CENTER);
		lblSerkan.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblSerkan.setBounds(374, 136, 134, 81);
		frame.getContentPane().add(lblSerkan);
		
		JPanel user2Frame2 = new JPanel();
		user2Frame2.setLayout(null);
		user2Frame2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame2.setBounds(603, 136, 83, 81);
		frame.getContentPane().add(user2Frame2);
		
		JPanel user2Frame2Ball1 = new JPanel();
		user2Frame2Ball1.setLayout(null);
		user2Frame2Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame2Ball1.setBounds(36, 2, 24, 26);
		user2Frame2.add(user2Frame2Ball1);
		
		JTextPane user2Frame2BallTxt1 = new JTextPane();
		user2Frame2BallTxt1.setEditable(false);
		user2Frame2BallTxt1.setBackground(Color.WHITE);
		user2Frame2BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame2Ball1.add(user2Frame2BallTxt1);
		
		JTextPane user2Frame2Score = new JTextPane();
		user2Frame2Score.setBackground(SystemColor.menu);
		user2Frame2Score.setBounds(10, 39, 63, 37);
		user2Frame2.add(user2Frame2Score);
		
		JPanel user2Frame2Ball2 = new JPanel();
		user2Frame2Ball2.setLayout(null);
		user2Frame2Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame2Ball2.setBounds(59, 2, 24, 26);
		user2Frame2.add(user2Frame2Ball2);
		
		JTextPane user2Frame2BallTxt2 = new JTextPane();
		user2Frame2BallTxt2.setEditable(false);
		user2Frame2BallTxt2.setBackground(Color.WHITE);
		user2Frame2BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame2Ball2.add(user2Frame2BallTxt2);
		
		JPanel user2Frame3 = new JPanel();
		user2Frame3.setLayout(null);
		user2Frame3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame3.setBounds(688, 136, 83, 81);
		frame.getContentPane().add(user2Frame3);
		
		JPanel user2Frame3Ball1 = new JPanel();
		user2Frame3Ball1.setLayout(null);
		user2Frame3Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame3Ball1.setBounds(36, 2, 24, 26);
		user2Frame3.add(user2Frame3Ball1);
		
		JTextPane user2Frame3BallTxt1 = new JTextPane();
		user2Frame3BallTxt1.setEditable(false);
		user2Frame3BallTxt1.setBackground(Color.WHITE);
		user2Frame3BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame3Ball1.add(user2Frame3BallTxt1);
		
		JTextPane user2Frame3Score = new JTextPane();
		user2Frame3Score.setBackground(SystemColor.menu);
		user2Frame3Score.setBounds(10, 39, 63, 37);
		user2Frame3.add(user2Frame3Score);
		
		JPanel user2Frame3Ball2 = new JPanel();
		user2Frame3Ball2.setLayout(null);
		user2Frame3Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame3Ball2.setBounds(59, 2, 24, 26);
		user2Frame3.add(user2Frame3Ball2);
		
		JTextPane user2Frame3BallTxt2 = new JTextPane();
		user2Frame3BallTxt2.setEditable(false);
		user2Frame3BallTxt2.setBackground(Color.WHITE);
		user2Frame3BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame3Ball2.add(user2Frame3BallTxt2);
		
		JPanel user2Frame4 = new JPanel();
		user2Frame4.setLayout(null);
		user2Frame4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame4.setBounds(773, 136, 83, 81);
		frame.getContentPane().add(user2Frame4);
		
		JPanel user2Frame4Ball1 = new JPanel();
		user2Frame4Ball1.setLayout(null);
		user2Frame4Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame4Ball1.setBounds(36, 2, 24, 26);
		user2Frame4.add(user2Frame4Ball1);
		
		JTextPane user2Frame4BallTxt1 = new JTextPane();
		user2Frame4BallTxt1.setEditable(false);
		user2Frame4BallTxt1.setBackground(Color.WHITE);
		user2Frame4BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame4Ball1.add(user2Frame4BallTxt1);
		
		JTextPane user2Frame4Score = new JTextPane();
		user2Frame4Score.setBackground(SystemColor.menu);
		user2Frame4Score.setBounds(10, 39, 63, 37);
		user2Frame4.add(user2Frame4Score);
		
		JPanel user2Frame4Ball2 = new JPanel();
		user2Frame4Ball2.setLayout(null);
		user2Frame4Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame4Ball2.setBounds(59, 2, 24, 26);
		user2Frame4.add(user2Frame4Ball2);
		
		JTextPane user2Frame4BallTxt2 = new JTextPane();
		user2Frame4BallTxt2.setEditable(false);
		user2Frame4BallTxt2.setBackground(Color.WHITE);
		user2Frame4BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame4Ball2.add(user2Frame4BallTxt2);
		
		JPanel user2Frame5 = new JPanel();
		user2Frame5.setLayout(null);
		user2Frame5.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame5.setBounds(858, 136, 83, 81);
		frame.getContentPane().add(user2Frame5);
		
		JPanel user2Frame5Ball1 = new JPanel();
		user2Frame5Ball1.setLayout(null);
		user2Frame5Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame5Ball1.setBounds(36, 2, 24, 26);
		user2Frame5.add(user2Frame5Ball1);
		
		JTextPane user2Frame5BallTxt1 = new JTextPane();
		user2Frame5BallTxt1.setEditable(false);
		user2Frame5BallTxt1.setBackground(Color.WHITE);
		user2Frame5BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame5Ball1.add(user2Frame5BallTxt1);
		
		JTextPane user2Frame5Score = new JTextPane();
		user2Frame5Score.setBackground(SystemColor.menu);
		user2Frame5Score.setBounds(10, 39, 63, 37);
		user2Frame5.add(user2Frame5Score);
		
		JPanel user2Frame5Ball2 = new JPanel();
		user2Frame5Ball2.setLayout(null);
		user2Frame5Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame5Ball2.setBounds(59, 2, 24, 26);
		user2Frame5.add(user2Frame5Ball2);
		
		JTextPane user2Frame5BallTxt2 = new JTextPane();
		user2Frame5BallTxt2.setEditable(false);
		user2Frame5BallTxt2.setBackground(Color.WHITE);
		user2Frame5BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame5Ball2.add(user2Frame5BallTxt2);
		
		JPanel user2Frame6 = new JPanel();
		user2Frame6.setLayout(null);
		user2Frame6.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame6.setBounds(943, 136, 83, 81);
		frame.getContentPane().add(user2Frame6);
		
		JPanel user2Frame6Ball1 = new JPanel();
		user2Frame6Ball1.setLayout(null);
		user2Frame6Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame6Ball1.setBounds(36, 2, 24, 26);
		user2Frame6.add(user2Frame6Ball1);
		
		JTextPane user2Frame6BallTxt1 = new JTextPane();
		user2Frame6BallTxt1.setEditable(false);
		user2Frame6BallTxt1.setBackground(Color.WHITE);
		user2Frame6BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame6Ball1.add(user2Frame6BallTxt1);
		
		JTextPane user2Frame6Score = new JTextPane();
		user2Frame6Score.setBackground(SystemColor.menu);
		user2Frame6Score.setBounds(10, 39, 63, 37);
		user2Frame6.add(user2Frame6Score);
		
		JPanel user2Frame6Ball2 = new JPanel();
		user2Frame6Ball2.setLayout(null);
		user2Frame6Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame6Ball2.setBounds(59, 2, 24, 26);
		user2Frame6.add(user2Frame6Ball2);
		
		JTextPane user2Frame6BallTxt2 = new JTextPane();
		user2Frame6BallTxt2.setEditable(false);
		user2Frame6BallTxt2.setBackground(Color.WHITE);
		user2Frame6BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame6Ball2.add(user2Frame6BallTxt2);
		
		JPanel user2Frame7 = new JPanel();
		user2Frame7.setLayout(null);
		user2Frame7.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame7.setBounds(1027, 136, 83, 81);
		frame.getContentPane().add(user2Frame7);
		
		JPanel user2Frame7Ball1 = new JPanel();
		user2Frame7Ball1.setLayout(null);
		user2Frame7Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame7Ball1.setBounds(36, 2, 24, 26);
		user2Frame7.add(user2Frame7Ball1);
		
		JTextPane user2Frame7BallTxt1 = new JTextPane();
		user2Frame7BallTxt1.setEditable(false);
		user2Frame7BallTxt1.setBackground(Color.WHITE);
		user2Frame7BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame7Ball1.add(user2Frame7BallTxt1);
		
		JTextPane user2Frame7Score = new JTextPane();
		user2Frame7Score.setBackground(SystemColor.menu);
		user2Frame7Score.setBounds(10, 39, 63, 37);
		user2Frame7.add(user2Frame7Score);
		
		JPanel user2Frame7Ball2 = new JPanel();
		user2Frame7Ball2.setLayout(null);
		user2Frame7Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame7Ball2.setBounds(59, 2, 24, 26);
		user2Frame7.add(user2Frame7Ball2);
		
		JTextPane user2Frame7BallTxt2 = new JTextPane();
		user2Frame7BallTxt2.setEditable(false);
		user2Frame7BallTxt2.setBackground(Color.WHITE);
		user2Frame7BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame7Ball2.add(user2Frame7BallTxt2);
		
		JPanel user2Frame8 = new JPanel();
		user2Frame8.setLayout(null);
		user2Frame8.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame8.setBounds(1112, 136, 83, 81);
		frame.getContentPane().add(user2Frame8);
		
		JPanel user2Frame8Ball1 = new JPanel();
		user2Frame8Ball1.setLayout(null);
		user2Frame8Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame8Ball1.setBounds(36, 2, 24, 26);
		user2Frame8.add(user2Frame8Ball1);
		
		JTextPane user2Frame8BallTxt1 = new JTextPane();
		user2Frame8BallTxt1.setEditable(false);
		user2Frame8BallTxt1.setBackground(Color.WHITE);
		user2Frame8BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame8Ball1.add(user2Frame8BallTxt1);
		
		JTextPane user2Frame8Score = new JTextPane();
		user2Frame8Score.setBackground(SystemColor.menu);
		user2Frame8Score.setBounds(10, 39, 63, 37);
		user2Frame8.add(user2Frame8Score);
		
		JPanel user2Frame8Ball2 = new JPanel();
		user2Frame8Ball2.setLayout(null);
		user2Frame8Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame8Ball2.setBounds(59, 2, 24, 26);
		user2Frame8.add(user2Frame8Ball2);
		
		JTextPane user2Frame8BallTxt2 = new JTextPane();
		user2Frame8BallTxt2.setEditable(false);
		user2Frame8BallTxt2.setBackground(Color.WHITE);
		user2Frame8BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame8Ball2.add(user2Frame8BallTxt2);
		
		JPanel user2Frame9 = new JPanel();
		user2Frame9.setLayout(null);
		user2Frame9.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame9.setBounds(1197, 136, 83, 81);
		frame.getContentPane().add(user2Frame9);
		
		JPanel user2Frame9Ball1 = new JPanel();
		user2Frame9Ball1.setLayout(null);
		user2Frame9Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame9Ball1.setBounds(36, 2, 24, 26);
		user2Frame9.add(user2Frame9Ball1);
		
		JTextPane user2Frame9BallTxt1 = new JTextPane();
		user2Frame9BallTxt1.setEditable(false);
		user2Frame9BallTxt1.setBackground(Color.WHITE);
		user2Frame9BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame9Ball1.add(user2Frame9BallTxt1);
		
		JTextPane user2Frame9Score = new JTextPane();
		user2Frame9Score.setBackground(SystemColor.menu);
		user2Frame9Score.setBounds(10, 39, 63, 37);
		user2Frame9.add(user2Frame9Score);
		
		JPanel user2Frame9Ball2 = new JPanel();
		user2Frame9Ball2.setLayout(null);
		user2Frame9Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame9Ball2.setBounds(59, 2, 24, 26);
		user2Frame9.add(user2Frame9Ball2);
		
		JTextPane user2Frame9BallTxt2 = new JTextPane();
		user2Frame9BallTxt2.setEditable(false);
		user2Frame9BallTxt2.setBackground(Color.WHITE);
		user2Frame9BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame9Ball2.add(user2Frame9BallTxt2);
		
		JPanel user2Frame10 = new JPanel();
		user2Frame10.setLayout(null);
		user2Frame10.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user2Frame10.setBounds(1282, 136, 106, 81);
		frame.getContentPane().add(user2Frame10);
		
		JPanel user2Frame10Ball1 = new JPanel();
		user2Frame10Ball1.setLayout(null);
		user2Frame10Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame10Ball1.setBounds(36, 2, 24, 26);
		user2Frame10.add(user2Frame10Ball1);
		
		JTextPane user2Frame10BallTxt1 = new JTextPane();
		user2Frame10BallTxt1.setEditable(false);
		user2Frame10BallTxt1.setBackground(Color.WHITE);
		user2Frame10BallTxt1.setBounds(3, 5, 18, 18);
		user2Frame10Ball1.add(user2Frame10BallTxt1);
		
		JTextPane user2Frame10Score = new JTextPane();
		user2Frame10Score.setBackground(SystemColor.menu);
		user2Frame10Score.setBounds(10, 39, 63, 37);
		user2Frame10.add(user2Frame10Score);
		
		JPanel user2Frame10Ball2 = new JPanel();
		user2Frame10Ball2.setLayout(null);
		user2Frame10Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame10Ball2.setBounds(59, 2, 24, 26);
		user2Frame10.add(user2Frame10Ball2);
		
		JTextPane user2Frame10BallTxt2 = new JTextPane();
		user2Frame10BallTxt2.setEditable(false);
		user2Frame10BallTxt2.setBackground(Color.WHITE);
		user2Frame10BallTxt2.setBounds(3, 5, 18, 18);
		user2Frame10Ball2.add(user2Frame10BallTxt2);
		
		JPanel user2Frame10Ball3 = new JPanel();
		user2Frame10Ball3.setLayout(null);
		user2Frame10Ball3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user2Frame10Ball3.setBounds(82, 2, 24, 26);
		user2Frame10.add(user2Frame10Ball3);
		
		JTextPane user2Frame10BallTxt3 = new JTextPane();
		user2Frame10BallTxt3.setEditable(false);
		user2Frame10BallTxt3.setBackground(Color.WHITE);
		user2Frame10BallTxt3.setBounds(3, 5, 18, 18);
		user2Frame10Ball3.add(user2Frame10BallTxt3);
		
		JPanel user3Frame1 = new JPanel();
		user3Frame1.setLayout(null);
		user3Frame1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame1.setBounds(518, 222, 83, 81);
		frame.getContentPane().add(user3Frame1);
		
		JPanel user3Frame1Ball1 = new JPanel();
		user3Frame1Ball1.setLayout(null);
		user3Frame1Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame1Ball1.setBounds(36, 2, 24, 26);
		user3Frame1.add(user3Frame1Ball1);
		
		JTextPane user3Frame1BallTxt1 = new JTextPane();
		user3Frame1BallTxt1.setEditable(false);
		user3Frame1BallTxt1.setBackground(Color.WHITE);
		user3Frame1BallTxt1.setBounds(3, 5, 18, 18);
		user3Frame1Ball1.add(user3Frame1BallTxt1);
		
		JTextPane user3Frame1Score = new JTextPane();
		user3Frame1Score.setBackground(SystemColor.menu);
		user3Frame1Score.setBounds(10, 39, 63, 37);
		user3Frame1.add(user3Frame1Score);
		
		JPanel user3Frame1Ball2 = new JPanel();
		user3Frame1Ball2.setLayout(null);
		user3Frame1Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame1Ball2.setBounds(59, 2, 24, 26);
		user3Frame1.add(user3Frame1Ball2);
		
		JTextPane user3Frame1BallTxt2 = new JTextPane();
		user3Frame1BallTxt2.setEditable(false);
		user3Frame1BallTxt2.setBackground(Color.WHITE);
		user3Frame1BallTxt2.setBounds(3, 5, 18, 18);
		user3Frame1Ball2.add(user3Frame1BallTxt2);
		
		JLabel lblLe = new JLabel("Le");
		lblLe.setHorizontalAlignment(SwingConstants.CENTER);
		lblLe.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblLe.setBounds(374, 222, 134, 81);
		frame.getContentPane().add(lblLe);
		
		JPanel user3Frame2 = new JPanel();
		user3Frame2.setLayout(null);
		user3Frame2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame2.setBounds(603, 222, 83, 81);
		frame.getContentPane().add(user3Frame2);
		
		JPanel user3Frame2Ball1 = new JPanel();
		user3Frame2Ball1.setLayout(null);
		user3Frame2Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame2Ball1.setBounds(36, 2, 24, 26);
		user3Frame2.add(user3Frame2Ball1);
		
		JTextPane user3Frame2BallTxt1 = new JTextPane();
		user3Frame2BallTxt1.setEditable(false);
		user3Frame2BallTxt1.setBackground(Color.WHITE);
		user3Frame2BallTxt1.setBounds(3, 5, 18, 18);
		user3Frame2Ball1.add(user3Frame2BallTxt1);
		
		JTextPane user3Frame2Score = new JTextPane();
		user3Frame2Score.setBackground(SystemColor.menu);
		user3Frame2Score.setBounds(10, 39, 63, 37);
		user3Frame2.add(user3Frame2Score);
		
		JPanel user3Frame2Ball2 = new JPanel();
		user3Frame2Ball2.setLayout(null);
		user3Frame2Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame2Ball2.setBounds(59, 2, 24, 26);
		user3Frame2.add(user3Frame2Ball2);
		
		JTextPane user3Frame2BallTxt2 = new JTextPane();
		user3Frame2BallTxt2.setEditable(false);
		user3Frame2BallTxt2.setBackground(Color.WHITE);
		user3Frame2BallTxt2.setBounds(3, 5, 18, 18);
		user3Frame2Ball2.add(user3Frame2BallTxt2);
		
		JPanel user3Frame3 = new JPanel();
		user3Frame3.setLayout(null);
		user3Frame3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame3.setBounds(688, 222, 83, 81);
		frame.getContentPane().add(user3Frame3);
		
		JPanel user3Frame3Ball1 = new JPanel();
		user3Frame3Ball1.setLayout(null);
		user3Frame3Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame3Ball1.setBounds(36, 2, 24, 26);
		user3Frame3.add(user3Frame3Ball1);
		
		JTextPane user3Frame3BallTxt1 = new JTextPane();
		user3Frame3BallTxt1.setEditable(false);
		user3Frame3BallTxt1.setBackground(Color.WHITE);
		user3Frame3BallTxt1.setBounds(3, 5, 18, 18);
		user3Frame3Ball1.add(user3Frame3BallTxt1);
		
		JTextPane user3Frame3Score = new JTextPane();
		user3Frame3Score.setBackground(SystemColor.menu);
		user3Frame3Score.setBounds(10, 39, 63, 37);
		user3Frame3.add(user3Frame3Score);
		
		JPanel user3Frame3Ball2 = new JPanel();
		user3Frame3Ball2.setLayout(null);
		user3Frame3Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame3Ball2.setBounds(59, 2, 24, 26);
		user3Frame3.add(user3Frame3Ball2);
		
		JTextPane user3Frame3BallTxt2 = new JTextPane();
		user3Frame3BallTxt2.setEditable(false);
		user3Frame3BallTxt2.setBackground(Color.WHITE);
		user3Frame3BallTxt2.setBounds(3, 5, 18, 18);
		user3Frame3Ball2.add(user3Frame3BallTxt2);
		
		JPanel user3Frame4 = new JPanel();
		user3Frame4.setLayout(null);
		user3Frame4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame4.setBounds(773, 222, 83, 81);
		frame.getContentPane().add(user3Frame4);
		
		JPanel user3Frame4Ball1 = new JPanel();
		user3Frame4Ball1.setLayout(null);
		user3Frame4Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame4Ball1.setBounds(36, 2, 24, 26);
		user3Frame4.add(user3Frame4Ball1);
		
		JTextPane user3Frame4BallTxt1 = new JTextPane();
		user3Frame4BallTxt1.setEditable(false);
		user3Frame4BallTxt1.setBackground(Color.WHITE);
		user3Frame4BallTxt1.setBounds(3, 5, 18, 18);
		user3Frame4Ball1.add(user3Frame4BallTxt1);
		
		JTextPane user3Frame4Score = new JTextPane();
		user3Frame4Score.setBackground(SystemColor.menu);
		user3Frame4Score.setBounds(10, 39, 63, 37);
		user3Frame4.add(user3Frame4Score);
		
		JPanel user3Frame4Ball2 = new JPanel();
		user3Frame4Ball2.setLayout(null);
		user3Frame4Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame4Ball2.setBounds(59, 2, 24, 26);
		user3Frame4.add(user3Frame4Ball2);
		
		JTextPane user3Frame4BallTxt2 = new JTextPane();
		user3Frame4BallTxt2.setEditable(false);
		user3Frame4BallTxt2.setBackground(Color.WHITE);
		user3Frame4BallTxt2.setBounds(3, 5, 18, 18);
		user3Frame4Ball2.add(user3Frame4BallTxt2);
		
		JPanel user3Frame5 = new JPanel();
		user3Frame5.setLayout(null);
		user3Frame5.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame5.setBounds(858, 222, 83, 81);
		frame.getContentPane().add(user3Frame5);
		
		JPanel user3Frame5Ball1 = new JPanel();
		user3Frame5Ball1.setLayout(null);
		user3Frame5Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame5Ball1.setBounds(36, 2, 24, 26);
		user3Frame5.add(user3Frame5Ball1);
		
		JTextPane user3Frame5BallTxt1 = new JTextPane();
		user3Frame5BallTxt1.setEditable(false);
		user3Frame5BallTxt1.setBackground(Color.WHITE);
		user3Frame5BallTxt1.setBounds(3, 5, 18, 18);
		user3Frame5Ball1.add(user3Frame5BallTxt1);
		
		JTextPane user3Frame5Score = new JTextPane();
		user3Frame5Score.setBackground(SystemColor.menu);
		user3Frame5Score.setBounds(10, 39, 63, 37);
		user3Frame5.add(user3Frame5Score);
		
		JPanel user3Frame5Ball2 = new JPanel();
		user3Frame5Ball2.setLayout(null);
		user3Frame5Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame5Ball2.setBounds(59, 2, 24, 26);
		user3Frame5.add(user3Frame5Ball2);
		
		JTextPane user3Frame5BallTxt2 = new JTextPane();
		user3Frame5BallTxt2.setEditable(false);
		user3Frame5BallTxt2.setBackground(Color.WHITE);
		user3Frame5BallTxt2.setBounds(3, 5, 18, 18);
		user3Frame5Ball2.add(user3Frame5BallTxt2);
		
		JPanel user3Frame6 = new JPanel();
		user3Frame6.setLayout(null);
		user3Frame6.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame6.setBounds(943, 222, 83, 81);
		frame.getContentPane().add(user3Frame6);
		
		JPanel user3Frame6Ball1 = new JPanel();
		user3Frame6Ball1.setLayout(null);
		user3Frame6Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame6Ball1.setBounds(36, 2, 24, 26);
		user3Frame6.add(user3Frame6Ball1);
		
		JTextPane user3Frame6BallTxt1 = new JTextPane();
		user3Frame6BallTxt1.setEditable(false);
		user3Frame6BallTxt1.setBackground(Color.WHITE);
		user3Frame6BallTxt1.setBounds(3, 5, 18, 18);
		user3Frame6Ball1.add(user3Frame6BallTxt1);
		
		JTextPane user3Frame6Score = new JTextPane();
		user3Frame6Score.setBackground(SystemColor.menu);
		user3Frame6Score.setBounds(10, 39, 63, 37);
		user3Frame6.add(user3Frame6Score);
		
		JPanel user3Frame6Ball2 = new JPanel();
		user3Frame6Ball2.setLayout(null);
		user3Frame6Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame6Ball2.setBounds(59, 2, 24, 26);
		user3Frame6.add(user3Frame6Ball2);
		
		JTextPane user3Frame6BallTxt2 = new JTextPane();
		user3Frame6BallTxt2.setEditable(false);
		user3Frame6BallTxt2.setBackground(Color.WHITE);
		user3Frame6BallTxt2.setBounds(3, 5, 18, 18);
		user3Frame6Ball2.add(user3Frame6BallTxt2);
		
		JPanel user3Frame7 = new JPanel();
		user3Frame7.setLayout(null);
		user3Frame7.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame7.setBounds(1027, 222, 83, 81);
		frame.getContentPane().add(user3Frame7);
		
		JPanel user3Frame7Ball1 = new JPanel();
		user3Frame7Ball1.setLayout(null);
		user3Frame7Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame7Ball1.setBounds(36, 2, 24, 26);
		user3Frame7.add(user3Frame7Ball1);
		
		JTextPane user3Frame7BallTxt1 = new JTextPane();
		user3Frame7BallTxt1.setEditable(false);
		user3Frame7BallTxt1.setBackground(Color.WHITE);
		user3Frame7BallTxt1.setBounds(3, 5, 18, 18);
		user3Frame7Ball1.add(user3Frame7BallTxt1);
		
		JTextPane user3Frame7Score = new JTextPane();
		user3Frame7Score.setBackground(SystemColor.menu);
		user3Frame7Score.setBounds(10, 39, 63, 37);
		user3Frame7.add(user3Frame7Score);
		
		JPanel user3Frame7Ball2 = new JPanel();
		user3Frame7Ball2.setLayout(null);
		user3Frame7Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame7Ball2.setBounds(59, 2, 24, 26);
		user3Frame7.add(user3Frame7Ball2);
		
		JTextPane user3Frame7BallTxt2 = new JTextPane();
		user3Frame7BallTxt2.setEditable(false);
		user3Frame7BallTxt2.setBackground(Color.WHITE);
		user3Frame7BallTxt2.setBounds(3, 5, 18, 18);
		user3Frame7Ball2.add(user3Frame7BallTxt2);
		
		JPanel user3Frame8 = new JPanel();
		user3Frame8.setLayout(null);
		user3Frame8.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame8.setBounds(1112, 222, 83, 81);
		frame.getContentPane().add(user3Frame8);
		
		JPanel user3Frame8Ball1 = new JPanel();
		user3Frame8Ball1.setLayout(null);
		user3Frame8Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame8Ball1.setBounds(36, 2, 24, 26);
		user3Frame8.add(user3Frame8Ball1);
		
		JTextPane user3Frame8BallTxt1 = new JTextPane();
		user3Frame8BallTxt1.setEditable(false);
		user3Frame8BallTxt1.setBackground(Color.WHITE);
		user3Frame8BallTxt1.setBounds(3, 5, 18, 18);
		user3Frame8Ball1.add(user3Frame8BallTxt1);
		
		JTextPane user3Frame8Score = new JTextPane();
		user3Frame8Score.setBackground(SystemColor.menu);
		user3Frame8Score.setBounds(10, 39, 63, 37);
		user3Frame8.add(user3Frame8Score);
		
		JPanel user3Frame8Ball2 = new JPanel();
		user3Frame8Ball2.setLayout(null);
		user3Frame8Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame8Ball2.setBounds(59, 2, 24, 26);
		user3Frame8.add(user3Frame8Ball2);
		
		JTextPane user3Frame8BallTxt2 = new JTextPane();
		user3Frame8BallTxt2.setEditable(false);
		user3Frame8BallTxt2.setBackground(Color.WHITE);
		user3Frame8BallTxt2.setBounds(3, 5, 18, 18);
		user3Frame8Ball2.add(user3Frame8BallTxt2);
		
		JPanel user3Frame9 = new JPanel();
		user3Frame9.setLayout(null);
		user3Frame9.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame9.setBounds(1197, 222, 83, 81);
		frame.getContentPane().add(user3Frame9);
		
		JPanel user3Frame9Ball1 = new JPanel();
		user3Frame9Ball1.setLayout(null);
		user3Frame9Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame9Ball1.setBounds(36, 2, 24, 26);
		user3Frame9.add(user3Frame9Ball1);
		
		JTextPane user3Frame9BallTxt1 = new JTextPane();
		user3Frame9BallTxt1.setEditable(false);
		user3Frame9BallTxt1.setBackground(Color.WHITE);
		user3Frame9BallTxt1.setBounds(3, 5, 18, 18);
		user3Frame9Ball1.add(user3Frame9BallTxt1);
		
		JTextPane user3Frame9Score = new JTextPane();
		user3Frame9Score.setBackground(SystemColor.menu);
		user3Frame9Score.setBounds(10, 39, 63, 37);
		user3Frame9.add(user3Frame9Score);
		
		JPanel user3Frame9Ball2 = new JPanel();
		user3Frame9Ball2.setLayout(null);
		user3Frame9Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame9Ball2.setBounds(59, 2, 24, 26);
		user3Frame9.add(user3Frame9Ball2);
		
		JTextPane user3Frame9BallTxt2 = new JTextPane();
		user3Frame9BallTxt2.setEditable(false);
		user3Frame9BallTxt2.setBackground(Color.WHITE);
		user3Frame9BallTxt2.setBounds(3, 5, 18, 18);
		user3Frame9Ball2.add(user3Frame9BallTxt2);
		
		JPanel user3Frame10 = new JPanel();
		user3Frame10.setLayout(null);
		user3Frame10.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user3Frame10.setBounds(1282, 222, 106, 81);
		frame.getContentPane().add(user3Frame10);
		
		JPanel user3Frame10Ball1 = new JPanel();
		user3Frame10Ball1.setLayout(null);
		user3Frame10Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame10Ball1.setBounds(36, 2, 24, 26);
		user3Frame10.add(user3Frame10Ball1);
		
		JTextPane textPane_89 = new JTextPane();
		textPane_89.setEditable(false);
		textPane_89.setBackground(Color.WHITE);
		textPane_89.setBounds(3, 5, 18, 18);
		user3Frame10Ball1.add(textPane_89);
		
		JTextPane user3Frame10Score = new JTextPane();
		user3Frame10Score.setBackground(SystemColor.menu);
		user3Frame10Score.setBounds(10, 39, 63, 37);
		user3Frame10.add(user3Frame10Score);
		
		JPanel user3Frame10Ball2 = new JPanel();
		user3Frame10Ball2.setLayout(null);
		user3Frame10Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame10Ball2.setBounds(59, 2, 24, 26);
		user3Frame10.add(user3Frame10Ball2);
		
		JTextPane textPane_91 = new JTextPane();
		textPane_91.setEditable(false);
		textPane_91.setBackground(Color.WHITE);
		textPane_91.setBounds(3, 5, 18, 18);
		user3Frame10Ball2.add(textPane_91);
		
		JPanel user3Frame10Ball3 = new JPanel();
		user3Frame10Ball3.setLayout(null);
		user3Frame10Ball3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user3Frame10Ball3.setBounds(82, 2, 24, 26);
		user3Frame10.add(user3Frame10Ball3);
		
		JTextPane textPane_92 = new JTextPane();
		textPane_92.setEditable(false);
		textPane_92.setBackground(Color.WHITE);
		textPane_92.setBounds(3, 5, 18, 18);
		user3Frame10Ball3.add(textPane_92);
		
		JPanel user4Frame1 = new JPanel();
		user4Frame1.setLayout(null);
		user4Frame1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame1.setBounds(518, 308, 83, 81);
		frame.getContentPane().add(user4Frame1);
		
		JPanel user4Frame1Ball1 = new JPanel();
		user4Frame1Ball1.setLayout(null);
		user4Frame1Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame1Ball1.setBounds(36, 2, 24, 26);
		user4Frame1.add(user4Frame1Ball1);
		
		JTextPane user4Frame1BallTxt1 = new JTextPane();
		user4Frame1BallTxt1.setEditable(false);
		user4Frame1BallTxt1.setBackground(Color.WHITE);
		user4Frame1BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame1Ball1.add(user4Frame1BallTxt1);
		
		JTextPane textPane_94 = new JTextPane();
		textPane_94.setBackground(SystemColor.menu);
		textPane_94.setBounds(10, 39, 63, 37);
		user4Frame1.add(textPane_94);
		
		JPanel user4Frame1Ball2 = new JPanel();
		user4Frame1Ball2.setLayout(null);
		user4Frame1Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame1Ball2.setBounds(59, 2, 24, 26);
		user4Frame1.add(user4Frame1Ball2);
		
		JTextPane user4Frame1BallTxt2 = new JTextPane();
		user4Frame1BallTxt2.setEditable(false);
		user4Frame1BallTxt2.setBackground(Color.WHITE);
		user4Frame1BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame1Ball2.add(user4Frame1BallTxt2);
		
		JLabel lblEloy = new JLabel("Eloy");
		lblEloy.setHorizontalAlignment(SwingConstants.CENTER);
		lblEloy.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblEloy.setBounds(374, 308, 134, 81);
		frame.getContentPane().add(lblEloy);
		
		JPanel user4Frame2 = new JPanel();
		user4Frame2.setLayout(null);
		user4Frame2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame2.setBounds(603, 308, 83, 81);
		frame.getContentPane().add(user4Frame2);
		
		JPanel user4Frame2Ball1 = new JPanel();
		user4Frame2Ball1.setLayout(null);
		user4Frame2Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame2Ball1.setBounds(36, 2, 24, 26);
		user4Frame2.add(user4Frame2Ball1);
		
		JTextPane user4Frame2BallTxt1 = new JTextPane();
		user4Frame2BallTxt1.setEditable(false);
		user4Frame2BallTxt1.setBackground(Color.WHITE);
		user4Frame2BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame2Ball1.add(user4Frame2BallTxt1);
		
		JTextPane textPane_97 = new JTextPane();
		textPane_97.setBackground(SystemColor.menu);
		textPane_97.setBounds(10, 39, 63, 37);
		user4Frame2.add(textPane_97);
		
		JPanel user4Frame2Ball2 = new JPanel();
		user4Frame2Ball2.setLayout(null);
		user4Frame2Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame2Ball2.setBounds(59, 2, 24, 26);
		user4Frame2.add(user4Frame2Ball2);
		
		JTextPane user4Frame2BallTxt2 = new JTextPane();
		user4Frame2BallTxt2.setEditable(false);
		user4Frame2BallTxt2.setBackground(Color.WHITE);
		user4Frame2BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame2Ball2.add(user4Frame2BallTxt2);
		
		JPanel user4Frame3 = new JPanel();
		user4Frame3.setLayout(null);
		user4Frame3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame3.setBounds(688, 308, 83, 81);
		frame.getContentPane().add(user4Frame3);
		
		JPanel user4Frame3Ball1 = new JPanel();
		user4Frame3Ball1.setLayout(null);
		user4Frame3Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame3Ball1.setBounds(36, 2, 24, 26);
		user4Frame3.add(user4Frame3Ball1);
		
		JTextPane user4Frame3BallTxt1 = new JTextPane();
		user4Frame3BallTxt1.setEditable(false);
		user4Frame3BallTxt1.setBackground(Color.WHITE);
		user4Frame3BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame3Ball1.add(user4Frame3BallTxt1);
		
		JTextPane textPane_100 = new JTextPane();
		textPane_100.setBackground(SystemColor.menu);
		textPane_100.setBounds(10, 39, 63, 37);
		user4Frame3.add(textPane_100);
		
		JPanel user4Frame3Ball2 = new JPanel();
		user4Frame3Ball2.setLayout(null);
		user4Frame3Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame3Ball2.setBounds(59, 2, 24, 26);
		user4Frame3.add(user4Frame3Ball2);
		
		JTextPane user4Frame3BallTxt2 = new JTextPane();
		user4Frame3BallTxt2.setEditable(false);
		user4Frame3BallTxt2.setBackground(Color.WHITE);
		user4Frame3BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame3Ball2.add(user4Frame3BallTxt2);
		
		JPanel user4Frame4 = new JPanel();
		user4Frame4.setLayout(null);
		user4Frame4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame4.setBounds(773, 308, 83, 81);
		frame.getContentPane().add(user4Frame4);
		
		JPanel user4Frame4Ball1 = new JPanel();
		user4Frame4Ball1.setLayout(null);
		user4Frame4Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame4Ball1.setBounds(36, 2, 24, 26);
		user4Frame4.add(user4Frame4Ball1);
		
		JTextPane user4Frame4BallTxt1 = new JTextPane();
		user4Frame4BallTxt1.setEditable(false);
		user4Frame4BallTxt1.setBackground(Color.WHITE);
		user4Frame4BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame4Ball1.add(user4Frame4BallTxt1);
		
		JTextPane textPane_103 = new JTextPane();
		textPane_103.setBackground(SystemColor.menu);
		textPane_103.setBounds(10, 39, 63, 37);
		user4Frame4.add(textPane_103);
		
		JPanel user4Frame4Ball2 = new JPanel();
		user4Frame4Ball2.setLayout(null);
		user4Frame4Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame4Ball2.setBounds(59, 2, 24, 26);
		user4Frame4.add(user4Frame4Ball2);
		
		JTextPane user4Frame4BallTxt2 = new JTextPane();
		user4Frame4BallTxt2.setEditable(false);
		user4Frame4BallTxt2.setBackground(Color.WHITE);
		user4Frame4BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame4Ball2.add(user4Frame4BallTxt2);
		
		JPanel user4Frame5 = new JPanel();
		user4Frame5.setLayout(null);
		user4Frame5.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame5.setBounds(858, 308, 83, 81);
		frame.getContentPane().add(user4Frame5);
		
		JPanel user4Frame5Ball1 = new JPanel();
		user4Frame5Ball1.setLayout(null);
		user4Frame5Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame5Ball1.setBounds(36, 2, 24, 26);
		user4Frame5.add(user4Frame5Ball1);
		
		JTextPane user4Frame5BallTxt1 = new JTextPane();
		user4Frame5BallTxt1.setEditable(false);
		user4Frame5BallTxt1.setBackground(Color.WHITE);
		user4Frame5BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame5Ball1.add(user4Frame5BallTxt1);
		
		JTextPane textPane_106 = new JTextPane();
		textPane_106.setBackground(SystemColor.menu);
		textPane_106.setBounds(10, 39, 63, 37);
		user4Frame5.add(textPane_106);
		
		JPanel user4Frame5Ball2 = new JPanel();
		user4Frame5Ball2.setLayout(null);
		user4Frame5Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame5Ball2.setBounds(59, 2, 24, 26);
		user4Frame5.add(user4Frame5Ball2);
		
		JTextPane user4Frame5BallTxt2 = new JTextPane();
		user4Frame5BallTxt2.setEditable(false);
		user4Frame5BallTxt2.setBackground(Color.WHITE);
		user4Frame5BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame5Ball2.add(user4Frame5BallTxt2);
		
		JPanel user4Frame6 = new JPanel();
		user4Frame6.setLayout(null);
		user4Frame6.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame6.setBounds(943, 308, 83, 81);
		frame.getContentPane().add(user4Frame6);
		
		JPanel user4Frame6Ball1 = new JPanel();
		user4Frame6Ball1.setLayout(null);
		user4Frame6Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame6Ball1.setBounds(36, 2, 24, 26);
		user4Frame6.add(user4Frame6Ball1);
		
		JTextPane user4Frame6BallTxt1 = new JTextPane();
		user4Frame6BallTxt1.setEditable(false);
		user4Frame6BallTxt1.setBackground(Color.WHITE);
		user4Frame6BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame6Ball1.add(user4Frame6BallTxt1);
		
		JTextPane textPane_109 = new JTextPane();
		textPane_109.setBackground(SystemColor.menu);
		textPane_109.setBounds(10, 39, 63, 37);
		user4Frame6.add(textPane_109);
		
		JPanel user4Frame6Ball2 = new JPanel();
		user4Frame6Ball2.setLayout(null);
		user4Frame6Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame6Ball2.setBounds(59, 2, 24, 26);
		user4Frame6.add(user4Frame6Ball2);
		
		JTextPane user4Frame6BallTxt2 = new JTextPane();
		user4Frame6BallTxt2.setEditable(false);
		user4Frame6BallTxt2.setBackground(Color.WHITE);
		user4Frame6BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame6Ball2.add(user4Frame6BallTxt2);
		
		JPanel user4Frame7 = new JPanel();
		user4Frame7.setLayout(null);
		user4Frame7.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame7.setBounds(1027, 308, 83, 81);
		frame.getContentPane().add(user4Frame7);
		
		JPanel user4Frame7Ball1 = new JPanel();
		user4Frame7Ball1.setLayout(null);
		user4Frame7Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame7Ball1.setBounds(36, 2, 24, 26);
		user4Frame7.add(user4Frame7Ball1);
		
		JTextPane user4Frame7BallTxt1 = new JTextPane();
		user4Frame7BallTxt1.setEditable(false);
		user4Frame7BallTxt1.setBackground(Color.WHITE);
		user4Frame7BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame7Ball1.add(user4Frame7BallTxt1);
		
		JTextPane textPane_112 = new JTextPane();
		textPane_112.setBackground(SystemColor.menu);
		textPane_112.setBounds(10, 39, 63, 37);
		user4Frame7.add(textPane_112);
		
		JPanel user4Frame7Ball2 = new JPanel();
		user4Frame7Ball2.setLayout(null);
		user4Frame7Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame7Ball2.setBounds(59, 2, 24, 26);
		user4Frame7.add(user4Frame7Ball2);
		
		JTextPane user4Frame7BallTxt2 = new JTextPane();
		user4Frame7BallTxt2.setEditable(false);
		user4Frame7BallTxt2.setBackground(Color.WHITE);
		user4Frame7BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame7Ball2.add(user4Frame7BallTxt2);
		
		JPanel user4Frame8 = new JPanel();
		user4Frame8.setLayout(null);
		user4Frame8.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame8.setBounds(1112, 308, 83, 81);
		frame.getContentPane().add(user4Frame8);
		
		JPanel user4Frame8Ball1 = new JPanel();
		user4Frame8Ball1.setLayout(null);
		user4Frame8Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame8Ball1.setBounds(36, 2, 24, 26);
		user4Frame8.add(user4Frame8Ball1);
		
		JTextPane user4Frame8BallTxt1 = new JTextPane();
		user4Frame8BallTxt1.setEditable(false);
		user4Frame8BallTxt1.setBackground(Color.WHITE);
		user4Frame8BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame8Ball1.add(user4Frame8BallTxt1);
		
		JTextPane textPane_115 = new JTextPane();
		textPane_115.setBackground(SystemColor.menu);
		textPane_115.setBounds(10, 39, 63, 37);
		user4Frame8.add(textPane_115);
		
		JPanel user4Frame8Ball2 = new JPanel();
		user4Frame8Ball2.setLayout(null);
		user4Frame8Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame8Ball2.setBounds(59, 2, 24, 26);
		user4Frame8.add(user4Frame8Ball2);
		
		JTextPane user4Frame8BallTxt2 = new JTextPane();
		user4Frame8BallTxt2.setEditable(false);
		user4Frame8BallTxt2.setBackground(Color.WHITE);
		user4Frame8BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame8Ball2.add(user4Frame8BallTxt2);
		
		JPanel user4Frame9 = new JPanel();
		user4Frame9.setLayout(null);
		user4Frame9.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame9.setBounds(1197, 308, 83, 81);
		frame.getContentPane().add(user4Frame9);
		
		JPanel user4Frame9Ball1 = new JPanel();
		user4Frame9Ball1.setLayout(null);
		user4Frame9Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame9Ball1.setBounds(36, 2, 24, 26);
		user4Frame9.add(user4Frame9Ball1);
		
		JTextPane user4Frame9BallTxt1 = new JTextPane();
		user4Frame9BallTxt1.setEditable(false);
		user4Frame9BallTxt1.setBackground(Color.WHITE);
		user4Frame9BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame9Ball1.add(user4Frame9BallTxt1);
		
		JTextPane textPane_118 = new JTextPane();
		textPane_118.setBackground(SystemColor.menu);
		textPane_118.setBounds(10, 39, 63, 37);
		user4Frame9.add(textPane_118);
		
		JPanel user4Frame9Ball2 = new JPanel();
		user4Frame9Ball2.setLayout(null);
		user4Frame9Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame9Ball2.setBounds(59, 2, 24, 26);
		user4Frame9.add(user4Frame9Ball2);
		
		JTextPane user4Frame9BallTxt2 = new JTextPane();
		user4Frame9BallTxt2.setEditable(false);
		user4Frame9BallTxt2.setBackground(Color.WHITE);
		user4Frame9BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame9Ball2.add(user4Frame9BallTxt2);
		
		JPanel user4Frame10 = new JPanel();
		user4Frame10.setLayout(null);
		user4Frame10.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		user4Frame10.setBounds(1282, 308, 106, 81);
		frame.getContentPane().add(user4Frame10);
		
		JPanel user4Frame10Ball1 = new JPanel();
		user4Frame10Ball1.setLayout(null);
		user4Frame10Ball1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame10Ball1.setBounds(36, 2, 24, 26);
		user4Frame10.add(user4Frame10Ball1);
		
		JTextPane user4Frame10BallTxt1 = new JTextPane();
		user4Frame10BallTxt1.setEditable(false);
		user4Frame10BallTxt1.setBackground(Color.WHITE);
		user4Frame10BallTxt1.setBounds(3, 5, 18, 18);
		user4Frame10Ball1.add(user4Frame10BallTxt1);
		user4Frame10.add(user4Frame10BallTxt1);
		
		JPanel user4Frame10Ball2 = new JPanel();
		user4Frame10Ball2.setLayout(null);
		user4Frame10Ball2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame10Ball2.setBounds(59, 2, 24, 26);
		user4Frame10.add(user4Frame10Ball2);
		
		JTextPane user4Frame10BallTxt2 = new JTextPane();
		user4Frame10BallTxt2.setEditable(false);
		user4Frame10BallTxt2.setBackground(Color.WHITE);
		user4Frame10BallTxt2.setBounds(3, 5, 18, 18);
		user4Frame10Ball2.add(user4Frame10BallTxt2);
		
		JPanel user4Frame10Ball3 = new JPanel();
		user4Frame10Ball3.setLayout(null);
		user4Frame10Ball3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		user4Frame10Ball3.setBounds(82, 2, 24, 26);
		user4Frame10.add(user4Frame10Ball3);
		
		JTextPane user4Frame10BallTxt3 = new JTextPane();
		user4Frame10BallTxt3.setEditable(false);
		user4Frame10BallTxt3.setBackground(Color.WHITE);
		user4Frame10BallTxt3.setBounds(3, 5, 18, 18);
		user4Frame10Ball3.add(user4Frame10BallTxt3);
	}
}
