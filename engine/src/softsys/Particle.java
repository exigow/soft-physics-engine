package softsys;

public class Particle extends Vector2 {

  public final Vector2
    prevPosition = new Vector2(),
    velocity = new Vector2();

  public Particle(float x, float y) {
    super(x, y);
    prevPosition.set(this);
  }

  public Particle(Vector2 where) {
    this(where.x, where.y);
  }

  public void updateVelocity() {
    velocity.x = -prevPosition.x + x;
    velocity.y = -prevPosition.y + y;
    prevPosition.set(this);
  }

}