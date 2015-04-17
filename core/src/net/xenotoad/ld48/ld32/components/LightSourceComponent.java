package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;

public class LightSourceComponent extends Component {
	public float radius;
	public float spread;
	public int r;
	public int g;
	public int b;
	
	public LightSourceComponent(int radius, int spread, int rgb) {
		this.radius = radius;
		this.spread = spread;
		this.r = (rgb & 0xFF0000) >> 0x10;
		this.g = (rgb & 0x00FF00) >> 0x08;
		this.b = (rgb & 0x0000FF) >> 0x00;
	}
}
