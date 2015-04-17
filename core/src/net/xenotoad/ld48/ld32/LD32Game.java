package net.xenotoad.ld48.ld32;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.xenotoad.ld48.ld32.screens.ScreenShaderTest;
import net.xenotoad.ld48.ld32.screens.ScreenTestTilemap;

public class LD32Game extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create() {
		this.setScreen(new ScreenTestTilemap());
	}
	
	@Override
	public void render() {
		super.render();
	}
}
