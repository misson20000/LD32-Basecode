package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;

import net.xenotoad.ld48.ld32.CollisionBox;
import net.xenotoad.ld48.ld32.CollisionState;

public class CollisionComponent extends Component {
	public static final ComponentMapper<CollisionComponent> map = ComponentMapper.getFor(CollisionComponent.class);
	
	public TiledMap tmx;
	
	private CollisionBox box;

	public CollisionState state;
	
	public CollisionComponent(TiledMap tmx, Entity e) {
		this.tmx = tmx;
		this.box = new CollisionBox(e);
		this.state = new CollisionState();
	}
	
	public CollisionBox getBox() {
		return box;
	}
}
