package ori.mas.core;

import ori.ogapi.util.Iterator;
import ori.ogapi.util.AbstractIterator;
import ori.ogapi.report.Reportable;
import ori.ogapi.report.Reporter;
import ori.ogapi.geometry.Surface;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * How does it work ?
 * Each agent 
 */
public class World implements Iterable<Agent>,Reportable {

	private Scene _scene;
	private LinkedList<Agent> _outsideAgents;
	private Heart _heart;

	public World(Heart h, Scene s) {
		setHeart(h);
		_scene = s;
		_outsideAgents = new LinkedList<Agent>();
	}

	public void setHeart(Heart h) {
		_heart = h;
		_heart.setWorld(this);
	}

	public void setScene(Scene s) {
		_scene = s;
	}
	
	public void tick() {
		for (Agent a : this)
			this.makeInfluence(a.tick(this));
		_heart.pulse();
	}

	public void makeInfluence(Influence i) {
		if (i != null)
			_heart.submitInfluence(i);
	}

	public void makeInfluence(Iterable<Influence> list) {
		for (Influence i : list)
			this.makeInfluence(i);
	}

	public Iterable<Agent> outsideAgents() {
		return _outsideAgents;
	}

	public Iterable<Agent> physicalAgents() {
		return new PhysicalAgentIterator(_scene.iterator());
	}

	public Scene scene() {
		return _scene;
	}
	public LinkedList<Agent> outside() {
		return _outsideAgents;
	}

	@Override 
	public Iterator<Agent> iterator() {
		return new AgentIterator();
	}



	public boolean remove(Agent a) {
		if ((a.hasBody()) && (_scene.remove(a.body())))
			return true;
		return _outsideAgents.remove(a);
	}

	public boolean add(Agent a) {
		if (a.hasBody())
			return _scene.add(a.body());
		return addOutside(a);
	}
	public boolean addToScene(Agent a) {
		if (!(a.hasBody()))
			return false;
		return _scene.add(a.body());
	}
	public boolean addOutside(Agent a) {
		if (_outsideAgents.contains(a))
			return false;
		_outsideAgents.add(a);
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void reportIn(Reporter out) {
		out.newSection(this.hashCode()+"@World{\n");
		out.incSection();
	//	out.report(this.scene());
		for (Agent a : this) {
			out.report(a);
		}
		out.decSection();
		out.newLine();
		out.report("}");
	}
	
	protected class PhysicalAgentIterator extends AbstractIterator<Agent> {
		public PhysicalAgentIterator(Iterator<Body> it) {
			_iterator = it;
		}
		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}
		@Override
		public Agent next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("no more physical agent body");
			return _iterator.next().agent();
		}
		@Override
		public void remove() throws UnsupportedOperationException,NoSuchElementException {
			_iterator.remove();
		}
		private Iterator<Body> _iterator;
	};
	protected class AgentIterator extends AbstractIterator<Agent> {
		public AgentIterator() {
			_physicalIterator = physicalAgents().iterator();
			_outsideIterator = outsideAgents().iterator();
		}
		@Override
		public boolean hasNext() {
			return (_physicalIterator.hasNext() || _outsideIterator.hasNext());
		}
		@Override
		public Agent next() throws NoSuchElementException {
			if (_physicalIterator.hasNext()) {
				_state = true;
				return _physicalIterator.next();
			}
			else if (_outsideIterator.hasNext()) {
				_state = false;
				return _outsideIterator.next();
			}
			throw new NoSuchElementException("no more agent in world");
		}
		@Override
		public void remove() throws UnsupportedOperationException,NoSuchElementException {
			if (_state)
				_physicalIterator.remove();
			else
				_outsideIterator.remove();
		}
		private java.util.Iterator<Agent> _physicalIterator;
		private java.util.Iterator<Agent> _outsideIterator;
		private boolean _state = true;
	}

};

