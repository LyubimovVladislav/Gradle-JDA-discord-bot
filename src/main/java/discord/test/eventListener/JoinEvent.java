package discord.test.eventListener;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Objects;

public class JoinEvent extends ListenerAdapter {
	public void onGuildMemberJoin(GuildMemberJoinEvent event){
		System.out.println("yooooss");
	}
//		System.out.println(event.getGuild().getRolesByName("Member", true).toString());
//		event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("Member", true).get(1)).queue();
}
	
//	@Override
//	public void onEvent(@Nonnull GenericEvent event) {
//		if(event instanceof GuildMemberJoinEvent) {
//			GuildMemberJoinEvent joinEvent = (GuildMemberJoinEvent) event;
//			System.out.println("it is working");
//			joinEvent.getGuild().addRoleToMember(joinEvent.getMember(), joinEvent.getGuild().getRoleById("756582888438497320")).queue();
//		}
//	}
//}

// "404033712573906955"
