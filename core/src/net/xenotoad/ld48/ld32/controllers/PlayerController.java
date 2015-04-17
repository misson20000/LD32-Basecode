package net.xenotoad.ld48.ld32.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import net.xenotoad.ld48.ld32.CollisionBox;
import net.xenotoad.ld48.ld32.Debug;
import net.xenotoad.ld48.ld32.components.CollisionComponent;
import net.xenotoad.ld48.ld32.components.FrictionComponent;
import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.SpriteComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;

public class PlayerController extends Controller {
	
	private static final float JUMP_BOOST_TIME = 0.1f;
	private static final float JUMP_ACCEL = 1700f;
	
	private static final float MAX_WALK_SPEED = 100f;
	private static final float WALK_ACCEL = MAX_WALK_SPEED*8f + 800;
	
	private static final int JUMP = Keys.Z;
	private static final int RIGHT = Keys.RIGHT;
	private static final int LEFT = Keys.LEFT;

	private float jumpTimer;
	
	@Override
	public void update(Entity p, float dt) {
		VelocityComponent vel = VelocityComponent.map.get(p);
		PositionComponent pos = PositionComponent.map.get(p);
		CollisionComponent cmp = CollisionComponent.map.get(p);
		SpriteComponent spr = SpriteComponent.map.get(p);
		
		Debug.showValue("jump", Float.toString(jumpTimer));
		
		if(Gdx.input.isKeyJustPressed(JUMP)) {
			if(cmp.state.blockD) {
				jumpTimer = JUMP_BOOST_TIME;
			}
		}
				
		boolean walking = false;
		
		if(Gdx.input.isKeyPressed(RIGHT)) {
			walking = true;
			if(vel.x <  MAX_WALK_SPEED) { vel.x+= WALK_ACCEL*dt; }
		}
		if(Gdx.input.isKeyPressed(LEFT )) {
			walking = true;
			if(vel.x > -MAX_WALK_SPEED) { vel.x-= WALK_ACCEL*dt; }
		}
		
		if(walking) {
			spr.setAnim("walk", false);
		} else {
			spr.setAnim("idle", false);
		}

		
		if(Gdx.input.isKeyPressed(JUMP)) {
			if(jumpTimer > 0) {
				vel.y+= JUMP_ACCEL*dt;
			}
			jumpTimer-= dt;
		} else {
			jumpTimer = 0;
		}
		
		spr.fps = 14;
	}
}