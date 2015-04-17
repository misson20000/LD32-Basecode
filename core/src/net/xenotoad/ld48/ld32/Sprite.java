package net.xenotoad.ld48.ld32;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite {
	public SpriteType type;
	public TextureRegion frames[];
	
	public float w;
	public float h;
	
	public Sprite(Texture t, int w, int h, SpriteType type) {
		this.type = type;
		this.w = w; this.h = h;
		
		this.frames = new TextureRegion[(int) ((t.getWidth()/w)*(t.getHeight()/h))];
		
		for(int x = 0; x < t.getWidth()/w; x++) {
			for(int y = 0; y < t.getHeight()/h; y++) {
				frames[y*(t.getWidth()/w) + x] = new TextureRegion(t, x*w, y*h, w, h);
			}
		}
	}	
}
