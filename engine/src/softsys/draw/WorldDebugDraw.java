package softsys.draw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import softsys.Particle;
import softsys.World;
import softsys.joints.Joint;

import java.util.Collection;

public class WorldDebugDraw {

  private final static float SCALE = 2f;
  private final static float ALPHA = 1f;
  private final static Color
    SHAPE_COLOR = new Color(.5f, .75f, 1f, ALPHA),
    VELOCITY_COLOR = new Color(.5f, 1f, .75f, ALPHA),
    JOINT_COLOR = new Color(.5f, .5f, .5f, ALPHA);

  // 116, 138, 151 - background
  //
  // 201, 218, 41 - medium
  // 164, 206, 58 - low

  private final World world;

  public WorldDebugDraw(World world) {
    this.world = world;
  }

  public void drawAll(ShapeRenderer renderer) {
    drawJoints(renderer, world.joints);
    //drawVelocities(renderer, world.particles);
    //drawParticles(renderer, world.particles);
  }

  private void drawParticles(ShapeRenderer renderer, Collection<Particle> particles) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Particle particle : particles) {
      drawOutlinedLine(renderer, particle.x, particle.y,
        particle.x + (float) Math.cos(particle.angle) * 16f,
        particle.y + (float) Math.sin(particle.angle) * 16f, SCALE * .75f,  VELOCITY_COLOR);
      drawOutlinedDot(renderer, particle.x, particle.y, SCALE * 2.25f, SHAPE_COLOR);
    }
    renderer.end();

  }

  /*private void drawVelocities(ShapeRenderer renderer, Collection<Particle> particles) {
    float multiple = 4f;
    renderer.setColor(VELOCITY_COLOR);
    renderer.begin(ShapeRenderer.ShapeType.Filled);*/
    /*for (Particle particle : particles)
      drawOutlinedLine(renderer, particle.x, particle.y,
        particle.x + particle.velocity.x * multiple,
        particle.y + particle.velocity.y * multiple, VELOCITY_COLOR);*/
    /*renderer.end();
  }*/


  //private final Color tensionColor = new Color();
  private void drawJoints(ShapeRenderer renderer, Collection<Joint> constraints) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Joint joint : constraints) {
      /*float tension = joint.getTension(),
        red = .125f + (tension * JOINT_COLOR.r) * .875f,
        green = .125f + ((1f - tension) * JOINT_COLOR.g) * .875f;
      tensionColor.set(red, green, JOINT_COLOR.b, JOINT_COLOR.a);*/
      drawLine(renderer, joint.red.x, joint.red.y, joint.blue.x, joint.blue.y, SCALE, JOINT_COLOR);
    }
    renderer.end();
  }

  /*private void drawCage(ShapeRenderer renderer) {
    renderer.setColor(CAGE_COLOR);
    renderer.begin(ShapeRenderer.ShapeType.Line);
    renderer.rect(-world.size.x, -world.size.y, world.size.x * 2f, world.size.y * 2f);
    renderer.end();
  }*/

  private void drawOutlinedDot(ShapeRenderer renderer, float x, float y, float size, Color color) {
    renderer.setColor(Color.BLACK);
    renderer.circle(x, y, (size + 2f) / 2f);
    renderer.setColor(color);
    renderer.circle(x, y, size / 2f);
  }

  private void drawLine(ShapeRenderer renderer, float ax, float ay, float bx, float by, float width, Color color) {
    renderer.setColor(color);
    renderer.rectLine(ax, ay, bx, by, width);
    renderer.circle(ax, ay, width / 2f);
    renderer.circle(bx, by, width / 2f);
  }

  private void drawOutlinedLine(ShapeRenderer renderer, float ax, float ay, float bx, float by, float width, Color color) {
    drawLine(renderer, ax, ay, bx, by, width + 2f, Color.BLACK);
    drawLine(renderer, ax, ay, bx, by, width, color);
  }

}
