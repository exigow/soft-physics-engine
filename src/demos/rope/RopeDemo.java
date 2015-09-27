package demos.rope;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import demos.utils.DefaultConfig;
import engine.Particle;
import engine.World;
import demos.utils.WorldDebugRenderer;
import demos.utils.FingerProcessor;
import engine.joints.SpringJoint;
import engine.joints.StaticJoint;

public class RopeDemo implements ApplicationListener {

  private OrthographicCamera camera;
  private World world;
  private final FingerProcessor processor = FingerProcessor.withFingerCount(4);

  @Override
  public void create() {
    com.badlogic.gdx.math.Vector2 size = new com.badlogic.gdx.math.Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    world = new World();
    Particle prev = new Particle(0, 0);
    world.particles.add(prev);
    world.joints.add(new StaticJoint(prev));
    for (int i = 1; i < 24; i++) {
      Particle b = new Particle(i * 16, 0);
      world.joints.add(new SpringJoint(b, prev, 1f));
      world.particles.add(b);
      prev = b;
    }
  }

  public void render() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 16);
    WorldDebugRenderer.render(world, camera.combined);
  }

  @Override
  public void resize(int w, int h) {
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

  public static void main(String[] args) {
    new LwjglApplication(new RopeDemo(), DefaultConfig.create());
  }

}
