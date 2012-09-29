package wjd.math;

/**
 * A useful Java vector class for games and graphic applications: the norm is
 * cached and recalculated only when needed, and there are a number of useful
 * methods implemented.
 *
 * @author wdyce
 * @date 24 Aug, 2012
 */
public class V2
{
  /* CONSTANTS */
  /**
   * Vector origin, (0,0).
   */
  public static final V2 ORIGIN = new V2(0.0f, 0.0f);

  /* FUNCTIONS */
  /**
   * The area of a parallelogram is the absolute value of the determinant of the
   * matrix formed by the vectors representing the parallelogram's sides.
   *
   * @see <a href="http://en.wikipedia.org/wiki/Determinant">Wikipedia</a>
   * @param a the first vector.
   * @param b the other vector.
   * @return the determinant of the two vectors.
   */
  public static float det(V2 a, V2 b)
  {
    return a.x * b.y - b.y * a.x;
  }

  /**
   * The dot product, also known as scalar or inner product, is used to
   * determine the length of the project of one vector onto another and hence to
   * find out, for instance, whether two vectors are perpendicular.
   *
   * @param a the first vector.
   * @param b the other vector.
   * @return (a.x*b.x + a.y*b.y);
   */
  public static float dot(V2 a, V2 b)
  {
    return a.x * b.x + a.y * b.y;
  }

  /**
   * Calculate the linear interpolation of two vector positions based on a given
   * real value between 0 and 1.
   *
   * @param a the first vector.
   * @param b the other vector.
   * @param frac real value representing the percent distance between a (0) and
   * b (1).
   * @return <li>= a if frac is less than or equal to 0. <li>= b if frac is
   * greater than or equal to 1. <li>= a + (b-a)*frac otherwise.
   */
  public static V2 inter(V2 a, V2 b, float frac)
  {
    return (frac < 0)
      ? a : ((frac > 1)
      ? b : (new V2(a, b).scale(frac).add(b)));
  }

  /**
   * Check if two vectors and collinear, in other words parallel.
   *
   * @param a the first vector.
   * @param b the other vector.
   * @return true if the two vectors are collinear, false if not.
   */
  public static boolean coline(V2 a, V2 b)
  {
    return dot(a, b) == a.norm() * b.norm();
  }
  /* ATTRIBUTES */
  private float x, y, norm, norm2;
  /* NB - cached norm and norm2 are negative if out-of-date */

  /* METHODS */
  // constructors
  /**
   * The null vector (0, 0)
   */
  public V2()
  {
    x = y = norm = norm2 = 0.0f;
  }

  /**
   * Vector created with specified abscissa (x) and ordinate (y)
   *
   * @param x the abscissa of the vector, its horizontal component.
   * @param y the ordinate of the vector, its vertical component.
   */
  public V2(float x, float y)
  {
    this.x = x;
    this.y = y;
    norm = norm2 = -1.0f;
  }

  /**
   * Vector created from a pair of point, id est the vector direction from the
   * first point to the second.
   *
   * @param source the starting point of the new vector.
   * @param destination the end point of the new vector.
   */
  public V2(V2 source, V2 destination)
  {
    x = destination.x - source.x;
    y = destination.y - source.y;
    norm = norm2 = -1;  // norm not yet calculated
  }

  private V2(float _x, float _y, float _norm, float _norm2)
  {
    x = _x;
    y = _y;
    norm = _norm;
    norm2 = _norm2;
  }

  // accessors
  /**
   * Return the abscissa (x).
   *
   * @return the abscissa of the vector, its horizontal component.
   */
  public float x()
  {
    return x;
  }

  /**
   * Return the ordinate (y).
   *
   * @return the ordinate of the vector, its vertical component.
   */
  public float y()
  {
    return y;
  }

  /**
   * Check if this is a null vector.
   *
   * @return true if this is a null vector, false otherwise
   */
  public boolean zero()
  {
    return (x == 0 && y == 0);
  }

  /**
   * Check the square norm of this vector: this is faster to calculate than the
   * norm itself, so this methods should be used where possible (ie: the norm is
   * less than a value v if the square norm is less than v squared).
   *
   * @return the square norm of the vector.
   * @see <a href="V2#norm()>norm2</a>
   */
  public float norm2()
  {
    // recalculate norm2 only if nessecary
    if (norm2 < 0.0f) norm2 = x * x + y * y;
    return norm2;
  }

  /**
   * Return the norm (magnitude, length) of the vector: it is preferable to use
   * the square norm where possible (ie: the norm is less than a value v if the
   * square norm is less than v squared).
   *
   * @return the norm of the vector.
   * @see <a href="V2#norm2()>norm2</a>
   */
  public float norm()
  {
    // recalculate norm only if nessecary
    if (norm < 0.0f) norm = (float) Math.sqrt(norm2());
    return norm;
  }

  public V2 sign()
  {
    return new V2(Math.signum(x), Math.signum(y));
  }

  @Override
  public String toString()
  {
    return ("(" + x + ',' + y + ')');
  }

  @Override
  public V2 clone()
  {
    return new V2(x, y, norm, norm2);
  }

  // base mutators
  public V2 x(float _x)
  {
    x = _x;
    norm = norm2 = -1.0f;
    return this;
  }

  public V2 y(float _y)
  {
    y = _y;
    norm = norm2 = -1.0f;
    return this;
  }

  public V2 xy(float _x, float _y)
  {
    x = _x;
    y = _y;
    norm = norm2 = -1.0f;
    return this;
  }
  // arithmetic mutators

  public V2 xadd(float dx)
  {
    return x(x + dx);
  }

  public V2 yadd(float dy)
  {
    return y(y + dy);
  }

  public V2 add(float dx, float dy)
  {
    return xy(x + dx, y + dy);
  }

  public V2 scale(float amount)
  {
    return xy(x * amount, y * amount);
  }

  public V2 inc()
  {
    return (xy(x + 1, y + 1));
  }

  public V2 dinc()
  {
    return (xy(x - 1, y - 1));
  }

  public V2 abs()
  {
    return (xy((float) Math.abs(x), (float) Math.abs(y)));
  }

  public V2 floor()
  {
    return (xy((float) Math.floor(x), (float) Math.floor(y)));
  }

  public V2 ceil()
  {
    return (xy((float) Math.ceil(x), (float) Math.ceil(y)));
  }
  // element-wise arithmetic mutators

  public V2 add(V2 v)
  {
    return xy(x + v.x, y + v.y);
  }

  public V2 sub(V2 v)
  {
    return xy(x - v.x, y - v.y);
  }

  public V2 scale(V2 v)
  {
    return xy(x * v.x, y * v.y);
  }
  // geometric mutators

  public V2 left()
  {
    return xy(y, -x);
  }

  public V2 right()
  {
    return xy(-y, x);
  }

  public V2 norm(float amount)
  {
    return xy(x / norm() * amount, y / norm() * amount);
  }

  public V2 addNorm(float amount)
  {
    return norm(norm() + amount);
  }

  public V2 normalise()
  {
    return (norm(1 / norm()));
  }

  public V2 addAngle(float angle)
  {
    double cos = Math.cos(angle), sin = Math.sin(angle);
    return xy((float) (x * cos - y * sin), (float) (x * sin + y * cos));
  }
}
