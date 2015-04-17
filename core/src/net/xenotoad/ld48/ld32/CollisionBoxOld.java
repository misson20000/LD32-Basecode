package net.xenotoad.ld48.ld32;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import net.xenotoad.ld48.ld32.components.SizeComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;
import net.xenotoad.ld48.ld32.systems.CollisionSystem;
import net.xenotoad.ld48.ld32.systems.CollisionSystem.Direction;

public class CollisionBoxOld {
	public SensorCorner ul = new SensorCorner();
	public SensorCorner ur = new SensorCorner();
	public SensorCorner bl = new SensorCorner();
	public SensorCorner br = new SensorCorner();
	public Sensor uc = new Sensor();
	public Sensor bc = new Sensor();
	
	public class SensorCorner {
		public Sensor u = new Sensor();
		public Sensor d = new Sensor();
		public Sensor l = new Sensor();
		public Sensor r = new Sensor();
		
		public Sensor get(CollisionSystem.Direction dir) {
			switch(dir) {
				case UP:    return u;
				case DOWN:  return d;
				case LEFT:  return l;
				case RIGHT: return r;
			}
			return null;
		}
		
		public void update(float x, float y) {
			u.x = x; u.y = y + 1;
			d.x = x; d.y = y - 1;
			
			l.x = x - 1; l.y = y;
			r.x = x + 1; r.y = y;
		}
		
		public boolean blocked() {
			return u.blocked || d.blocked || l.blocked || r.blocked;
		}
	}
	
	public class Sensor {
		public float x;
		public float y;
		public boolean blocked = false;
	}
	
	private Entity e;
	
	public CollisionBoxOld(Entity e) {
		this.e = e;
	}
	
	public void update(float x, float y) {
		SizeComponent s = Mappers.size.get(e);
		
		ul.update(x, y + s.h);
		ur.update(x + s.w, y + s.h);
		bl.update(x, y);
		br.update(x + s.w, y);
		
		uc.x = x + (s.w/2);
		uc.y = y + s.h + 1;
		bc.x = x + (s.w/2);
		bc.y = y + s.h - 1;
	}
	
	private boolean isTileSolid(Cell c) {
		return c != null && c.getTile() != null && c.getTile().getId() > 0;
	}
	
	private void handleSensor(String debugID, TiledMap tmx, CollisionBoxOld.Sensor s, Direction d) {
		s.blocked = false;
		for(MapLayer ml : tmx.getLayers()) {
			if(ml instanceof TiledMapTileLayer) {
  			TiledMapTileLayer l = (TiledMapTileLayer) ml;
  			if(l.getProperties().containsKey("collide")) {
    			int tx = (int) Math.floor(s.x/l.getTileWidth ());
    			int ty = (int) Math.floor(s.y/l.getTileHeight());
    			s.blocked = isTileSolid(l.getCell(tx,	ty));
    			if(s.blocked) {	break; }
  			}
			}
		}
	}
	
	public boolean wouldCollide(float x, float y, TiledMap tmx, Direction dir) {
		this.update(x, y);
		
		handleSensor("ul-l", tmx, ul.l, Direction.LEFT);
		handleSensor("bl-l", tmx, bl.l, Direction.LEFT);
		
		handleSensor("ul-u", tmx, ul.u, Direction.UP);
		handleSensor("ur-u", tmx, ur.u, Direction.UP);
		handleSensor("uc",   tmx, uc,   Direction.UP);
		
		handleSensor("ur-r", tmx, ur.r, Direction.RIGHT);
		handleSensor("br-r", tmx, br.r, Direction.RIGHT);
		
		handleSensor("bl-d", tmx, bl.d, Direction.DOWN);
		handleSensor("br-d", tmx, br.d, Direction.DOWN);
		handleSensor("bc",   tmx, bc,   Direction.DOWN);
		
		if(dir != null) {
  		switch(dir) {
  			case LEFT: return ul.l.blocked || bl.l.blocked;
  			case RIGHT: return ur.r.blocked || br.r.blocked;
  			case UP: return ul.u.blocked || ur.u.blocked || uc.blocked;
  			case DOWN: return bl.d.blocked || br.d.blocked || bc.blocked;
  		}
  		return true; //java doesn't like my switch
		} else {
			return ul.blocked() || ur.blocked() || bl.blocked() || br.blocked()
					|| uc.blocked   || bc.blocked;
		}
	}

	private void cancelSensor(Sensor s, Direction d, VelocityComponent vel) {
		if(s.blocked && d.isGoing(vel)) {
			switch(d.zero) {
				case X: vel.x = 0;
				case Y: vel.y = 0;
			}
		}
	}
	
	public void cancelMovement(VelocityComponent vel) {
		cancelSensor(ul.l, Direction.LEFT,  vel);
		cancelSensor(bl.l, Direction.LEFT,  vel);
		
		cancelSensor(ul.u, Direction.UP,    vel);
		cancelSensor(ur.u, Direction.UP,    vel);
		cancelSensor(uc,   Direction.UP,    vel);
		
		cancelSensor(ur.r, Direction.RIGHT, vel);
		cancelSensor(br.r, Direction.RIGHT, vel);
		
		cancelSensor(bl.d, Direction.DOWN,  vel);
		cancelSensor(br.d, Direction.DOWN,  vel);
		cancelSensor(bc,   Direction.DOWN,  vel);
	}
}
