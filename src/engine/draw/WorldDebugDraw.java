package engine.draw;

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

public class WorldDebugDraw {

  private final static float SCALE = 2f;
  private final static float ALPHA = 1f;
  private final static Color
    OUTLINE_COLOR = new Color(.223529412f, .258823529f, .278431373f, ALPHA),
    JOINT_COLOR = new Color(.643137255f, .807843137f, .227450981f, ALPHA),
    SHAPE_COLOR = new Color(.785156252f, .854901961f, .160784314f, ALPHA);
  public final static Color
    BACKGROUND_COLOR = new Color(.454901961f, .541176471f, .592156863f, ALPHA);

  private final static ShapeRenderer renderer = new ShapeRenderer();

  public static void draw(World world, Matrix4 matrix) {
    Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
    renderer.setProjectionMatrix(matrix);
    drawJoints(world.joints);
    drawParticles(world.particles);
  }

  private static void drawParticles(Collection<Particle> particles) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Particle particle : particles)
      drawOutlinedDot(particle, SCALE * 2.5f, SHAPE_COLOR, OUTLINE_COLOR);
    renderer.end();
  }

  private static final Color color = new Color();
  private static void drawJoints(Collection<Joint> constraints) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Joint joint : constraints) {
      float tension = Math.min(joint.getTension(), 1f);
      color.set(JOINT_COLOR.r + tension, JOINT_COLOR.g - tension * .5f, JOINT_COLOR.b - tension, JOINT_COLOR.a);
      drawOutlinedLine(joint.red, joint.blue, SCALE, color, OUTLINE_COLOR);
    }
    renderer.end();
  }

  private static void drawDot(Vector point, float size, Color color) {
    renderer.setColor(color);
    renderer.circle(point.x, point.y, size / 2f);
  }

  private static void drawOutlinedDot(Vector point, float size, Color color, Color outlineColor) {
    drawDot(point, size + 2f, outlineColor);
    drawDot(point, size, color);
  }

  private static void drawLine(Vector a, Vector b, float width, Color color) {
    renderer.setColor(color);
    renderer.rectLine(a.x, a.y, b.x, b.y, width);
    renderer.circle(a.x, a.y, width / 2f);
    renderer.circle(b.x, b.y, width / 2f);
  }

  private static void drawOutlinedLine(Vector a, Vector b, float width, Color color, Color outlineColor) {
    drawLine(a, b, width + 2f, outlineColor);
    drawLine(a, b, width, color);
  }

}
