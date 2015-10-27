package demos.tree;

import demos.Demo;
import engine.Particle;
import engine.joints.SpringJoint;
import org.joml.Matrix4f;
import org.joml.MatrixStack;
import org.joml.Vector3f;

public class TreeDemo extends Demo {

  private final MatrixStack stack = new MatrixStack(6);
  private final Matrix4f result = new Matrix4f();
  private int depth = 0;

  {
    stack.translate(-256, 0, 0);
    applyFunction();
  }

  private void applyFunction() {
    if (depth > 4)
      return;
    //put(96, .65f, -.45f);
    //put(128, .85f, 0);
    //put(96, .65f, .45f);

    put(128, .75f, .45f);
    put(128, .75f, -.45f);
  }

  private void put(float displacement, float scale, float angle) {
    stack.pushMatrix();
    stack.get(result);

    Vector3f a = new Vector3f(0, 0, 0).mulProject(result);
    Vector3f b = new Vector3f(displacement, 0, 0).mulProject(result);
    Particle particleA = Particle.on(a.x, a.y);
    Particle particleB = Particle.on(b.x, b.y);
    world.particles.add(particleA);
    world.particles.add(particleB);
    world.joints.add(new SpringJoint(particleA, particleB, .1f));

    stack.translate(displacement, 0, 0);
    stack.scale(scale, scale, 1);
    stack.rotate(angle, 0, 0, 1);
    depth += 1;
    applyFunction();
    depth -= 1;
    stack.popMatrix();
  }

  public static void main(String[] args) {
    Initializer.initializeLazy(TreeDemo::new);
  }

}
