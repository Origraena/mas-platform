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

package wjd.gui.window;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import org.lwjgl.LWJGLException;
import wjd.gui.control.AWTInput;
import wjd.gui.control.IInput;
import wjd.gui.view.AWTCanvas;
import wjd.gui.view.Camera;
import wjd.math.V2;

/** 
 * Application using IWindow AWT: only use this version if your computer does not 
 * support hardware accelerated graphics!
 * 
 * @author wdyce 
 * @since 25 Jan, 2012
 */
public class AWTWindow extends JFrame implements IWindow
{
  /* CONSTANTS */

  /* ATTRIBUTES */
  // window
  private V2 size;
  // model
  private IScene scene;
  // view
  private AWTCanvas canvas;
  private Camera camera;
  // control
  private IInput input;

  /* METHODS */

  // life-cycle
  
  /**
   * Create the AWT JFrame IWindow of the given size, with a corresponding JPanel 
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
  public void create(String name, V2 size, IScene scene) throws LWJGLException
  {
    // window
    this.size = size;
    // Set up AWT window
    setTitle(name);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize((int)size.x(), (int)size.y());
    setResizable(false);
    setLocationRelativeTo(null);    // move to center of screen
    // model 
    this.scene = scene;
    // view
    canvas = new AWTCanvas();
    camera = new Camera(size, null); // null => no boundary
    setContentPane(canvas);
    // control
    input = AWTInput.getInstance();
    // This should always be last
    setVisible(true);
  }

  /**
   * Launch the application and run until some event interrupts its execution.
   */
  @Override
  public void run()
  {
    // Run until told to stop
    boolean running = true;
    while(running)
    {
      // deal with input
      camera.processInput(input, size);
      scene.processInput(input, size);
      
      // queue rendering
      scene.render(canvas, camera);
      repaint();

      // Catch exceptions
      try
      {
        // Leave some time for other threads
        Thread.sleep(1000/MAX_FPS);
      } 
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    // End of the loop : close the window
    WindowEvent e = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(e);
  }
  
  /**
   * Clean up anything we might have allocated.
   */
  @Override
  public void destroy()
  {
  }
}
