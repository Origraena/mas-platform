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

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/** 
 * Application using Window AWT: only use this version if your computer does not 
 * support hardware accelerated graphics!
 * 
 * @author wdyce 
 * @since 25 Jan, 2012
 */
public class AWTWindow extends JFrame implements Window
{
  /* CONSTANTS */

  /* ATTRIBUTES */ 
  // JFrame
  private int width, height;
  private final String name;
  // JPanel
  private AWTCanvas canvas;

  /* METHODS */

  // creation
  private AWTWindow(String name, int width, int height)
  {
    this.name = name;
    this.width = width;
    this.height = height;
    // Set up AWT window
    setTitle(name);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(width, height);
    setResizable(false);
    setLocationRelativeTo(null);    // move to center of screen
    // Content
    canvas = new AWTCanvas();
    setContentPane(canvas);
    // This should always be last
    setVisible(true);
  }

  // launch and update
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
        // Do update
        running = canvas.update();

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

  @Override
  public Window <error>(String name, int width, int height)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
