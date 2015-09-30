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
    return IntStream.range(-3, 3)
      .mapToObj(i -> new Particle(0, -i * 128))
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
    return new Texture(Gdx.files.internal("data/troll.png"));
  }

  public void render() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 16);
    WorldDebugRenderer.render(world, camera.combined);
    List<Vector2f> pos = dividePositions(ropeParticles.stream().map(p -> p.pos).collect(Collectors.toList()));
    shape.setProjectionMatrix(camera.combined);
    shape.begin(ShapeRenderer.ShapeType.Filled);
    boolean swapColor = false;
    for (int i = 1; i < pos.size(); i++) {
      Vector2f prev = pos.get(i - 1);
      Vector2f next = pos.get(i);
      shape.setColor(swapColor ? 1 : 0, 0, !swapColor ? 1 : 0, 1);
      swapColor = !swapColor;
      shape.rectLine(prev.x, prev.y, next.x, next.y, 4);
    }
    shape.end();
    //renderRope(renderer, camera.combined, dividePositions(ropeParticles.stream().map(p -> p.pos).collect(Collectors.toList())), texture);
  }

  private static void renderRope(ImmediateModeRenderer20 renderer, Matrix4 matrix, List<Vector2f> positions, Texture tex) {
    float alpha = .75f;
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    tex.bind();
    renderer.begin(matrix, GL20.GL_TRIANGLE_STRIP);
    Iterator<Vector2f> iterator = positions.iterator();
    Vector2f previous = iterator.next();
    renderer.texCoord(.5f, 1);
    renderer.color(1, 1, 1, alpha);
    renderer.vertex(previous.x, previous.y, 0);
    Vector2f perpendicular = new Vector2f();
    boolean swapTextures = false;
    while (iterator.hasNext()) {
      Vector2f next = iterator.next();
      int swapped = swapTextures ? 1 : 0;
      perpendicular.set(next).sub(previous).normalize().perpendicular().mul(64);
      renderer.texCoord(1, swapped);
      renderer.color(1, 1, 1, alpha);
      renderer.vertex(next.x + perpendicular.x, next.y + perpendicular.y, 0);
      renderer.texCoord(0, swapped);
      renderer.color(1, 1, 1, alpha);
      renderer.vertex(next.x - perpendicular.x, next.y - perpendicular.y, 0);
      swapTextures = !swapTextures;
      previous = next;
    }
    renderer.end();
  }

  private static List<Vector2f> dividePositions(List<Vector2f> positions) {
    List<Vector2f> result = new ArrayList<>();
    for (int i = 0; i < positions.size() - 3; i += 1) {
      Vector2f a = positions.get(i);
      Vector2f b = positions.get(i + 1);
      Vector2f c = positions.get(i + 2);
      Vector2f d = positions.get(i + 3);
      /*result.add(cubic2(a, b, c, d, .1f));
      result.add(cubic2(a, b, c, d, .2f));
      result.add(cubic2(a, b, c, d, .3f));
      result.add(cubic2(a, b, c, d, .4f));
      result.add(cubic2(a, b, c, d, .5f));
      result.add(cubic2(a, b, c, d, .6f));
      result.add(cubic2(a, b, c, d, .7f));
      result.add(cubic2(a, b, c, d, .8f));
      result.add(cubic2(a, b, c, d, .9f));
      result.add(cubic2(a, b, c, d, 1f));*/
      for (float t = 0; t < 1f; t += .05f) {
        result.add(cubic2(a, b, c, d, t));
      }
    }
    return result;
  }

  private static Vector2f cubic(Vector2f start, Vector2f control, Vector2f end, float t) {
    float x = (1 - t) * (1 - t) * start.x + 2 * (1 - t) * t * control.x + t * t * end.x;
    float y = (1 - t) * (1 - t) * start.y + 2 * (1 - t) * t * control.y + t * t * end.y;
    return new Vector2f(x, y);
  }

  private static Vector2f cubic2(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3, float t) {
    float x = (1-t)*(1-t)*(1-t)*p0.x + 3*(1-t)*(1-t)*t*p1.x + 3*(1-t)*t*t*p2.x + t*t*t*p3.x;
    float y = (1-t)*(1-t)*(1-t)*p0.y + 3*(1-t)*(1-t)*t*p1.y + 3*(1-t)*t*t*p2.y + t*t*t*p3.y;
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


  void asd() {

  }

  /*
  void curve4(Polygon* p,
            double x1, double y1,   //Anchor1
            double x2, double y2,   //Control1
            double x3, double y3,   //Control2
            double x4, double y4)   //Anchor2
{
    double dx1 = x2 - x1;
    double dy1 = y2 - y1;
    double dx2 = x3 - x2;
    double dy2 = y3 - y2;
    double dx3 = x4 - x3;
    double dy3 = y4 - y3;

    double subdiv_step  = 1.0 / (NUM_STEPS + 1);
    double subdiv_step2 = subdiv_step*subdiv_step;
    double subdiv_step3 = subdiv_step*subdiv_step*subdiv_step;

    double pre1 = 3.0 * subdiv_step;
    double pre2 = 3.0 * subdiv_step2;
    double pre4 = 6.0 * subdiv_step2;
    double pre5 = 6.0 * subdiv_step3;

    double tmp1x = x1 - x2 * 2.0 + x3;
    double tmp1y = y1 - y2 * 2.0 + y3;

    double tmp2x = (x2 - x3)*3.0 - x1 + x4;
    double tmp2y = (y2 - y3)*3.0 - y1 + y4;

    double fx = x1;
    double fy = y1;

    double dfx = (x2 - x1)*pre1 + tmp1x*pre2 + tmp2x*subdiv_step3;
    double dfy = (y2 - y1)*pre1 + tmp1y*pre2 + tmp2y*subdiv_step3;

    double ddfx = tmp1x*pre4 + tmp2x*pre5;
    double ddfy = tmp1y*pre4 + tmp2y*pre5;

    double dddfx = tmp2x*pre5;
    double dddfy = tmp2y*pre5;

    int step = NUM_STEPS;

    // Suppose, we have some abstract object Polygon which
    // has method AddVertex(x, y), similar to LineTo in
    // many graphical APIs.
    // Note, that the loop has only operation add!
    while(step--)
    {
        fx   += dfx;
        fy   += dfy;
        dfx  += ddfx;
        dfy  += ddfy;
        ddfx += dddfx;
        ddfy += dddfy;
        p->AddVertex(fx, fy);
    }
    p->AddVertex(x4, y4); // Last step must go exactly to x4, y4
}
   */

  public static void main(String[] args) {
    new LwjglApplication(new RopeDemo(), DefaultConfig.create());
  }

}
