package engine.joints;

import engine.Particle;
import engine.Vector;

public class StaticJoint extends Joint {

  public StaticJoint(Particle particle, Vector where) {
    super(particle, new Particle(where), 0f);
  }

  public StaticJoint(Particle particle) {
    this(particle, particle);
  }

  @Override
  public void relax(float delta) {
    from.forcePosition(to.x, to.y);
  }

}
