package softsys.joints;

import softsys.Particle;
import softsys.Vector;

public class StaticJoint extends Joint {

  public StaticJoint(Particle particle, Vector where) {
    super(particle, new Particle(where), 0f);
  }

  public StaticJoint(Particle particle) {
    this(particle, particle);
  }

  @Override
  public void relax(float delta) {
    red.forcePosition(blue.x, blue.y);
  }

}
