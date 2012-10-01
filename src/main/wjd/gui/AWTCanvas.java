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

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/** 
 * 
 * @author wdyce 
 * @since 25 Jan, 2012
 */
public class AWTCanvas extends JPanel
{
    /// ATTRIBUTES

    /// METHODS

    // creation
    public AWTCanvas()
    {
    }

    // update

    public boolean update()
    {
      // check for interruptions
      //if(Input.getInstance().isKeyPressed(Input.KeyCode.ESC))
          //return false;
      // redraw the entire screen
      repaint();
      // no interruptions occured
      return true;
    }

    @Override
    public void paintComponent(Graphics g)
    {
      // Get the graphics object
      Graphics2D g2d = (Graphics2D)g;
      // Redraw the background
      g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
