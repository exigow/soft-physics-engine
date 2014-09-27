package softsys.joints;

import softsys.Particle;
import softsys.Vector2;

public abstract class Joint {

  public final Particle red, blue;
  private final Vector2 normal = new Vector2();

  public Joint(Particle red, Particle blue) {
    this.red = red;
    this.blue = blue;
  }

  public abstract void relax(float delta);

  protected Vector2 normal() {
    normal.x = -blue.x + red.x;
    normal.y = -blue.y + red.y;
    return normal;
  }

  public float getLenght() {
    return Vector2.distanceBetween(red, blue);
  }

}
