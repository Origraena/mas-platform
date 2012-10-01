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

package wjd.gui.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JPanel;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import wjd.math.Rect;
import wjd.math.V2;

/** 
 * 
 * @author wdyce 
 * @since 25 Jan, 2012
 */
public class AWTCanvas extends JPanel implements ICanvas
{
  /* ATTRIBUTES */
  private Queue<Shape> shapes;

  /* METHODS */
  // creation
  /**
   * Create the JPanel.
   */
  public AWTCanvas()
  {
    shapes = new LinkedList<Shape>();
  }

  // setup functions
  @Override
  public ICanvas setColour(Colour colour)
  {
    // TODO
    return this;
  }

  @Override
  public ICanvas setLineWidth(float lineWidth)
  {
    // TODO
    return this;
  }

  @Override
  public ICanvas setFont(Object new_font)
  {
    // TODO
     return this;
  }

  // drawing functions
  /**
   * Clear the screen.
   */
  @Override
  public void clear()
  {
    // empty the list of shapes after drawing, not when clear is called !
  }
  
  /**
   * Draw a circle outline around the specified position, using the given
   * radius.
   *
   * @param centre vector position corresponding to the centre of the circle.
   * @param radius the size of the circle from centre to outskirts.
   */
  @Override
  public void circle(V2 centre, float radius)
  {
    shapes.add(new Ellipse2D.Float(centre.x(), centre.y(), radius*2, radius*2));
  }

  /**
   * Draw a straight black line between the two specified points.
   *
   * @param start vector position corresponding to the start of the line.
   * @param end vector position corresponding to the end of the line.
   */
  @Override
  public void line(V2 start, V2 end)
  {
    shapes.add(new Line2D.Float(start.x(), start.y(), end.x(), end.y()));
  }

  /**
   * Draw the outline of a Rectangle.
   *
   * @param rect the rectangle object whose outline will be drawn.
   */
  @Override
  public void box(Rect rect)
  {
    shapes.add(new Rectangle2D.Float(rect.x(), rect.y(), rect.w(), rect.h()));
  }

  /**
   * Draw a String of characters at the indicated position.
   *
   * @param string the String of characters to be drawn.
   * @param position the position on the screen to draw the String.
   */
  @Override
  public void text(String string, V2 position)
  {
    //g2d.drawString(string, position.x(), position.y());
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    // Get the graphics object
    Graphics2D g2d = (Graphics2D)g;
    
    // Clear the screen
    g2d.fillRect(0, 0, getWidth(), getHeight());
    
    // Draw each shape
    Shape s;
    while((s = shapes.poll()) != null)
      g2d.draw(s);
    
    // empty the list of shapes
    shapes.clear();

  }
}


/*
 * 
 *     /// METHODS

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

 */