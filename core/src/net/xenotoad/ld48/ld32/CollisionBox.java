package net.xenotoad.ld48.ld32;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.SizeComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;
import net.xenotoad.ld48.ld32.systems.CollisionSystem;
import net.xenotoad.ld48.ld32.systems.CollisionSystem.Direction;

public class CollisionBox {	
	private Entity e;
	private PositionComponent p;
	private SizeComponent s;
	
	public static Pool<CollisionState> pool = new Pool<CollisionState>(new Pool.Factory<CollisionState>() {
		@Override
		public CollisionState construct() {
			return new CollisionState();
		}
	});
	
	public CollisionBox(Entity e) {
		this.e = e;
		this.p = PositionComponent.map.get(e);
		this.s = SizeComponent.map.get(e);
	}
	
	public CollisionState wouldCollide(float x, float y, TiledMap tmx) {
		return pool.get().update(x, y, s, tmx);
	}
	
	public CollisionState wouldCollide(CollisionState st, float x, float y, TiledMap tmx) {
		return st.update(x, y, s, tmx);
	}
}