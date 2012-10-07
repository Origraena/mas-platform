/*
 Copyright (C) 2012 William James Dyce

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wjd.gui.model;

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
import ori.ogapi.util.Iterator;
import wjd.gui.control.EUpdateResult;
import wjd.gui.control.IInput;
import wjd.gui.control.IInput.KeyPress;
import wjd.gui.control.IInput.MouseClick;
import wjd.gui.view.Camera;
import wjd.gui.view.ICanvas;
import wjd.gui.window.IWindow;
import wjd.math.V2;

/**
 * An IScene in which agent go about the business.
 *
 * @author wdyce
 * @since Sep 27, 2012
 */
public class AgentScene extends Scene
{
  /* CONSTANTS */
  private static final V2 HELLO_POS = new V2(100, 100);
  private static final String HELLO_TEXT = "Hello Agents!";
  
  /* FUNCTIONS */
  private static Agent newPredator(V2 position)
  {
    // build the predate-agent's body
    Body body = new Body();
    body.set(Properties.HEALTH, new Integer(1000));
    body.set(Properties.HEALTH_MIN, new Integer(0));
    body.set(Properties.HEALTH_MAX, new Integer(1000));
    body.set(Properties.FEED, new Integer(100));
    body.set(Properties.FEED_MIN, new Integer(0));
    body.set(Properties.FEED_MAX, new Integer(100));
    body.translate(new Point(position.x(), position.y()));
    body.addActor(new MovementActor(body, 8));
    body.addActor(new EatActor(body, 10));
    body.addSensor(new ShapeSensor(body, new Circle(0, 0, 100)));
    // attach a simple mind and add to the world
    return new Agent(new StateMachineMind(new PredateState()), body);
  }
  
  private static Agent newPrey(V2 position)
  {
    // build the patrol-agent's body
    Body body = new Body();
    body.set(Properties.HEALTH, new Integer(1000));
    body.set(Properties.HEALTH_MIN, new Integer(0));
    body.set(Properties.HEALTH_MAX, new Integer(1000));
    body.set(Properties.FEED, new Integer(100));
    body.set(Properties.FEED_MIN, new Integer(0));
    body.set(Properties.FEED_MAX, new Integer(100));
    body.translate(new Point(position.x(), position.y()));
    body.addActor(new MovementActor(body, 4));
    body.addSensor(new ShapeSensor(body, new Circle(0, 0, 150)));
    // attach a simple mind and add to the world
    return new Agent(new StateMachineMind(new PatrolState()), body);
  }
  
  /* ATTRIBUTES */
  private World world;
  private Camera camera;

  /// METHODS
  // construction
  public AgentScene(IWindow window)
  {
    super(window);
    // view
    camera = new Camera(new V2(), null); // null => no boundary
    // start up
    reset();
  }

  private void reset()
  {
    // create the world
    world = new World(new DefaultHeart(),
      new AdaptedScene(new LinkedListSurface<Body>()));

    // create agents
    V2 spawn_pos = new V2();
    for(int i = 0; i < 50; i++)
    {
      spawn_pos.xy((float)Math.random()*2000-1000, (float)Math.random()*2000-1000);
      world.add(newPredator(spawn_pos));
      spawn_pos.xy((float)Math.random()*2000-1000, (float)Math.random()*2000-1000);
      world.add(newPrey(spawn_pos));
    }
  }

  // overrides
  @Override
  public EUpdateResult update(int t_delta)
  {
    // update the clear
    world.tick();
    
    // all clear !
    return EUpdateResult.CONTINUE;
  }

  @Override
  public void render(ICanvas canvas, Camera unused)
  {
    // clear the screen
    canvas.clear();
    
    // draw agents
    Iterator itr = world.iterator();
    int i =0;
    while (itr.hasNext())
    {
      Point pos = ((Agent) itr.next()).body().center();
      canvas.circle(camera.getPerspective(new V2(pos.x, pos.y)), 
                                            8 * camera.getZoom());
    }

    // draw hello text
    canvas.text(HELLO_TEXT, camera.getPerspective(HELLO_POS));
  }

  @Override
  public EUpdateResult processStaticInput(IInput input, V2 window_size)
  {
    // exit if the escape key is pressed
    if(input.isKeyHeld(IInput.EKeyCode.ESC))
      return EUpdateResult.STOP;
    else
      return EUpdateResult.CONTINUE;
  }

  @Override
  public void processWindowResize(V2 new_size)
  {
    camera.setCanvasSize(new_size);
  }

  @Override
  public EUpdateResult processKeyPress(KeyPress event)
  {
    return EUpdateResult.CONTINUE;
  }

  @Override
  public EUpdateResult processMouseClick(MouseClick event)
  {
    return EUpdateResult.CONTINUE;
  }
}
