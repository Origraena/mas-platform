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

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import wjd.gui.window.AWTWindow;
import wjd.gui.model.AgentScene;
import wjd.gui.window.IWindow;
import wjd.gui.window.LWJGLWindow;
import wjd.math.V2;

/**
 * Launches the Graphical User Interface for our Multi-agent simulation.
 *
 * @author wdyce
 * @since Sep 27, 2012
 */
abstract class Main
{
  /// CLASS NAMESPACE CONSTANTS
  public static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  public static final String WIN_NAME = "Virtual Societies";
  public static final V2 WIN_SIZE = new V2(960, 640);

  /// CLASS INITIALISATION
  static
  {
    try
    {
      LOGGER.addHandler(new FileHandler("errors.log", true));
    }
    catch (IOException ex)
    {
      LOGGER.log(Level.WARNING, ex.toString(), ex);
    }
  }

  /// PROGRAM ENTRANCE POINT
  public static void main(String[] args)
  {
    /* NB - LWJGL uses native libraries, so this program will crash at run-time
     * unless you indicate to the JVM where to find them! As such the program
     * must be run with the following argument: 
     * -Djava.library.path=/a/path/to/lwjgl-2.8.4/native/your_operating_system
     */
    IWindow window = null;
    try
    {
      // create the window
      window = new AWTWindow(); //new LWJGLWindow();
      window.create(WIN_NAME, WIN_SIZE, new AgentScene());
      window.run();
    }
    catch (Exception ex)
    {
      LOGGER.log(Level.SEVERE, ex.toString(), ex);
    }
    finally
    {
      if (window != null)
        window.destroy(); // clean-up is required because of native code
    }
  }
}
