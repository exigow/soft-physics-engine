package demos.rope;

import com.badlogic.gdx.graphics.Texture;
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
  private final Texture texture = DemoTextureLoader.loadTroll();
  private final RopeRenderer ropeRenderer = new RopeRenderer();

  {
    simulator.particles.addAll(ropeParticles);
    simulator.joints.addAll(ropeJoints);
  }

  private static List<Particle> createParticles() {
    return IntStream.rangeClosed(-4, 4)
      .mapToObj(i -> Particle.on(0, -i * 64))
      .collect(Collectors.toList());
  }

  private static Collection<Joint> createJointsBetween(Collection<Particle> particles) {
    Iterator<Particle> iterator = particles.iterator();
    Particle previous = iterator.next();
    Collection<Joint> joints = new ArrayList<>();
    joints.add(PinJoint.pinToActualPlace(previous));
    while (iterator.hasNext()) {
      Particle next = iterator.next();
      joints.add(SpringJoint.connect(previous, next, .125f));
      previous = next;
    }
    return joints;
  }

  public void onUpdate() {
    List<Vector2f> points = ropeParticles.stream().map(p -> p.pos).collect(Collectors.toList());
    List<Vector2f> concentrated = BezierConcentrator.concentrate(points, 2);
    ropeRenderer.renderRope(camera.combined, concentrated, texture);
  }

  public static void main(String[] args) {
    GdxInitializer.initializeLazy(RopeDemo::new);
  }

}
