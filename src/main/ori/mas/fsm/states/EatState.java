package ori.mas.fsm.states;

import ori.mas.core.Actor;
import ori.mas.core.Agent;
import ori.mas.core.Body;
import ori.mas.core.Properties;
import ori.mas.core.Scene;

import ori.mas.actors.Actors;
import ori.mas.actors.EatActor;
import ori.mas.actors.MovementActor;

import ori.mas.fsm.Transition;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class EatState extends AbstractState {

	public EatState() {
		super();
	}

	public EatState(List<Transition> transitions) {
		super(transitions);
	}

	@Override 
	public Actor actor(Agent a, Scene world) {
		EatActor actor = Actors.selectEatActor(a.body().actors());
		if (actor == null)
			return null;
		Body t = null;
		t = (Body)(a.body().get(Properties.TARGET));
		if ((t != null) && (world.getPartlyIn(a.body()).contains(t))) {
			actor.setTarget(t);
			a.body().set(Properties.TARGET,null);
			return actor;
		}
		return null;
	}

};

