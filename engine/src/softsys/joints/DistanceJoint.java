package softsys.joints;

import softsys.Particle;
import softsys.Vec2;

public class DistanceJoint extends Joint {

  private float flexibility, length;

  public DistanceJoint(Particle red, Particle blue, float flexibility) {
    super(red, blue);
    this.flexibility = flexibility;
    this.length = distance(red, blue);
  }

  // based on hooke's law
  public void relax(float delta) {
    Vec2 normal = normal();
    float scalar = (length / normal.getLength() - 1f) * flexibility * delta;
    normal.x *= scalar;
    normal.y *= scalar;
    red.x += normal.x;
    red.y += normal.y;
    blue.x += -normal.x;
    blue.y += -normal.y;
  }

  public float getTension() {
    float dist = distance(red, blue);
    return Math.abs(dist - length) / length;
  }

  private float distance(Vec2 a, Vec2 b) {
    return com.badlogic.gdx.math.Vector2.dst(a.x, a.y, b.x, b.y);
  }

}
