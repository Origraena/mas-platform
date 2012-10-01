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

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import wjd.math.V2;

/** 
* @author wdyce
* @since 01-Oct-2012
*/
public class LWJGLInput implements IInput
{
  /* ATTRIBUTES */
  private V2 key_direction = new V2(),
             mouse_position = new V2();
  
  /* SINGLETON */
  
  private static LWJGLInput instance = null;
  
  public static LWJGLInput getInstance() throws LWJGLException
  {
    if(instance == null)
      instance = new LWJGLInput();
    return instance;
  }
  
  private LWJGLInput() throws LWJGLException
  {
    // LWJGL - Keyboard
    Keyboard.create();
    Keyboard.enableRepeatEvents(false);
    // LWJGL - Mouse
    Mouse.setGrabbed(false);
    Mouse.create();
  }
  
  /* IMPLEMENTATIONS -- IINPUT */
  
  @Override
  public int getMouseWheelDelta()
  {
    return Mouse.getDWheel();
  }

  @Override
  public V2 getMousePosition(V2 window_size)
  {
    mouse_position.xy(Mouse.getX(), window_size.y() - Mouse.getY());
    return mouse_position;
  }

  @Override
  public V2 getKeyDirection()
  {
    // Update keyboard direction vector
    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
      key_direction.yadd(1);
    if (Keyboard.isKeyDown(Keyboard.KEY_UP))
      key_direction.yadd(-1);
    if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
      key_direction.xadd(1);
    if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
      key_direction.xadd(-1);
    // Return the vector
    return key_direction;
  }

  @Override
  public boolean isKeyHeld(EKeyCode code)
  {
    switch(code)
    {
      case L_SHIFT: return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
      case L_CTRL: return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
      case L_ALT: return Keyboard.isKeyDown(Keyboard.KEY_LMENU);
      case SPACE: return Keyboard.isKeyDown(Keyboard.KEY_SPACE);
      case R_ALT: return Keyboard.isKeyDown(Keyboard.KEY_RMENU);
      case R_CTRL: return Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
      case R_SHIFT: return Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
      case ENTER: return Keyboard.isKeyDown(Keyboard.KEY_RETURN);
      case ESC: return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
      case BACKSPACE: return Keyboard.isKeyDown(Keyboard.KEY_BACK);
      default: return false;
    }
  }

  @Override
  public boolean isMouseClicking(EMouseButton button)
  {
    return Mouse.isButtonDown(button.ordinal());
  }

}
