package net.xenotoad.ld48.ld32;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

import net.xenotoad.ld48.ld32.components.LightFlickerComponent;
import net.xenotoad.ld48.ld32.components.LightSourceComponent;
import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.SizeComponent;
import net.xenotoad.ld48.ld32.components.SpriteComponent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class ObjectTransformer {
	
	private static final Sprite TORCH_SPRITE = new Sprite(
			new Texture(Gdx.files.internal("torch.png")), 16, 16,
			new SpriteType(new BufferedReader(new InputStreamReader(
					Gdx.files.internal("spriteTypes/torch.spr").read()))));
	
	public Entity transform(MapObject o, MapHost mh) {
		MapProperties p = o.getProperties();
		
		Rectangle r = ((RectangleMapObject) o).getRectangle();
		
		if(p.get("type", String.class).equals("player_spawn")) {
			System.out.println("RAW: (" + r.x + ", " + r.y + ") x (" + r.width + ", " + r.height + ")");

			mh.setSpawn(r.x, r.y);
			//mh.setSpawn(r.x, mh.getHeight()-r.y-r.height);
			return null;
		}
		
		Entity e = new Entity();
		if(p.get("type", String.class).equals("torch")) {
			e.add(new LightSourceComponent(100, -10, 0xFFFFFF));
			e.add(new LightFlickerComponent(100, -10, 4, 2f, 1f/20f));
			e.add(new PositionComponent(r.x, r.y));
			e.add(new SizeComponent(r.width, r.height));
			e.add(new SpriteComponent(TORCH_SPRITE));
		}
		return e;
	}
}
