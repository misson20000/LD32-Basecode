package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;

public class GentleRandomMovementComponent extends Component {

	public float x;
	public float y;
	
	public GentleRandomMovementComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
}
