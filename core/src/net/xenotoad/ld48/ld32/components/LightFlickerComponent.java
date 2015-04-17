package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;

public class LightFlickerComponent extends Component {

	public float radius;
	public float spread;
	public float rrange;
	public float srange;
	public float speed;
	public float timer;
	
	public LightFlickerComponent(float radius, float spread, float rrange, float srange, float speed) {
		this.radius = radius;
		this.spread = spread;
		this.rrange = rrange;
		this.srange = srange;
		this.speed = speed;
		this.timer = 0f;
	}
}
