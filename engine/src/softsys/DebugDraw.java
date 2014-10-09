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
    JOINT_COLOR = new Color(.75f, .75f, .75f, ALPHA),
    CAGE_COLOR = new Color(.325f, .325f, .325f, ALPHA);

  private final World world;

  public DebugDraw(World world) {
    this.world = world;
  }

  public void drawAll(ShapeRenderer renderer) {
    drawJoints(renderer, world.joints);
    //drawVelocities(renderer, world.particles);
    drawParticles(renderer, world.particles);
    drawCage(renderer);
  }

  private void drawParticles(ShapeRenderer renderer, Collection<Particle> particles) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Vector particle : particles)
      drawOutlinedDot(renderer, particle, 1.5f, SHAPE_COLOR);
    renderer.end();

    /*renderer.begin(ShapeRenderer.ShapeType.Line);
    for (Particle particle : particles)
      drawOutlinedLine(renderer, particle.x, particle.y,
        particle.x + (float) Math.cos(particle.angle) * 16f,
        particle.y + (float) Math.sin(particle.angle) * 16f, VELOCITY_COLOR);
    renderer.end();*/
  }

  private void drawVelocities(ShapeRenderer renderer, Collection<Particle> particles) {
    renderer.setColor(VELOCITY_COLOR);
    renderer.begin(ShapeRenderer.ShapeType.Line);
    for (Particle particle : particles)
      drawOutlinedLine(renderer, particle.x, particle.y, particle.x + particle.velocity.x, particle.y+ particle.velocity.y, VELOCITY_COLOR);
    renderer.end();
  }


  private final Color tensionColor = new Color();
  private void drawJoints(ShapeRenderer renderer, Collection<Joint> constraints) {
    renderer.begin(ShapeRenderer.ShapeType.Line);
    for (Joint joint : constraints) {
      float tension = joint.getTension(),
        red = .125f + (tension * JOINT_COLOR.r) * .875f,
        green = .125f + ((1f - tension) * JOINT_COLOR.g) * .875f;
      tensionColor.set(red, green, JOINT_COLOR.b, JOINT_COLOR.a);
      drawOutlinedLine(renderer, joint.red.x, joint.red.y, joint.blue.x, joint.blue.y, tensionColor);
    }
    renderer.end();
  }

  private void drawCage(ShapeRenderer renderer) {
    renderer.setColor(CAGE_COLOR);
    renderer.begin(ShapeRenderer.ShapeType.Line);
    renderer.rect(-world.size.x, -world.size.y, world.size.x * 2f, world.size.y * 2f);
    renderer.end();
  }

  private void drawOutlinedDot(ShapeRenderer renderer, Vector where, float size, Color color) {
    renderer.setColor(Color.BLACK);
    renderer.circle(where.x, where.y, size + 1);
    renderer.setColor(color);
    renderer.circle(where.x, where.y, size);
  }

  private final static int scale = 1;
  private final static int[] samples[] = {new int[] {scale, 0}, new int[] {-scale, 0}, new int[] {0, scale}, new int[] {0, -scale}};
  private void drawOutlinedLine(ShapeRenderer renderer, float ax, float ay, float bx, float by, Color color) {
    renderer.setColor(Color.BLACK);
    for (int[] sample : samples)
      renderer.line(ax + sample[0], ay + sample[1], bx + sample[0], by + sample[1]);
    renderer.setColor(color);
    renderer.line(ax, ay, bx, by);
  }

}
