package discord.test;

import discord.test.eventListener.PrivateEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import discord.test.eventListener.BoopEvent;


import discord.test.eventListener.GuildMessageEvent;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Bot {
	public final static String prefix = "!";
	
	public Bot() throws LoginException, InterruptedException, FileNotFoundException {
		File file = new File("token.txt");
		Scanner scanner = new Scanner(file);
		JDA jda = JDABuilder.createDefault(scanner.next()).build();
		jda.addEventListener(new GuildMessageEvent(), new PrivateEvent(), new BoopEvent());
		jda.getPresence().setActivity(Activity.listening("your commands"));
		jda.awaitReady();
	}
	
}
