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

import wjd.gui.control.IDynamic;
import wjd.gui.control.IInteractive;
import wjd.gui.view.IVisible;
import wjd.math.V2;

/**
 * @author wdyce 
 * @since 1 Aug, 2012
 */
public interface IWindow 
{
  /* NESTING */
  
  public interface IScene extends IDynamic, IVisible, IInteractive { }
  
  /* CONSTANTS */
  
  public static final int MAX_FPS = 60;
  
  /* LIFE CYCLE */
  
  /**
   * Create the IWindow and OpenGL canvas based on the size and other parameters
   * that were given to the constructor.
   *
   * @param name the String of characters to be displayed at the top of the
   * IWindow (NB - the name java is still used to identify the process itself).
   * @param width the width of the IWindow, in pixels.
   * @param height the height of the IWindow, in pixels.
   * @throws an Exception if there's or problem: this is optional.
   */
  public void create(String name, V2 size, IScene scene) throws Exception;
  
  /**
   * Launch the application and run until some event interrupts its execution.
   */
  public abstract void run();
  
  /**
   * Clean up anything we might have allocated.
   */
  public void destroy();
  
}
