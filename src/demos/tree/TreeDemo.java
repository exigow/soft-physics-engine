package demos.tree;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import demos.utils.DefaultConfig;
import demos.utils.FingerProcessor;
import demos.utils.WorldDebugRenderer;
import engine.Particle;
import engine.World;
import engine.joints.AngleJoint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TreeDemo implements ApplicationListener {

  private OrthographicCamera camera;
  private World world = new World();
  private final FingerProcessor processor = FingerProcessor.withFingerCount(4);

  @Override
  public void create() {
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    SpringJoint branchJoint = addMainBranch();
    addBranches(addBranches(addBranch(branchJoint)));
  }

  private Collection<SpringJoint> addBranches(Collection<SpringJoint> joints) {
    Collection<SpringJoint> result = new ArrayList<>();
    for (SpringJoint joint : joints)
      result.addAll(addBranch(joint));
    return result;
  }

  public void render() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 32);
    WorldDebugRenderer.render(world, camera.combined);
  }

  private SpringJoint addMainBranch() {
    Particle start = Particle.onZero();
    world.particles.add(start);
    Particle end = Particle.on(0, 64);
    world.particles.add(end);
    SpringJoint branch = connect(start, end);
    world.joints.add(branch);
    world.joints.add(PinJoint.pin(start));
    world.joints.add(PinJoint.pin(end));
    return branch;
  }

  private Collection<SpringJoint> addBranch(SpringJoint joint) {
    Particle left = Particle.onZero();
    world.particles.add(left);
    SpringJoint leftBranch = connect(joint.to, left);
    world.joints.add(leftBranch);
    world.joints.add(new AngleJoint(joint.from, joint.to, left, .5f, .35f));
    Particle right = Particle.onZero();
    world.particles.add(right);
    SpringJoint rightBranch = connect(joint.to, right);
    world.joints.add(rightBranch);
    world.joints.add(new AngleJoint(joint.from, joint.to, right, .5f, -.35f));
    return Arrays.asList(leftBranch, rightBranch);
  }

  private static SpringJoint connect(Particle a, Particle b) {
    return new SpringJoint(a, b, .75f, 64);
  }

  @Override
  public void resize(int w, int h) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void dispose() {
  }

  public static void main(String[] args) {
    new LwjglApplication(new TreeDemo(), DefaultConfig.create());
  }

}
