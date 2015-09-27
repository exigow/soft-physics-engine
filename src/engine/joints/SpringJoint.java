package engine.joints;

import engine.Particle;
import engine.Vector;

public class SpringJoint extends Joint {

  private float flexibility;

  public SpringJoint(Particle red, Particle blue, float flexibility) {
    super(red, blue, Vector.distanceBetween(red, blue));
    this.flexibility = flexibility;
  }

  public void relax(float delta) {
    Vector normal = normal();
    float scalar = (expectedLength / normal.getLength() - 1f) * flexibility * delta;
    normal.scale(scalar);
    red.add(normal);
    blue.add(normal.invert());
  }

}
