package softsys.joints;

import softsys.Particle;
import softsys.Vector2;

public class SpringJoint extends Joint {

  private float flexibility;

  public SpringJoint(Particle red, Particle blue, float flexibility) {
    super(red, blue, Vector2.distanceBetween(red, blue));
    this.flexibility = flexibility;
  }

  // based on hooke's law
  public void relax(float delta) {
    Vector2 normal = normal();
    float scalar = (expectedLength / normal.getLength() - 1f) * flexibility * delta;
    normal.scale(scalar);
    red.add(normal);
    blue.add(normal.invert());
  }



}
