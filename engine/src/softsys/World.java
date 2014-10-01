package softsys;

import softsys.joints.Joint;

import java.util.ArrayList;
import java.util.Collections;

public class World {

  public final Vector
    gravity = new Vector(),
    size = new Vector();

  public final ArrayList<Particle> particles = new ArrayList<Particle>();
  public final ArrayList<Joint> joints = new ArrayList<Joint>();

  public World(Vector gravity, Vector size) {
    this.gravity.set(gravity);
    this.size.set(size);
  }

  Vector gravityDelta = new Vector();
  public void simulate(float deltaTime, int iterations) {
    Collections.shuffle(joints);

    gravityDelta.set(gravity).scale(deltaTime);
    for (Particle particle : particles) {
      particle.updateVelocity();
      particle.velocity.add(gravityDelta);
      block(particle);
      particle.add(particle.velocity);
    }

    float delta = 1.0f / iterations;
    for (int iteration = 0; iteration < iterations; ++iteration)
      for (Joint joint : joints)
        joint.relax(delta);
  }

  private void block(Particle particle) {
    float mul = 1f;
    if (particle.y < -size.y) {
      particle.y = -size.y;
      particle.prevPosition.y = particle.y;
      particle.velocity.y = -particle.velocity.y * mul;
    }
    if (particle.y > size.y) {
      particle.y = size.y;
      particle.prevPosition.y = particle.y;
      particle.velocity.y = -particle.velocity.y * mul;
    }
    if (particle.x < -size.x) {
      particle.x = -size.x;
      particle.prevPosition.x = particle.x;
      particle.velocity.x = -particle.velocity.x * mul;
    }
    if (particle.x > size.x) {
      particle.x = size.x;
      particle.prevPosition.x = particle.x;
      particle.velocity.x = -particle.velocity.x * mul;
    }
  }

}
