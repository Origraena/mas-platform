/**
 * ***************
 * @author wdyce
 * @date Feb 15, 2012
 ****************
 */
package wjd.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import wjd.gui.view.DrawGL;
import wjd.gui.view.ViewPort;
import wjd.math.FVect;

public class GUIWindow extends LWJGLWindow
{
  /// CONSTANTS
  private static final int SCOLL_MOUSE_DISTANCE = 48;
  private static final int SCROLL_SPEED = 6;
  private static final float ZOOM_SPEED = 0.001f;
  
  /// TODO -- remove this
  private static final FVect HELLO_POS = new FVect(100, 100);
  private static final String HELLO_TEXT = "Hello Swan!";
  
  /// ATTRIBUTES
  private ViewPort view;

  /// METHODS
  // construction
  public GUIWindow(String _name, int _width, int _height)
  {
    // save variables
    super(_name, _width, _height);

    // create view
    view = new ViewPort(new FVect(_width, _height));

    // start up
    reset();
  }

  private void reset()
  {
    // create view
    view.reset();
  }

  // overrides
  @Override
  protected void render()
  {
    // standard stuff
    super.render();

    // draw code goes here
    DrawGL.text(HELLO_TEXT, HELLO_POS);
  }

  @Override
  protected void processKeyboard()
  {
    // get arrow-key input
    FVect key_dir = new FVect(0, 0);
    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
      key_dir.y += 1;
    if (Keyboard.isKeyDown(Keyboard.KEY_UP))
      key_dir.y -= 1;
    if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
      key_dir.x += 1;
    if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
      key_dir.x -= 1;

    // move the view
    view.translate(key_dir.scale(SCROLL_SPEED));
  }

  @Override
  protected void processMouse()
  {
    // mouse position
    FVect mouse_pos = new FVect(Mouse.getX(), getHeight() - Mouse.getY());
    FVect mouse_true = view.getGlobal(mouse_pos);

    // mouse near edges = pan
    /*FVect scroll_dir = new FVect(0, 0);
     if(mouse_pos.x < SCOLL_MOUSE_DISTANCE) 
     scroll_dir.x = -1;
     else if(mouse_pos.x > getWidth()-SCOLL_MOUSE_DISTANCE) 
     scroll_dir.x = 1;
     if(mouse_pos.y < SCOLL_MOUSE_DISTANCE) 
     scroll_dir.y = -1;
     else if(mouse_pos.y > getHeight()-SCOLL_MOUSE_DISTANCE) 
     scroll_dir.y = 1;
     view.translate(scroll_dir.scale(SCROLL_SPEED));*/

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
    view.setWindowSize(new FVect(getWidth(), getHeight()));
  }
}
