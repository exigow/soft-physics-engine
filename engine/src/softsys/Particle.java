package softsys;

import com.badlogic.gdx.math.Vector2;

public class Particle {

  public final Vec2
    position = new Vec2(),
    prevPosition = new Vec2(),
    velocity = new Vec2();

  public Particle(Vector2 destination) {
    position.x = destination.x;
    position.y = destination.y;
    prevPosition.x = destination.x;
    prevPosition.y = destination.y;
  }

  public void updateVelocity() {
    velocity.x = -prevPosition.x + position.x;
    velocity.y = -prevPosition.y + position.y;
    prevPosition.x = position.x;
    prevPosition.y = position.y;
  }

}