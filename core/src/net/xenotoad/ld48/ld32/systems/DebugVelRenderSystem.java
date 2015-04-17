package net.xenotoad.ld48.ld32.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import net.xenotoad.ld48.ld32.Debug;
import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.SizeComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;

public class DebugVelRenderSystem extends IteratingSystem {
	
	@SuppressWarnings("unchecked")
	public DebugVelRenderSystem() {
		super(Family.getFor(PositionComponent.class, VelocityComponent.class, SizeComponent.class));
	}
	
	@Override
	protected void processEntity(Entity e, float dt) {
		PositionComponent pos = PositionComponent.map.get(e);
		VelocityComponent vel = VelocityComponent.map.get(e);
		SizeComponent siz = SizeComponent.map.get(e);
		
		//Debug.shapes.begin(ShapeType.Line);
		
	}
	
}
