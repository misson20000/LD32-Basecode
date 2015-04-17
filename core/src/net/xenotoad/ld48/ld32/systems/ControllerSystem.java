package net.xenotoad.ld48.ld32.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import net.xenotoad.ld48.ld32.components.ControllerComponent;

public class ControllerSystem extends IteratingSystem {
	
	@SuppressWarnings("unchecked")
	public ControllerSystem() {
		super(Family.getFor(ControllerComponent.class));
	}
	
	@Override
	protected void processEntity(Entity e, float dt) {
		ControllerComponent con = ControllerComponent.map.get(e);
		con.controller.update(e, dt);
	}
}
