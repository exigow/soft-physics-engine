package softsys;

public class Vec2 {

  public float x, y;

  public float len () {
    return (float)Math.sqrt(x * x + y * y);
  }

}
