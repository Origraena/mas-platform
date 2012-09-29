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
import ori.ogapi.util.Iterator;
import wjd.gui.view.DrawGL;
import wjd.gui.view.Camera;
import wjd.math.Rect;
import wjd.math.V2;

/**
 * Window manager of the Graphical User Interface of our Multi-agent simulation.
 *
 * @author wdyce
 * @date Sep 27, 2012
 */
public class GUIWindow extends LWJGLWindow
{
  /// CONSTANTS
  private static final int SCOLL_MOUSE_DISTANCE = 48;
  private static final int SCROLL_SPEED = 6;
  private static final float ZOOM_SPEED = 0.001f;
  /// TODO -- remove this
  private static final V2 HELLO_POS = new V2(100, 100);
  private static final String HELLO_TEXT = "Hello Agents!";
  /// ATTRIBUTES
  private Camera view;
  private World world;

  /// METHODS
  // construction
  public GUIWindow(String name, int width, int height)
  {
    // save variables
    super(name, width, height);

    // create view
    view = new Camera(new V2(width, height), null);

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
  protected void update(long t_delta)
  {
  }

  @Override
  protected void render()
  {
    // standard stuff
    super.render();

    // draw agents
    Iterator itr = world.iterator();
    while (itr.hasNext())
    {
      Point pos = ((Agent) itr.next()).body().center();
      DrawGL.circle(view.getPerspective(new V2(pos.x, pos.y)), 8 * view.
        getZoom());
    }

    // draw hello text
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
    view.pan(key_dir.scale(SCROLL_SPEED));
  }

  @Override
  protected void processMouse()
  {
    // mouse position
    V2 mouse_pos = new V2(Mouse.getX(), getHeight() - Mouse.getY());

    // mouse near edges = pan
    V2 scroll_dir = new V2();
    if (mouse_pos.x() < SCOLL_MOUSE_DISTANCE)
      scroll_dir.x(-1);
    else if (mouse_pos.x() > getWidth() - SCOLL_MOUSE_DISTANCE)
      scroll_dir.x(1);
    if (mouse_pos.y() < SCOLL_MOUSE_DISTANCE)
      scroll_dir.y(-1);
    else if (mouse_pos.y() > getHeight() - SCOLL_MOUSE_DISTANCE)
      scroll_dir.y(1);
    view.pan(scroll_dir.scale(SCROLL_SPEED));

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

    // resize camera viewport too
    view.setCanvasSize(new V2(getWidth(), getHeight()));
  }
}
