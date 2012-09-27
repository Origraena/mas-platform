/* @author william */
package wjd.math;

public class V2
{
  /* CONSTANTS */
  public static final V2 ORIGIN = new V2(0.0f, 0.0f);

  
  /* FUNCTIONS */
  public static float det(V2 a, V2 b) { return a.x*b.y - b.y*a.x; }
  public static float dot(V2 a, V2 b) { return a.x*b.x + a.y*b.y; }
  public static V2 inter(V2 a, V2 b, float frac) 
  {
    return (frac > 1 || frac < 0) ? new V2() : (new V2(a,b).scale(frac).add(b)); 
  }
  public static boolean coline(V2 a, V2 b) 
  { 
    return dot(a, b) == a.norm()*b.norm();
  } 
  
  
  /* ATTRIBUTES */
  private float x, y, norm, norm2; 
                  /* NB - cached norm and norm2 are negative if out-of-date */

  
  /* METHODS */
  
  // constructors
  public V2() { x = y = norm = norm2 = 0.0f; }
  public V2(float _x, float _y) { x = _x; y = _y; norm = norm2 = -1;  }
  public V2(V2 source, V2 destination)
  {
    x = destination.x - source.x;
    y = destination.y - source.y;
    norm = norm2 = -1;  // norm not yet calculated
  }
  private V2(float _x, float _y, float _norm, float _norm2)
  {
    x = _x; y = _y; norm = _norm; norm2 = _norm2;
  }
  
  // accessors
  public float x() { return x; }
  public float y() { return y; }
  public boolean zero() { return (x == 0 && y == 0); }
  public float norm2()
  {
    // recalculate norm2 only if nessecary
    if(norm2 < 0.0f) norm2 = x*x + y*y;
    return norm2;
  }
  public float norm()
  {
    // recalculate norm only if nessecary
    if(norm < 0.0f) norm = (float)Math.sqrt(norm2());
    return norm;
  }
  public V2 sign() { return new V2(Math.signum(x), Math.signum(y)); }
  @Override
  public String toString() { return ( "(" + x + ',' + y + ')'); }
  @Override
  public V2 clone() { return new V2(x, y, norm, norm2); }
    
  // base mutators
  public V2 x(float _x) { x = _x; norm = norm2 = -1.0f; return this; }
  public V2 y(float _y) { y = _y; norm = norm2 = -1.0f; return this; }
  public V2 xy(float _x, float _y) 
  { 
    x = _x; y = _y; norm = norm2 = -1.0f; return this; 
  }
  // arithmetic mutators
  public V2 xadd(float dx) { return x(x+dx); }
  public V2 yadd(float dy) { return y(y+dy); }
  public V2 add(float dx, float dy) { return xy(x + dx, y + dy); }
  public V2 scale(float amount) { return xy(x*amount, y*amount); } 
  public V2 inc() { return (xy(x + 1, y + 1)); }
  public V2 dinc() { return (xy(x - 1, y - 1)); }
  public V2 abs() { return (xy((float)Math.abs(x), (float)Math.abs(y))); }
  public V2 floor() { return (xy((float)Math.floor(x), (float)Math.floor(y))); }
  public V2 ceil() { return (xy((float)Math.ceil(x), (float)Math.ceil(y))); }
  // element-wise arithmetic mutators
  public V2 add(V2 v) { return xy(x + v.x, y + v.y); } 
  public V2 sub(V2 v) { return xy(x - v.x, y - v.y); } 
  public V2 scale(V2 v) { return xy(x*v.x, y*v.y); }
  // geometric mutators
  public V2 left() { return xy(y, -x); }
  public V2 right() { return xy(-y, x); }
  public V2 norm(float amount) { return xy(x/norm()*amount, y/norm()*amount); }
  public V2 addNorm(float amount) { return norm(norm() + amount); }
  public V2 normalise() { return (norm(1 / norm())); }
  public V2 addAngle(float angle)
  {
    double cos = Math.cos(angle), sin = Math.sin(angle);
    return xy((float)(x*cos - y*sin),(float)(x*sin + y*cos));
  }
}
