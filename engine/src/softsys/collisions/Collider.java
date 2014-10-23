package softsys.collisions;

import softsys.Vector;

public class Collider {

  public final Vector a, b;

  public Collider(Vector a, Vector b) {
    this.a = a;
    this.b = b;
  }

  private final Vector normal = new Vector();
  public Vector getNormal() {
    normal.x = a.x - b.x;
    normal.y = a.y - b.y;
    //normal.x = - normal.x;
    //float length = Math.sqrt((ax * ax) + (ay * ay));
    return normal;
  }

  private final Vector center = new Vector();
  public Vector getCenter() {
    return center.set((a.x + b.x) / 2f, (a.y + b.y) / 2f);
  }

  /*public static Vector2 reflect(Vector2 vector, Vector2 normal) {
    float dot = Vector2.dot(vector.x, vector.y, normal.x, normal.y);
    return new Vector2(-2, -2).scl(dot);
  }*/

}
