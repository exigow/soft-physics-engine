package demos.rope;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
  private final List<Particle> ropeParticles = createParticles();
  private final Collection<Joint> ropeJoints = createJointsBetween(ropeParticles);
  private ImmediateModeRenderer20 renderer;
  private Texture texture;
  private ShapeRenderer shape;

  @Override
  public void create() {
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    world.particles.addAll(ropeParticles);
    world.joints.addAll(ropeJoints);
    renderer = new ImmediateModeRenderer20(false, true, 1);
    shape = new ShapeRenderer();
    texture = loadTexture();
  }

  private static List<Particle> createParticles() {
    return IntStream.rangeClosed(-3, 3)
      .mapToObj(i -> new Particle(0, -i * 96))
      .collect(Collectors.toList());
  }

  private static Collection<Joint> createJointsBetween(Collection<Particle> particles) {
    Iterator<Particle> iterator = particles.iterator();
    Particle previous = iterator.next();
    Collection<Joint> joints = new ArrayList<>();
    joints.add(new StaticJoint(previous));
    while (iterator.hasNext()) {
      Particle next = iterator.next();
      joints.add(new SpringJoint(previous, next, .25f));
      previous = next;
    }
    return joints;
  }

  private static Texture loadTexture() {
    Texture texture = new Texture(Gdx.files.internal("data/troll.png"));
    texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    return texture;
  }

  public void render() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 16);
    WorldDebugRenderer.render(world, camera.combined);
    List<Vector2f> positions = dividePositions(ropeParticles.stream().map(p -> p.pos).collect(Collectors.toList()), 4);
    renderRope(renderer, camera.combined, positions, texture);
  }

  private static void renderRope(ImmediateModeRenderer20 renderer, Matrix4 matrix, List<Vector2f> positions, Texture tex) {
    float alpha = .75f;
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    tex.bind();
    renderer.begin(matrix, GL20.GL_TRIANGLE_STRIP);
    Iterator<Vector2f> iterator = positions.iterator();
    Vector2f previous = iterator.next();
    Vector2f perpendicular = new Vector2f();
    float step = 0f;
    while (iterator.hasNext()) {
      Vector2f next = iterator.next();
      step += .25f;
      perpendicular.set(next).sub(previous).normalize().perpendicular().mul(32);
      renderer.texCoord(1, step);
      renderer.color(1, 1, 1, alpha);
      renderer.vertex(next.x + perpendicular.x, next.y + perpendicular.y, 0);
      renderer.texCoord(0, step);
      renderer.color(1, 1, 1, alpha);
      renderer.vertex(next.x - perpendicular.x, next.y - perpendicular.y, 0);
      previous = next;
    }
    renderer.end();
  }

  private static List<Vector2f> dividePositions(List<Vector2f> positions, int maxSegments) {
    positions.add(positions.get(positions.size() - 1)); // hack, duplicate last reference
    List<Vector2f> result = new ArrayList<>();
    Vector2f controlA = new Vector2f();
    Vector2f controlB = new Vector2f();
    Vector2f next = new Vector2f();
    Vector2f prev = new Vector2f();
    for (int i = 0; i < positions.size() - 2; i += 1) {
      Vector2f a = positions.get(i);
      Vector2f b = positions.get(i + 1);
      Vector2f c = positions.get(i + 2);
      next.set(c).sub(a).normalize().mul(32);
      controlA.set(a.x + prev.x, a.y + prev.y);
      controlB.set(b.x - next.x, b.y - next.y);
      prev.set(next);
      for (int f = 0; f < maxSegments; f += 1) {
        float t = (1f / maxSegments) * f;
        result.add(cubic2(a, controlA, controlB, b, t));
      }
    }
    return result;
  }

  private static Vector2f cubic2(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3, float t) {
    float x = (1 - t) * (1 - t) * (1 - t) * p0.x + 3 * (1 - t) * (1 - t) * t * p1.x + 3 * (1 - t) * t * t * p2.x + t * t * t * p3.x;
    float y = (1 - t) * (1 - t) * (1 - t) * p0.y + 3 * (1 - t) * (1 - t) * t * p1.y + 3 * (1 - t) * t * t * p2.y + t * t * t * p3.y;
    return new Vector2f(x, y);
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
