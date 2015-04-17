package net.xenotoad.ld48.ld32;

import com.badlogic.ashley.core.ComponentMapper;

import net.xenotoad.ld48.ld32.components.FrictionComponent;
import net.xenotoad.ld48.ld32.components.GravityComponent;
import net.xenotoad.ld48.ld32.components.LightFlickerComponent;
import net.xenotoad.ld48.ld32.components.LightSourceComponent;
import net.xenotoad.ld48.ld32.components.PositionComponent;
import net.xenotoad.ld48.ld32.components.SizeComponent;

public class Mappers {
	public static final ComponentMapper<    PositionComponent> position     = ComponentMapper.getFor(    PositionComponent.class);
	public static final ComponentMapper< LightSourceComponent> lightSource  = ComponentMapper.getFor( LightSourceComponent.class);
	public static final ComponentMapper<LightFlickerComponent> lightFlicker = ComponentMapper.getFor(LightFlickerComponent.class);
	public static final ComponentMapper<    FrictionComponent> friction     = ComponentMapper.getFor(    FrictionComponent.class);
	public static final ComponentMapper<        SizeComponent> size         = ComponentMapper.getFor(        SizeComponent.class);
	public static final ComponentMapper<     GravityComponent> gravity      = ComponentMapper.getFor(     GravityComponent.class);
}
