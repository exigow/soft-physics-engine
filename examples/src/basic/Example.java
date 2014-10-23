package basic;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import softsys.Particle;
import softsys.World;
import softsys.draw.WorldDebugDraw;
import softsys.interactionhelpers.FingerProcessor;
import softsys.joints.SpringJoint;
import softsys.joints.StaticJoint;

public class Example implements ApplicationListener {

  private OrthographicCamera camera;
  private World world;
  private WorldDebugDraw worldDebugDraw;
  private final FingerProcessor processor = new FingerProcessor(4);

  @Override
  public void create() {
    com.badlogic.gdx.math.Vector2 size = new com.badlogic.gdx.math.Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    world = new World();
    worldDebugDraw = new WorldDebugDraw(world);
    Particle a = new Particle(0, 0),
      b = new Particle(64, 0),
      c = new Particle(128, 0);
    world.particles.add(a);
    world.particles.add(b);
    world.particles.add(c);
    world.joints.add(new StaticJoint(a));
    world.joints.add(new SpringJoint(a, b, 1f));
    world.joints.add(new SpringJoint(b, c, 1f));
  }

  public void render() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 16);
    Gdx.gl.glClearColor(.075f, .075f, .075f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    worldDebugDraw.draw(camera.combined);
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
