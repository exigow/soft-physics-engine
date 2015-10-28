package engine;

import engine.joints.CollisionJoint;
import engine.joints.Joint;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collection;

public class World {

  public final Collection<Particle> particles = new ArrayList<>();
  public final Collection<Joint> joints = new ArrayList<>();
  public float gravity = 7f;

  public void simulate(float deltaTime, int iterations) {
    Vector2f velocity = new Vector2f();
    for (Particle particle : particles) {
      velocity.set(-particle.prev.x + particle.pos.x, -particle.prev.y + particle.pos.y);
      velocity.y -= gravity * deltaTime;
      particle.prev.set(particle.pos);
      particle.pos.add(velocity);
    }
    float delta = 1.0f / iterations;
    for (int iteration = 0; iteration < iterations; ++iteration)
      for (Joint joint : joints)
        joint.relax(delta);
    for (Particle particle : particles) {
      for (Joint joint : joints) {
        if (joint instanceof CollisionJoint) {
          CollisionJoint collision = (CollisionJoint) joint;
          if (collision.a == particle || collision.b == particle)
            continue;
          if (areIntersecting(particle.pos, particle.prev, collision.a.pos, collision.b.pos)) {
            Vector2f movement = new Vector2f(particle.prev).sub(particle.pos);

            Vector3f movement3 = new Vector3f(movement.x, movement.y, 0);
            Vector3f normal3 = new Vector3f(collision.normal.x, collision.normal.y, 0);
            Vector3f reflection3 = new Vector3f(movement3).reflect(normal3).mul(.75f);
            Vector2f reflection = new Vector2f(reflection3.x, reflection3.y);


            particle.reset(particle.prev.x, particle.prev.y);
            particle.prev.add(reflection);

            reflection.mul(.5f);
            collision.a.prev.sub(reflection);
            collision.b.prev.sub(reflection);

          }
        }
      }
    }
  }

  private static boolean areIntersecting(Vector2f aStart, Vector2f aEnd, Vector2f bStart, Vector2f bEnd) {
    return Line2D.linesIntersect(
      aStart.x, aStart.y,
      aEnd.x, aEnd.y,
      bStart.x, bStart.y,
      bEnd.x, bEnd.y
    );
  }

}
