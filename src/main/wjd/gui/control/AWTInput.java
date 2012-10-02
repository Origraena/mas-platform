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
package wjd.gui.control;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import wjd.math.V2;

/**
 * Adaptor interface to deal with input coming from either LWJGL or AWT.
 *
 * @author wdyce
 * @since 25 Jan, 2012
 */
public class AWTInput implements IInput, KeyListener, MouseListener,
  MouseMotionListener
{
  
  /* NESTING */
  
  private static class Mouse
  {
    // mouse buttons are set out: left, middle, right
    public boolean clicking[] =
    {
      false, false, false
    };
    public V2 position = new V2(0, 0);
  }

  private static class Keyboard
  {
    public boolean pressing[] =
    {
      false, false, false, false, false, false, false, false, false, false
    };
    // overall direction of arrow-key movement
    public V2 direction = new V2(0, 0);
  }
  
  
  /* SINGLETON */
  
  private static AWTInput instance;

  public static AWTInput getInstance()
  {
    if (instance == null)
      instance = new AWTInput();
    return instance;
  }
  /* ATTRIBUTES */
  Mouse mouse;
  Keyboard keyboard;

  
  /* METHODS */
  
  // creation
  private AWTInput()
  {
    mouse = new Mouse();
    keyboard = new Keyboard();
    // Java has a problem with Linux key repeats: this hack patches the issue
    KeyRepeatFix.install();
  }

  
  /* IMPLEMENTATIONS - IINPUT */
  
  @Override
  public int getMouseWheelDelta()
  {
    // TODO !
    return 0;
  }
  
  @Override
  public V2 getKeyDirection()
  {
    return keyboard.direction;
  }

  @Override
  public boolean isKeyHeld(EKeyCode code)
  {
    return keyboard.pressing[code.ordinal()];
  }

  @Override
  public boolean isMouseClicking(EMouseButton button)
  {
    return mouse.clicking[button.ordinal()];
  }

  @Override
  public V2 getMousePosition(V2 window_size)
  {
    return mouse.position;
  }
  
  /* IMPLEMENTATIONS - KEYLISTENER */

  @Override
  public void keyTyped(KeyEvent e)
  { /* unused */ }

  @Override
  public void keyPressed(KeyEvent e)
  {
    setKeyState(e, true);
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    setKeyState(e, false);
  }

  
  /* IMPLEMENTATIONS - MOUSELISTENER */
  
  @Override
  public void mouseClicked(MouseEvent e)
  { /* unused */ }

  @Override
  public void mouseEntered(MouseEvent e)
  { /* unused */ }

  @Override
  public void mouseExited(MouseEvent e)
  { /* unused */ }

  @Override
  public void mousePressed(MouseEvent e)
  {
    mouse.clicking[e.getButton() - 1] = true;
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    mouse.clicking[e.getButton() - 1] = false;
  }

  
  /* IMPLEMENTATIONS - MOUSEMOTIONLISTENER */

  @Override
  public void mouseDragged(MouseEvent e)
  { /* unused */ }

  @Override
  public void mouseMoved(MouseEvent e)
  {
    Point p = e.getPoint();
    mouse.position.xy(p.x, p.y);
  }

  
  /* SUBROUTINES */
  
  private void setKeyState(KeyEvent e, boolean new_state)
  {
    switch (e.getKeyCode())
    {
      // arrow keys
      case KeyEvent.VK_UP:
        keyboard.direction.y((new_state)
          ? Math.max(keyboard.direction.y() - 1, -1)
          : Math.min(keyboard.direction.y() + 1, 0));
        break;
      case KeyEvent.VK_DOWN:
        keyboard.direction.y((new_state)
          ? Math.min(keyboard.direction.y() + 1, 1)
          : Math.max(keyboard.direction.y() - 1, 0));
        break;
      case KeyEvent.VK_LEFT:
        keyboard.direction.x((new_state)
          ? Math.max(keyboard.direction.x() - 1, -1)
          : Math.min(keyboard.direction.x() + 1, 0));
        break;
      case KeyEvent.VK_RIGHT:
        keyboard.direction.x((new_state)
          ? Math.min(keyboard.direction.x() + 1, 1)
          : Math.max(keyboard.direction.x() - 1, 0));
        break;

      // keys with location
      case KeyEvent.VK_SHIFT:
        if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT)
          keyboard.pressing[EKeyCode.L_SHIFT.ordinal()] = new_state;
        else
          keyboard.pressing[EKeyCode.R_SHIFT.ordinal()] = new_state;
        break;
      case KeyEvent.VK_CONTROL:
        if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT)
          keyboard.pressing[EKeyCode.L_CTRL.ordinal()] = new_state;
        else
          keyboard.pressing[EKeyCode.R_CTRL.ordinal()] = new_state;
        break;
      case KeyEvent.VK_ALT:
        if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT)
          keyboard.pressing[EKeyCode.L_ALT.ordinal()] = new_state;
        else
          keyboard.pressing[EKeyCode.R_ALT.ordinal()] = new_state;
        break;

      // absolute keys
      case KeyEvent.VK_SPACE:
        keyboard.pressing[EKeyCode.SPACE.ordinal()] = new_state;
        break;
      case KeyEvent.VK_ENTER:
        keyboard.pressing[EKeyCode.ENTER.ordinal()] = new_state;
        break;
      case KeyEvent.VK_ESCAPE:
        keyboard.pressing[EKeyCode.ESC.ordinal()] = new_state;
        break;

      default:
        break;
    }
  }
}
