package softsys;

public class Vector2 {

  public float x, y;

  public Vector2() {
    this(0f, 0f);
  }

  public Vector2(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public Vector2 set(float x, float y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Vector2 set(Vector2 from) {
    x = from.x;
    y = from.y;
    return this;
  }

  public Vector2 add(Vector2 from) {
    x += from.x;
    y += from.y;
    return this;
  }

  public Vector2 invert() {
    x = -x;
    y = -y;
    return this;
  }

  public Vector2 scale(float scalar) {
    x *= scalar;
    y *= scalar;
    return this;
  }

  public float getLength() {
    return (float)Math.sqrt(getLengthSquare());
  }

  public float getLengthSquare() {
    return x * x + y * y;
  }

  private final static Vector2 temp = new Vector2();
  public static float distanceBetween(Vector2 a, Vector2 b) {
    temp.set(a);
    temp.x -= b.x;
    temp.y -= b.y;
    return temp.getLength();
  }

}
