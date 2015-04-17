package net.xenotoad.ld48.ld32.screens;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import net.xenotoad.ld48.ld32.Debug;
import net.xenotoad.ld48.ld32.MapHost;
import net.xenotoad.ld48.ld32.Mappers;
import net.xenotoad.ld48.ld32.ObjectTransformer;
import net.xenotoad.ld48.ld32.ShakeController;
import net.xenotoad.ld48.ld32.Sprite;
import net.xenotoad.ld48.ld32.SpriteType;
import net.xenotoad.ld48.ld32.components.CollisionComponent;
import net.xenotoad.ld48.ld32.components.ControllerComponent;
import net.xenotoad.ld48.ld32.components.FrictionComponent;
import net.xenotoad.ld48.ld32.components.GravityComponent;
import net.xenotoad.ld48.ld32.components.GroundShakeComponent;
import net.xenotoad.ld48.ld32.components.LightSourceComponent;
import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.SizeComponent;
import net.xenotoad.ld48.ld32.components.SpriteComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;
import net.xenotoad.ld48.ld32.controllers.PlayerController;
import net.xenotoad.ld48.ld32.systems.AnimationSystem;
import net.xenotoad.ld48.ld32.systems.CollisionSystem;
import net.xenotoad.ld48.ld32.systems.ControllerSystem;
import net.xenotoad.ld48.ld32.systems.DebugBBRenderSystem;
import net.xenotoad.ld48.ld32.systems.FrictionSystem;
import net.xenotoad.ld48.ld32.systems.GravitySystem;
import net.xenotoad.ld48.ld32.systems.LightFlickerSystem;
import net.xenotoad.ld48.ld32.systems.PhysicsSystem;

public class ScreenTestTilemap implements Screen, MapHost, ShakeController {
	
	private static final float PIXEL_SCALE = 2f;
	
	private float tmH;
	private float tmW;
	
	private SpriteBatch sprites;
	private FrameBuffer fbo;
	private ShaderProgram shader;
	
	private Engine eng;
	private TiledMap tmx;
	private OrthographicCamera cam;
	private OrthogonalTiledMapRenderer tmr;
	private float spawnX=0,spawnY=0;
	private Entity player;
	private PositionComponent ppos;
	
	private DebugBBRenderSystem sys;
	
	private float shakeStrength;
	private Random shakeRNG = new Random();
	
	private static final int NUM_LIGHTS = 16;
	
	private float[] color   = new float[NUM_LIGHTS*3];
	private float[] pos     = new float[NUM_LIGHTS*2];
	private float[] radius  = new float[NUM_LIGHTS];
	private float[] spread  = new float[NUM_LIGHTS];
	private float[] ambient = {1f, 1f, 1f, 0.5f};

	private SpriteBatch lightBatch;
	
	@Override
	public void show() {
		TmxMapLoader.Parameters p = new TmxMapLoader.Parameters();
		p.convertObjectToTileSpace = false;
		tmx = new TmxMapLoader().load("levels/test.tmx", p);
		cam = new OrthographicCamera(800/PIXEL_SCALE, 600/PIXEL_SCALE);
		tmr = new OrthogonalTiledMapRenderer(tmx, 1);
		eng = new Engine();
		
		sprites = new SpriteBatch();
		lightBatch = new SpriteBatch();
		
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(
				Gdx.files.internal("vertex.glsl"),
				Gdx.files.internal("lights.f.glsl"));
		
		if(!shader.isCompiled()) {
			System.out.println(shader.getLog());
		}
		
		calcTmWH();
		
		ObjectTransformer ot = new ObjectTransformer();
		
		for(MapLayer l : tmx.getLayers()) {
			for(MapObject o : l.getObjects()) {
				Entity e = ot.transform(o, this);
				if(e != null) { eng.addEntity(e); }
			}
		}
		
		player = new Entity();
		player.add(       new  FrictionComponent(800));
		player.add(ppos = new  PositionComponent(spawnX, spawnY));
		player.add(       new  SizeComponent(16, 16));
		player.add(       new  VelocityComponent(0, 0));
		player.add(       new   GravityComponent(-200));
		player.add(       new CollisionComponent(tmx, player));
		
		player.add(new SpriteComponent(new Sprite(
				new Texture(Gdx.files.internal("knight.png")),
				16, 16, new SpriteType(new BufferedReader(new InputStreamReader(
						Gdx.files.internal("spriteTypes/human.spr").read()))))));
		
		player.add(new GroundShakeComponent());
		player.add(new ControllerComponent(new PlayerController()));
		eng.addEntity(player);
		
		//eng.addSystem(new DebugBBRenderSystem(new ShapeRenderer(), cam));
		eng.addSystem(new FrictionSystem());
		eng.addSystem(new PhysicsSystem(this));
		eng.addSystem(new GravitySystem());
		eng.addSystem(new CollisionSystem());
		eng.addSystem(new ControllerSystem());
		eng.addSystem(new AnimationSystem());
		eng.addSystem(new LightFlickerSystem());
		
		eng.addSystem(new EntitySystem() {
			@Override
			public void update(float dt) {

				@SuppressWarnings("unchecked")
				ImmutableArray<Entity> arr = eng.getEntitiesFor(
				  Family.getFor(PositionComponent.class, LightSourceComponent.class));
				
				Vector3 work = new Vector3();
				
				for(int i = 0; i < Math.min(arr.size(), 256); i++) {
					LightSourceComponent l = Mappers.lightSource.get(arr.get(i));
					   PositionComponent p = Mappers.   position.get(arr.get(i));
					   
					color[(i*3)+0] = l.r/256f;
					color[(i*3)+1] = l.g/256f;
					color[(i*3)+2] = l.b/256f;
					
					work.x = p.x; work.y = p.y; work.z = 0;
					work = cam.project(work);
					
					pos[(i*2)+0] = work.x;
					pos[(i*2)+1] = work.y;
					
					radius[i] = l.radius;
					spread[i] = l.spread;
				}
			}
		});
	}
	
	private void calcTmWH() {
    MapProperties prop = tmx.getProperties();
    int mapWidth = prop.get("width", Integer.class);
    int mapHeight = prop.get("height", Integer.class);
    int tilePixelWidth = prop.get("tilewidth", Integer.class);
    int tilePixelHeight = prop.get("tileheight", Integer.class);
    tmW = mapWidth  * tilePixelWidth;
    tmH = mapHeight * tilePixelHeight;
  }

	@Override
	public void render(float dt) {
		cam.position.x = ppos.x;
		cam.position.y = ppos.y;
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			shakeStrength+= 10;
		}
		
		if(shakeStrength > 1) {
		
			cam.position.x+= (shakeRNG.nextFloat()*shakeStrength)-(shakeStrength/2);
			cam.position.y+= (shakeRNG.nextFloat()*shakeStrength)-(shakeStrength/2);
			
			shakeStrength*= 0.9;
		} else {
			shakeStrength = 0;
		}
		cam.update();
		
		fbo.begin();
  		Gdx.gl.glClearColor(0.7f, 0.7f, 1f, 1f);
  		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
  		
  		tmr.setView(cam);
  		tmr.render();
  		
  		sprites.setProjectionMatrix(cam.combined);
  		sprites.begin();
  		
  		@SuppressWarnings("unchecked")
  		ImmutableArray<Entity> ia = eng.getEntitiesFor(Family.getFor(
  				SpriteComponent.class, PositionComponent.class));
  		for(int i = 0; i < ia.size(); i++) {
  			SpriteComponent spr = SpriteComponent.map.get(ia.get(i));
  			PositionComponent pos = PositionComponent.map.get(ia.get(i));
  			
  			sprites.draw(spr.sprite.frames[spr.currentAnimation.frames[spr.animFrame]], pos.x, pos.y);
  		}
  		sprites.end();
		fbo.end();
		
		shader.begin();
  		shader.setUniformi("buf", 0);
  		shader.setUniform3fv("color", color, 0, NUM_LIGHTS*3);
  		shader.setUniform1fv("radius", radius, 0, NUM_LIGHTS);
  		shader.setUniform1fv("spread", spread, 0, NUM_LIGHTS);
  		shader.setUniform2fv("pos", pos, 0, NUM_LIGHTS*2);
  		shader.setUniform4fv("ambient", ambient, 0, 4);
  		
  		fbo.getColorBufferTexture().bind(0);
  		
  		lightBatch.begin();
    		lightBatch.setShader(shader);
    		lightBatch.draw(fbo.getColorBufferTexture(), 0, 0);
  		lightBatch.end();
  	shader.end();
		
		Debug.batch.begin();
		eng.update(dt);
		
		Debug.batch.end();
		
		Debug.renderValues();
	}
	
	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width/PIXEL_SCALE, height/PIXEL_SCALE);
		fbo = new FrameBuffer(Format.RGB888, width, height, true);
	}
	
	@Override
	public void pause() {}
	
	@Override
	public void resume() {}
	
	@Override
	public void hide() {}
	
	@Override
	public void dispose() {
		tmx.dispose();
		tmr.dispose();
	}

	@Override
	public void setSpawn(float x, float y) {
		spawnX = x;
		spawnY = y;
	}
	
	@Override
	public float getHeight() {
		return tmH;
	}

	@Override
	public void shake(float str) {
		this.shakeStrength+= str;
	}	
}
