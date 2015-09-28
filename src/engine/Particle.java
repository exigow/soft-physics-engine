package engine;

public class Particle {

  public final Vector pos = new Vector();
  public final Vector prev = new Vector();

  public Particle(float x, float y) {
    pos.set(x, y);
    prev.set(pos);
  }

  public Particle(Vector where) {
    this(where.x, where.y);
  }

  public void forcePosition(float x, float y) {
    pos.set(x, y);
    prev.set(x, y);
  }

}