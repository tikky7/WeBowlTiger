
public class Score {
	public int[] calculateScore(int[][] allfrme) {
		int frme[] = new int[10];
		if (allfrme[0][0] + allfrme[0][1] == 10) {
			frme[0] = 10 + allfrme[0][1];
		}
		else {
			frme[0] = allfrme[0][0] + allfrme[1][0];
		}

		// middle
		if (allfrme[0][0] == 10) {
			if (allfrme[0][1] == 10)
				frme[0] = 20 + allfrme[0][2];
			else
				frme[0] = 10 + allfrme[0][1] + allfrme[1][1];
		}

		// end
		for (int j = 1; j < 10; j++) {

			if (allfrme[0][j] == 10) {
				if (allfrme[0][j + 1] == 10)
					frme[j] = frme[j - 1] + 20 + allfrme[0][j + 2];
				else
					frme[j] = frme[j - 1] + 10 + allfrme[0][j + 1]
							+ allfrme[1][j + 1];
				continue;
			}

			if (allfrme[0][j] + allfrme[1][j] == 10)
				frme[j] = frme[j - 1] + 10 + allfrme[0][j + 1];
			else
				frme[j] = frme[j - 1] + allfrme[0][j] + allfrme[1][j];

		}
		return frme;
	}
}
