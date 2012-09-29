package wjd.gui.view;

import wjd.math.Rect;
import wjd.math.V2;

/** A Camera that can be panned with the keyboard or mouse, and zoomed towards a 
 * specific target (say, the mouse) so that the target is kept in the same place
 * relative to the view, as in Google Maps.
 * @author wdyce
 * @date 05-Mar-2012
 */
public class Camera
{
  /* CONSTANTS */
  private static final float ZOOM_MIN = 0.1f;
  private static final float ZOOM_MAX = 2.0f;
  
  
  /* ATTRIBUTES */
  
  private V2 canvas_size;
  private Rect view, boundary;
  private float zoom;

  
  /* METHODS */
  
  // creation
  /** Create a camera by specifying the size of the canvas its perspective will
   * be drawn to as well as the boundary rectangle  the view should remain 
   * within.
   * @param canvas_size the vector size in pixels of the area of the screen that 
   * the camera's view will be projected on to.
   * @param boundary the Rectangle that the view must remain within, or null for
   * no boundary.
   */
  public Camera(V2 canvas_size, Rect boundary)
  {
    this.canvas_size = canvas_size;
    this.boundary = boundary;
    view = new Rect(V2.ORIGIN, canvas_size);
    zoom = 1.0f;
  }

  /** Reset the position and zoom of the Camera.
   */
  public void reset()
  {
    view.reset(V2.ORIGIN, canvas_size);
    zoom = 1.0f;
  }

  // query
  /** Return the amount of zoom, the real factor by which all visible objects'
   * sizes we be multiplied.
   * @return the amount of zoom between <a href="ViewPort#ZOOM_MIN">ZOOM_MIN</a> 
   * and <a href="ViewPort#ZOOM_MAX">ZOOM_MAX</a>.
   */
  public float getZoom()
  {
    return zoom;
  }

  /** Convert a position relative to the world origin (for instance, an agent) 
   * into a position relative to the view.
   * @param position the vector position to convert.
   * @return a new vector position corresponding to the position of the
   * specified point relative to the view.
   */
  public V2 getPerspective(V2 position)
  {
    return new V2((position.x() - view.x())*zoom, (position.y() - view.y())*zoom);
  }

  /** Convert a position relative to the view (for instance, the position of the
   * mouse cursor) into a position relative to the world origin.
   * @param position the vector position to convert.
   * @return a new vector position corresponding to the absolute position of the
   * specified point.
   */
  public V2 getGlobal(V2 position)
  {
    return new V2(position.x()/zoom + view.x(), position.y()/zoom + view.y());
  }

  /** Return true if the specified position is in view, false if not.
   * @param position the vector point to check, relative to the world origin not 
   * the Window.
   * @return true if the position is inside the view Rectangle.
   */
  public boolean containsPoint(V2 position)
  {
    return view.contains(position);
  }

  // modification
  /** Change the size of the canvas this view is to manage: this could 
   * theoretically be subsection of the application Window, though there isn't
   * as yet support for this (currently one canvas takes up the whole Window).
   * @param canvas_size the new size of the canvas.
   */
  public void setCanvasSize(V2 canvas_size)
  {
    this.canvas_size = canvas_size;
    view.size(canvas_size.clone().scale(1.0f / zoom));
  }

  /** Pan the view at a fixed height (zoom) level: the pan speed depends on the
   * level of zoom (the closer we are the faster we move).
   * @param translation a vector direction to move the view in.
   */
  public void pan(V2 translation)
  {
    // move the view
    view.shift(translation.scale(1 / zoom));
    
    // don't stray out of bounds
    if(boundary != null)
      keepInsideBounds();
  }

  /** Zoom towards or away from the specified target.
   * @param delta amount to zoom: negative moves us away from the target, 
   * positive towards it, and speed depends on the zoom we already have.
   * @param target vector position on the screen to move towards or away from.
   */
  public void zoom(float delta, V2 target)
  {
    V2 target_true = getGlobal(target);
    V2 target_relative = new V2(canvas_size.x() / target.x(),
      canvas_size.y() / target.y());

    // reset zoom counter, don't zoom too much
    zoom += delta * zoom;
    if (zoom > ZOOM_MAX)
      zoom = ZOOM_MAX;
    else if (zoom < ZOOM_MIN)
      zoom = ZOOM_MIN;

    // perform the zoom
    view.size(canvas_size.clone().scale(1.0f / zoom));
    view.x(target_true.x() - view.w() / target_relative.x());
    view.y(target_true.y() - view.h() / target_relative.y());
    
    // don't stray out of bounds
    if(boundary != null)
      keepInsideBounds();
  }
  
  /* SUBROUTINES */
  private void keepInsideBounds()
  {
    V2 overlap = view.overlap(boundary);
    
    // pan view to keep within borders
    
    // pan view to keep within borders -- left/right
    if(overlap.x() < 0)
    {
      // left
      if(view.x() < boundary.x())
        view.x(boundary.x());
      // right
      else if(view.endx() > boundary.endx())
        view.x(boundary.endx()-view.w());
    }
    else if(overlap.x() > 0)
      view.x(boundary.x() - overlap.x()*0.5f);
    
    // pan view to keep within borders -- top/bottom
    if(overlap.y() < 0)
    {
      // top
      if(view.y() < boundary.y())
        view.y(boundary.y());
      // bottom
      else if(view.endy() > boundary.endy())
        view.y(boundary.endy()-view.h());
    }
    else if(overlap.y() > 0)
      view.y(boundary.y() - overlap.y()*0.5f);
  }
}
