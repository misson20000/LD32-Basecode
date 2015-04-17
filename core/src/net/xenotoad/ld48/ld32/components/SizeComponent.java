package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class SizeComponent extends Component {
	public static final ComponentMapper<SizeComponent> map =
			ComponentMapper.getFor(SizeComponent.class);
	public float w;
	public float h;
	public SizeComponent(float width, float height) {
		this.w = width; this.h = height;
	}
}
