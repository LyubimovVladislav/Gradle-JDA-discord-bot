package discord.test.eventListener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberLeaveEvent extends ListenerAdapter {
	public void onGuildLeave(GuildLeaveEvent e){
		EmbedBuilder leave = new EmbedBuilder();
	}
}
