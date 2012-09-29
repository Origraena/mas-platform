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
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import wjd.math.Rect;
import wjd.math.V2;

/**
 * A collection of static drawing factions to render primitives (circles, boxes
 * and so-on) using OpenGL.
 *
 * @author wdyce
 * @date 16-Feb-2012
 */
public abstract class DrawGL
{
  /// CLASS NAMESPACE CONSTANTS
  private static final int CIRCLE_BASE_SEGMENTS = 6;
  private static TrueTypeFont font = null;

  /// CLASS NAMESPACE FUNCTIONS
  // initialisation
  /**
   * Set up the OpenGL state machine for drawing, including setting clear colour
   * and depth, and enabling/disabling various options (namely 3D).
   */
  public static void init()
  {
    // background colour and depth
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    glClearDepth(1);

    // 2d initialisation
    glDisable(GL_DEPTH_TEST);
    glDisable(GL_LIGHTING);
    glEnable(GL_TEXTURE_2D);

    // we need blending (alpha) for drawing strings
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    // load a default java font
    font = new TrueTypeFont(new Font("Arial", Font.PLAIN, 12), false);
  }

  // drawing functions
  /**
   * Draw a black circle outline around the specified position, using the given
   * radius.
   *
   * @param centre vector position corresponding to the centre of the circle.
   * @param radius the size of the circle from centre to outskirts.
   */
  public static void circle(V2 centre, float radius)
  {
    int deg_step = (int) (360 / (CIRCLE_BASE_SEGMENTS * radius));
    glBegin(GL_TRIANGLE_FAN);
    glVertex2f(centre.x(), centre.y());
    for (int deg = 0; deg < 360 + deg_step; deg += deg_step)
    {
      double rad = deg * Math.PI / 180;
      glVertex2f((float) (centre.x() + Math.cos(rad) * radius),
        (float) (centre.y() + Math.sin(rad) * radius));
    }
    glEnd();
  }

  /**
   * Draw a circle at around the specified position, using the given radius and
   * Colour.
   *
   * @param centre vector position corresponding to the centre of the circle.
   * @param radius the size of the circle from centre to outskirts.
   * @param colour the Colour to be used to draw the circle.
   */
  public static void circle(V2 centre, float radius, Colour colour)
  {
    glColor3f(colour.r, colour.b, colour.g);
    circle(centre, radius);
  }

  /**
   * Draw a straight black line between the two specified points.
   *
   * @param start vector position corresponding to the start of the line.
   * @param end vector position corresponding to the end of the line.
   */
  public static void line(V2 start, V2 end)
  {
    glBegin(GL_LINES);
    glVertex2d(start.x(), start.y());
    glVertex2d(end.x(), end.y());
    glEnd();
  }

  /**
   * Draw a straight line between the two specified points using the specified
   * Colour and the specified line width.
   *
   * @param start vector position corresponding to the start of the line.
   * @param end vector position corresponding to the end of the line.
   * @param colour Colour to be used to draw the line.
   * @param width the width of the line in pixels.
   */
  public static void line(V2 start, V2 end, Colour colour, float width)
  {
    glLineWidth(width);
    glColor3f(colour.r, colour.b, colour.g);
    line(start, end);
  }

  /**
   * Draw the outline of a Rectangle in black.
   *
   * @param rect the rectangle object whose outline will be drawn.
   */
  public static void box(Rect rect)
  {
    glBegin(GL_QUADS);
    glVertex2f(rect.x(), rect.y());
    glVertex2f(rect.x() + rect.w(), rect.y());
    glVertex2f(rect.x(), rect.y() + rect.h());
    glVertex2f(rect.x() + rect.w(), rect.y() + rect.h());
    glEnd();
  }

  /**
   * Draw the outline of a Rectangle using the specified Colour.
   *
   * @param rect the rectangle object whose outline will be drawn.
   * @param colour the Colour to be used to draw the rectangle.
   */
  public static void box(Rect rect, Colour colour)
  {
    glColor3f(colour.r, colour.b, colour.g);
    box(rect);
  }

  /**
   * Draw a String of characters at the indicated position in black.
   *
   * @param string the String of characters to be drawn.
   * @param position the position on the screen to draw the String.
   */
  public static void text(String string, V2 position)
  {
    glEnable(GL_BLEND);
    font.drawString(position.x(), position.y(), string, Color.black);
    glDisable(GL_BLEND);
  }

  /**
   * Draw a String of characters at the indicated position using the specified
   * Colour.
   *
   * @param string the String of characters to be drawn.
   * @param position the position on the screen to draw the String.
   * @param colour the Colour to be used to draw the String.
   */
  public static void text(String string, V2 position, Colour colour)
  {
    glEnable(GL_BLEND);
    font.drawString(position.x(), position.y(), string,
      new Color(colour.r, colour.g, colour.b));
    glDisable(GL_BLEND);
  }
}
