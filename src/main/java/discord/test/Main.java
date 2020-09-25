package discord.test;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws LoginException, InterruptedException {
		Scanner scanner;
		String currentDirectory = System.getProperty("user.dir");
		File file = new File(currentDirectory + "\\token.txt");
		try {
			scanner = new Scanner(new FileInputStream(file));
			new Bot(scanner.nextLine());
		} catch (FileNotFoundException | NoSuchElementException e) {
			System.err.println(Arrays.toString(e.getStackTrace()));
			System.exit(0);
		}

//		or this
//
//		String token="your token here";
//		new Bot(token);
	}
}
