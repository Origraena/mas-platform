/*****************
 * @author william
 * @date 16-Feb-2012
 *****************/


package wjd.gui.view;

import java.awt.Font;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import wjd.math.Rect;
import wjd.math.V2;

public abstract class DrawGL 
{
  /// CLASS NAMESPACE CONSTANTS
  private static final int CIRCLE_SEGMENTS = 20;
  private static TrueTypeFont font = null;

  public static final float[] BLACK = { 0.0f, 0.0f, 0.0f };
  public static final float[] YELLOW = { 1.0f, 1.0f, 0.0f };


  /// CLASS NAMESPACE FUNCTIONS

  // initialisation

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

  public static void circle(V2 center, float radius)
  {
    glBegin(GL_TRIANGLE_FAN);
      glVertex2f(center.x(), center.y());
      for(int deg = 0; deg <= 360; deg += 360/CIRCLE_SEGMENTS)
      {
          double rad = deg * Math.PI/180;
          glVertex2f((float)(center.x() + Math.cos(rad)*radius),
                     (float)(center.y() + Math.sin(rad)*radius));
      }
    glEnd();
  }

  public static void circle(V2 center, float radius, float[] colour)
  {
    glColor3f(colour[0], colour[1], colour[2]);
    circle(center, radius);
  }

  public static void line(V2 start, V2 end)
  {
    line(start, end, BLACK, 1.0f);
  }

  public static void line(V2 start, V2 end, float[] colour, float width)
  {
    glLineWidth(width);
    glColor3f(colour[0], colour[1], colour[2]);
    glBegin(GL_LINES);
        glVertex2d(start.x(), start.y());
        glVertex2d(end.x(), end.y());
    glEnd();
  }

  public static void box(Rect rect)
  {
    box(rect, BLACK);
  }

  public static void box(Rect rect, float[] colour)
  {
    glColor3f(colour[0], colour[1], colour[2]);
    glBegin(GL_QUADS);
      glVertex2f(rect.x, rect.y);
      glVertex2f(rect.x+rect.w, rect.y);
      glVertex2f(rect.x, rect.y+rect.h);
      glVertex2f(rect.x+rect.w, rect.y+rect.h);
    glEnd();
  }

  public static void text(String string, V2 position)
  {
    glEnable(GL_BLEND);
    font.drawString(position.x(), position.y(), string, Color.black);
    glDisable(GL_BLEND);    
  }

  public static void text(String string, V2 position, float[] colour)
  {
    glEnable(GL_BLEND);
    font.drawString(position.x(), position.y(), string, 
            new Color(colour[0], colour[1], colour[2]));
    glDisable(GL_BLEND);
  }
}
