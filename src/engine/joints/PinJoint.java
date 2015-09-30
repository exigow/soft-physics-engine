package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class PinJoint extends Joint {

  private PinJoint(Particle particle, Vector2f where) {
    super(particle, new Particle(where), 0f);
  }

  public static PinJoint toActualPosition(Particle particle) {
    return new PinJoint(particle, particle.pos);
  }

  @Override
  public void relax(float delta) {
    from.forcePosition(to.pos.x, to.pos.y);
  }

}
