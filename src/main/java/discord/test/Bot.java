package discord.test;

import discord.test.eventListeners.PrivateEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import discord.test.eventListeners.BoopEvent;


import discord.test.eventListeners.GuildMessageEvent;

import javax.security.auth.login.LoginException;

public class Bot {
	public final static String prefix = "!";
	JDA jda;
	
	public Bot(String token) throws LoginException, InterruptedException{
		
		jda = JDABuilder.createDefault(token).build();
		start();
	}
	
	private void start() throws InterruptedException {
		jda.addEventListener(new GuildMessageEvent(), new PrivateEvent(), new BoopEvent());
		jda.getPresence().setActivity(Activity.listening("your commands"));
		jda.awaitReady();
	}
}
