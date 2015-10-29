package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class AngleJoint implements Joint {

  public final Particle first;
  public final Particle second;
  public final Particle last;
  private final float stiffness;
  private final float convolution;

  private AngleJoint(Particle first, Particle second, Particle last, float stiffness, float convolution) {
    this.first = first;
    this.second = second;
    this.last = last;
    this.stiffness = stiffness;
    this.convolution = convolution;
  }

  public static AngleJoint connect(Particle first, Particle second, Particle last, float stiffness, float convolution) {
    return new AngleJoint(first, second, last, stiffness, convolution);
  }

  public static AngleJoint connectStraightening(Particle first, Particle second, Particle last, float stiffness) {
    return new AngleJoint(first, second, last, stiffness, 0f);
  }

  @Override
  public void relax(float delta) {
    float diff = curvatureBetween(first.pos, second.pos, last.pos) + convolution;
    if (diff <= -Math.PI)
      diff += 2*Math.PI;
    else if (diff >= Math.PI)
      diff -= 2*Math.PI;
    diff *= stiffness * delta;
    first.pos.set(rotate(first.pos, second.pos, diff));
    last.pos.set(rotate(last.pos, second.pos, -diff));
    second.pos.set(rotate(second.pos, first.pos, diff));
    second.pos.set(rotate(second.pos, last.pos, -diff));
  }

  public static float curvatureBetween(Vector2f middle, Vector2f vLeft, Vector2f vRight) {
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
