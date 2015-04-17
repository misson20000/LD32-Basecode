package net.xenotoad.ld48.ld32;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import net.xenotoad.ld48.ld32.components.SizeComponent;
import net.xenotoad.ld48.ld32.systems.CollisionSystem.Direction;

public class CollisionState {

	public boolean blockD;
	public boolean blockU;
	public boolean blockL;
	public boolean blockR;
	
	public boolean isBlocked(Direction d) {
		switch(d) {
			case UP: return blockU;
			case LEFT: return blockL;
			case RIGHT: return blockR;
			case DOWN: return blockD;
		}
		return false;
	}
	
	private boolean isSolid(TiledMapTileLayer.Cell cell) {
		return cell != null && cell.getTile() != null && cell.getTile().getId() > 0;
	}
	
	private boolean test(TiledMap tmx, float x, float y) {
		for(MapLayer ml : tmx.getLayers()) {
			if(ml.getProperties().containsKey("collide") && ml instanceof TiledMapTileLayer) {
				TiledMapTileLayer l = (TiledMapTileLayer) ml;
				int tx = (int) Math.floor(x/l.getTileWidth());
				int ty = (int) Math.floor(y/l.getTileHeight());
				
				if(isSolid(l.getCell(tx, ty))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public CollisionState update(float x, float y, SizeComponent s, TiledMap tmx) {
		boolean ul = test(tmx, x, y+s.h);
		boolean ur = test(tmx, x+s.w, y+s.h);
		boolean bl = test(tmx, x, y);
		boolean br = test(tmx, x+s.w, y);
		
		blockD = bl || br;
		blockU = ul || ur;
		blockL = bl || ul;
		blockR = br || ur;
		return this;
	}
	
	public void free() {
		CollisionBox.pool.free(this);
	}
}
