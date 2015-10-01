package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class PinJoint implements Joint {

  public final Particle which;
  public final Vector2f where;

  private PinJoint(Particle which, Vector2f where) {
    this.which = which;
    this.where = where;
  }

  public static PinJoint pin(Particle particle) {
    Vector2f where = new Vector2f(particle.pos);
    return new PinJoint(particle, where);
  }

  @Override
  public void relax(float delta) {
    which.reset(where.x, where.y);
  }

}
