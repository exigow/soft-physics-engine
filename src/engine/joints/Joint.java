package engine.joints;

import engine.Particle;
import engine.Vector;

public abstract class Joint {

  public final Particle from, to;
  protected float expectedLength;
  private final Vector normal = new Vector();

  public Joint(Particle from, Particle to, float expectedLength) {
    this.from = from;
    this.to = to;
    this.expectedLength = expectedLength;
  }

  public abstract void relax(float delta);

  protected Vector normal() {
    normal.x = -to.x + from.x;
    normal.y = -to.y + from.y;
    return normal;
  }

  public float getLength() {
    return Vector.distanceBetween(from, to);
  }

  public float getTension() {
    float length = getLength();
    return Math.abs(length - expectedLength) / expectedLength;
  }

}
