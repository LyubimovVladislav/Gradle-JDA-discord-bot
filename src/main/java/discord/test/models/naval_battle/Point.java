package discord.test.models.naval_battle;

public class Point {
	private int x, y;
	private int hitCount, length;
	
	
	private enum Orientation {
		vertical,
		horizontal
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.hitCount = this.length = length;
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		orientation = Orientation.horizontal;
	}
	
	private Orientation orientation;
	
	public Point() {
		x = (int) (Math.random() * 10);
		y = (int) (Math.random() * 10);
		if ((int) (Math.random() * 2) == 0)
			orientation = Orientation.horizontal;
		else
			orientation = Orientation.vertical;
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isHorizontal() {
		return orientation.equals(Orientation.horizontal);
	}
	
	public boolean isDestroyed(){
		hitCount--;
		return hitCount <= 0;
	}
}
