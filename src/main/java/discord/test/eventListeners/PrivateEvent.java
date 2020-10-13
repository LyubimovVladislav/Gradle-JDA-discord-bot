package discord.test.eventListeners;

import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrivateEvent extends ListenerAdapter {
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent eventDM){
		System.out.printf("[PM] %s: %s\n", eventDM.getAuthor().getName(),
				eventDM.getMessage().getContentDisplay());
	}
}

