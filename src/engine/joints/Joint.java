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
    normal.x = -to.pos.x + from.pos.x;
    normal.y = -to.pos.y + from.pos.y;
    return normal;
  }

  public float getLength() {
    return Vector.distanceBetween(from.pos, to.pos);
  }

  public float getTension() {
    float length = getLength();
    return Math.abs(length - expectedLength) / expectedLength;
  }

}
