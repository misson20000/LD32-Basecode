package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;

public class GravityComponent extends Component {
	public float accel;
	public GravityComponent(float accel) {
		this.accel = accel;
	}
}
