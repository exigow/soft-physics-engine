package main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import main.constructors.PlantConstructor;
import softsys.DebugDraw;
import softsys.Particle;
import softsys.Vector;
import softsys.World;

import java.util.Collection;

public class Example implements ApplicationListener {

  private OrthographicCamera camera;
  private ShapeRenderer shapeRenderer;
  private final Vector3 mousePosition = new Vector3();
  private World world;
  private DebugDraw worldDebugDraw;
  private Particle selected = null;
  private boolean clicked = false;

  @Override
  public void create() {
    com.badlogic.gdx.math.Vector2 size = new com.badlogic.gdx.math.Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    shapeRenderer  = new ShapeRenderer();
    world = new World(new Vector(0f, -9f), new Vector(size.x / 2f - 32f, size.y / 2f - 32f));
    worldDebugDraw = new DebugDraw(world);

    //new ClothConstructor(new Vector(0f, 0f), new Vector(384f, 384f), 24, .75f).flush(world);
    new PlantConstructor().flush(world);
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
      selected.set(mousePosition.x, mousePosition.y);

    Gdx.gl.glClearColor(.125f, .125f, .125f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.circle(mousePosition.x, mousePosition.y, 32f);
    shapeRenderer.end();
    worldDebugDraw.drawAll(shapeRenderer);
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
