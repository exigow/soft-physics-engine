package demos.angle;

import demos.Demo;
import engine.Particle;
import engine.joints.AngleJoint;
import engine.joints.Joint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AngleDemo extends Demo {

  private final List<Particle> ropeParticles = createParticles();
  private final Collection<Joint> ropeJoints = createJointsBetween(ropeParticles);

  {
    world.particles.addAll(ropeParticles);
    world.joints.addAll(ropeJoints);
  }

  private static List<Particle> createParticles() {
    return IntStream.rangeClosed(-4, 4)
      .mapToObj(i -> Particle.on(0, -i * 64))
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
        result.add(new AngleJoint(a, b, c, .25f, 0f));
      }
    }
    return result;
  }

  public static void main(String[] args) {
    Initializer.initializeLazy(AngleDemo::new);
  }

}
