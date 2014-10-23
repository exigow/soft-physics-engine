package basic;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import softsys.Particle;
import softsys.World;
import softsys.draw.WorldDebugDraw;
import softsys.joints.SpringJoint;

import java.util.Collection;

public class Example implements ApplicationListener {

  private OrthographicCamera camera;
  private ShapeRenderer shapeRenderer;
  private final Vector3 mousePosition = new Vector3();
  private World world;
  private WorldDebugDraw worldDebugDraw;
  private Particle selected = null;
  private boolean clicked = false;

  @Override
  public void create() {
    com.badlogic.gdx.math.Vector2 size = new com.badlogic.gdx.math.Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    shapeRenderer  = new ShapeRenderer();
    world = new World();
    worldDebugDraw = new WorldDebugDraw(world);

    Particle a = new Particle(-64, 0);
    Particle b = new Particle(64, 0);
    world.particles.add(a);
    world.particles.add(b);
    world.joints.add(new SpringJoint(a, b, 1f));
  }

  public void render() {
    mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
    camera.update();
    camera.unproject(mousePosition);

    world.simulate(Gdx.graphics.getDeltaTime(), 16);

    boolean prevClicked = clicked;
    clicked = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    if (!prevClicked && clicked) {
      Particle nearest = findNearestTo(world.particles, new com.badlogic.gdx.math.Vector2(mousePosition.x, mousePosition.y));
      if (nearest != null)
        selected = nearest;
    }
    if (prevClicked && !clicked)
      selected = null;
    if (selected != null)
      selected.forcePosition(mousePosition.x, mousePosition.y);

    Gdx.gl.glClearColor(.075f, .075f, .075f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.circle(mousePosition.x, mousePosition.y, 32f);
    shapeRenderer.end();
    worldDebugDraw.draw(camera.combined);
  }

  private Particle findNearestTo(Collection<Particle> particles, com.badlogic.gdx.math.Vector2 position) {
    float length = 32f;
    Particle nearest = null;
    for (Particle particle : particles) {
      float checkLength = com.badlogic.gdx.math.Vector2.dst(particle.x, particle.y, position.x, position.y);
      if (checkLength < length) {
        nearest = particle;
        length = checkLength;
      }
    }
    return nearest;
  }

  @Override
  public void resize(int i, int i2) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void dispose() {
  }

}
