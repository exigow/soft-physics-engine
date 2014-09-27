package softsys;

public class Particle extends Vec2 {

  public final Vec2
    prevPosition = new Vec2(),
    velocity = new Vec2();

  public Particle(float x, float y) {
    super(x, y);
    prevPosition.set(this);
  }

  public void updateVelocity() {
    velocity.x = -prevPosition.x + x;
    velocity.y = -prevPosition.y + y;
    prevPosition.set(this);
  }

}