package ori.mas.fsm.states;

import ori.mas.core.Agent;
import ori.mas.core.Actor;
import ori.mas.core.Scene;

import ori.mas.actors.Actors;
import ori.mas.actors.MovementActor;

import ori.mas.fsm.Transition;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class PatrolState extends AbstractState {

	public PatrolState() {
		super();
	}

	public PatrolState(List<Transition> transitions) {
		super(transitions);
	}

	@Override 
	public Actor actor(Agent a, Scene world) {
		MovementActor actor = Actors.selectMaxSpeedMovementActor(a.body().actors());
		if (actor == null)
			return null;
		_angle = _angle + random(-22,22);
		actor.setAngle(_angle);
		actor.setSpeed(actor.maxSpeed());
		return actor;
	}

	private int _angle = 0;

};

