package demos.angle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import demos.utils.DefaultConfig;
import demos.utils.FingerProcessor;
import demos.utils.WorldDebugRenderer;
import engine.Particle;
import engine.World;
import engine.joints.AngleJoint;
import engine.joints.Joint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AngleDemo implements ApplicationListener {

  private OrthographicCamera camera;
  private World world = new World();
  private final FingerProcessor processor = FingerProcessor.withFingerCount(4);
  private final List<Particle> ropeParticles = createParticles();
  private final Collection<Joint> ropeJoints = createJointsBetween(ropeParticles);

  @Override
  public void create() {
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    world.particles.addAll(ropeParticles);
    world.joints.addAll(ropeJoints);
  }

  private static List<Particle> createParticles() {
    return IntStream.rangeClosed(-4, 4)
      .mapToObj(i -> new Particle(0, -i * 64))
      .collect(Collectors.toList());
  }

  private static Collection<Joint> createJointsBetween(List<Particle> particles) {
    Collection<Joint> result = new ArrayList<>();
    result.add(PinJoint.pin(particles.get(0)));
    for (int i = 0; i < particles.size() - 1; i++) {
      Particle a = particles.get(i);
      Particle b = particles.get(i + 1);
      result.add(new SpringJoint(a, b, .5f));
      if (i < particles.size() - 2) {
        Particle c = particles.get(i + 2);
        result.add(new AngleJoint(a, b, c, .25f));
      }
    }
    return result;
  }

  public void render() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 32);
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
    new LwjglApplication(new AngleDemo(), DefaultConfig.create());
  }

}
