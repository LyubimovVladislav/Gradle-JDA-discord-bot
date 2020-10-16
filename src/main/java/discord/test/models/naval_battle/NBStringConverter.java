package discord.test.models.naval_battle;

public abstract class NBStringConverter {
	public static StringBuilder convert(String[][] message, String[][] message2) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 20; j++) {
				if (j < 10) {
					if (message[i][j].equals("X")) {
						stringBuilder.append(":negative_squared_cross_mark:");
						continue;
					}
					if (message[i][j].equals("*")) {
						stringBuilder.append(":red_square:");
						continue;
					}
				} else {
					if (j-10==0)
						stringBuilder.append("  ");
					if (message[i][j - 10].equals("X")) {
						stringBuilder.append(":negative_squared_cross_mark:");
						continue;
					}
					if (message[i][j - 10].equals("*")) {
						stringBuilder.append(":red_square:");
						continue;
					}
				}
				stringBuilder.append(":white_large_square:");
				
			}
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}
}
