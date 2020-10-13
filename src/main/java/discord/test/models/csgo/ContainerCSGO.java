package discord.test.models.csgo;

public class ContainerCSGO {
	private String message;
	private boolean isDead;
	ContainerCSGO(String message, boolean isDead){
		this.message=message;
		this.isDead=isDead;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean getIsDead(){
		return isDead;
	}
}
