package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class SpringJoint implements Joint {

  public final Particle from;
  public final Particle to;
  private final float flexibility;
  public float expectedLength;

  private SpringJoint(Particle from, Particle to, float flexibility, float expectedLength) {
    this.from = from;
    this.to = to;
    this.flexibility = flexibility;
    this.expectedLength = expectedLength;
  }

  public static SpringJoint connect(Particle from, Particle to, float flexibility) {
    float distance = from.pos.distance(to.pos);
    return new SpringJoint(from, to, flexibility, distance);
  }

  public static SpringJoint connectWithFixedLength(Particle from, Particle to, float flexibility, float expectedLength) {
    return new SpringJoint(from, to, flexibility, expectedLength);
  }

  @Override
  public void relax(float delta) {
    Vector2f normal = new Vector2f(to.pos).negate().add(from.pos);
    float scalar = (expectedLength / normal.length() - 1f) * flexibility * delta;
    normal.mul(scalar);
    from.pos.add(normal);
    to.pos.add(normal.negate());
  }

}
