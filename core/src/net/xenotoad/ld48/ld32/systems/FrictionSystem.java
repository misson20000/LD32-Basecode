package net.xenotoad.ld48.ld32.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import net.xenotoad.ld48.ld32.Mappers;
import net.xenotoad.ld48.ld32.components.FrictionComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;

public class FrictionSystem extends IteratingSystem {
	
	@SuppressWarnings("unchecked")
	public FrictionSystem() {
		super(Family.getFor(
				VelocityComponent.class,
				FrictionComponent.class));
		
	}
	
	@Override
	protected void processEntity(Entity e, float dt) {
		VelocityComponent vel = VelocityComponent.map.get(e);
		FrictionComponent fri = Mappers.friction.get(e);
		
		if(vel.x > 0) {
			vel.x-= fri.factor*dt;
			if(vel.x < 0) { vel.x = 0; }
		} else {
			vel.x+= fri.factor*dt;
			if(vel.x > 0) { vel.x = 0; }
		}
	}
	
}
