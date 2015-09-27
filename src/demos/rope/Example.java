package demos.rope;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import engine.Particle;
import engine.World;
import engine.draw.WorldDebugDraw;
import engine.interactionhelpers.FingerProcessor;
import engine.joints.SpringJoint;
import engine.joints.StaticJoint;

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
