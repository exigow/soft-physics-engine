package engine;

public class Particle extends Vector {

  public final Vector prev = new Vector();
  public float angle = 0f;

  public Particle(float x, float y) {
    super(x, y);
    prev.set(this);
  }

  public Particle(Vector where) {
    this(where.x, where.y);
  }

  public void forcePosition(float x, float y) {
    set(x, y);
    prev.set(x, y);
  }

}