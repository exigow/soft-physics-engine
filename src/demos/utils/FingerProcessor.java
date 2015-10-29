package demos.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import engine.Particle;
import engine.Simulator;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collection;

public class FingerProcessor {

  private final Collection<Finger> fingers;

  private FingerProcessor(Collection<Finger> fingers) {
    this.fingers = fingers;
  }

  public static FingerProcessor withFingerCount(int maxCount) {
    Collection<Finger> fingers = new ArrayList<>();
    for (int i = 0; i < maxCount; i++)
      fingers.add(new Finger(i));
    return new FingerProcessor(fingers);
  }

  public void update(OrthographicCamera camera, Simulator simulator) {
    for (Finger finger : fingers)
      finger.update(camera, simulator.particles);
  }

  private static Particle findNearestTo(Collection<Particle> particles, Vector2 position) {
    float length = 32f;
    Particle nearest = null;
    for (Particle particle : particles) {
      float checkLength = Vector2.dst(particle.pos.x, particle.pos.y, position.x, position.y);
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
        pinched.reset(new Vector2f(position.x, position.y));
    }

  }

}
