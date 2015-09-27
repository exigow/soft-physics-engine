package engine;

import engine.joints.Joint;

import java.util.ArrayList;

public class World {

  public final ArrayList<Particle> particles = new ArrayList<Particle>();
  public final ArrayList<Joint> joints = new ArrayList<Joint>();

  private final static Vector velocity = new Vector();
  public void simulate(float deltaTime, int iterations) {
    for (Particle particle : particles) {
      velocity.set(-particle.prev.x + particle.x, -particle.prev.y + particle.y);
      velocity.y -= 7f * deltaTime;
      particle.prev.set(particle);
      particle.add(velocity);
    }
    float delta = 1.0f / iterations;
    for (int iteration = 0; iteration < iterations; ++iteration) {
      for (Joint joint : joints)
        joint.relax(delta);
    }
  }

}
