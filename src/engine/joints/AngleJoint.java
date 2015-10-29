package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

import static com.badlogic.gdx.math.MathUtils.PI;

public class AngleJoint implements Joint {

  public final Particle first;
  public final Particle middle;
  public final Particle last;
  private final float stiffness;
  private final float convolution;

  private AngleJoint(Particle first, Particle middle, Particle last, float stiffness, float convolution) {
    this.first = first;
    this.middle = middle;
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
    float diff = computeAngleDifference(delta);
    first.pos.set(rotate(first.pos, middle.pos, diff));
    last.pos.set(rotate(last.pos, middle.pos, -diff));
    middle.pos.set(rotate(middle.pos, first.pos, diff));
    middle.pos.set(rotate(middle.pos, last.pos, -diff));
  }

  private float computeAngleDifference(float delta) {
    float diff = curvatureBetween(first.pos, middle.pos, last.pos) + convolution;
    return cycleAngleToPi(diff) * stiffness * delta;
  }

  private static float cycleAngleToPi(float angle) {
    if (angle <= -PI)
      return angle + 2 * PI;
    if (angle >= PI)
      return angle - 2 * PI;
    return angle;
  }

  public static float curvatureBetween(Vector2f first, Vector2f middle, Vector2f last) {
    Vector2f middleDelta = new Vector2f(middle).sub(first);
    Vector2f lastDelta = new Vector2f(last).sub(first);
    return middleDelta.angle(lastDelta);
  }

  private static Vector2f rotate(Vector2f from, Vector2f origin, float theta) {
    float x = from.x - origin.x;
    float y = from.y - origin.y;
    float cos = (float) Math.cos(theta);
    float sin = (float) Math.sin(theta);
    return new Vector2f(x * cos - y*sin + origin.x, x * sin + y * cos + origin.y);
  }

}
