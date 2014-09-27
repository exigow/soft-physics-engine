package softsys;

import softsys.joints.Joint;

import java.util.ArrayList;

public class World {

  public final Vector2
    gravity = new Vector2(),
    size = new Vector2();

  public final ArrayList<Particle> particles = new ArrayList<Particle>();
  public final ArrayList<Joint> joints = new ArrayList<Joint>();

  public World(Vector2 gravity, Vector2 size) {
    this.gravity.set(gravity);
    this.size.set(size);
  }

  public void simulate(int iterations) {
    for (Particle particle : particles) {
      particle.updateVelocity();
      particle.velocity.x += gravity.x;
      particle.velocity.y += gravity.y;
      block(particle);
      particle.x += particle.velocity.x;
      particle.y += particle.velocity.y;
    }

    //Collections.shuffle(joints, new Random(1235678));
    float delta = 1.0f / iterations;
    for (int iteration = 0; iteration < iterations; ++iteration)
      for (Joint joint : joints)
        joint.relax(delta);
  }

  private void block(Particle particle) {
    float mul = .5f;
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
