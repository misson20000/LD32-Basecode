package net.xenotoad.ld48.ld32.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import net.xenotoad.ld48.ld32.Mappers;
import net.xenotoad.ld48.ld32.components.GravityComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;

public class GravitySystem extends IteratingSystem {
	
	@SuppressWarnings("unchecked")
	public GravitySystem() {
		super(Family.getFor(
				GravityComponent.class,
				VelocityComponent.class));
	}
	
	@Override
	protected void processEntity(Entity e, float dt) {
		VelocityComponent v = VelocityComponent.map.get(e);
		GravityComponent g = Mappers.gravity.get(e);
		
		v.y+= dt*g.accel;
	}
	
}
