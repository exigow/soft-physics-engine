package demos.rope;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Matrix4;
import demos.utils.DefaultConfig;
import demos.utils.FingerProcessor;
import demos.utils.WorldDebugRenderer;
import engine.Particle;
import engine.World;
import engine.joints.Joint;
import engine.joints.SpringJoint;
import engine.joints.StaticJoint;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RopeDemo implements ApplicationListener {

  private OrthographicCamera camera;
  private World world = new World();
  private final FingerProcessor processor = FingerProcessor.withFingerCount(4);
  private final Collection<Particle> ropeParticles = createParticles();
  private final Collection<Joint> ropeJoints = createJointsBetween(ropeParticles);
  private ImmediateModeRenderer20 renderer;
  private Texture texture;

  @Override
  public void create() {
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    world.particles.addAll(ropeParticles);
    world.joints.addAll(ropeJoints);
    renderer = new ImmediateModeRenderer20(false, true, 1);
    texture = loadTexture();
  }

  private static List<Particle> createParticles() {
    return IntStream.range(-4, 4)
      .mapToObj(i -> new Particle(0, -i * 64))
      .collect(Collectors.toList());
  }

  private static Collection<Joint> createJointsBetween(Collection<Particle> particles) {
    Iterator<Particle> iterator = particles.iterator();
    Particle previous = iterator.next();
    Collection<Joint> joints = new ArrayList<>();
    joints.add(new StaticJoint(previous));
    while (iterator.hasNext()) {
      Particle next = iterator.next();
      joints.add(new SpringJoint(previous, next, .125f));
      previous = next;
    }
    return joints;
  }

  private static Texture loadTexture() {
    return new Texture(Gdx.files.internal("data/troll.png"));
  }

  public void render() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 16);
    WorldDebugRenderer.render(world, camera.combined);
    renderRope(renderer, camera.combined, ropeParticles, texture);
  }

  private static void renderRope(ImmediateModeRenderer20 renderer, Matrix4 matrix, Collection<Particle> particles, Texture tex) {
    float alpha = .75f;
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    tex.bind();
    renderer.begin(matrix, GL20.GL_TRIANGLE_STRIP);
    Iterator<Particle> iterator = particles.iterator();
    Particle previous = iterator.next();
    renderer.texCoord(.5f, 1);
    renderer.color(1, 1, 1, alpha);
    renderer.vertex(previous.pos.x, previous.pos.y, 0);
    Vector2f perpendicular = new Vector2f();
    boolean swapTextures = false;
    while (iterator.hasNext()) {
      Particle next = iterator.next();
      int swapped = swapTextures ? 1 : 0;
      perpendicular.set(next.pos).sub(previous.pos).normalize().perpendicular().mul(32);
      renderer.texCoord(1, swapped);
      renderer.color(1, 1, 1, alpha);
      renderer.vertex(next.pos.x + perpendicular.x, next.pos.y + perpendicular.y, 0);
      renderer.texCoord(0, swapped);
      renderer.color(1, 1, 1, alpha);
      renderer.vertex(next.pos.x - perpendicular.x, next.pos.y - perpendicular.y, 0);
      swapTextures = !swapTextures;
      previous = next;
    }
    renderer.end();
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
