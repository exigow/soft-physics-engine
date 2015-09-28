package engine;

import org.joml.Vector2f;

public class Particle {

  public final Vector2f pos = new Vector2f();
  public final Vector2f prev = new Vector2f();

  public Particle(float x, float y) {
    pos.set(x, y);
    prev.set(pos);
  }

  public Particle(Vector2f where) {
    this(where.x, where.y);
  }

  public void forcePosition(float x, float y) {
    pos.set(x, y);
    prev.set(x, y);
  }

}