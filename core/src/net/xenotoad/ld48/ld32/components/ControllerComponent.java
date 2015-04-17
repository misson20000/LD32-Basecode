package net.xenotoad.ld48.ld32.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

import net.xenotoad.ld48.ld32.controllers.Controller;

public class ControllerComponent extends Component {
	public static final ComponentMapper<ControllerComponent> map =
			ComponentMapper.getFor(ControllerComponent.class);
	
	public Controller controller;
	
	public ControllerComponent(Controller c) {
		this.controller = c;
	}
}
