package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

import net.xenotoad.ld48.ld32.Anim;
import net.xenotoad.ld48.ld32.Sprite;

public class SpriteComponent extends Component {
	public static final ComponentMapper<SpriteComponent> map =
			ComponentMapper.getFor(SpriteComponent.class);
	
	public Sprite sprite;
	public float animTimer;
	public int animFrame;
	public float fps = 10;
	public Anim currentAnimation;
	
	public SpriteComponent(Sprite sprite) {
		this.sprite = sprite;
		this.currentAnimation = sprite.type.animMap.get("idle");
		this.animFrame = 0;
		this.animTimer = 1f / fps;
	}
	
	public void setAnim(String name, boolean forceRestart) {
		if(!forceRestart && sprite.type.animMap.get(name) == currentAnimation) { return; }
		this.animFrame = 0;
		this.currentAnimation = sprite.type.animMap.get(name);
	}
}
