package net.xenotoad.ld48.ld32.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import net.xenotoad.ld48.ld32.CollisionBox;
import net.xenotoad.ld48.ld32.components.CollisionComponent;
import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.SizeComponent;
import net.xenotoad.ld48.ld32.components.VelocityComponent;

public class CollisionSystem extends IteratingSystem {
	
	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Family.getFor(
				PositionComponent.class,
				SizeComponent.class,
				CollisionComponent.class));
		
		this.priority = -2;
	}

	@Override
	protected void processEntity(Entity e, float dt) {
		CollisionComponent cmp = CollisionComponent.map.get(e);
		PositionComponent pos = PositionComponent.map.get(e);

		cmp.state.blockD = false;
		cmp.state.blockU = false;
		cmp.state.blockL = false;
		cmp.state.blockR = false;

		//cmp.getBox().wouldCollide(cmp.state, pos.x, pos.y, cmp.tmx);
	}
	
	enum Comparation {
		    LESS     {@Override	public boolean compare(float a, float b) {
				return a < b;
		}}, DONTCARE {@Override public boolean compare(float a, float b) {
				return true;
		}}, GREATER  {@Override public boolean compare(float a, float b) {
				return a>b;
		}};
		
		public abstract boolean compare(float a, float b);
	}
	
	public enum Axis {
		X,Y
	}
	
	public enum Direction {
		UP   (Comparation.DONTCARE, Comparation.GREATER,  Axis.Y),
		LEFT (Comparation.LESS,     Comparation.DONTCARE, Axis.X),
		RIGHT(Comparation.GREATER,  Comparation.DONTCARE, Axis.X),
		DOWN (Comparation.DONTCARE, Comparation.LESS,     Axis.Y);
		
		public Comparation x,y;
		public Axis zero;
		
		Direction(Comparation x, Comparation y, Axis zero) {
			this.x = x; this.y = y;
			this.zero = zero;
		}
		
		public boolean isGoing(VelocityComponent vel) {
			return x.compare(vel.x, 0) && y.compare(vel.y, 0);
		}
	}
}
