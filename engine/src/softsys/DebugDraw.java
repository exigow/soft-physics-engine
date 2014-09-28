package softsys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import softsys.joints.Joint;

import java.util.Collection;

public class DebugDraw {

  private final static float ALPHA = 1f;
  private final static Color
    SHAPE_COLOR = new Color(.5f, .75f, 1f, ALPHA),
    VELOCITY_COLOR = new Color(.5f, 1f, .75f, ALPHA),
    JOINT_COLOR = new Color(.75f, .5f, .325f, ALPHA),
    CAGE_COLOR = new Color(.325f, .325f, .325f, ALPHA);

  private final World world;

  public DebugDraw(World world) {
    this.world = world;
  }

  public void drawAll(ShapeRenderer renderer) {
    drawJoints(renderer, world.joints);
    drawVelocities(renderer, world.particles);
    drawParticles(renderer, world.particles);
    drawCage(renderer);
  }

  private void drawParticles(ShapeRenderer renderer, Collection<? extends Vector> particles) {
    renderer.setColor(SHAPE_COLOR);
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Vector particle : particles)
      renderer.circle(particle.x, particle.y, 1f);
    renderer.end();
  }

  private void drawVelocities(ShapeRenderer renderer, Collection<Particle> particles) {
    renderer.setColor(VELOCITY_COLOR);
    renderer.begin(ShapeRenderer.ShapeType.Line);
    for (Particle particle : particles)
      renderer.line(particle.x, particle.y, particle.x + particle.velocity.x, particle.y + particle.velocity.y);
    renderer.end();
  }

  private void drawJoints(ShapeRenderer renderer, Collection<Joint> constraints) {
    renderer.begin(ShapeRenderer.ShapeType.Line);
    for (Joint joint : constraints) {
      /*float tension = joint.getTension(),
        red = .125f + (tension * JOINT_COLOR.r) * .875f,
        green = .125f + ((1f - tension) * JOINT_COLOR.g) * .875f;
      Color color = new Color(red, green, JOINT_COLOR.b, JOINT_COLOR.a);
      renderer.setColor(color);*/
      renderer.setColor(JOINT_COLOR);
      renderer.line(joint.red.x, joint.red.y, joint.blue.x, joint.blue.y);
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
