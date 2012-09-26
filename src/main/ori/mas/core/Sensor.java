package ori.mas.core;

import java.util.List;

/**
 * Senses a world and converts it into a list of percepts.
 */
public interface Sensor {

	/**
	 * The sense operation may be seen as a filter of a <i>global</i> perception.
	 * @param w The world to sense.
	 * @return A list of percepts corresponding to the world evolution since last
	 * call to this method.
	 */
	public List<Percept> sense(World w);
	public void setBody(Body b);

};
