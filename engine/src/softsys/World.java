package softsys;

import softsys.joints.Joint;

import java.util.ArrayList;
import java.util.Collections;

public class World {

  /*public final Vector
    gravity = new Vector();*/

  public final ArrayList<Particle> particles = new ArrayList<Particle>();
  public final ArrayList<Joint> joints = new ArrayList<Joint>();

  /*public World(Vector gravity) {
    this.gravity.set(gravity);
  }*/

  //Vector gravityDelta = new Vector();

  private final static Vector velocity = new Vector();
  public void simulate(float deltaTime, int iterations) {
    for (Particle particle : particles) {
      velocity.set(-particle.prev.x + particle.x,  -particle.prev.y + particle.y);
      particle.prev.set(particle);
      particle.add(velocity);
    }
    float delta = 1.0f / iterations;
    for (int iteration = 0; iteration < iterations; ++iteration) {
      Collections.shuffle(joints);
      for (Joint joint : joints)
        joint.relax(delta);
    }
  }

  /*private void block(Particle particle) {
    float mul = 1f;
    if (particle.y < -size.y) {
      particle.y = -size.y;
      particle.prev.y = particle.y;
      particle.velocity.y = -particle.velocity.y * mul;
    }
    if (particle.y > size.y) {
      particle.y = size.y;
      particle.prev.y = particle.y;
      particle.velocity.y = -particle.velocity.y * mul;
    }
    if (particle.x < -size.x) {
      particle.x = -size.x;
      particle.prev.x = particle.x;
      particle.velocity.x = -particle.velocity.x * mul;
    }
    if (particle.x > size.x) {
      particle.x = size.x;
      particle.prev.x = particle.x;
      particle.velocity.x = -particle.velocity.x * mul;
    }
  }*/

}
