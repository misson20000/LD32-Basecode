package net.xenotoad.ld48.ld32.systems;

import java.util.Random;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import net.xenotoad.ld48.ld32.Mappers;
import net.xenotoad.ld48.ld32.components.LightFlickerComponent;
import net.xenotoad.ld48.ld32.components.LightSourceComponent;

public class LightFlickerSystem extends IteratingSystem {
	
	private Random rng;
	
	@SuppressWarnings("unchecked")
	public LightFlickerSystem() {
		super(Family.getFor(
				LightSourceComponent.class,
				LightFlickerComponent.class
				));
		this.rng = new Random();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		LightSourceComponent  src = Mappers.lightSource .get(entity);
		LightFlickerComponent flk = Mappers.lightFlicker.get(entity);
		
		if((flk.timer-= deltaTime) <= 0) {
  		src.radius = flk.radius + (rng.nextFloat()*flk.rrange) - (flk.rrange/2);
  		src.spread = flk.spread + (rng.nextFloat()*flk.srange) - (flk.srange/2);
  		flk.timer = flk.speed;
		}
	}
	
}
