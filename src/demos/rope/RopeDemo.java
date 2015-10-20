package demos.rope;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Matrix4;
import demos.Demo;
import demos.utils.DemoTextureLoader;
import engine.Particle;
import engine.joints.Joint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RopeDemo extends Demo {

  private final List<Particle> ropeParticles = createParticles();
  private final Collection<Joint> ropeJoints = createJointsBetween(ropeParticles);
  private final ImmediateModeRenderer20 renderer = new ImmediateModeRenderer20(false, true, 1);
  private final Texture texture = DemoTextureLoader.loadTroll();

  {
    world.particles.addAll(ropeParticles);
    world.joints.addAll(ropeJoints);
  }

  private static List<Particle> createParticles() {
    return IntStream.rangeClosed(-4, 4)
      .mapToObj(i -> new Particle(0, -i * 64))
      .collect(Collectors.toList());
  }

  private static Collection<Joint> createJointsBetween(Collection<Particle> particles) {
    Iterator<Particle> iterator = particles.iterator();
    Particle previous = iterator.next();
    Collection<Joint> joints = new ArrayList<>();
    joints.add(PinJoint.pin(previous));
    while (iterator.hasNext()) {
      Particle next = iterator.next();
      joints.add(new SpringJoint(previous, next, .25f));
      previous = next;
    }
    return joints;
  }

  public void onUpdate() {
    List<Vector2f> points = ropeParticles.stream().map(p -> p.pos).collect(Collectors.toList());
    List<Vector2f> concentrated = BezierConcentrator.concentrate(points, 4);
    renderRope(renderer, camera.combined, concentrated, texture);
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

  public static void main(String[] args) {
    Initializer.initializeLazy(RopeDemo::new);
  }

}
