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

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import wjd.math.V2;

/**
 * Basic Light-weight Java Game Library (LWJGL) holder class which creates a
 * IWindow and OpenGL canvas we can use to draw on.
 *
 * @author wdyce
 * @since 16-Feb-2012
 * @see <a href="http://lwjgl.org/">LWJGL Home Page</a>
 */
public abstract class LWJGLWindow implements IWindow
{
  /* ATTRIBUTES */
  // window
  private V2 size;
  private String name;
  // timing
  private long t_previous = -1; // uninitialised

  /* METHODS */
  
  // life-cycle

  /**
   * Create a LWJGL Display of the given size, with a corresponding OpenGL 
   * canvas.
   *
   * @param name the String of characters to be displayed at the top of the
   * IWindow (NB - the name java is still used to identify the process itself).
   * @param width the width of the IWindow, in pixels.
   * @param height the height of the IWindow, in pixels.
   * @throws LWJGLException if native libraries are not found, graphics card or
   * drivers do not support hardware rendering...
   */
  @Override
  public void create(String name, V2 size) throws LWJGLException
  {
    // Save argument
    this.name = name;
    this.size = size.floor();
    // Display
    Display.setDisplayMode(new DisplayMode((int)size.x(), (int)size.y()));
    Display.setFullscreen(false);
    Display.setTitle(name);
    Display.create();
    Display.setResizable(true);
    // Keyboard
    Keyboard.create();
    Keyboard.enableRepeatEvents(false);
    // Mouse
    Mouse.setGrabbed(false);
    Mouse.create();
    // OpenGL
    resizeGL();
  }

  // launch and update
  /**
   * Launch the application and run until some event interrupts its execution.
   */
  @Override
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
  
  /**
   * Clean up anything we might have allocated.
   */
  @Override
  public void destroy()
  {
    // these methods already check if created before destroying
    Mouse.destroy();
    Keyboard.destroy();
    Display.destroy();
  }

  /* SUBROUTINES */
  // update and input
  /**
   * Update the application based on the amount of time elapsed since the last
   * update, in milliseconds.
   *
   * @param t_delta number of milliseconds since the last update.
   */
  protected abstract void update(int t_delta);

  /**
   * Treat any Keyboard events that might has occurred.
   */
  protected abstract void processKeyboard();

  /**
   * Treat any IWindow events that might has occurred.
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
    glViewport(0, 0, (int)size.x(), (int)size.y());

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, size.x(), size.y(), 0, -1, 1);
    glPushMatrix();

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glPushMatrix();
  }

  /**
   * Treat any IWindow events that might has occurred, for instance minimisation
   * or resizing of the IWindow.
   */
  private void processWindow()
  {
    // check if window was resized
    if (Display.wasResized())
    {
      size.xy(Display.getWidth(), Display.getHeight());
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
    int t_delta = (t_previous < 0) ? 0 : (int) (t_now - t_previous);
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
