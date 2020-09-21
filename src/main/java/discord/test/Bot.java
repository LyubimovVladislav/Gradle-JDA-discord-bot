package discord.test;

import discord.test.eventListener.PrivateEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import discord.test.eventListener.BoopEvent;


import discord.test.eventListener.GuildMessageEvent;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class Bot {
	private final static String token = "NzU2NTMwMDI0MzQ5MzAyODI0.X2TLfQ.QO142LvbMcH8ReA-oK26lD2U7kA";
	public final static String prefix = "!";
	
	public Bot() throws LoginException, InterruptedException {
	JDA jda = JDABuilder.createDefault(token).build();
	jda.addEventListener(new GuildMessageEvent(), new PrivateEvent(), new BoopEvent());
	jda.getPresence().setActivity(Activity.listening("your commands"));
	jda.awaitReady();
	}
	
}
