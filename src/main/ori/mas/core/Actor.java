package ori.mas.core;

public interface Actor {

	public void setBody(Body b);
	public Influence act(float t_delta);
	public Actor clone();

};

