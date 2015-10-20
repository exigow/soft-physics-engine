package demos;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import demos.utils.FingerProcessor;
import demos.utils.WorldDebugRenderer;
import engine.World;

import java.util.function.Supplier;

public abstract class Demo {

  protected final World world = new World();
  private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final FingerProcessor processor = FingerProcessor.withFingerCount(4);

  public final void onRender() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 32);
    WorldDebugRenderer.render(world, camera.combined);
  }

  protected static class Initializer implements ApplicationListener {

    public final Supplier<Demo> supplier;
    private Demo demo;

    private Initializer(Supplier<Demo> supplier) {
      this.supplier = supplier;
    }

    public static void initializeLazy(Supplier<Demo> supplier) {
      new LwjglApplication(new Initializer(supplier), createDefaultConfiguration());
    }

    @Override
    public void create() {
      demo = supplier.get();
    }

    @Override
    public void render() {
      demo.onRender();
    }

    private static LwjglApplicationConfiguration createDefaultConfiguration() {
      return new LwjglApplicationConfiguration() {{
        title = "demo";
        width = 1280;
        height = 768;
        resizable = false;
        samples = 8;
      }};
    }

    @Override
    public void resize(int i, int i1) {
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

}
