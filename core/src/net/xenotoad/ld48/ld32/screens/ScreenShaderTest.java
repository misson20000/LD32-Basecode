package net.xenotoad.ld48.ld32.screens;

import java.util.Random;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import net.xenotoad.ld48.ld32.Mappers;
import net.xenotoad.ld48.ld32.components.FrictionComponent;
import net.xenotoad.ld48.ld32.components.GentleRandomMovementComponent;
import net.xenotoad.ld48.ld32.components.LightFlickerComponent;
import net.xenotoad.ld48.ld32.components.LightSourceComponent;
import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;
import net.xenotoad.ld48.ld32.render.LightSystem;
import net.xenotoad.ld48.ld32.systems.FrictionSystem;
import net.xenotoad.ld48.ld32.systems.GentleRandomMovementSystem;
import net.xenotoad.ld48.ld32.systems.LightFlickerSystem;
import net.xenotoad.ld48.ld32.systems.PhysicsSystem;

public class ScreenShaderTest implements Screen {
	
	private Engine eng;
	private Random rng;
		
	private FrameBuffer fbo;
	private SpriteBatch lightBatch;
	private SpriteBatch   guiBatch;
	
	private ShaderProgram shader;
	
	private static final int NUM_LIGHTS = 16;
	
	private float[] color   = new float[NUM_LIGHTS*3];
	private float[] pos     = new float[NUM_LIGHTS*2];
	private float[] radius  = new float[NUM_LIGHTS];
	private float[] spread  = new float[NUM_LIGHTS];
	private float[] ambient = {0.5f, 0f, 0.5f, 0.2f};
	
	private BitmapFont font;
	
	@Override
	public void show() {
		eng = new Engine();
		rng = new Random();
		
		fbo = null;
				
		lightBatch = new SpriteBatch();
		  guiBatch = new SpriteBatch();
		
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(
				Gdx.files.internal("vertex.glsl"),
				Gdx.files.internal("lights.f.glsl"));
		
		if(!shader.isCompiled()) {
			System.out.println(shader.getLog());
		}
		
		eng.addSystem(new GentleRandomMovementSystem());
		eng.addSystem(new LightFlickerSystem());
		eng.addSystem(new FrictionSystem());
		eng.addSystem(new PhysicsSystem(null));
		
		eng.addSystem(new EntitySystem() {
			@Override
			public void update(float dt) {

				@SuppressWarnings("unchecked")
				ImmutableArray<Entity> arr = eng.getEntitiesFor(
				  Family.getFor(PositionComponent.class, LightSourceComponent.class));
				
				for(int i = 0; i < Math.min(arr.size(), 256); i++) {
					LightSourceComponent l = Mappers.lightSource.get(arr.get(i));
					   PositionComponent p = Mappers.   position.get(arr.get(i));
					color[(i*3)+0] = l.r/256f;
					color[(i*3)+1] = l.g/256f;
					color[(i*3)+2] = l.b/256f;
					
					pos[(i*2)+0] = p.x;
					pos[(i*2)+1] = p.y;
					
					radius[i] = l.radius;
					spread[i] = l.spread;
				}
			}
		});
		
		font = new BitmapFont();
	}
	
	@Override
	public void render(float delta) {
		eng.update(delta);
		
		fbo.begin();
	  Gdx.gl.glViewport(0, 0, fbo.getWidth(), fbo.getHeight());
	  Gdx.gl.glClearColor(1, 1, 1, 1);
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		fbo.end();
		
		shader.begin();
  		shader.setUniformi("buf", 1);
  		shader.setUniform3fv("color", color, 0, NUM_LIGHTS*3);
  		shader.setUniform1fv("radius", radius, 0, NUM_LIGHTS);
  		shader.setUniform1fv("spread", spread, 0, NUM_LIGHTS);
  		shader.setUniform2fv("pos", pos, 0, NUM_LIGHTS*2);
  		shader.setUniform4fv("ambient", ambient, 0, 4);
  		
  		fbo.getColorBufferTexture().bind(1);
  		
  		lightBatch.begin();
    		lightBatch.setShader(shader);
    		lightBatch.draw(fbo.getColorBufferTexture(), 0, 0);
  		lightBatch.end();
  	shader.end();
		
		System.out.println(1/delta);
	}
	
	@Override
	public void resize(int width, int height) {
		if(fbo != null) { fbo.dispose(); }
		fbo = new FrameBuffer(Format.RGB888, width, height, true);
		
		eng.removeAllEntities();
		for(int i = 0; i < 16; i++) {
			Entity f = new Entity();
			f.add(new PositionComponent(rng.nextInt(width), rng.nextInt(height)));
			f.add(new LightSourceComponent(20, -10, 0xE1F5BF));
			f.add(new LightFlickerComponent(20, -10, 1, 0, 1f/20f));
			f.add(new VelocityComponent(0, 0));
			f.add(new FrictionComponent(0.05f));
			f.add(new GentleRandomMovementComponent(10f, 10f));
			eng.addEntity(f);
		}
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void resume() {		
	}
	
	@Override
	public void hide() {		
	}
	
	@Override
	public void dispose() {		
		fbo.dispose();
		lightBatch.dispose();
	}
}
