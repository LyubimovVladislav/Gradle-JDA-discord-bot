package discord.test.view;

public abstract class NBStringConverter {
	
	public static StringBuilder convert(String[][] message, String[][] message2) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 20; j++) {
				if (j < 10) {
					if (message[i][j].equals("X")) {
						stringBuilder.append("1");
						continue;
					}
					if (message[i][j].equals("*")) {
						stringBuilder.append("0");
						continue;
					}
				} else {
					if (j - 10 == 0)
						stringBuilder.append("  ");
					if (message2[i][j - 10].equals("X")) {
						stringBuilder.append("1");
						continue;
					}
					if (message2[i][j - 10].equals("*")) {
						stringBuilder.append("0");
						continue;
					}
				}
				stringBuilder.append("x");
				
			}
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}
	
	public static StringBuilder convert(String[][] message) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (message[i][j].equals("X")) {
					stringBuilder.append(":ship:");
					continue;
				}
				if (message[i][j].equals("*")) {
					stringBuilder.append(":o2:");
					continue;
				}
				stringBuilder.append(":fog:");
			}
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}
}
