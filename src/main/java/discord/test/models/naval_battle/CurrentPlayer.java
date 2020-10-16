package discord.test.models.naval_battle;

public class CurrentPlayer {
	//TODO: implement this?
	private String[][] field;
	
	public CurrentPlayer(String[][] field){
		this.field=field;
	}
	
	public String[][] getField() {
		return field;
	}
	public void markPos(Point point, String msg){
		field[point.getX()][point.getY()]= msg;
	}
	
	public String getPos(Point point){
		return field[point.getX()][point.getY()];
	}
	
	public boolean isHit(Point point){
		return field[point.getX()][point.getY()].equals("1");
	}
	
}
