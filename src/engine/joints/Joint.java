package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public abstract class Joint {

  public final Particle from, to;
  protected float expectedLength;
  private final Vector2f normal = new Vector2f();

  public Joint(Particle from, Particle to, float expectedLength) {
    this.from = from;
    this.to = to;
    this.expectedLength = expectedLength;
  }

  public abstract void relax(float delta);

  protected Vector2f normal() {
    normal.x = -to.pos.x + from.pos.x;
    normal.y = -to.pos.y + from.pos.y;
    return normal;
  }

  public float getLength() {
    return from.pos.distance(to.pos);
  }

  public float getTension() {
    float length = getLength();
    return Math.abs(length - expectedLength) / expectedLength;
  }

}
