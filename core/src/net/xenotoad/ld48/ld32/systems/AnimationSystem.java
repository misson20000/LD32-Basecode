package net.xenotoad.ld48.ld32.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import net.xenotoad.ld48.ld32.components.SpriteComponent;

public class AnimationSystem extends IteratingSystem {
	
	@SuppressWarnings("unchecked")
	public AnimationSystem() {
		super(Family.getFor(SpriteComponent.class));
	}
	
	@Override
	protected void processEntity(Entity e, float dt) {
		SpriteComponent spr = SpriteComponent.map.get(e);
		spr.animTimer-= dt;
		if(spr.animTimer <= 0) {
			spr.animTimer = 1f/spr.fps;
			spr.animFrame++;
			if(spr.animFrame >= spr.currentAnimation.frames.length) {
				spr.animFrame = 0;
			}
		}
	}
	
}
