package softsys;

import com.badlogic.gdx.math.Vector2;
import softsys.joints.DistanceJoint;

import java.util.ArrayList;

public class World {

  public final Vector2 gravity, size;

  public final ArrayList<Particle> particles = new ArrayList<Particle>();
  public final ArrayList<DistanceJoint> joints = new ArrayList<DistanceJoint>();

  public World(Vector2 gravity, Vector2 size) {
    this.gravity = gravity;
    this.size = size;
  }

  public void simulate(int iterations) {
    for (Particle particle : particles) {
      particle.updateVelocity();
      particle.velocity.x += gravity.x;
      particle.velocity.y += gravity.y;
      block(particle);
      particle.position.x += particle.velocity.x;
      particle.position.y += particle.velocity.y;
    }

    //Collections.shuffle(joints, new Random(1235678));
    float delta = 1.0f / iterations;
    for (int iteration = 0; iteration < iterations; ++iteration)
      for (DistanceJoint joint : joints)
        joint.relax(delta);
  }

  private void block(Particle particle) {
    float mul = .5f;
    if (particle.position.y < -size.y) {
      particle.position.y = -size.y;
      particle.prevPosition.y = particle.position.y;
      particle.velocity.y = -particle.velocity.y * mul;
    }
    if (particle.position.y > size.y) {
      particle.position.y = size.y;
      particle.prevPosition.y = particle.position.y;
      particle.velocity.y = -particle.velocity.y * mul;
    }
    if (particle.position.x < -size.x) {
      particle.position.x = -size.x;
      particle.prevPosition.x = particle.position.x;
      particle.velocity.x = -particle.velocity.x * mul;
    }
    if (particle.position.x > size.x) {
      particle.position.x = size.x;
      particle.prevPosition.x = particle.position.x;
      particle.velocity.x = -particle.velocity.x * mul;
    }
  }

}
