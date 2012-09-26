package ori.mas.actors;

import ori.mas.core.Body;
import ori.mas.influences.MovementInfluence;
import ori.ogapi.geometry.Point;

public class MovementActor extends AbstractActor {

	public MovementActor() {
		super();
	}

	public MovementActor(Body b) {
		super(b);
	}

	public MovementActor(Body b, int maxSpeed) {
		super(b);
		_maxSpeed = maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		_maxSpeed = maxSpeed;
	}

	public int maxSpeed() {
		return _maxSpeed;
	}

	public void setSpeed(int value) {
		_speed = value;
		if (_speed < 0)
			_speed = 0;
		if (_speed > _maxSpeed)
			_speed = _maxSpeed;
	}

	public void setAngle(int angle) {
		_angle = angle;
	}

	public void setHead(Body b) {
		// TODO
		if (b == null) {
			_speed = 0;
			return;
		}
	}

	@Override 
	public MovementInfluence act() {
		if (_speed <= 0)
			return null;
		int x = (int)Math.floor(Math.cos(_angle)*((double)_speed));
		int y = (int)Math.floor(Math.sin(_angle)*((double)_speed));
		return new MovementInfluence(this,this.body(),new Point(x,y));
	}

	private int _speed, _maxSpeed;
	private int _angle;

};

