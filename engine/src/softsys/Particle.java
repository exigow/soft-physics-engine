package softsys;

public class Particle extends Vector {

  public final Vector
    prevPosition = new Vector(),
    velocity = new Vector();

  public float angle = 0f;

  public Particle(float x, float y) {
    super(x, y);
    prevPosition.set(this);
  }

  public Particle(Vector where) {
    this(where.x, where.y);
  }

  public void forcePosition(float wherex, float wherey) {
    set(wherex, wherey);
    prevPosition.set(wherex, wherey);
  }

  public void updateVelocity() {
    velocity.x = -prevPosition.x + x;
    velocity.y = -prevPosition.y + y;
    prevPosition.set(this);
  }

}