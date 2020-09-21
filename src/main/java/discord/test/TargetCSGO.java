package discord.test;

import java.util.Random;

public class TargetCSGO {
	private int hit;
	private int dmg;
	private int currentHits;
	private String targetID;
	public TargetCSGO(String targetID){
		hit=0;
		dmg=0;
		this.targetID=targetID;
	}
	
	public boolean isDeadByNewHit(){
		Random rand = new Random();
		for(int i=0;i<30;i++)
			spray();
		hit+=currentHits;
//		hit+=rand.nextInt(11);
		for(;currentHits>0 && dmg<100;currentHits--)
			dmg+=rand.nextInt(50);
		hit-=currentHits;
		return dmg>=100;
	}
	
	
	private void spray(){
		Random rand = new Random();
		int res=rand.nextInt(30);
		if(res<(1+rand.nextInt(5)))
			currentHits++;
	}
	
	public String getTargetID() {
		return targetID;
	}
	
	public int getHit() {
		return hit;
	}
	
	public int getDmg() {
		return dmg;
	}
}




//TODO: make dmg/death history for the target(then need to comment target deletion in TargetListCSGO and GuildMessageEvent deletion of all targets)
