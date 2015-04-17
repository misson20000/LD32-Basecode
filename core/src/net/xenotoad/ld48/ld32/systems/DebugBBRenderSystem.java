package net.xenotoad.ld48.ld32.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import net.xenotoad.ld48.ld32.Mappers;
import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.SizeComponent;

public class DebugBBRenderSystem extends EntitySystem {
	private Engine eng;
	private ShapeRenderer render;
	private OrthographicCamera cam;
	
	public DebugBBRenderSystem(ShapeRenderer shape, OrthographicCamera cam) {
		this.render = shape;
		this.cam = cam;
	}
	
	@Override
	public void addedToEngine(Engine eng) {
		super.addedToEngine(eng);
		this.eng = eng;
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		
		render.setProjectionMatrix(cam.combined);
		render.begin(ShapeType.Line);
		
		@SuppressWarnings("unchecked")
		ImmutableArray<Entity> ents = eng.getEntitiesFor(Family.getFor(
				PositionComponent.class,
				SizeComponent.class));
		for(int i = 0; i < ents.size(); i++) {
			Entity e = ents.get(i);
			PositionComponent pos = Mappers.position.get(e);
			SizeComponent siz = Mappers.size.get(e);
			
			render.rect(pos.x, pos.y, siz.w, siz.h);
		}
		
		render.end();
	}
}
