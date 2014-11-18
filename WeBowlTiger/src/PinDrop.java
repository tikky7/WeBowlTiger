import java.util.ArrayList;
import java.util.Random;


public class PinDrop {

	private int KnockDown(boolean[] PinsCurrentlyUp, int TotalPinsUp)
	{
		
		Random randomGenerator = new Random();
		//value is between 1-10 below function is exclusive on borders
		int valKnockedDownTotal = randomGenerator.nextInt(TotalPinsUp+1);
		
		return valKnockedDownTotal;
	}
	
	
	public boolean[] getPinsStillStanding (boolean[] PinsCurrentlyUp)
	{
		int totalPinsUp = 0;
		int numPinsToKnockDown = 0;
		ArrayList locations = new ArrayList();
		Random randomGen = new Random();
		int location = 0;
		
		for(int i = 0; i<PinsCurrentlyUp.length; i++)
		{
			if(PinsCurrentlyUp[i]==true)
			{
				totalPinsUp=totalPinsUp+1;
				locations.add(i);
			}
		}
		
		if (totalPinsUp > 0)
		{
			numPinsToKnockDown = KnockDown(PinsCurrentlyUp, totalPinsUp);
			
			if (numPinsToKnockDown == totalPinsUp)
				for(int i = 0; i<10; i++)
					PinsCurrentlyUp[i]=false;
			else
				while(numPinsToKnockDown > 0)
				{
					location = randomGen.nextInt(locations.size());
					PinsCurrentlyUp[(int) locations.get(location)] = false;
					locations.remove(location);
					numPinsToKnockDown--;
				}
			
			return PinsCurrentlyUp;
		}
		else
		{
			return PinsCurrentlyUp;
		}
	}
	
}

	
