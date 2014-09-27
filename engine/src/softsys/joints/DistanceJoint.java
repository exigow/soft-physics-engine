package softsys.joints;

import com.badlogic.gdx.math.Vector2;
import softsys.Particle;

public class DistanceJoint extends Joint {

  private float flexibility, length;

  public DistanceJoint(Particle red, Particle blue, float flexibility) {
    super(red, blue);
    this.flexibility = flexibility;
    this.length = distance(red.pos, blue.pos);
  }

  /*public void relax(float delta) {
    Vector2 normal = normal();
    float mul = normal.len2(),
      scale = ((length * length - mul) / mul) * flexibility * delta;
    normal.scl(scale);
    red.pos.add(normal);
    blue.pos.add(normal.scl(-1f));
  }*/

  // based on hooke's law
  public void relax(float delta) {
    Vector2 normal = normal();
    float scalar = (length / normal.len() - 1f) * flexibility * delta;
    normal.scl(scalar);
    red.pos.add(normal);
    blue.pos.add(normal.scl(-1f));
  }

  public float getTension() {
    float dist = distance(red.pos, blue.pos);
    return Math.abs(dist - length) / length;
  }

  private float distance(Vector2 a, Vector2 b) {
    return Vector2.dst(a.x, a.y, b.x, b.y);
  }

}
