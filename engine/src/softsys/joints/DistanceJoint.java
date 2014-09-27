package softsys.joints;

import softsys.Particle;
import softsys.Vector2;

public class DistanceJoint extends Joint {

  private float flexibility, length;

  public DistanceJoint(Particle red, Particle blue, float flexibility) {
    super(red, blue);
    this.flexibility = flexibility;
    this.length = Vector2.distanceBetween(red, blue);
  }

  // based on hooke's law
  public void relax(float delta) {
    Vector2 normal = normal();
    float scalar = (length / normal.getLength() - 1f) * flexibility * delta;
    normal.scale(scalar);
    red.add(normal);
    blue.add(normal.invert());
  }

  public float getTension() {
    float dist = getLenght();
    return Math.abs(dist - length) / length;
  }

}
