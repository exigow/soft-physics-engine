package demos.utils.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
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
    SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
    renderJoints(simulator.joints);
    renderParticles(simulator.particles);
    SHAPE_RENDERER.end();
  }

  private static void renderParticles(Collection<Particle> particles) {
    for (Particle particle : particles)
      renderPoint(particle.pos, 5, SHAPE.color);
  }

  private static void renderJoints(Collection<Joint> joints) {
    for (Joint joint : joints) {
      if (joint instanceof PinJoint)
        renderPinJoint((PinJoint) joint);
      if (joint instanceof SpringJoint)
        renderSpringJojnt((SpringJoint) joint);
      if (joint instanceof AngleJoint)
        renderAngleJoint((AngleJoint) joint);
    }
  }

  private static void renderPinJoint(PinJoint pin) {
    renderPoint(pin.where, 13, PIN_JOINT.color);
    renderLine(pin.where, pin.which.pos, 7, PIN_JOINT.color);
  }

  private static final Color MUTABLE_TENSION_COLOR = new Color();
  private static void renderSpringJojnt(SpringJoint joint) {
    float tension = Math.min(tensionOf(joint), 1);
    MUTABLE_TENSION_COLOR.set(SPRING_JOINT.color.r + tension, SPRING_JOINT.color.g - tension * .5f, SPRING_JOINT.color.b - tension, SPRING_JOINT.color.a);
    renderLine(joint.from.pos, joint.to.pos, 2, MUTABLE_TENSION_COLOR);
  }

  private static void renderAngleJoint(AngleJoint joint) {
    Vector2f prev = new Vector2f(joint.first.pos);
    boolean tick = false;
    for (float slide = 0f; slide <= 1f; slide += .125f) {
      Vector2f computed = curvePoint(joint.first.pos, joint.middle.pos, joint.last.pos, slide);
      renderLine(prev, computed, 1.5f, tick ? ANGLE_JOINT_A.color : ANGLE_JOINT_B.color);
      tick = !tick;
      prev.set(computed);
    }
  }

  private static Vector2f curvePoint(Vector2f from, Vector2f control, Vector2f to, float slide) {
    float reversed = (1 - slide);
    return new Vector2f(
      reversed * reversed * from.x + 2 * reversed * slide * control.x + slide * slide * to.x,
      reversed * reversed * from.y + 2 * reversed * slide * control.y + slide * slide * to.y
    );
  }

  private static float tensionOf(SpringJoint joint) {
    float length = joint.from.pos.distance(joint.to.pos);
    return Math.abs(length - joint.expectedLength) / joint.expectedLength;
  }

}
