package discord.test.control;

import discord.test.*;
import discord.test.models.csgo.ContainerCSGO;
import discord.test.models.csgo.TargetCSGO;
import discord.test.models.csgo.TargetListCSGO;
import discord.test.models.Players;
import discord.test.models.naval_battle.Field;
import discord.test.view.Builder;
import discord.test.view.NBStringConverter;
import discord.test.models.naval_battle.Point;
import discord.test.models.ttt.TTT;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import java.awt.*;
import java.util.*;


public class GuildMessageEvent extends ListenerAdapter {
	private final HashMap<String, Member> ticTacToePL = new HashMap<>();
	private final HashMap<String, Member> navalBattlePL = new HashMap<>();
	private final HashMap<Member, TargetListCSGO> csgoHash = new HashMap<>();
	
	private final HashMap<Players, TTT> startedTTT = new HashMap<>();
	private final HashMap<Players, Field> startedNB = new HashMap<>();
	
	
	private TextChannel channel;
	private Member caller;
	
	private final String[] answers = {"As I see it, yes.", "Ask again later.", "Better not tell you now.", "Cannot predict now.",
			"Concentrate and ask again.", "Don’t count on it.", "It is certain.", "It is decidedly so.",
			"Most likely.", "My reply is no.", "My sources say no.", "Outlook not so good.", "Outlook good.",
			"Reply hazy, try again.", "Signs point to yes.", "Very doubtful.", "Without a doubt.", "Yes.",
			"Yes – definitely.", "You may rely on it."};
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent eventGuild) {
		caller = eventGuild.getMember();
		channel = eventGuild.getChannel();
		
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
				case "!nb":
					handleNB(message);
					break;
				case "!accept":
					handleAccept(message);
					break;
				case "!halp":
					channel.sendMessage(Builder.buildEmbed("!halp - get all commands\n!ttt - play a game of Tic-Tac-Toe with your friend\n!csgo - Test your luck with spray in csgo\n!8-ball if you want to know the answer!", Color.blue)).queue();
					break;
				
				case "!csgo":
					handleCsgo(message);
					break;
				case "!8-ball":
					channel.sendMessage(caller.getAsMention() + ", " + answers[(int) (Math.random() * answers.length)]).queue();

//              TODO: try multithreading

//		    	case "!rainbow":
//		    		try {
//		    			changeColours(eventGuild);
//		    		} catch (InterruptedException e) {
//		    			e.printStackTrace();
//		    		}
//		    		eventGuild.getGuild().addRoleToMember(caller,eventGuild.getGuild().getRoleById("404033712573906955")).queue();();
//		    		break;
//		    	case "!stop":
////	    			if (caller.getRoles().contains(eventGuild.getGuild().getRoleById("404033712573906955")))
////	    			eventGuild.getGuild().removeRoleFromMember(caller,  eventGuild.getGuild().getRoleById("404033712573906955")).queue();();
//		    		break;
//				case "!red":
//					eventGuild.getGuild().getRoleById("404033712573906955").getManager().setColor(Color.RED).queue();
				default:
					break;
			}
			
		} else if (!startedNB.isEmpty() && message.length >= 2 && message[0].matches("10|[1-9]") && message[1].matches("10|[1-9]")) {
			resumeNB(Integer.parseInt(message[0]), Integer.parseInt(message[1]));
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
			String str = resumeTTT(Integer.parseInt(message[0]), caller.getId());
			if (str != null)
				channel.sendMessage(Builder.buildEmbed(str, Color.RED)).queue();
			
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
	
	public void startTTT(String player1, String player2) {
		ticTacToePL.remove(player2);
		Players players = new Players(player1, player2);
		TTT ttt = new TTT(players);
		startedTTT.put(players, ttt);
		channel.sendMessage(Builder.buildEmbed(ttt.getGraphicalMap().append("\n<@").append(ttt.getCurrentPl()).append("> turn").toString(), Color.red)).queue();
	}
	
	public void startCSGO(String target) {
		//broken af cuz was designed for more functions than needed
		TargetListCSGO obj;
		if (csgoHash.containsKey(caller)) {
			obj = csgoHash.get(caller);
		} else {
			obj = new TargetListCSGO(new TargetCSGO(target));
			csgoHash.put(caller, obj);
		}
		ContainerCSGO container = obj.makeHitMessage(target);
//		EmbedBuilder msg = new EmbedBuilder();
//		msg.setColor(Color.RED);
//		msg.setDescription(cont.getMessage());
		channel.sendMessage(Builder.buildEmbed(container.getMessage(), Color.RED)).queue();
//		e.getChannel().sendMessage(cont.getMessage()).queue();


//		if (container.getIsDead())
//			csgoHash.get(e.getMember()).deleteTarget(target);
		csgoHash.get(caller).deleteTarget(target);
	}
	
	public String resumeTTT(int pos, String player) {
		
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
				str.append("<@").append(ttt.getPlayers().getPlayer1()).append("> wins!");
			else if (winner == 2)
				str.append("<@").append(ttt.getPlayers().getPlayer2()).append("> wins!");
			else
				str.append("Tie");
			startedTTT.remove(pl);
		} else
			str.append("\n<@").append(ttt.getCurrentPl()).append("> turn");
		return str.toString();
	}


//	public void changeColours(GuildMessageReceivedEvent event) throws InterruptedException {
//		List<Color> colors = Arrays.asList(Color.MAGENTA, Color.blue, Color.cyan, Color.GREEN, Color.ORANGE, Color.yellow, Color.white, Color.black);
//		for(int i=0;i<360;i++){
//			colors.forEach((c)->{
//				event.getGuild().getRoleById("754879837843226706").getManager().setColor(c).queue();();
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
	
	
	private void handleTTT(String[] message) {
		if (message.length <= 1)
			channel.sendMessage(Builder.buildEmbed("Usage: !ttt [start/stop] {@PingYourOpponentIfStart} \nStart the game of Tic Tac Toe with your friend!\nStop the game to cancel your invite.", Color.blue)).queue();
		else if (message[1].equals("start")) {
			if (message.length <= 2 || !message[2].replaceAll("[<>!@]*", "").matches("\\d*"))
				channel.sendMessage(Builder.buildEmbed("Usage: !ttt [start/stop] {@PingYourOpponentIfStart}\nWarning: You missed/misspelled 3rd argument!", Color.blue)).queue();
			else {
				String player2 = message[2].replaceAll("[<>!@]*", "");
				
				if (caller.getId().equals(player2)) {
					channel.sendMessage("You can't play with yourself!").queue();
					return;
				}
				
				if (startedTTT.entrySet().stream().anyMatch(entry -> { String pl1=entry.getKey().getPlayer1(), pl2=entry.getKey().getPlayer2();
//					entry.getKey().getPlayer1().equals(caller.getId()) || entry.getKey().getPlayer2().equals(caller.getId())
					return pl1.equals(caller.getId()) || pl2.equals(caller.getId()) || pl1.equals(player2) || pl2.equals(player2);
				})) {
					channel.sendMessage("You cant invite because at least one of the players already in TTT game!").queue();
					return;
				}
				
//				if (startedTTT.entrySet().stream().anyMatch(entry -> entry.getKey().getPlayer1().equals(player2) || entry.getKey().getPlayer2().equals(player2))) {
//					channel.sendMessage("You cant invite!").queue();
//					return;
//				}
				if (ticTacToePL.containsKey(player2)) {
					channel.sendMessage(Builder.buildEmbed(caller.getUser().getAsMention() + " you have already sent an invitation to <@" + player2 + ">", Color.blue)).queue();
					return;
				}

//							else {
//								channel.sendMessage(buildEmbed("I can't find this person on the server",Color.blue)).queue();
//								break;
//							}
				channel.sendMessage(Builder.buildEmbed("<@" + player2 + "> was invited to play TicTacToe, type \n!accept ttt \nto join!", Color.blue)).queue();
				ticTacToePL.put(player2, caller);
			}
			
		} else if (message[1].equals("stop")) {
			channel.sendMessage(Builder.buildEmbed("This command have no purpose yet", Color.blue)).queue();
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
	
	private void handleAccept(String[] message) {
		if (message.length <= 1)
			return;
		if (message[1].equals("ttt")) {
			if (startedTTT.entrySet().stream().anyMatch(entry -> entry.getKey().getPlayer1().equals(caller.getId()) || entry.getKey().getPlayer2().equals(caller.getId())))
				channel.sendMessage("You cant start the game!").queue();
			else if (ticTacToePL.get(caller.getId()) != null) {
				channel.sendMessage("Game starts!").queue();
//						ticTacToePL.remove(caller.getId());
				startTTT(ticTacToePL.get(caller.getId()).getId(), caller.getId());
			} else
				channel.sendMessage("<@" + caller.getId() + ">, you haven't been invited to any game").queue();
		}
		if (message[1].equals("nb")) {
			if (startedNB.entrySet().stream().anyMatch(entry -> entry.getKey().getPlayer1().equals(caller.getId()) || entry.getKey().getPlayer2().equals(caller.getId())))
				channel.sendMessage("You cant start the game!").queue();
			else if (navalBattlePL.get(caller.getId()) != null) {
				channel.sendMessage("Game starts!").queue();
//						ticTacToePL.remove(caller.getId());
				startNB(navalBattlePL.get(caller.getId()).getId(), caller.getId());
			} else
				channel.sendMessage("<@" + caller.getId() + ">, you haven't been invited to any game").queue();
		}
	}
	
	private void handleCsgo(String[] message) {
		if (isBadRequest(message, "csgo"))
			return;
		
		String target = message[1].replaceAll("[<>!@]*", "");
		startCSGO(target);

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
	
	
	private void handleNB(String[] message) {
		if (isBadRequest(message, "nb"))
			return;
		
		String player2 = message[1].replaceAll("[<>!@]*", "");
		
		if (caller.getId().equals(player2)) {
			channel.sendMessage("You can't play with yourself!").queue();
			return;
		}
		
		if (startedNB.entrySet().stream().anyMatch(entry -> { String pl1=entry.getKey().getPlayer1(), pl2=entry.getKey().getPlayer2();
			return pl1.equals(caller.getId()) || pl2.equals(caller.getId()) || pl1.equals(player2) || pl2.equals(player2);
		})) {
			channel.sendMessage("You cant invite because at least one of the players already in NB game!").queue();
			return;
		}
		
		if (navalBattlePL.containsKey(player2)) {
			channel.sendMessage(Builder.buildEmbed(caller.getUser().getAsMention() + " you have already sent an invitation to <@" + player2 + ">", Color.blue)).queue();
			return;
		}
		
		channel.sendMessage(Builder.buildEmbed("<@" + player2 + "> was invited to play Naval Battle, type \n!accept nb \nto join!", Color.blue)).queue();
		navalBattlePL.put(player2, caller);
		
	}
	
	private boolean isBadRequest(String[] message, String command) {
		if (message.length <= 1) {
			channel.sendMessage(Builder.buildEmbed("Usage: !" + command + " {@PingYourTarget}", Color.BLUE)).queue();
			return true;
		} else if (!message[1].replaceAll("[<>!@]*", "").matches("\\d*")) {
			channel.sendMessage(Builder.buildEmbed("Usage: !" + command + " {@PingYourTarget}\nWarning: You missed/misspelled 2rd argument!", Color.blue)).queue();
			return true;
		} else
			return false;
		
	}
	
	private void startNB(String player1, String player2) {
		navalBattlePL.remove(player2);
		Players players = new Players(player1, player2);
		Field nb = new Field(players);
		startedNB.put(players, nb);
		channel.sendMessage(Builder.buildEmbed(NBStringConverter.convert(nb.getPlayer1Field()).append("\nplayer<@").append(nb.getPlayers().getPlayer1()).append("> field").toString(), Color.red)).queue();
		channel.sendMessage(Builder.buildEmbed(NBStringConverter.convert(nb.getPlayer2Field()).append("\nplayer<@").append(nb.getPlayers().getPlayer2()).append("> field").toString(), Color.red)).queue();
		channel.sendMessage("Player <@" + nb.getCurrentPlayer() + "> turn").queue();
	}
	
	public void resumeNB(int x, int y) {
		
		Optional<Map.Entry<Players, Field>> game = startedNB.entrySet().stream().filter(entry -> entry.getKey().getPlayer1().equals(caller.getId()) || entry.getKey().getPlayer2().equals(caller.getId())).findAny();
		if (game.isEmpty())
			return;
		Players pl = game.get().getKey();
		Field nb = game.get().getValue();
		
		if (!nb.getCurrentPlayer().equals(caller.getId()))
			return;
		if (!nb.isAvailableToHit(new Point(x - 1, y - 1)))
			return;
		String str;
		if (nb.isHit(new Point(x - 1, y - 1)) && nb.gameFinished()) {
			str = "\nGame ended with result: <@" + nb.getCurrentPlayer() + "> wins!";
			startedTTT.remove(pl);
		} else
			str = "Player <@" + nb.getCurrentPlayer() + "> turn";
		
		channel.sendMessage(Builder.buildEmbed(NBStringConverter.convert(nb.getPlayer1Field()).append("\nplayer<@").append(nb.getPlayers().getPlayer1()).append("> field").toString(), Color.red)).queue();
		channel.sendMessage(Builder.buildEmbed(NBStringConverter.convert(nb.getPlayer2Field()).append("\nplayer<@").append(nb.getPlayers().getPlayer2()).append("> field").toString(), Color.red)).queue();
		channel.sendMessage(str).queue();
	}
}
