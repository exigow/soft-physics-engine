package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class CollisionJoint implements Joint {

  public final Particle a;
  public final Particle b;
  public final Vector2f normal = new Vector2f();

  public CollisionJoint(Particle a, Particle b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public void relax(float delta) {
    normal.set(a.pos).sub(b.pos).normalize().perpendicular();
  }

}
