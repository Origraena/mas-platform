/* @author william */
package wjd.math;

public class Rect
{
  /* CONSTANTS */

  /* FUNCTIONS */

  /* ATTRIBUTES */
  public float x, y, w, h;

  /* METHODS */
  
  // constructors
  public Rect() { x = y = w = h = 0.0f; }
  public Rect(float _x, float _y, float _w, float _h) 
  { 
    x = _x; y = _y; w = _w; h = _h;
  }
  public Rect(V2 position, V2 size)
  {
    x = position.x(); y = position.y();
    w = position.x(); h = position.y();
  }
  
  // accessors
  public float x() { return x; }
  public float y() { return y; }
  public float w() { return w; }
  public float h() { return h; }
  public V2 pos() { return new V2(x, y); }
  public V2 size() { return new V2(w, h); }
  public float ratio() { return w/h; }
  @Override
  public String toString() 
  { 
    return ( "(" + x + ',' + y + ',' + w + ',' + h + ')'); 
  }
  
  // query
  public boolean contains(float px, float py)
  {
    return (px >= x && px <= x+w && py >= y && py <= y+h);
  }
  public boolean contains(V2 pos)
  {
    return contains(pos.x(), pos.y());
  }
  public boolean inside(Rect r)
  {
    return (r.contains(x, y) 
          && r.contains(x + w, y)
          && r.contains(x, y + h)
          && r.contains(x + w, y + h));
  }
  public boolean collides(Rect r)
  {
    return (V2.dot(new V2(pos(), r.pos().add(r.size())), 
                  new V2(r.pos(), pos().add(size()))) > 0);
  }
  
  // base mutators
  public Rect x(float _x) { x = _x; return this; }
  public Rect y(float _y) { y = _y; return this; }
  public Rect xy(float _x, float _y) { x = _x; y = _y; return this; }
  public Rect w(float _w) { w = _w; return this; }
  public Rect h(float _h) { h = _h; return this; }
  public Rect wh(float _w, float _h) { w = _w; h = _h; return this; }
  public Rect xywh(float _x, float _y, float _w, float _h) 
  { 
    x = _x; y = _y; w = _w; h = _h; return this; 
  }
  public Rect ratio(float ratio) 
  {
    return (ratio > ratio()) ? h(w/ratio) : w(h*ratio);
  }
  // arithmetic mutators
  public Rect xadd(float dx) { return x(x+dx); }
  public Rect yadd(float dy) { return y(y+dy); }
  public Rect shift(float dx, float dy) { return xy(x + dx, y + dy); }
  public Rect scale(float amount) { return wh(w*amount, h*amount); } 
  // element-wise arithmetic mutators
  public Rect pos(V2 v) { return xy(v.x(), v.y()); }
  public Rect size(V2 v) { return wh(v.x(), v.y()); }
  public Rect shift(V2 v) { return xy(x + v.x(), y + v.y()); } 
  public Rect unshift(V2 v) { return xy(x - v.x(), y - v.y()); } 
  public Rect scale(V2 v) { return wh(w*v.x(), h*v.y()); }
  // geometric mutators
  public Rect centerOn(V2 v)
  {
    x = v.x() - w*0.5f; y = v.y() - h*0.5f; return this;
  }
  public Rect centerWithin(Rect container)
  {
    if(w > container.w && h > container.h)
      return this;
    else if(w > container.w)
      wh(container.w, h*container.w/w);
    else if(h > container.h)
      wh(w*container.h/h, container.h);
    
    return xy(container.x + container.w*0.5f - w*0.5f, 
              container.y + container.h*0.5f - h*0.5f);
  }
  public Rect scaleCentral(float amount)
  {
    return shift(size().scale(1.0f-amount).scale(0.5f)).scale(amount);
  }
}
