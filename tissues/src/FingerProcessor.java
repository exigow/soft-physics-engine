import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import softsys.Particle;

import java.util.Collection;

public class FingerProcessor {

  private Particle pinched;
  private final int inputId;
  private boolean clicked = false;
  private Vector3 position = new Vector3();

  public FingerProcessor(int inputId) {
    this.inputId = inputId;
  }

  public void update(OrthographicCamera camera, Collection<Particle> particles) {
    position.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
    camera.unproject(position);
    boolean prevClicked = clicked;
    clicked = Gdx.input.isTouched(inputId);
    if (!prevClicked && clicked) {
      //System.out.println(inputId + ": is pressed");
      Particle nearest = findNearestTo(particles, new Vector2(position.x, position.y));
      if (nearest != null) {
        //System.out.println(inputId + ": pin");
        pinched = nearest;
      }
    }
    if (prevClicked && !clicked) {
      //System.out.println(inputId + ": is released");
      if (pinched != null) {
        pinched = null;
        //System.out.println(inputId + ": unpin");
      }
    }
    if (pinched != null)
      pinched.forcePosition(position.x, position.y);
  }

  public void draw(ShapeRenderer renderer) {
    renderer.begin(ShapeRenderer.ShapeType.Line);
    renderer.circle(position.x, position.y, 32f);
    renderer.end();
  }

  private Particle findNearestTo(Collection<Particle> particles, Vector2 position) {
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

}
