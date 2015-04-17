package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class PositionComponent extends Component {
	public static final ComponentMapper<PositionComponent> map =
			ComponentMapper.getFor(PositionComponent.class);
	
	public float x;
	public float y;
	
	public PositionComponent(float x, float y) { this.x = x; this.y = y; }
}
