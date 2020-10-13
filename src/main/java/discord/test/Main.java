package discord.test;

import discord.test.models.OSValidator;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws LoginException, InterruptedException {
		OSValidator os = new OSValidator();
		
		
		Scanner scanner;
		String currentDirectory = System.getProperty("user.dir");
		File file;
		if (os.getUserOS() == 2 || os.getUserOS() == 3)
			file = new File(currentDirectory + "/token.txt");
		else {
			if (os.getUserOS() != 1)
				System.out.println("Can not figure out what OS you are using. Bot do not support Solaris. \nProgram will continue, assuming os is windows");
			file = new File(currentDirectory + "\\token.txt");
		}
		try {
			scanner = new Scanner(new FileInputStream(file));
			new Bot(scanner.nextLine());
		} catch (FileNotFoundException | NoSuchElementException e) {
			System.err.println(Arrays.toString(e.getStackTrace()));
			System.err.println("Error: file token.txt not found");
			System.exit(0);
		}

//		or this
//
//		String token="your token here";
//		new Bot(token);
	}
	
}
