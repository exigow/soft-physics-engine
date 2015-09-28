package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class StaticJoint extends Joint {

  public StaticJoint(Particle particle, Vector2f where) {
    super(particle, new Particle(where), 0f);
  }

  public StaticJoint(Particle particle) {
    this(particle, particle.pos);
  }

  @Override
  public void relax(float delta) {
    from.forcePosition(to.pos.x, to.pos.y);
  }

}
