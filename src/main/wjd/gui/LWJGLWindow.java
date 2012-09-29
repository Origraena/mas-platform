/**
 * ***************
 * @author jediTofu
 * @see <a href="http://lwjgl.org/">LWJGL Home Page</a>
 ****************
 */
package wjd.gui;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import wjd.gui.view.DrawGL;

public abstract class LWJGLWindow
{
  /* ATTRIBUTES */
  // window
  private int width, height;
  private final String name;
  // timing
  private long t_previous;

  /* METHODS */
  // creation and destruction
  public LWJGLWindow(String _name, int _width, int _height)
  {
    name = _name;
    width = _width;
    height = _height;
  }

  public void create() throws LWJGLException
  {
    //Display
    Display.setDisplayMode(new DisplayMode(width, height));
    Display.setFullscreen(false);
    Display.setTitle(name);
    Display.create();
    Display.setResizable(true);

    //Keyboard
    Keyboard.create();
    Keyboard.enableRepeatEvents(false);

    //Mouse
    Mouse.setGrabbed(false);
    Mouse.create();

    //OpenGL
    DrawGL.init();
    resizeGL();
  }

  public void destroy()
  {
    //Methods already check if created before destroying
    Mouse.destroy();
    Keyboard.destroy();
    Display.destroy();
  }

  // launch and update
  public void run()
  {
    while (!Display.isCloseRequested()
      && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      if (Display.isVisible())
      {
        // don't update if display is not in focus
        processKeyboard();
        processMouse();
        update(timeDelta());
        render();
      }
      else
      {
        // redraw screen if out of date
        if (Display.isDirty())
          render();
        try
        {
          Thread.sleep(100);
        }
        catch (InterruptedException ex)
        {
          // Thread interrupted by another
        }
      }
      Display.update();
      Display.sync(60);   // 60 frames per second
    }
  }

  // query
  public int getHeight()
  {
    return height;
  }
  public int getWidth()
  {
    return width;
  }


  /* SUBROUTINES */
  // update and input
  protected void update(long t_delta)
  {
    if (Display.wasResized())
    {
      width = Display.getWidth();
      height = Display.getHeight();
      resizeGL();
    }
  }

  protected abstract void processKeyboard();

  protected abstract void processMouse();

  // graphics
  protected void render()
  {
    glClear(GL_COLOR_BUFFER_BIT);
    glLoadIdentity();

    /*
     * RENDER CODE HERE
     */
  }

  protected void resizeGL()
  {
    //Here we are using a 2D Scene
    glViewport(0, 0, width, height);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, width, height, 0, -1, 1);
    glPushMatrix();

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glPushMatrix();
  }

  public int timeDelta() 
  {
    long t_now = timeNow();
    int t_delta = (int)(t_now - t_previous);
    t_previous = t_now;
    return t_delta;
	}
  
  public long timeNow() 
  {
    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
}
