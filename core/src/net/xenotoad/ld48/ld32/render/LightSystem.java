package net.xenotoad.ld48.ld32.render;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import net.xenotoad.ld48.ld32.components.LightSourceComponent;
import net.xenotoad.ld48.ld32.components.PositionComponent;

public class LightSystem extends EntitySystem {
	
	private Engine eng;
	private FrameBuffer fbo;
	
	public LightSystem(FrameBuffer fbo) {
		this.fbo = fbo;
	}
	
	@Override
	public void addedToEngine(Engine eng) {
		this.eng = eng;
	}
	
	@Override
	public void update(float dt) {
		@SuppressWarnings("unchecked")
		ImmutableArray<Entity> entities = eng.getEntitiesFor(
				Family.getFor(LightSourceComponent.class, PositionComponent.class));
		
		for(int i = 0; i < entities.size(); i++) {
			
		}
	}
}
