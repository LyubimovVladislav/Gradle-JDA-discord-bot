package discord.test.models.naval_battle;

import discord.test.models.Players;

import java.util.Arrays;

public class Field {
	private String[][] player1Field;
	private String[][] player2Field;
	//	private ArrayList<Listener> listeners;
	private Point[][] player1Points;
	private Point[][] player2Points;
	private int player1Alive = 10;
	private int player2Alive = 10;
	private final Players players;
	private String currentPlayer;
	
	public Field(Players players) {
		fillField();
		this.players = players;
		currentPlayer = players.getPlayer1();
	}
	
	private void fillField() {
		makeFields();
		Integer[] shipsAvailable = new Integer[4];
		for (int i = 0; i < shipsAvailable.length; i++) {
			shipsAvailable[i] = i + 1;
		}
		int i = 0;
		while (i <= 3) {
			Point point1 = getHead(player1Field, 4 - i);
			Point point2 = getHead(player2Field, 4 - i);
			
			player1Points[point1.getX()][point1.getY()] = point1;
			player2Points[point2.getX()][point2.getY()] = point2;
			point1.setLength(4 - i);
			point2.setLength(4 - i);
			makeShip(point1, player1Field, 4 - i);
			makeShip(point2, player2Field, 4 - i);
			shipsAvailable[i] -= 1;
			if (shipsAvailable[i] <= 0)
				i++;
		}
	}
	
	
	private void makeShip(Point point, String[][] field, int length) {
		if (point.isHorizontal()) {
			for (int i = 0; i < length; i++) {
				field[point.getX() + i][point.getY()] = "1";
			}
		} else {
			for (int i = 0; i < length; i++) {
				field[point.getX()][point.getY() + i] = "1";
			}
		}
	}
	
	private void makeFields() {
		player1Points = new Point[10][10];
		player2Points = new Point[10][10];
		player1Field = new String[10][10];
		player2Field = new String[10][10];

//		for (int i = 0; i < 10; i++)
//			for (int j = 0; j < 10; j++) {
//				player1Points[i][j] = null;
//				player2Points[i][j] = null;
//			}
		for (Point[] points : player1Points) {
			Arrays.fill(points, null);
		}
		for (Point[] points : player2Points) {
			Arrays.fill(points, null);
		}


//		for (int i = 0; i < 10; i++)
//			for (int j = 0; j < 10; j++){
//				player1Field[i][j] = "";
//				player2Field[i][j] = "";
//	    }
		for (String[] strings : player1Field) {
			Arrays.fill(strings, "");
		}
		for (String[] strings : player2Field) {
			Arrays.fill(strings, "");
		}
	}
	
	
	private Point getHead(String[][] field, int length) {
		Point point = new Point();
		while (!checkPlacement(field, point, length)) {
			point = new Point();
		}
		return point;
	}
	
	private boolean checkPlacement(String[][] field, Point point, int shipLength) {
		if (field[point.getX()][point.getY()].equals("1"))
			return false;
		
		if (point.isHorizontal()) {
			if (point.getX() + shipLength - 1 > 9)
				return false;
			for (int i = point.getX() - 1; i < point.getX() + shipLength + 1; i++) {
				if (i < 0)
					continue;
				if (i > 9)
					continue;
				if (field[i][point.getY()].equals("1"))
					return false;
				if (point.getY() - 1 > -1)
					if (field[i][point.getY() - 1].equals("1"))
						return false;
				if (point.getY() + 1 < 10)
					if (field[i][point.getY() + 1].equals("1"))
						return false;
			}
		} else {
			if (point.getY() + shipLength - 1 > 9)
				return false;
			for (int i = point.getY() - 1; i < point.getY() + shipLength + 1; i++) {
				if (i < 0)
					continue;
				if (i > 9)
					continue;
				if (field[point.getX()][i].equals("1"))
					return false;
				if (point.getX() - 1 > -1)
					if (field[point.getX() - 1][i].equals("1"))
						return false;
				if (point.getX() + 1 < 10)
					if (field[point.getX() + 1][i].equals("1"))
						return false;
			}
		}
		return true;
	}
	
	
	public String[][] getPlayer1Field() {
		return player1Field;
	}
	
	public String[][] getPlayer2Field() {
		return player2Field;
	}

//	public boolean isPresent(double x, double y) {
//		Point point = roundToLowNumber(x, y);
//		return player1Field[point.getX()][point.getY()] == 1;
//	}

//	public boolean isHit(double x, double y) {
//		Point point = roundToLowNumber(x, y);
//		if (player1Field[point.getX()][point.getY()] == 1) {
//			player2Field[point.getX()][point.getY()] = "X";
//			Point head = FindHead(point);
//			if (head.isDestroyed()) {
//				alive -= 1;
//				markAdjacent(head);
//			}
////			fire();
//			return true;
//		} else {
//			player2Field[point.getX()][point.getY()] = "*";
////			fire();
//			return false;
//		}
//	}
	
	public boolean isHit(Point point) {
		String[][] field;
		Point[][] points;
		if (players.getPlayer1().equals(currentPlayer)) {
			field = player1Field;
			points = player1Points;
		} else {
			field = player2Field;
			points = player2Points;
		}
		if (field[point.getX()][point.getY()].equals("1")) {
			field[point.getX()][point.getY()] = "X";
			Point head = FindHead(points, field, point);
			if (head.isDestroyed()) {
				if (players.getPlayer1().equals(currentPlayer))
					player1Alive -= 1;
				else
					player2Alive -= 1;
				markAdjacent(field, head);
			}
			return true;
		} else {
			field[point.getX()][point.getY()] = "*";
//			fire();
			if (currentPlayer.equals(players.getPlayer1()))
				currentPlayer = players.getPlayer2();
			else
				currentPlayer = players.getPlayer1();
			return false;
		}
	}
	
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	private void markAdjacent(String[][] field, Point point) {
		if (point.isHorizontal()) {
			if (point.getX() - 1 > -1) {
				field[point.getX() - 1][point.getY()] = "*";
			}
			if (point.getX() + point.getLength() < 10)
				field[point.getX() + point.getLength()][point.getY()] = "*";
			for (int i = point.getX() - 1; i < point.getX() + point.getLength() + 1; i++) {
				if (i < 0)
					continue;
				if (i > 9)
					continue;
				if (point.getY() - 1 > -1)
					field[i][point.getY() - 1] = "*";
				if (point.getY() + 1 < 10)
					field[i][point.getY() + 1] = "*";
			}
			
		} else {
			if (point.getY() - 1 > -1) {
				field[point.getX()][point.getY() - 1] = "*";
			}
			if (point.getY() + point.getLength() < 10)
				field[point.getX()][point.getY() + point.getLength()] = "*";
			for (int i = point.getY() - 1; i < point.getY() + point.getLength() + 1; i++) {
				if (i < 0)
					continue;
				if (i > 9)
					continue;
				if (point.getX() - 1 > -1)
					field[point.getX() - 1][i] = "*";
				if (point.getX() + 1 < 10)
					field[point.getX() + 1][i] = "*";
			}
		}
	}


//	private Point roundToLowNumber(double x, double y) {
//		if (x >= 550)
//			x -= 550;
//		x -= 50;
//		y -= 50;
//		int i = 0;
//		for (; x >= 50; i++) {
//			x -= 50;
//		}
//		int j = 0;
//		for (; y >= 50; j++) {
//			y -= 50;
//		}
//		return new Point(i, j);
//	}

//	private void fire() {
//		for (Listener l : listeners)
//			l.dataChanged();
//	}
	
	public String getSpecCellP2(int x, int y) {
		return player2Field[x][y];
	}
	
	public String getSpecCellP1(int x, int y) {
		return player1Field[x][y];
	}
	
	private Point FindHead(Point[][] points, String[][] field, Point point) {
		if (points[point.getX()][point.getY()] != null)
			return points[point.getX()][point.getY()];
		if (point.getX() - 1 > -1 && field[point.getX() - 1][point.getY()].equals("1")) {
			int delta = 0;
			while (points[point.getX() - delta][point.getY()] == null)
				delta++;
			return points[point.getX() - delta][point.getY()];
		} else {
			int delta = 0;
			while (points[point.getX()][point.getY() - delta] == null)
				delta++;
			return points[point.getX()][point.getY() - delta];
		}
	}

//	public Point turn() {
//		Point point = new Point();
//		while (!player2Field[point.getX()][point.getY()].equals(""))
//			point = new Point();
//		return point;
//	}
	
	public int getPlayer1Alive() {
		return player1Alive;
	}
	
	public boolean gameFinished() {
		return player1Alive == 0 || player2Alive == 0;
	}
	
	public int getPlayer2Alive() {
		return player2Alive;
	}
	
}
