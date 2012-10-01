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

/**
 * @author wdyce 
 * @since 1 Aug, 2012
 */
public abstract class Window 
{
  /* ATTRIBUTES */
  private int width, height;
  private final String name;
  
  /* METHODS */
  // creation and destruction
  /**
   * Prepare the Window to be created with the given parameters.
   *
   * @param name the String of characters to be displayed at the top of the
   * Window (NB - the name java is still used to identify the process itself).
   * @param width the width of the Window, in pixels.
   * @param height the height of the Window, in pixels.
   */
  public Window(String name, int width, int height)
  {
    this.name = name;
    this.width = width;
    this.height = height;
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
  
  /**
   * Return the name of the Window.
   *
   * @return the name of the Window
   */
  public String getName()
  {
    return name;
  }
  
  /* ABSTRACT */
  
  /**
   * Launch the application and run until some event interrupts its execution.
   */
  public abstract void run();
}
