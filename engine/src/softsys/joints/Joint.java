package softsys.joints;

import softsys.Particle;
import softsys.Vector2;

public abstract class Joint {

  public final Particle red, blue;
  protected float expectedLength;
  private final Vector2 normal = new Vector2();

  public Joint(Particle red, Particle blue, float expectedLength) {
    this.red = red;
    this.blue = blue;
    this.expectedLength = expectedLength;
  }

  public abstract void relax(float delta);

  protected Vector2 normal() {
    normal.x = -blue.x + red.x;
    normal.y = -blue.y + red.y;
    return normal;
  }

  public float getLength() {
    return Vector2.distanceBetween(red, blue);
  }

  public float getTension() {
    float length = getLength();
    return Math.abs(length - expectedLength) / expectedLength;
  }

}
