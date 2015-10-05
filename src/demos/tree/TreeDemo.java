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
import engine.joints.SpringJoint;

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
    Collection<SpringJoint> joints = addBranches(branchJoint);
    joints.forEach(this::addBranches);
  }

  public void render() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 32);
    WorldDebugRenderer.render(world, camera.combined);
  }

  private SpringJoint addMainBranch() {
    Particle start = Particle.on(0, 0);
    world.particles.add(start);
    Particle end = Particle.on(0, 64);
    world.particles.add(end);
    SpringJoint branch = connect(start, end);
    world.joints.add(branch);
    return branch;
  }

  private Collection<SpringJoint> addBranches(SpringJoint joint) {
    Particle previousEnd = joint.to;
    Particle left = Particle.on(previousEnd.pos.x + 32, previousEnd.pos.y + 64);
    world.particles.add(left);
    SpringJoint leftBranch = connect(previousEnd, left);
    world.joints.add(leftBranch);
    Particle right = Particle.on(previousEnd.pos.x - 32, previousEnd.pos.y + 64);
    world.particles.add(right);
    SpringJoint rightBranch = connect(previousEnd, right);
    world.joints.add(rightBranch);
    return Arrays.asList(leftBranch, rightBranch);
  }

  private static SpringJoint connect(Particle a, Particle b) {
    return new SpringJoint(a, b, .5f);
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
