package ori.mas.actors;

import ori.mas.core.Body;
import ori.mas.influences.ChangeBodyPropertyInfluence;

import ori.ogapi.util.OperatorPlus;
import java.util.Comparator;

public abstract class ChangeBodyPropertyActor extends BodyTargetActor {

	public ChangeBodyPropertyActor() {
		super();
	}

	public ChangeBodyPropertyActor(Body b) {
		super(b);
	}

	public ChangeBodyPropertyActor(Body b, String property) {
		super(b);
		_property = property;
	}

	public ChangeBodyPropertyActor(Body b, String property, Object value) {
		super(b);
		_property = property;
		_value = value;
	}

	public String property() {
		return _property;
	}

	public Object value() {
		return _value;
	}

	public void setValue(Object value) {
		_value = value;
	}

	@Override
	public ChangeBodyPropertyInfluence act() {
		return new ChangeBodyPropertyInfluence(this,
		                                       this.target(),
		                                       this.property(),
		                                       this.value());
	}


	private String _property;
	private Object _value;

};

