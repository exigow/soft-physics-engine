package demos.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import demos.utils.rendering.ElementColor;
import engine.Particle;
import engine.Simulator;
import engine.joints.AngleJoint;
import engine.joints.Joint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;
import org.joml.Vector2f;

import java.util.Collection;

import static demos.utils.rendering.ElementColor.*;
import static demos.utils.rendering.RenderUtils.*;

public class WorldDebugRenderer {

  public static void render(Simulator simulator, Matrix4 matrix) {
    fillBackground(BACKGROUND.color);
    SHAPE_RENDERER.setProjectionMatrix(matrix);
    renderJoints(simulator.joints);
    renderParticles(simulator.particles);
  }

  private static void renderParticles(Collection<Particle> particles) {
    SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
    for (Particle particle : particles)
      renderPoint(particle.pos, 5, ElementColor.SHAPE.color);
    SHAPE_RENDERER.end();
  }

  private static final Color MUTABLE_TENSION_COLOR = new Color();
  private static void renderJoints(Collection<Joint> joints) {
    SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
    for (Joint joint : joints) {
      if (joint instanceof PinJoint) {
        PinJoint pin = (PinJoint) joint;
        renderPoint(pin.where, 13, PIN_JOINT.color);
        renderLine(pin.where, pin.which.pos, 7, PIN_JOINT.color);
      }
      if (joint instanceof SpringJoint) {
        SpringJoint spring = (SpringJoint) joint;
        float tension = Math.min(tensionOf(spring), 1);
        MUTABLE_TENSION_COLOR.set(SPRING_JOINT.color.r + tension, SPRING_JOINT.color.g - tension * .5f, SPRING_JOINT.color.b - tension, SPRING_JOINT.color.a);
        renderLine(spring.from.pos, spring.to.pos, 2, MUTABLE_TENSION_COLOR);
      }
      if (joint instanceof AngleJoint) {
        AngleJoint angle = (AngleJoint) joint;
        Vector2f prev = new Vector2f(angle.first.pos);
        Vector2f next = new Vector2f();
        boolean tick = false;
        for (float t = 0f; t <= 1f; t += .125f) {
          Vector2f a = angle.first.pos;
          Vector2f b = angle.middle.pos;
          Vector2f c = angle.last.pos;
          next.x = (1 - t) * (1 - t) * a.x + 2 * (1 - t) * t * b.x + t * t * c.x;
          next.y = (1 - t) * (1 - t) * a.y + 2 * (1 - t) * t * b.y + t * t * c.y;
          renderLine(prev, next, 1.5f, tick ? ANGLE_JOINT_A.color : ANGLE_JOINT_B.color);
          tick = !tick;
          prev.set(next);
        }
      }
    }
    SHAPE_RENDERER.end();
  }

  private static float tensionOf(SpringJoint joint) {
    float length = joint.from.pos.distance(joint.to.pos);
    return Math.abs(length - joint.expectedLength) / joint.expectedLength;
  }

}
