package net.xenotoad.ld48.ld32.systems;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import net.xenotoad.ld48.ld32.components.GentleRandomMovementComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;

public class GentleRandomMovementSystem extends IteratingSystem {
	
	private Random rng;
	
	@SuppressWarnings("unchecked")
	public GentleRandomMovementSystem() {
		super(Family.getFor(
				VelocityComponent.class,
				GentleRandomMovementComponent.class
				));
		
		rng = new Random();
	}
	
	private static final ComponentMapper<GentleRandomMovementComponent> grmMapper =
			ComponentMapper.getFor(GentleRandomMovementComponent.class);
	
	@Override
	protected void processEntity(Entity e, float dt) {
		VelocityComponent vel = VelocityComponent.map.get(e);
		GentleRandomMovementComponent grm = grmMapper.get(e);
		
		if(vel.getSpeedSqrd() < 0.2f*0.2f) {
			vel.x = (rng.nextFloat()*grm.x)-(grm.x/2);
			vel.y = (rng.nextFloat()*grm.y)-(grm.y/2);
		}
	}
}
