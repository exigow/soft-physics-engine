package engine.joints;

import engine.Particle;
import engine.Vector;

public abstract class Joint {

  public final Particle red, blue;
  protected float expectedLength;
  private final Vector normal = new Vector();

  public Joint(Particle red, Particle blue, float expectedLength) {
    this.red = red;
    this.blue = blue;
    this.expectedLength = expectedLength;
  }

  public abstract void relax(float delta);

  protected Vector normal() {
    normal.x = -blue.x + red.x;
    normal.y = -blue.y + red.y;
    return normal;
  }

  public float getLength() {
    return Vector.distanceBetween(red, blue);
  }

  public float getTension() {
    float length = getLength();
    return Math.abs(length - expectedLength) / expectedLength;
  }

}
