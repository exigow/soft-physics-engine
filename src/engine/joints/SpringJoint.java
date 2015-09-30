package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class SpringJoint extends Joint {

  // todo factory & private constructor

  private final float flexibility;
  private final Vector2f normal = new Vector2f();

  public SpringJoint(Particle from, Particle to, float flexibility) {
    super(from, to, from.pos.distance(to.pos));
    this.flexibility = flexibility;
  }

  public void relax(float delta) {
    normal.x = -to.pos.x + from.pos.x;
    normal.y = -to.pos.y + from.pos.y;
    float scalar = (expectedLength / normal.length() - 1f) * flexibility * delta;
    normal.mul(scalar);
    from.pos.add(normal);
    to.pos.add(normal.negate());
  }

}
