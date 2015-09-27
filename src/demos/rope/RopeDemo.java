package demos.rope;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import demos.utils.DefaultConfig;
import demos.utils.FingerProcessor;
import demos.utils.WorldDebugRenderer;
import engine.Particle;
import engine.World;
import engine.joints.Joint;
import engine.joints.SpringJoint;
import engine.joints.StaticJoint;

public class RopeDemo implements ApplicationListener {

  private OrthographicCamera camera;
  private World world;
  private final FingerProcessor processor = FingerProcessor.withFingerCount(4);

  @Override
  public void create() {
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    world = new World();
    float[] flexibilities = {.25f, .5f, 1f, 2f, 8f};
    for (int ix = 0; ix < 5; ix++) {
      int column = -256 + ix * 128;
      int row = 256;
      Particle previous = new Particle(column, row);
      world.particles.add(previous);
      world.joints.add(new StaticJoint(previous));
      for (int iy = 1; iy < 32; iy++) {
        Particle b = new Particle(column - iy * 8, row);
        Joint joint = new SpringJoint(b, previous, flexibilities[ix]);
        world.joints.add(joint);
        world.particles.add(b);
        previous = b;
      }
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
