package engine.joints;

import engine.Particle;
import engine.Vector;

public class SpringJoint extends Joint {

  private float flexibility;

  public SpringJoint(Particle from, Particle to, float flexibility) {
    super(from, to, Vector.distanceBetween(from.pos, to.pos));
    this.flexibility = flexibility;
  }

  public void relax(float delta) {
    Vector normal = normal();
    float scalar = (expectedLength / normal.getLength() - 1f) * flexibility * delta;
    normal.scale(scalar);
    from.pos.add(normal);
    to.pos.add(normal.invert());
  }

}
