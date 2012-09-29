package wjd.gui;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import wjd.gui.view.DrawGL;

/**
 * Basic Light-weight Java Game Library (LWJGL) holder class which creates a
 * Window and OpenGL canvas we can use to draw on.
 *
 * @author wdyce
 * @date 16-Feb-2012
 * @see <a href="http://lwjgl.org/">LWJGL Home Page</a>
 */
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
  /**
   * Prepare the Window to be created with the given parameters: it will not
   * actually be created until <a href="LWJGLWindow#create()">create</a> is
   * called.
   *
   * @see LWJGLWindow#create()
   * @param name the String of characters to be displayed at the top of the
   * Window (NB - the name java is still used to identify the process itself).
   * @param width the width of the Window, in pixels.
   * @param height the height of the Window, in pixels.
   */
  public LWJGLWindow(String name, int width, int height)
  {
    this.name = name;
    this.width = width;
    this.height = height;
  }

  /**
   * Create the Window and OpenGL canvas based on the size and other parameters
   * that were given to the constructor.
   *
   * @throws LWJGLException if native libraries are not found, graphics card or
   * drivers do not support hardware rendering...
   */
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

  /**
   * Clean up anything we might have allocated.
   */
  public void destroy()
  {
    // these methods already check if created before destroying
    Mouse.destroy();
    Keyboard.destroy();
    Display.destroy();
  }

  // launch and update
  /**
   * Launch the application and run until some event interrupts its execution.
   */
  public void run()
  {
    while (!Display.isCloseRequested()
      && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      // don't update if display is not in focus
      if (Display.isVisible())
      {
        // deal with input
        processKeyboard();
        processMouse();
        processWindow();
        // update and redraw
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
  /**
   * Return the width of the Window.
   *
   * @return the width of the Window in pixels.
   */
  public int getWidth()
  {
    return width;
  }

  /**
   * Return the height of the Window.
   *
   * @return the height of the Window in pixels.
   */
  public int getHeight()
  {
    return height;
  }


  /* SUBROUTINES */
  // update and input
  /**
   * Update the application based on the amount of time elapsed since the last
   * update, in milliseconds.
   *
   * @param t_delta number of milliseconds since the last update.
   */
  protected abstract void update(long t_delta);

  /**
   * Treat any Keyboard events that might has occurred.
   */
  protected abstract void processKeyboard();

  /**
   * Treat any Window events that might has occurred.
   */
  protected abstract void processMouse();

  // graphics
  /**
   * Draw whatever the application wants to draw onto the OpenGL canvas: but
   * default just clear the canvas and reset the transform matrix.
   */
  protected void render()
  {
    glClear(GL_COLOR_BUFFER_BIT);
    glLoadIdentity();

    /*
     * RENDER CODE HERE
     */
  }

  /**
   * Resize the OpenGL canvas.
   */
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

  /**
   * Treat any Window events that might has occurred, for instance minimisation
   * or resizing of the Window.
   */
  private void processWindow()
  {
    // check if window was resized
    if (Display.wasResized())
    {
      width = Display.getWidth();
      height = Display.getHeight();
      resizeGL();
    }
  }

  /**
   * Return the amount of time since the method was last called.
   *
   * @return the current time in milliseconds since this method was last called,
   * or 0 the first time the method is called.
   */
  private int timeDelta()
  {
    long t_now = timeNow();
    int t_delta = (int) (t_now - t_previous);
    t_previous = t_now;
    return t_delta;
  }

  /**
   * Return the current time.
   *
   * @return the current system time in milliseconds.
   */
  private long timeNow()
  {
    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
  }
}
