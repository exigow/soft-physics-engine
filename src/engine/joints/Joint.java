package engine.joints;

import engine.Particle;

public abstract class Joint {

  public final Particle from, to;
  protected float expectedLength;

  public Joint(Particle from, Particle to, float expectedLength) {
    this.from = from;
    this.to = to;
    this.expectedLength = expectedLength;
  }

  public abstract void relax(float delta);

  public float computeTension() {
    float length = from.pos.distance(to.pos);
    return Math.abs(length - expectedLength) / expectedLength;
  }

}
