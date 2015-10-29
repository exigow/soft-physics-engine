package demos;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import demos.utils.FingerProcessor;
import demos.utils.rendering.DebugRenderer;
import engine.Particle;
import engine.Simulator;

import java.util.function.Supplier;

public abstract class Demo {

  protected final Simulator simulator = new Simulator();
  protected final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final FingerProcessor processor = FingerProcessor.withFingerCount(4);

  public final void onRender() {
    processor.update(camera, simulator);
    camera.update();
    applyGravityForEachParticle();
    simulator.simulate(8);
    DebugRenderer.render(simulator, camera.combined);
  }

  private void applyGravityForEachParticle() {
    float delta = Gdx.graphics.getDeltaTime();
    for (Particle particle : simulator.particles)
      particle.pos.y -= 7f * delta;
  }

  public void onUpdate() {
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
      demo.onUpdate();
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
