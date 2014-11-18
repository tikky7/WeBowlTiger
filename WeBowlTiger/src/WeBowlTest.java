import static org.junit.Assert.*;

import org.junit.Test;


public class WeBowlTest {

	@Test
	public void allgutter() {
		Score score = new Score();
		assert(score != null);
		int[][] gutter = new int[2][10];
		for( int attempt=0; attempt<2; attempt++ ) 
			for( int frame=0; frame<10; frame++ ) 
				gutter[attempt][frame]=0;
		int tally[]=score.calculateScore(gutter);
		assertEquals(0,tally[9]);
			
	}
	
	@Test
	public void allstrikes() {
		Score score = new Score();
		assert(score != null);
		int[][] strikes = new int[2][12];
		for( int attempt=0; attempt<2; attempt++ ) 
			for( int frame=0; frame<12; frame++ ) 
				strikes[attempt][frame]=10;
		int tally[]=score.calculateScore(strikes);
		assertEquals(300,tally[9]);
	}
	
	@Test
	public void allones() {
		Score score = new Score();
		assert(score != null);
		int[][] bowls = new int[2][10];
		for( int attempt=0; attempt<2; attempt++ ) 
			for( int frame=0; frame<10; frame++ ) 
				bowls[attempt][frame]=1;
		int tally[]=score.calculateScore(bowls);
		assertEquals(20,tally[9]);
			
	}
	
	@Test
	public void lastspares() {
		Score score = new Score();
		assert(score != null);
		int[][] bowls = new int[2][12];
		for( int attempt=0; attempt<2; attempt++ ) 
			for( int frame=0; frame<12; frame++ ) 
				bowls[attempt][frame]=1;
		bowls[0][9]=1;
		bowls[1][9]=9;
		int tally[]=score.calculateScore(bowls);
		assertEquals(29,tally[9]);
			
	}
	
	@Test
	public void hoppingspares() {
		Score score = new Score();
		assert(score != null);
		int[][] bowls = new int[2][12];
		for( int attempt=0; attempt<2; attempt++ ) 
			for( int frame=0; frame<12; frame++ ) {
				if (attempt==0)
				  bowls[attempt][frame]=1;
				else
				  bowls[attempt][frame]=9;
		}
		int tally[]=score.calculateScore(bowls);
		assertEquals(111,tally[9]);
			
	}
	@Test
	public void finalspares() {
		Score score = new Score();
		assert(score != null);
		int[][] bowls = new int[2][12];
		for( int attempt=0; attempt<2; attempt++ ) 
			for( int frame=0; frame<12; frame++ ) {
				if (attempt==0)
				  bowls[attempt][frame]=0;
				else
				  bowls[attempt][frame]=0;
		}
		bowls[0][9]=0;
		bowls[1][9]=10;

		int tally[]=score.calculateScore(bowls);
		assertEquals(20,tally[9]);
			
	}

}
