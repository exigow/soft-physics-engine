package engine;

import engine.joints.Joint;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collection;

public class Simulator {

  public final Collection<Particle> particles = new ArrayList<>();
  public final Collection<Joint> joints = new ArrayList<>();

  public void simulate(int iterations) {
    updateVerletParticles();
    relaxJoints(iterations);
  }

  private void updateVerletParticles() {
    Vector2f velocity = new Vector2f();
    for (Particle particle : particles) {
      velocity.set(-particle.prev.x + particle.pos.x, -particle.prev.y + particle.pos.y);
      particle.prev.set(particle.pos);
      particle.pos.add(velocity);
    }
  }

  private void relaxJoints(float iterations) {
    float delta = 1f / iterations;
    for (int iteration = 0; iteration < iterations; ++iteration)
      for (Joint joint : joints)
        joint.relax(delta);
  }

}
