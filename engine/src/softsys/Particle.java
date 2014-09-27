package softsys;

import com.badlogic.gdx.math.Vector2;

public class Particle {

  public final Vector2 pos = new Vector2(),
    prevPosition = new Vector2(),
    velocity = new Vector2();

  public Particle(Vector2 destination) {
    pos.set(destination);
    prevPosition.set(destination);
  }

  public void updateVelocity() {
    velocity.set(prevPosition).scl(-1f).add(pos);
    prevPosition.set(pos);
  }

}