/**
 * ***************
 * @author william
 * @date 05-Mar-2012
 ****************
 */
package wjd.gui.view;

import wjd.math.Rect;
import wjd.math.V2;

public class ViewPort
{
  /// CONSTANTS
  private static final float ZOOM_MIN = 0.1f;
  private static final float ZOOM_MAX = 2.0f;
  /// ATTRIBUTES
  private V2 window_size;
  private Rect area;
  private float zoom;

  /// METHODS
  // creation
  public ViewPort(V2 size)
  {
    area = new Rect(V2.ORIGIN, size);
    window_size = size;
    zoom = 1.0f;
  }

  public void reset()
  {
    area.pos(V2.ORIGIN);
    area.size(window_size);
    zoom = 1.0f;
  }

  // query
  public float getZoom()
  {
    return zoom;
  }

  public V2 getPerspective(V2 p)
  {
    return new V2((p.x() - area.x) * zoom, (p.y() - area.y) * zoom);
  }

  public V2 getGlobal(V2 p)
  {
    return new V2(p.x() / zoom + area.x, p.y() / zoom + area.y);
  }

  public boolean containsPoint(V2 position)
  {
    return area.contains(position);
  }

  public boolean containsLine(V2 start, V2 end)
  {
    // discard useless states
    if (Math.min(start.x(), end.x()) > area.x + area.w
      || Math.max(start.x(), end.x()) < area.x)
      return false;
    if (Math.min(start.y(), end.y()) > area.y + area.h
      || Math.max(start.y(), end.y()) < area.y)
      return false;

    // otherwise it's all good!
    return true;
  }

  // modification
  public void setWindowSize(V2 _window_size)
  {
    window_size = _window_size;
    area.size(window_size.clone().scale(1.0f / zoom));
  }

  public void translate(V2 delta)
  {
    area.shift(delta.scale(1 / zoom));
  }

  public void zoom(float delta, V2 mouse_pos)
  {

    V2 mouse_true = getGlobal(mouse_pos);
    V2 mouse_rel = new V2(window_size.x() / mouse_pos.x(),
      window_size.y() / mouse_pos.y());

    // reset zoom counter, don't zoom too much
    zoom += delta * zoom;
    if (zoom > ZOOM_MAX)
      zoom = ZOOM_MAX;
    else if (zoom < ZOOM_MIN)
      zoom = ZOOM_MIN;

    // perform the zoom
    area.size(window_size.clone().scale(1.0f / zoom));


    //area.x = (mouse_pos.x/zoom + area.x) - area.width/(window_size.x/mouse_pos.x);

    area.x = mouse_true.x() - area.w / mouse_rel.x();
    area.y = mouse_true.y() - area.h / mouse_rel.y();

  }
}
