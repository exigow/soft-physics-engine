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

public class AngleJointDemo extends Demo {

  {
    List<Particle> ropeParticles = createParticles();
    Collection<Joint> ropeJoints = createJointsBetween(ropeParticles);
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
    result.add(PinJoint.pinToActualPlace(particles.get(0)));
    for (int i = 0; i < particles.size() - 1; i++) {
      Particle a = particles.get(i);
      Particle b = particles.get(i + 1);
      result.add(SpringJoint.connect(a, b, .5f));
      if (i < particles.size() - 2) {
        Particle c = particles.get(i + 2);
        result.add(AngleJoint.connectStraightening(a, b, c, .25f));
      }
    }
    return result;
  }

  public static void main(String[] args) {
    Initializer.initializeLazy(AngleJointDemo::new);
  }

}
