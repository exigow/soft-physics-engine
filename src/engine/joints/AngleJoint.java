package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class AngleJoint implements Joint{

  public final Particle a;
  public final Particle b;
  public final Particle c;
  private final float stiffness;
  private final float convolution;

  // todo fabryka i prywatyzacja

  public AngleJoint(Particle a, Particle b, Particle c, float stiffness, float convolution) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.stiffness = stiffness;
    this.convolution = convolution;
  }

  @Override
  public void relax(float delta) {
    float diff = angle2(a.pos, b.pos, c.pos) + convolution;
    if (diff <= -Math.PI)
      diff += 2*Math.PI;
    else if (diff >= Math.PI)
      diff -= 2*Math.PI;
    diff *= stiffness * delta;
    a.pos.set(rotate(a.pos, b.pos, diff));
    c.pos.set(rotate(c.pos, b.pos, -diff));
    b.pos.set(rotate(b.pos, a.pos, diff));
    b.pos.set(rotate(b.pos, c.pos, -diff));
  }

  public static float angle2(Vector2f middle, Vector2f vLeft, Vector2f vRight) {
    Vector2f copyLeft = new Vector2f(vLeft);
    Vector2f copyRight = new Vector2f(vRight);
    return copyLeft.sub(middle).angle(copyRight.sub(middle));
  }

  public Vector2f rotate(Vector2f from, Vector2f origin, float theta) {
    float x = from.x - origin.x;
    float y = from.y - origin.y;
    return new Vector2f((float) (x*Math.cos(theta) - y*Math.sin(theta) + origin.x), (float) (x*Math.sin(theta) + y*Math.cos(theta) + origin.y));
  }

}
