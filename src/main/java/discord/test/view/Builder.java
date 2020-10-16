package discord.test.view;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public abstract class Builder {
	public static MessageEmbed buildEmbed(String text, Color color) {
		EmbedBuilder msg = new EmbedBuilder();
		msg.setColor(color);
		msg.setDescription(text);
		return msg.build();
	}
	
}
