package discord.test.models.ttt;

import discord.test.models.Players;

import java.util.stream.Stream;

public class TTT {
	private int[][] hMap;
	private int winner;
	private Players players;
//	private String player1, player2;
	private String currentPl;
	private StringBuilder graphicalMap;
	
	public TTT(Players players) {
		hMap = new int[3][3];
		winner = 0;
		this.players = players;
		currentPl = players.getPlayer1(); //can be random
		nullifyHMap();
		winner=0;
	}
	
	private void nullifyHMap() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				hMap[i][j] = 0;
	}
	
	public boolean makeATurn(String player, int position) {
		if (!player.equals(currentPl))
			return false;
		if (position <= 3) {
			position-=1;
			if (hMap[0][position] == 0)
				markPos(0, position);
		} else if (position <= 6) {
			position -= 4;
			if (hMap[1][position] == 0)
				markPos(1, position);
		} else {
			position -= 7;
			if (hMap[2][position] == 0)
				markPos(2, position);
		}

		return true;
	}
	
	private void markPos(int i, int j) {
		int pl;
		if (currentPl.equals(players.getPlayer1()))
			pl = 1;
		else pl = 2;
		hMap[i][j] = pl;
		
		if(currentPl.equals(players.getPlayer1()))
			currentPl=players.getPlayer2();
		else
			currentPl=players.getPlayer1();
	}
	
	public int isGameFinished() {
//		for (int i = 0; i < 3; i++) {
//			for (int j = 0; j < 3; j++) {
//				HMap
//			}
//		}
		if(hMap[0][0]==hMap[1][1] && hMap[0][0]==hMap[2][2])
			winner=hMap[0][0];
		else if(hMap[0][2]==hMap[1][1] && hMap[0][2]==hMap[2][0])
			winner=hMap[0][2];
		else {
			int i=0;
			int j=0;
			while (i<3){
				if(hMap[i][0]==hMap[i][1]&&hMap[i][0]==hMap[i][2]) {
					winner = hMap[i][0];
					break;
				}
				if(hMap[0][j]==hMap[1][j]&&hMap[0][j]==hMap[2][j]) {
					winner = hMap[0][j];
					break;
				}
				i++;
				j++;
			}
		}
		if (winner!=0)
			return winner;
		boolean hasAvailableMoves=false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (hMap[i][j] == 0) {
					hasAvailableMoves = true;
					break;
				}
			}
		}
		if(!hasAvailableMoves)
			winner=3;
		return winner;
	}
	
	private void makeGraphicalMap() {
		StringBuilder str = new StringBuilder("<@" + players.getPlayer1() + "> vs <@" + players.getPlayer2() + ">\n");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (hMap[i][j] == 1)
					str.append(":orange_square: ");
				else if (hMap[i][j] == 2)
					str.append(":blue_square: ");
				else
					str.append(":white_large_square: ");
			}
			str.append("   [").append((i * 2) + 1 + i).append("] [").append((i * 3) + 2).append("] [").append((i + 1) * 3).append("]\n");
		}
		graphicalMap = str;
	}
	
	public StringBuilder getGraphicalMap() {
		makeGraphicalMap();
		return graphicalMap;
	}
	
	public Players getPlayers(){ return players; }
	
	public String getCurrentPl() {
		return currentPl;
	}
}
