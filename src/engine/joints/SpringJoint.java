package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class SpringJoint extends Joint {

  private float flexibility;

  public SpringJoint(Particle from, Particle to, float flexibility) {
    super(from, to, from.pos.distance(to.pos));
    this.flexibility = flexibility;
  }

  public void relax(float delta) {
    Vector2f normal = normal();
    float scalar = (expectedLength / normal.length() - 1f) * flexibility * delta;
    normal.mul(scalar);
    from.pos.add(normal);
    to.pos.add(normal.negate());
  }

}
