package softsys;

public class Vector {

  public float x, y;

  public Vector() {
    this(0f, 0f);
  }

  public Vector(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public Vector set(float x, float y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Vector set(Vector from) {
    x = from.x;
    y = from.y;
    return this;
  }

  public Vector add(Vector from) {
    x += from.x;
    y += from.y;
    return this;
  }

  public Vector add(float x, float y) {
    this.x += x;
    this.y += y;
    return this;
  }

  public Vector sub(Vector from) {
    this.x -= from.x;
    this.y -= from.y;
    return this;
  }

  public Vector mul(Vector from) {
    this.x *= from.x;
    this.y *= from.y;
    return this;
  }

  public Vector invert() {
    x = -x;
    y = -y;
    return this;
  }

  public Vector scale(float scalar) {
    x *= scalar;
    y *= scalar;
    return this;
  }

  public Vector divide(float scalar) {
    scale(1f / scalar);
    return this;
  }

  public float angleTo(Vector target) {
    float angle = (float) Math.atan2(target.y - y, target.x - x);
    if(angle < 0)
      angle += Math.PI * 2;
    return angle;
  }

  public static float angdiff(float angle0, float angle1) {
    double angle0Deg = Math.toDegrees(angle0);
    double angle1Deg = Math.toDegrees(angle1);
    return (float)Math.toRadians(((((angle0Deg - angle1Deg) % 360f) + 540f) % 360f) - 180f);
  }

  public float distanceTo(Vector target) {
    return distanceBetween(this, target);
  }

  public float getLength() {
    return (float)Math.sqrt(getLengthSquare());
  }

  public float getLengthSquare() {
    return x * x + y * y;
  }

  private final static Vector temp = new Vector();
  public static float distanceBetween(Vector a, Vector b) {
    temp.set(a);
    temp.x -= b.x;
    temp.y -= b.y;
    return temp.getLength();
  }

}
