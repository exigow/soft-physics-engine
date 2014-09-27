package softsys.joints;

import com.badlogic.gdx.math.Vector2;
import softsys.Particle;
import softsys.Vec2;

public class DistanceJoint extends Joint {

  private float flexibility, length;

  public DistanceJoint(Particle red, Particle blue, float flexibility) {
    super(red, blue);
    this.flexibility = flexibility;
    this.length = distance(red.position, blue.position);
  }

  // based on hooke's law
  public void relax(float delta) {
    Vec2 normal = normal();
    float scalar = (length / normal.len() - 1f) * flexibility * delta;
    normal.x *= scalar;
    normal.y *= scalar;
    red.position.x += normal.x;
    red.position.y += normal.y;
    blue.position.x += -normal.x;
    blue.position.y += -normal.y;
  }

  public float getTension() {
    float dist = distance(red.position, blue.position);
    return Math.abs(dist - length) / length;
  }

  private float distance(Vec2 a, Vec2 b) {
    return Vector2.dst(a.x, a.y, b.x, b.y);
  }

}
