package softsys;

public class Vec2 {

  public float x, y;

  public Vec2() {
    this(0f, 0f);
  }

  public Vec2(float x, float y) {
    this.x = x;
    this.y = y;
  }


  public Vec2 set(float x, float y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Vec2 set(Vec2 from) {
    x = from.x;
    y = from.y;
    return this;
  }

  public Vec2 add(Vec2 from) {
    x += from.x;
    y += from.y;
    return this;
  }

  public float getLength() {
    return (float)Math.sqrt(getLengthSquare());
  }

  public float getLengthSquare() {
    return x * x + y * y;
  }

}
