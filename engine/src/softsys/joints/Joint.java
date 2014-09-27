package softsys.joints;

import com.badlogic.gdx.math.Vector2;
import softsys.Particle;

public abstract class Joint {

  public final Particle red, blue;
  private final Vector2 normal = new Vector2();

  public Joint(Particle red, Particle blue) {
    this.red = red;
    this.blue = blue;
  }

  public abstract void relax(float delta);

  protected Vector2 normal() {
    return normal.set(blue.pos).scl(-1f).add(red.pos);
  }

}
