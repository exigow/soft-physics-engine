package engine.joints;

import engine.Particle;

public class AngleJoint implements Joint{

  public final Particle a;
  public final Particle b;
  public final Particle c;

  public AngleJoint(Particle a, Particle b, Particle c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  @Override
  public void relax(float delta) {

  }

}
