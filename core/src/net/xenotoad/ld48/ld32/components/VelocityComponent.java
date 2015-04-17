package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class VelocityComponent extends Component {
	public float x;
	public float y;
	public static final ComponentMapper<VelocityComponent> map = ComponentMapper.getFor(VelocityComponent.class);
	
	public VelocityComponent(float x, float y) { this.x = x; this.y = y; }
	
	public float getSpeedSqrd() {
		return (x*x)+(y*y);
	}
}
