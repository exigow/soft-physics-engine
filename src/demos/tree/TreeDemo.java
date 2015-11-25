package demos.tree;

import demos.Demo;
import engine.Particle;
import engine.joints.AngleJoint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;
import org.joml.Matrix4f;
import org.joml.MatrixStack;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class TreeDemo extends Demo {

  private final MatrixStack stack = new MatrixStack(6);
  private final Matrix4f result = new Matrix4f();
  private int depth = 0;

  {
    float verticalDisplacement = -256;
    Particle zero = Particle.on(0, verticalDisplacement);
    simulator.particles.add(zero);
    simulator.joints.add(PinJoint.pinToActualPlace(zero));

    Particle plus = Particle.on(0, verticalDisplacement + 128);
    simulator.particles.add(plus);
    simulator.joints.add(PinJoint.pinToActualPlace(plus));

    stack.translate(0, verticalDisplacement, 0);
    applyFunction(plus, zero);
  }

  private void applyFunction(Particle previous, Particle veryPrevious) {
    float scale = 1f;
    put(96, scale, .75f, previous, veryPrevious);
    put(128, scale, 0, previous, veryPrevious);
    put(96, scale, -.75f, previous, veryPrevious);
  }

  private void put(float displacement, float scale, float angle, Particle previous, Particle veryPrevious) {
    if (depth > 2)
      return;

    stack.pushMatrix();

    stack.translate(0, displacement, 0);
    stack.scale(scale, scale, 1);
    stack.rotate(angle, 0, 0, 1);

    stack.get(result);

    Vector3f b = new Vector3f(0, displacement, 0).mulProject(result);
    Particle particleB = Particle.on(b.x, b.y);
    simulator.particles.add(particleB);
    if (previous != null) {
      float var = (depth + 1);
      float powered = var * var * var;
      float stiffness = .875f / powered;
      simulator.joints.add(SpringJoint.connect(previous, particleB, stiffness));
      if (veryPrevious != null) {
        float desiredAngle = -AngleJoint.curvatureBetween(
          new Vector2f(veryPrevious.pos.x, veryPrevious.pos.y),
          new Vector2f(previous.pos.x, previous.pos.y),
          new Vector2f(particleB.pos.x, particleB.pos.y)
        );
        simulator.joints.add(AngleJoint.connect(veryPrevious, previous, particleB, stiffness, desiredAngle));
      }
    }

    depth += 1;
    applyFunction(particleB, previous);
    depth -= 1;

    stack.popMatrix();
  }

  public static void main(String[] args) {
    GdxInitializer.initializeLazy(TreeDemo::new);
  }

}
