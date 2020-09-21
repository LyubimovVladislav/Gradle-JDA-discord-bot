package discord.test.eventListener;


import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.ReceivedMessage;
import org.w3c.dom.events.Event;

import javax.annotation.Nonnull;

//public class BoopEvent extends ListenerAdapter {
//
//	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
//	}
//
//
//}

public class BoopEvent implements EventListener{
	
	@Override
	public void onEvent(@Nonnull GenericEvent event) {
		if (event instanceof ReadyEvent) {
			ReadyEvent readyEvent = (ReadyEvent)event;
			System.out.println("Ready!");
//
//			if (eventGuild.getMember().getUser().isBot())
//				return;
//
//			System.out.printf("[%s][%s] %s: %s\n", eventGuild.getGuild().getName(),
//					eventGuild.getChannel().getName(), eventGuild.getMember().getEffectiveName(),
//					eventGuild.getMessage().getContentRaw());
//
//			String[] message = eventGuild.getMessage().getContentRaw().toLowerCase().split(" ");
//			switch (message[0]){
//				case "!halp": eventGuild.getChannel().sendMessage("There will be halp message soon!").queue();break;
//				case "hallo": eventGuild.getChannel().sendMessage("Hallo, <@" + eventGuild.getMember().getUser().getId() +">").queue();break;
//				default:break;
//			}
//
//			if (message.length >=2)
//			switch (message[0]+" "+message[1]) {
//				case "boop beep": case "boop beep?":eventGuild.getChannel().sendMessage("Beep Boop!").queue();break;
//				case "beep boop": case "beep boop?":eventGuild.getChannel().sendMessage("Boop Beep!").queue();break;
//				default:break;
//			}
//		}
//		else if(event instanceof PrivateMessageReceivedEvent){
//			PrivateMessageReceivedEvent eventDM = (PrivateMessageReceivedEvent) event;
//
//			System.out.printf("[PM] %s: %s\n", eventDM.getAuthor().getName(),
//					eventDM.getMessage().getContentDisplay());
		}
	}
}