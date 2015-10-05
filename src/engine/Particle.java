package engine;

import org.joml.Vector2f;

public class Particle {

  public final Vector2f pos = new Vector2f();
  public final Vector2f prev = new Vector2f();

  public static Particle on(float x, float y) {
    return new Particle(x, y);
  }

  public static Particle onZero() {
    return Particle.on(0, 0);
  }

  // todo prywatyzacja

  public Particle(float x, float y) {
    pos.set(x, y);
    prev.set(pos);
  }

  public void reset(float x, float y) {
    pos.set(x, y);
    prev.set(x, y);
  }

}