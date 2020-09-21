package discord.test;

import java.util.LinkedList;
import java.util.Optional;

public class TargetListCSGO {
	private LinkedList<TargetCSGO> targets;

	public TargetListCSGO(TargetCSGO target){
		this.targets = new LinkedList<TargetCSGO>();
		this.targets.add(target);
	}
	
	public void addTarget(TargetCSGO target){
		targets.add(target);
	}
	
	public ContainerCSGO makeHitMessage(String target){
		Optional<TargetCSGO> opt = findTargetCSGO(target);
		TargetCSGO targetCSGO;
		if (opt.isPresent())
			targetCSGO=opt.get();
		else {
			targetCSGO = new TargetCSGO(target);
			addTarget(targetCSGO);
		}
		StringBuilder str = new StringBuilder();
		ContainerCSGO container;
		if(targetCSGO.isDeadByNewHit()) {
			container = new ContainerCSGO(str.append("Damage Given to <@").append(targetCSGO.getTargetID()).append("> - ").append(targetCSGO.getDmg()).append(" in ").append(targetCSGO.getHit()).append(" hit").toString(),
					true);
			
		}
		else
			container = new ContainerCSGO(str.append("Damage Given to <@").append(targetCSGO.getTargetID()).append("> - ").append(targetCSGO.getDmg()).append(" in ").append(targetCSGO.getHit()).append(" hit").toString(),
				false);
		
		return container;
	}

	public Optional<TargetCSGO> findTargetCSGO(String target){
		return targets.stream().filter((t)->t.getTargetID().equals(target)).findFirst();
	}
	
	public void deleteTarget(TargetCSGO target){
		targets.remove(target);
	}
	
	public void deleteTarget(String target){
//		Optional<TargetCSGO> optionalTargetCSGO = targets.stream().filter((t)->t.getTargetID().equals(target)).findFirst();
		Optional<TargetCSGO> optionalTargetCSGO = findTargetCSGO(target);
		optionalTargetCSGO.ifPresent(this::deleteTarget);
	}
	
	public LinkedList<TargetCSGO> getTargets() {
		return targets;
	}
	
	
}

//	Damage Given to "El Calvo de Carglass" - 12 in 1 hit
//		Player: Violet Evergarden - Damage Taken
//		-------------------------
//		Damage Taken from "El Calvo de Carglass" - 116 in 2 hits