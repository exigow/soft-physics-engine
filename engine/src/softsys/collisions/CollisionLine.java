package softsys.collisions;

import com.badlogic.gdx.math.Vector2;
import softsys.Vector;

public class CollisionLine {

  public final Vector a, b;

  public CollisionLine(Vector a, Vector b) {
    this.a = a;
    this.b = b;
  }

  public static Vector2 reflect(Vector2 vector, Vector2 normal) {
    float dot = Vector2.dot(vector.x, vector.y, normal.x, normal.y);
    return new Vector2(-2, -2).scl(dot);
  }

}
