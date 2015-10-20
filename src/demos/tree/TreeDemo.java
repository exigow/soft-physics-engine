package demos.tree;

import demos.Demo;
import engine.Particle;
import engine.joints.AngleJoint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TreeDemo extends Demo {

  {
    SpringJoint branchJoint = addMainBranch();
    addBranches(addBranches(growth(branchJoint)));
  }

  private Collection<SpringJoint> addBranches(Collection<SpringJoint> joints) {
    Collection<SpringJoint> result = new ArrayList<>();
    for (SpringJoint joint : joints)
      result.addAll(growth(joint));
    return result;
  }

  private SpringJoint addMainBranch() {
    float startingHeight = -128;
    Particle start = Particle.on(0, startingHeight);
    world.particles.add(start);
    Particle middle = Particle.on(0, startingHeight + 32);
    world.particles.add(middle);
    Particle end = Particle.on(0, startingHeight + 64);
    world.particles.add(end);
    SpringJoint toMiddle = new SpringJoint(start, middle, 4, 32);
    SpringJoint fromMiddle =new SpringJoint(middle, end, 4, 32);
    world.joints.add(toMiddle);
    world.joints.add(fromMiddle);
    world.joints.add(PinJoint.pin(start));
    world.joints.add(PinJoint.pin(middle));
    world.joints.add(new AngleJoint(start, middle, end, 1, 0f));
    return fromMiddle;
  }

  private Collection<SpringJoint> growth(SpringJoint joint) {
    return Arrays.asList(
      addBranch(joint, -.35f),
      addBranch(joint, .35f)
    );
  }

  private SpringJoint addBranch(SpringJoint joint, float angle) {
    Particle left = Particle.onZero();
    world.particles.add(left);
    SpringJoint leftBranch = connect(joint.to, left);
    world.joints.add(leftBranch);
    world.joints.add(new AngleJoint(joint.from, joint.to, left, .5f, angle));
    return leftBranch;
  }

  private static SpringJoint connect(Particle a, Particle b) {
    return new SpringJoint(a, b, 1.25f, 64);
  }

  public static void main(String[] args) {
    Initializer.initializeLazy(TreeDemo::new);
  }

}
