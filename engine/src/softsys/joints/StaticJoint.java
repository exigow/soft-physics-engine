package softsys.joints;

import softsys.Particle;
import softsys.Vector2;

public class StaticJoint extends Joint {

  private final Vector2 where;

  public StaticJoint(Particle particle, Vector2 where) {
    super(particle, new Particle(where), 0f);
    this.where = where;
  }

  @Override
  public void relax(float delta) {
    red.set(where);
  }

}
