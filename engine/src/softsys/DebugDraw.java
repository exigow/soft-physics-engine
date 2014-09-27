package softsys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import softsys.joints.DistanceJoint;

import java.util.Collection;

public class DebugDraw {

  private final static float ALPHA = 1f;
  private final static Color
    SHAPE_COLOR = new Color(.5f, .75f, 1f, ALPHA),
    VELOCITY_COLOR = new Color(.5f, 1f, .75f, ALPHA),
    JOINT_COLOR = new Color(.5f, .325f, .25f, ALPHA),
    CAGE_COLOR = new Color(.325f, .325f, .325f, ALPHA);

  private final World world;

  public DebugDraw(World world) {
    this.world = world;
  }

  public void drawAll(ShapeRenderer renderer) {
    drawJoints(renderer, world.joints);
    //drawVelocities(renderer, world.particles);
    //drawParticles(renderer, world.particles);
    drawCage(renderer);
  }

  private void drawParticles(ShapeRenderer renderer, Collection<Particle> particles) {
    renderer.setColor(SHAPE_COLOR);
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Particle particle : particles)
      renderer.circle(particle.position.x, particle.position.y, 1f);
    renderer.end();
  }

  private void drawVelocities(ShapeRenderer renderer, Collection<Particle> particles) {
    renderer.setColor(VELOCITY_COLOR);
    renderer.begin(ShapeRenderer.ShapeType.Line);
    for (Particle particle : particles)
      renderer.line(particle.position.x, particle.position.y, particle.position.x + particle.velocity.x, particle.position.y + particle.velocity.y);
    renderer.end();
  }

  private void drawJoints(ShapeRenderer renderer, Collection<DistanceJoint> constraints) {
    renderer.begin(ShapeRenderer.ShapeType.Line);
    for (DistanceJoint constraint : constraints) {
      float tension = constraint.getTension(),
        red = tension * JOINT_COLOR.r,
        green = (1 - tension) * JOINT_COLOR.g;
      Color color = new Color(red, green, 1f * JOINT_COLOR.b, 1f * JOINT_COLOR.a);
      renderer.setColor(color);
      renderer.line(constraint.red.position.x, constraint.red.position.y, constraint.blue.position.x, constraint.blue.position.y);
    }
    renderer.end();
  }

  private void drawCage(ShapeRenderer renderer) {
    renderer.setColor(CAGE_COLOR);
    renderer.begin(ShapeRenderer.ShapeType.Line);
    renderer.rect(-world.size.x, -world.size.y, world.size.x * 2f, world.size.y * 2f);
    renderer.end();
  }

}
