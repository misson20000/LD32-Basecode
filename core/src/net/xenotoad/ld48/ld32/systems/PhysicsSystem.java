package net.xenotoad.ld48.ld32.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import net.xenotoad.ld48.ld32.CollisionState;
import net.xenotoad.ld48.ld32.Mappers;
import net.xenotoad.ld48.ld32.ShakeController;
import net.xenotoad.ld48.ld32.components.CollisionComponent;
import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.SizeComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;

public class PhysicsSystem extends IteratingSystem {
	
	private ShakeController shake;
	
	@SuppressWarnings("unchecked")
	public PhysicsSystem(ShakeController shake) {
		super(Family.getFor(
				PositionComponent.class,
				VelocityComponent.class
				));
		
		this.shake = shake;
		this.priority = -1;
	}
	
	@Override
	protected void processEntity(Entity e, float dt) {
		PositionComponent pos = Mappers.position.get(e);
		VelocityComponent vel = VelocityComponent.map.get(e);
		CollisionComponent cmp = CollisionComponent.map.get(e);
		SizeComponent siz = SizeComponent.map.get(e);
		
		CollisionState state = new CollisionState();
		if(vel.x > 0) {
			for(float vx = vel.x*dt; vx > 0; vx--) {
				float d = (vx < 1) ? vx : 1;
				if(state.update(pos.x+d, pos.y, siz, cmp.tmx).blockR) {
					vel.x = 0;
					cmp.state.blockR = true;
					break;
				} else { pos.x+= d; }
			}
		} else {
			for(float vx = vel.x*dt; vx < 0; vx++) {
				float d = (vx > -1) ? vx : -1;
				if(state.update(pos.x+d, pos.y, siz, cmp.tmx).blockL) {
					vel.x = 0;
					cmp.state.blockL = true;
					break;
				} else { pos.x+= d; }
			}
		}
		
		if(vel.y > 0) {
			for(float vy = vel.y*dt; vy > 0; vy--) {
				float d = (vy < 1) ? vy : 1;
				if(state.update(pos.x, pos.y+d, siz, cmp.tmx).blockU) {
					vel.y = -50;
					cmp.state.blockU = true;
					break;
				} else { pos.y+= d; }
			}
		} else {
			for(float vy = vel.y*dt; vy < 0; vy++) {
				float d = (vy > -1) ? vy : -1;
				if(state.update(pos.x, pos.y+d, siz, cmp.tmx).blockD) {
					vel.y = 0;
					cmp.state.blockD = true;
					break;
				} else { pos.y+= d; }
			}
		}
	}
	
}
