package demos.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import engine.Particle;
import engine.World;
import engine.joints.AngleJoint;
import engine.joints.Joint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import java.util.Collection;

public class WorldDebugRenderer {

  private final static Color OUTLINE_COLOR = new Color(.223f, .258f, .278f, 1f);
  private final static Color JOINT_COLOR = new Color(.643f, .807f, .227f, 1f);
  private final static Color SHAPE_COLOR = new Color(.785f, .854f, .160f, 1);
  private final static Color BACKGROUND_COLOR = new Color(.454f, .541f, .592f, 1f);
  private final static Color PIN_JOINT_COLOR = new Color(.733f, .329f, .458f, 1f);
  private final static Color ANGLE_JOINT_A_COLOR = new Color(.997f, .905f, .298f, 1f);
  private final static Color ANGLE_JOINT_B_COLOR = new Color(.356F, .751f, .921f, 1f);
  private final static ShapeRenderer shape = new ShapeRenderer();

  public static void render(World world, Matrix4 matrix) {
    clearBackground();
    shape.setProjectionMatrix(matrix);
    renderJoints(world.joints);
    renderParticles(world.particles);
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
  }

  private static void renderParticles(Collection<Particle> particles) {
    shape.begin(ShapeRenderer.ShapeType.Filled);
    for (Particle particle : particles)
      renderPoint(particle.pos, 5, SHAPE_COLOR);
    shape.end();
  }

  private static final Color MUTABLE_TENSION_COLOR = new Color();
  private static void renderJoints(Collection<Joint> joints) {
    shape.begin(ShapeRenderer.ShapeType.Filled);
    for (Joint joint : joints) {
      if (joint instanceof PinJoint) {
        PinJoint pin = (PinJoint) joint;
        renderPoint(pin.where, 13, PIN_JOINT_COLOR);
        renderLine(pin.where, pin.which.pos, 7, PIN_JOINT_COLOR);
      }
      if (joint instanceof SpringJoint) {
        SpringJoint spring = (SpringJoint) joint;
        float tension = Math.min(tensionOf(spring), 1);
        MUTABLE_TENSION_COLOR.set(JOINT_COLOR.r + tension, JOINT_COLOR.g - tension * .5f, JOINT_COLOR.b - tension, JOINT_COLOR.a);
        renderLine(spring.from.pos, spring.to.pos, 2, MUTABLE_TENSION_COLOR);
      }
      if (joint instanceof AngleJoint) {
        AngleJoint angle = (AngleJoint) joint;
        Vector2f prev = new Vector2f(angle.first.pos);
        Vector2f next = new Vector2f();
        boolean tick = false;
        for (float t = 0f; t <= 1f; t += .125f) {
          Vector2f a = angle.first.pos;
          Vector2f b = angle.second.pos;
          Vector2f c = angle.last.pos;
          next.x = (1 - t) * (1 - t) * a.x + 2 * (1 - t) * t * b.x + t * t * c.x;
          next.y = (1 - t) * (1 - t) * a.y + 2 * (1 - t) * t * b.y + t * t * c.y;
          renderLine(prev, next, 1.5f, tick ? ANGLE_JOINT_A_COLOR : ANGLE_JOINT_B_COLOR);
          tick = !tick;
          prev.set(next);
        }
      }
    }
    shape.end();
  }

  private static float tensionOf(SpringJoint joint) {
    float length = joint.from.pos.distance(joint.to.pos);
    return Math.abs(length - joint.expectedLength) / joint.expectedLength;
  }

  private static void renderPoint(Vector2f point, float size, Color color) {
    shape.setColor(OUTLINE_COLOR);
    shape.circle(point.x, point.y, size / 2f + 2);
    shape.setColor(color);
    shape.circle(point.x, point.y, size / 2f);
  }

  private static void renderLine(Vector2f a, Vector2f b, float width, Color color) {
    shape.setColor(OUTLINE_COLOR);
    shape.rectLine(a.x, a.y, b.x, b.y, width + 2);
    shape.setColor(color);
    shape.rectLine(a.x, a.y, b.x, b.y, width);
  }

}
