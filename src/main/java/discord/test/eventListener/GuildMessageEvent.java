package discord.test.eventListener;

import discord.test.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import discord.test.*;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class GuildMessageEvent extends ListenerAdapter {
	private final HashMap<String, Member> ticTacToePL = new HashMap<>();
	private final HashMap<Member, TargetListCSGO> csgoHash = new HashMap<>();
	
	private final HashMap<Players, TTT> startedTTT = new HashMap<>();
	
	private TextChannel channel;
	private Member caller;
	
	private final String[] answers = {"As I see it, yes.", "Ask again later.", "Better not tell you now.", "Cannot predict now.",
			"Concentrate and ask again.", "Don’t count on it.", "It is certain.", "It is decidedly so.",
			"Most likely.", "My reply is no.", "My sources say no.", "Outlook not so good.", "Outlook good.",
			"Reply hazy, try again.", "Signs point to yes.", "Very doubtful.", "Without a doubt.", "Yes.",
			"Yes – definitely.", "You may rely on it."};
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent eventGuild) {
		caller = eventGuild.getMember();
		channel= eventGuild.getChannel();
		
		assert caller != null;
		if (caller.getUser().isBot())
			return;
		
		
		System.out.printf("[%s][%s] %s: %s\n", eventGuild.getGuild().getName(),
				channel.getName(), caller.getEffectiveName(),
				eventGuild.getMessage().getContentRaw());
		
		String[] message = eventGuild.getMessage().getContentRaw().toLowerCase().split("\\s+");
		
		if (message[0].startsWith(Bot.prefix)) {
			switch (message[0]) {
				case "!ttt":
					handleTTT(message);
					break;
				case "!accept":
					handleAccept(eventGuild);
					break;
				case "!halp":
					channel.sendMessage(buildEmbed("!halp - get all commands\n!ttt - play a game of Tic-Tac-Toe with your friend\n!csgo - Test your luck with spray in csgo\n!8-ball if you want to know the answer!", Color.blue)).queue();
					break;
				
				case "!csgo":
					handleCsgo(message,eventGuild);
					break;
				case "!8-ball":
					channel.sendMessage(caller.getAsMention() + ", " + answers[(int) (Math.random() * answers.length)]).queue();


//		    	case "!rainbow":
//		    		try {
//		    			changeColours(eventGuild);
//		    		} catch (InterruptedException e) {
//		    			e.printStackTrace();
//		    		}
//		    		eventGuild.getGuild().addRoleToMember(caller,eventGuild.getGuild().getRoleById("404033712573906955")).complete();
//		    		break;
//		    	case "!stop":
////	    			if (caller.getRoles().contains(eventGuild.getGuild().getRoleById("404033712573906955")))
////	    			eventGuild.getGuild().removeRoleFromMember(caller,  eventGuild.getGuild().getRoleById("404033712573906955")).complete();
//		    		break;
//				case "!red":
//					eventGuild.getGuild().getRoleById("404033712573906955").getManager().setColor(Color.RED).queue();
				default:
					break;
			}
		} else if (message.length >= 2)
			switch (message[0] + " " + message[1]) {
				case "boop beep":
				case "boop beep?":
					channel.sendMessage("Beep Boop!").queue();
					break;
				case "beep boop":
				case "beep boop?":
					channel.sendMessage("Boop Beep!").queue();
					break;
				default:
					break;
			}
		else if (!startedTTT.isEmpty() && message[0].matches("[1-9]")) {
			String str = resumeGame(Integer.parseInt(message[0]), caller.getId());
			if (str != null)
				channel.sendMessage(buildEmbed(str, Color.RED)).queue();
			
		} else {
			switch (message[0]) {
				case "hallo":
					channel.sendMessage("Hallo, <@" + caller.getUser().getId() + ">").queue();
					break;
				case "uwu":
					channel.sendMessage("UwU").queue();
					break;
				case "owo":
					channel.sendMessage("OwO").queue();
					break;
				default:
					break;
			}
		}
		
	}
	
	public void playTTT(String player1, String player2, GuildMessageReceivedEvent e) {
		ticTacToePL.remove(player2);
		Players players = new Players(player1, player2);
		TTT ttt = new TTT(player1, player2);
		startedTTT.put(players, ttt);
		e.getChannel().sendMessage(buildEmbed(ttt.getGraphicalMap().append("\n<@").append(ttt.getCurrentPl()).append("> turn").toString(), Color.red)).complete();
	}
	
	public void playCSGO(GuildMessageReceivedEvent e, String target) {
		TargetListCSGO obj;
		if (csgoHash.containsKey(e.getMember())) {
			obj = csgoHash.get(e.getMember());
		} else {
			obj = new TargetListCSGO(new TargetCSGO(target));
			csgoHash.put(e.getMember(), obj);
		}
		ContainerCSGO container = obj.makeHitMessage(target);
//		EmbedBuilder msg = new EmbedBuilder();
//		msg.setColor(Color.RED);
//		msg.setDescription(cont.getMessage());
		e.getChannel().sendMessage(buildEmbed(container.getMessage(), Color.RED)).queue();
//		e.getChannel().sendMessage(cont.getMessage()).queue();


//		if (container.getIsDead())
//			csgoHash.get(e.getMember()).deleteTarget(target);
		csgoHash.get(e.getMember()).deleteTarget(target);
	}
	
	public String resumeGame(int pos, String player) {
		
		Optional<Map.Entry<Players, TTT>> game = startedTTT.entrySet().stream().filter(entry -> entry.getKey().getPlayer1().equals(player) || entry.getKey().getPlayer2().equals(player)).findAny();
		if (game.isEmpty())
			return null;
		
		Players pl = game.get().getKey();
		TTT ttt = game.get().getValue();
		if (!ttt.makeATurn(player, pos))
			return null;
		StringBuilder str = ttt.getGraphicalMap();
		int winner;
		if ((winner = ttt.isGameFinished()) > 0) {
			str.append("\nGame ended with result: ");
			if (winner == 1)
				str.append("<@").append(ttt.getPlayer1()).append("> wins!");
			else if (winner == 2)
				str.append("<@").append(ttt.getPlayer2()).append("> wins!");
			else
				str.append("Tie");
			startedTTT.remove(pl);
		} else
			str.append("\n<@").append(ttt.getCurrentPl()).append("> turn");
		return str.toString();
	}
	
	public MessageEmbed buildEmbed(String text, Color color) {
		EmbedBuilder msg = new EmbedBuilder();
		msg.setColor(color);
		msg.setDescription(text);
		return msg.build();
	}

//	public void changeColours(GuildMessageReceivedEvent event) throws InterruptedException {
//		List<Color> colors = Arrays.asList(Color.MAGENTA, Color.blue, Color.cyan, Color.GREEN, Color.ORANGE, Color.yellow, Color.white, Color.black);
//		for(int i=0;i<360;i++){
//			colors.forEach((c)->{
//				event.getGuild().getRoleById("754879837843226706").getManager().setColor(c).complete();
//				try {
//					TimeUnit.SECONDS.sleep(2);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//
//			});
//			System.out.println(i);
//
//		}
//	}

	
	private void handleTTT(String[] message){
		if (message.length <= 1)
			channel.sendMessage(buildEmbed("Usage: !ttt [start/stop] {@PingYourOpponentIfStart} \nStart the game of Tic Tac Toe with your friend!\nStop the game to cancel your invite.", Color.blue)).queue();
		else if (message[1].equals("start")) {
			if (message.length <= 2 || !message[2].replaceAll("[<>!@]*", "").matches("\\d*"))
				channel.sendMessage(buildEmbed("Usage: !ttt [start/stop] {@PingYourOpponentIfStart}\nWarning: You missed/misspelled 3rd argument!", Color.blue)).queue();
			else {
				String player2 = message[2].replaceAll("[<>!@]*", "");
//							User player2 = eventGuild.getJDA().getUserById(message[2].replaceAll("[<>!@]*",""));
//							Member player2 = eventGuild.getGuild().getMemberById(player2ID);
//							channel.sendMessage(eventGuild.getGuild().getMembers().toString());
//							System.out.println(eventGuild.getGuild().getMembers());
//							Optional<Member> player2opt = eventGuild.getGuild().getMembers().stream().filter((Member member)-> member.getId().equals(player2ID)).findFirst();
//							Member player2;
//							if (player2opt.isPresent()){
//								player2=player2opt.get();
				
				if (startedTTT.entrySet().stream().anyMatch(entry -> entry.getKey().getPlayer1().equals(caller.getId()) || entry.getKey().getPlayer2().equals(caller.getId()))) {
					channel.sendMessage("You cant invite!").queue();
					return;
				}
				
				if (startedTTT.entrySet().stream().anyMatch(entry -> entry.getKey().getPlayer1().equals(player2) || entry.getKey().getPlayer2().equals(player2))) {
					channel.sendMessage("You cant invite!").queue();
					return;
				}
				if (ticTacToePL.containsKey(player2)) {
					channel.sendMessage(buildEmbed(caller.getUser().getAsMention() + " you have already sent an invitation to <@" + player2 + ">", Color.blue)).queue();
					return;
				}

//							else {
//								channel.sendMessage(buildEmbed("I can't find this person on the server",Color.blue)).queue();
//								break;
//							}
				channel.sendMessage(buildEmbed("<@" + player2 + "> was invited to play TicTacToe, type !accept to join!", Color.blue)).queue();
				ticTacToePL.put(player2, caller);
			}
			
		} else if (message[1].equals("stop")) {
			channel.sendMessage(buildEmbed("This command have no purpose yet", Color.blue)).queue();
//						channel.sendMessage(buildEmbed(caller.getAsMention() + ", all your invites are deleted!", Color.blue)).queue();
//						if(ticTacToePL.containsValue(caller.getId()))
//							ticTacToePL.remove();

//						ticTacToePL.forEach((key, value) -> {
//							if (value.getId().equals(caller.getId()))
//								ticTacToePL.remove(key);
//						});
		} else
			channel.sendMessage("Usage: !ttt [start/stop] {@PingYourOpponentIfStart}\nWarning: You misspelled 2nd argument ").queue();
	}
	
	private void handleAccept(GuildMessageReceivedEvent eventGuild){
		if (startedTTT.entrySet().stream().anyMatch(entry -> entry.getKey().getPlayer1().equals(caller.getId()) || entry.getKey().getPlayer2().equals(caller.getId())))
			channel.sendMessage("You cant start the game!").queue();
		else if (ticTacToePL.get(caller.getId()) != null) {
			channel.sendMessage("Game starts!").queue();
//						ticTacToePL.remove(caller.getId());
			playTTT(ticTacToePL.get(caller.getId()).getId(), caller.getId(), eventGuild);
		} else
			channel.sendMessage("<@" + caller.getId() + ">, you haven't been invited to any game").queue();
	}
	
	private void handleCsgo(String[] message, GuildMessageReceivedEvent eventGuild){
		if (message.length <= 1) {
			channel.sendMessage(buildEmbed("Usage: !csgo {@PingYourTarget}", Color.BLUE)).queue();
		} else if (!message[1].replaceAll("[<>!@]*", "").matches("\\d*")) {
			channel.sendMessage(buildEmbed("Usage: !csgo {@PingYourTarget}\nWarning: You missed/misspelled 2rd argument!", Color.blue)).queue();
		} else {
			String target = message[1].replaceAll("[<>!@]*", "");
			playCSGO(eventGuild, target);

//					NumbersCSGO obj;
//					if (csgoHash.containsKey(caller)) {
//						obj = csgoHash.get(caller);
//					}
//					else {
//						obj = new NumbersCSGO();
//						csgoHash.put(caller, obj);
//					}
//					if(obj.isDeadByNewHit()) {
//						channel.sendMessage("You received "+obj.getDmg()+" in "+obj.getHit() +" hits"+"\n"+caller.getUser().getAsMention() + ", you are dead!").queue();
//						csgoHash.remove(caller);
//						break;
//					}
//					channel.sendMessage("You received "+obj.getDmg()+" in "+obj.getHit() +" hits").queue();
		}
	}
}
