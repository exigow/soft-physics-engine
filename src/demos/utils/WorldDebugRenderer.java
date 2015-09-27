package demos.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import engine.Particle;
import engine.Vector;
import engine.World;
import engine.joints.Joint;
import org.lwjgl.opengl.GL11;

import java.util.Collection;

public class WorldDebugRenderer {

  private final static Color OUTLINE_COLOR = new Color(.223529412f, .258823529f, .278431373f, 1f);
  private final static Color JOINT_COLOR = new Color(.643137255f, .807843137f, .227450981f, 1f);
  private final static Color SHAPE_COLOR = new Color(.785156252f, .854901961f, .160784314f, 1);
  public final static Color BACKGROUND_COLOR = new Color(.454901961f, .541176471f, .592156863f, 1f);

  private final static ShapeRenderer renderer = new ShapeRenderer();

  public static void render(World world, Matrix4 matrix) {
    Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
    renderer.setProjectionMatrix(matrix);
    renderJoints(world.joints);
    renderParticles(world.particles);
  }

  private static void renderParticles(Collection<Particle> particles) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Particle particle : particles)
      renderOutlinedDot(particle, 5f, SHAPE_COLOR, OUTLINE_COLOR);
    renderer.end();
  }

  private static final Color color = new Color();
  private static void renderJoints(Collection<Joint> constraints) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Joint joint : constraints) {
      float tension = Math.min(joint.getTension(), 1f);
      color.set(JOINT_COLOR.r + tension, JOINT_COLOR.g - tension * .5f, JOINT_COLOR.b - tension, JOINT_COLOR.a);
      renderOutlinedLine(joint.red, joint.blue, 2f, color, OUTLINE_COLOR);
    }
    renderer.end();
  }

  private static void renderDot(Vector point, float size, Color color) {
    renderer.setColor(color);
    renderer.circle(point.x, point.y, size / 2f);
  }

  private static void renderOutlinedDot(Vector point, float size, Color color, Color outlineColor) {
    renderDot(point, size + 2f, outlineColor);
    renderDot(point, size, color);
  }

  private static void renderLine(Vector a, Vector b, float width, Color color) {
    renderer.setColor(color);
    renderer.rectLine(a.x, a.y, b.x, b.y, width);
    renderer.circle(a.x, a.y, width / 2f);
    renderer.circle(b.x, b.y, width / 2f);
  }

  private static void renderOutlinedLine(Vector a, Vector b, float width, Color color, Color outlineColor) {
    renderLine(a, b, width + 2f, outlineColor);
    renderLine(a, b, width, color);
  }

}
