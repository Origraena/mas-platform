package ori.mas.sensors;

import ori.mas.core.Body;
import ori.mas.core.Percept;
import ori.mas.core.World;

import ori.mas.percepts.SurfacePercept;

import ori.ogapi.geometry.Shape;
import ori.ogapi.geometry.Point;
import java.util.List;
import java.util.LinkedList;

public class ShapeSensor extends AbstractSensor {

	public ShapeSensor() {
		super();
	}

	public ShapeSensor(Body b) {
		super(b);
		_shape = null;
	}

	public ShapeSensor(Body b, Shape shape) {
		super(b);
		_shape = shape;
	}

	@Override 
	public List<Percept> sense(World w) {
		List<Percept> result = new LinkedList<Percept>();
		Shape shape = _shape.clone();
		shape.translate(this.body().center());
		SurfacePercept percept = new SurfacePercept(w.scene().getPartlyIn(shape));
		result.add(percept);
		return result;
	}

	private Shape _shape;

};

