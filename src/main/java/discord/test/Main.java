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
//		Scanner scanner;
//		File file = new File(".\\token.txt");
//		Bot bot;
//		try {
//			scanner = new Scanner(new FileInputStream(file));
//			bot = new Bot(scanner.nextLine());
//		} catch (FileNotFoundException | NoSuchElementException e) {
//			System.err.println(Arrays.toString(e.getStackTrace()));
//			System.exit(0);
//		}

//		or this
		
		String token="your token here";
		new Bot(token);
	}
}
