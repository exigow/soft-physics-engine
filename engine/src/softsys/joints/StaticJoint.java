package softsys.joints;

import softsys.Particle;
import softsys.Vector;

public class StaticJoint extends Joint {

  private final Vector where;

  public StaticJoint(Particle particle, Vector where) {
    super(particle, new Particle(where), 0f);
    this.where = where;
  }

  @Override
  public void relax(float delta) {
    red.set(where);
  }

}
