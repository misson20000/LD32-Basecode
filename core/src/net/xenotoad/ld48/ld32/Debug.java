package net.xenotoad.ld48.ld32;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Debug {
	public static SpriteBatch batch = new SpriteBatch();
	public static BitmapFont font = new BitmapFont(
			Gdx.files.internal("font.fnt"),
			Gdx.files.internal("font.png"), false);
	
	public static ShapeRenderer shapes = new ShapeRenderer();
	
	private static Map<String, String> values = new HashMap<String, String>();
	
	public static void showValue(String key, String val) {
		values.put(key, val);
	}
	
	public static void renderValues() {
		int y = Gdx.graphics.getHeight();
		shapes.begin(ShapeType.Filled);
		
		float maxKey = font.getBounds("Key").width;
		float maxVal = font.getBounds("Value").width;
		
		for(Map.Entry<String, String> e : values.entrySet()) {
			TextBounds k = font.getBounds(e.getKey());
			if(k.width > maxKey) { maxKey = k.width; }
			
			TextBounds v = font.getBounds(e.getValue());
			if(v.width > maxVal) { maxVal = v.width; }
		}
		
		shapes.setColor(0.8f, 0.8f, 0.8f, 0.5f);
		
		y-= 30;
		shapes.rect(10, y, maxKey + maxVal + 20, 20);
		shapes.rect(10, y, maxKey + 6, 20);
		
		boolean zebra = false;
		for(Map.Entry<String, String> e : values.entrySet()) {
			
			if(zebra) {
				shapes.setColor(0.6f, 0.6f, 0.6f, 0.5f);
			} else {
				shapes.setColor(0.4f, 0.4f, 0.4f, 0.5f);
			}
			
			y-= 23;
			shapes.rect(10, y, maxKey + maxVal + 20, 23);
			shapes.rect(10, y, maxKey + 6, 23);
			zebra = !zebra;
		}
		shapes.end();
		
		batch.begin();
		y = Gdx.graphics.getHeight()-14;
		font.draw(batch, "Key", 13, y);
		font.draw(batch, "Value", maxKey + 20, y);
		for(Map.Entry<String, String> e : values.entrySet()) {
			y-= 23;
			font.draw(batch, e.getKey(), 13, y);
			font.draw(batch, e.getValue(), maxKey + 20, y);
		}
		batch.end();
	}
}
