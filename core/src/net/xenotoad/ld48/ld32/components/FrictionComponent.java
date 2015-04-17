package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;

public class FrictionComponent extends Component {

	public float factor;
	
	public FrictionComponent(float f) {
		this.factor = f;
	}
	
}
