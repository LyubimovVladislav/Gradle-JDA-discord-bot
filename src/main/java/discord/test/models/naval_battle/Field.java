package discord.test.models.naval_battle;

import java.util.Arrays;

public class Field {
	private Integer[][] shipField;
	private String[][] hitField;
//	private ArrayList<Listener> listeners;
	private Point[][] points;
	private int alive;
	
	
	public Field() {
		fillField();
//		listeners = new ArrayList<>();
		alive = 10;
	}
	
	private void fillField() {
		makeField();
		Integer[] shipsAvailable = new Integer[4];
		for (int i = 0; i < shipsAvailable.length; i++) {
			shipsAvailable[i] = i + 1;
		}
		int i = 0;
		while (i <= 3) {
			Point point = getHead(4 - i);
			points[point.getX()][point.getY()] = point;
			point.setLength(4 - i);
			makeShip(point, 4 - i);
			shipsAvailable[i] -= 1;
			if (shipsAvailable[i] <= 0)
				i++;
		}
	}
	
	
	private void makeShip(Point point, int length) {
		if (point.isHorizontal()) {
			for (int i = 0; i < length; i++) {
				shipField[point.getX() + i][point.getY()] = 1;
			}
		} else {
			for (int i = 0; i < length; i++) {
				shipField[point.getX()][point.getY() + i] = 1;
			}
		}
	}
	
	private void makeField() {
		points = new Point[10][10];
		shipField = new Integer[10][10];
		hitField = new String[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				points[i][j] = null;
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				hitField[i][j] = "";
		for (Integer[] integers : shipField) {
			Arrays.fill(integers, 0);
		}
	}
	
	
	private Point getHead(int length) {
		Point point = new Point();
		while (!checkPlacement(point, length)) {
			point = new Point();
		}
		return point;
	}
	
	private boolean checkPlacement(Point point, int shipLength) {
		if (shipField[point.getX()][point.getY()] == 1)
			return false;
		
		if (point.isHorizontal()) {
			if (point.getX() + shipLength - 1 > 9)
				return false;
			for (int i = point.getX() - 1; i < point.getX() + shipLength + 1; i++) {
				if (i < 0)
					continue;
				if (i > 9)
					continue;
				if (shipField[i][point.getY()] == 1)
					return false;
				if (point.getY() - 1 > -1)
					if (shipField[i][point.getY() - 1] == 1)
						return false;
				if (point.getY() + 1 < 10)
					if (shipField[i][point.getY() + 1] == 1)
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
				if (shipField[point.getX()][i] == 1)
					return false;
				if (point.getX() - 1 > -1)
					if (shipField[point.getX() - 1][i] == 1)
						return false;
				if (point.getX() + 1 < 10)
					if (shipField[point.getX() + 1][i] == 1)
						return false;
			}
		}
		return true;
	}
	
//	public void addListener(Listener l) {
//		listeners.add(l);
//	}
//
//	public void deleteListener(Listener l) {
//		listeners.remove(l);
//	}
	
	public Integer[][] getShipField() {
		return shipField;
	}
	
	public boolean isPresent(double x, double y) {
		Point point = roundToLowNumber(x, y);
		return shipField[point.getX()][point.getY()] == 1;
	}
	
	public boolean isHit(double x, double y) {
		Point point = roundToLowNumber(x, y);
		if (shipField[point.getX()][point.getY()] == 1) {
			hitField[point.getX()][point.getY()] = "X";
			Point head = FindHead(point);
			if (head.isDestroyed()) {
				alive -= 1;
				markAdjacent(head);
			}
//			fire();
			return true;
		} else {
			hitField[point.getX()][point.getY()] = "*";
//			fire();
			return false;
		}
	}
	
	public boolean isHit(Point point) {
		if (shipField[point.getX()][point.getY()] == 1) {
			hitField[point.getX()][point.getY()] = "X";
			Point head = FindHead(point);
			if (head.isDestroyed()) {
				alive -= 1;
				markAdjacent(head);
			}
//			fire();
			return true;
		} else {
			hitField[point.getX()][point.getY()] = "*";
//			fire();
			return false;
		}
	}
	
	
	private void markAdjacent(Point point) {
		if (point.isHorizontal()) {
			if (point.getX() - 1 > -1) {
				hitField[point.getX() - 1][point.getY()] = "*";
			}
			if (point.getX() + point.getLength() < 10)
				hitField[point.getX() + point.getLength()][point.getY()] = "*";
			for (int i = point.getX() - 1; i < point.getX() + point.getLength() + 1; i++) {
				if (i < 0)
					continue;
				if (i > 9)
					continue;
				if (point.getY() - 1 > -1)
					hitField[i][point.getY() - 1] = "*";
				if (point.getY() + 1 < 10)
					hitField[i][point.getY() + 1] = "*";
			}
			
		} else {
			if (point.getY() - 1 > -1) {
				hitField[point.getX()][point.getY() - 1] = "*";
			}
			if (point.getY() + point.getLength() < 10)
				hitField[point.getX()][point.getY() + point.getLength()] = "*";
			for (int i = point.getY() - 1; i < point.getY() + point.getLength() + 1; i++) {
				if (i < 0)
					continue;
				if (i > 9)
					continue;
				if (point.getX() - 1 > -1)
					hitField[point.getX() - 1][i] = "*";
				if (point.getX() + 1 < 10)
					hitField[point.getX() + 1][i] = "*";
			}
		}
	}
	
	
	private Point roundToLowNumber(double x, double y) {
		if (x >= 550)
			x -= 550;
		x -= 50;
		y -= 50;
		int i = 0;
		for (; x >= 50; i++) {
			x -= 50;
		}
		int j = 0;
		for (; y >= 50; j++) {
			y -= 50;
		}
		return new Point(i, j);
	}
	
//	private void fire() {
//		for (Listener l : listeners)
//			l.dataChanged();
//	}
	
	public String getSpecCell(int x, int y) {
		return hitField[x][y];
	}
	
	private Point FindHead(Point point) {
		if (points[point.getX()][point.getY()] != null)
			return points[point.getX()][point.getY()];
		if (point.getX() - 1 > -1 && shipField[point.getX() - 1][point.getY()] == 1) {
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
	
	public Point turn() {
		Point point = new Point();
		while (!hitField[point.getX()][point.getY()].equals(""))
			point = new Point();
		return point;
	}
	
	public int getAlive() {
		return alive;
	}
	
	public boolean gameFinished() {
		return alive == 0;
	}
}
