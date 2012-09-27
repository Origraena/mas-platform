/**
 * ***************
 * @author wdyce
 * @date Feb 15, 2012
 ****************
 */
package wjd.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import ori.mas.actors.EatActor;
import ori.mas.actors.MovementActor;
import ori.mas.core.AdaptedScene;
import ori.mas.core.Agent;
import ori.mas.core.Body;
import ori.mas.core.DefaultHeart;
import ori.mas.core.Properties;
import ori.mas.core.World;
import ori.mas.fsm.StateMachineMind;
import ori.mas.fsm.states.PatrolState;
import ori.mas.fsm.states.PredateState;
import ori.mas.sensors.ShapeSensor;
import ori.ogapi.geometry.Circle;
import ori.ogapi.geometry.LinkedListSurface;
import ori.ogapi.geometry.Point;
import wjd.gui.view.DrawGL;
import wjd.gui.view.ViewPort;
import wjd.math.V2;

public class GUIWindow extends LWJGLWindow
{
  /// CONSTANTS
  private static final int SCOLL_MOUSE_DISTANCE = 48;
  private static final int SCROLL_SPEED = 6;
  private static final float ZOOM_SPEED = 0.001f;
  
  /// TODO -- remove this
  private static final V2 HELLO_POS = new V2(100, 100);
  private static final String HELLO_TEXT = "Hello Swan!";
  
  /// ATTRIBUTES
  private ViewPort view;
  private World world;

  /// METHODS
  // construction
  public GUIWindow(String _name, int _width, int _height)
  {
    // save variables
    super(_name, _width, _height);

    // create view
    view = new ViewPort(new V2(_width, _height));
    
    // start up
    reset();
  }

  private void reset()
  {
    // reset view
    view.reset();
    
    // create the world
    world = new World(new DefaultHeart(), 
      new AdaptedScene(new LinkedListSurface<Body>()));

    // build the patrol-agent's body
		Body body = new Body();
		body.set(Properties.HEALTH, new Integer(1000));
		body.set(Properties.HEALTH_MIN, new Integer(0));
		body.set(Properties.HEALTH_MAX, new Integer(1000));
		body.set(Properties.FEED, new Integer(100));
		body.set(Properties.FEED_MIN, new Integer(0));
		body.set(Properties.FEED_MAX, new Integer(100));
		body.translate(new Point(100, 100));
		body.addActor(new MovementActor(body, 5));
		body.addSensor(new ShapeSensor(body, new Circle(0, 0, 150)));
    // attach a simple mind and add to the world
    world.add(new Agent(new StateMachineMind(new PatrolState()), body));
    
    // build the predate-agent's body
		body = new Body();
		body.set(Properties.HEALTH, new Integer(1000));
		body.set(Properties.HEALTH_MIN, new Integer(0));
		body.set(Properties.HEALTH_MAX, new Integer(1000));
		body.set(Properties.FEED, new Integer(100));
		body.set(Properties.FEED_MIN, new Integer(0));
		body.set(Properties.FEED_MAX, new Integer(100));
		body.translate(new Point(100, 120));
		body.addActor(new MovementActor(body, 10));
		body.addActor(new EatActor(body, 10));
		body.addSensor(new ShapeSensor(body, new Circle(0, 0, 100)));
    // attach a simple mind and add to the world
    world.add(new Agent(new StateMachineMind(new PredateState()), body));
  }

  // overrides
  @Override
  protected void render()
  {
    // standard stuff
    super.render();

    // draw code goes here
    DrawGL.text(HELLO_TEXT, view.getPerspective(HELLO_POS));
  }

  @Override
  protected void processKeyboard()
  {
    // get arrow-key input
    V2 key_dir = new V2(0, 0);
    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
      key_dir.yadd(1);
    if (Keyboard.isKeyDown(Keyboard.KEY_UP))
      key_dir.yadd(-1);
    if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
      key_dir.xadd(1);
    if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
      key_dir.xadd(-1);

    // move the view
    view.translate(key_dir.scale(SCROLL_SPEED));
  }

  @Override
  protected void processMouse()
  {
    // mouse position
    V2 mouse_pos = new V2(Mouse.getX(), getHeight() - Mouse.getY());
    V2 mouse_true = view.getGlobal(mouse_pos);

    // mouse near edges = pan
    V2 scroll_dir = new V2();
     if(mouse_pos.x() < SCOLL_MOUSE_DISTANCE) 
      scroll_dir.x(-1);
     else if(mouse_pos.x() > getWidth() - SCOLL_MOUSE_DISTANCE) 
      scroll_dir.x(1);
     if(mouse_pos.y() < SCOLL_MOUSE_DISTANCE) 
      scroll_dir.y(-1);
     else if(mouse_pos.y() > getHeight() - SCOLL_MOUSE_DISTANCE) 
       scroll_dir.y(1);
     view.translate(scroll_dir.scale(SCROLL_SPEED));

    // mouse wheel = zoom
    int wheel = Mouse.getDWheel();
    if (wheel != 0)
      view.zoom(wheel * ZOOM_SPEED, mouse_pos);
  }

  @Override
  protected void resizeGL()
  {
    // standard stuff
    super.resizeGL();

    // resize viewport too
    view.setWindowSize(new V2(getWidth(), getHeight()));
  }
}
