package demos.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import engine.Particle;
import engine.World;

import java.util.ArrayList;
import java.util.Collection;

public class FingerProcessor {

  private final Collection<Finger> fingers;

  public FingerProcessor(final int howManyFingers) {
    fingers = new ArrayList<Finger>() {{
      for (int i = 0; i < howManyFingers; i++)
        add(new Finger(i));
    }};
  }

  public void update(OrthographicCamera camera, World world) {
    for (Finger finger : fingers)
      finger.update(camera, world.particles);
  }

  private static Particle findNearestTo(Collection<Particle> particles, Vector2 position) {
    float length = 32f;
    Particle nearest = null;
    for (Particle particle : particles) {
      float checkLength = Vector2.dst(particle.x, particle.y, position.x, position.y);
      if (checkLength < length) {
        nearest = particle;
        length = checkLength;
      }
    }
    return nearest;
  }

  private static class Finger {

    private Particle pinched;
    private final Vector3 position = new Vector3();
    private final int inputId;
    private boolean clicked = false;

    public Finger(int inputId) {
      this.inputId = inputId;
    }

    public void update(OrthographicCamera camera, Collection<Particle> particles) {
      position.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
      camera.unproject(position);
      boolean prevClicked = clicked;
      clicked = Gdx.input.isTouched(inputId);
      if (!prevClicked && clicked) {
        Particle nearest = findNearestTo(particles, new Vector2(position.x, position.y));
        if (nearest != null)
          pinched = nearest;
      }
      if (prevClicked && !clicked)
        if (pinched != null)
          pinched = null;
      if (pinched != null)
        pinched.forcePosition(position.x, position.y);
    }

  }

}
