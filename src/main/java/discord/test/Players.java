package discord.test;

public class Players {
	private String player1;
	private String player2;
	
	public Players(String player1, String player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public Players(String player1) {
		this.player1 = player1;
		this.player2 = "";
	}
	
	public String getPlayer1() {
		return player1;
	}
	
	public String getPlayer2() {
		return player2;
	}
	
//	@Override
//	public boolean equals (Object obj){//проверять ссылки, если они показывают на один объект
//		if(!(obj instanceof Players))
//			return false;
//		Players players = (Players) obj;
//		return player1.equals(players.getPlayer1()) || player2.equals(players.getPlayer1());
//	}

}
