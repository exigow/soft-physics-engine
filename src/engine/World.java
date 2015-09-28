package engine;

import engine.joints.Joint;

import java.util.ArrayList;
import java.util.Collection;

public class World {

  public final Collection<Particle> particles = new ArrayList<>();
  public final Collection<Joint> joints = new ArrayList<>();

  public void simulate(float deltaTime, int iterations) {
    Vector velocity = new Vector();
    for (Particle particle : particles) {
      velocity.set(-particle.prev.x + particle.pos.x, -particle.prev.y + particle.pos.y);
      velocity.y -= 7f * deltaTime;
      particle.prev.set(particle.pos);
      particle.pos.add(velocity);
    }
    float delta = 1.0f / iterations;
    for (int iteration = 0; iteration < iterations; ++iteration)
      for (Joint joint : joints)
        joint.relax(delta);
  }

}
