package engine.draw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import org.lwjgl.opengl.GL11;
import engine.Particle;
import engine.Vector;
import engine.World;
import engine.collisions.Collider;
import engine.joints.Joint;

import java.util.Collection;

public class WorldDebugDraw {

  private final static float SCALE = 2f;
  private final static float ALPHA = 1f;
  private final static Color
    OUTLINE_COLOR = new Color(.223529412f, .258823529f, .278431373f, ALPHA),
    JOINT_COLOR = new Color(.643137255f, .807843137f, .227450981f, ALPHA),
    SHAPE_COLOR = new Color(.785156252f, .854901961f, .160784314f, ALPHA),
    COLLIDER_COLOR = new Color(1f, 0f, 0f, ALPHA);
  public final static Color
    BACKGROUND_COLOR = new Color(.454901961f, .541176471f, .592156863f, ALPHA);

  private final World world;
  private final Shapes shapes;
  private final ShapeRenderer renderer = new ShapeRenderer();

  public WorldDebugDraw(World world) {
    this.world = world;
    this.shapes = new Shapes(renderer);
  }

  public void draw(Matrix4 matrix) {
    Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
    renderer.setProjectionMatrix(matrix);
    drawColliders(world.colliders);
    drawJoints(world.joints);
    drawParticles(world.particles);
  }

  private void drawParticles(Collection<Particle> particles) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Particle particle : particles)
      shapes.drawOutlinedDot(particle, SCALE * 2.5f, SHAPE_COLOR, OUTLINE_COLOR);
    renderer.end();
  }

  private void drawColliders(Collection<Collider> particles) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Collider collider : particles) {
      shapes.drawOutlinedLine(collider.a, collider.b, SCALE, COLLIDER_COLOR, OUTLINE_COLOR);
      Vector center = collider.getCenter();
      shapes.drawOutlinedDot(center, SCALE * 3f, COLLIDER_COLOR, OUTLINE_COLOR);
      //Vector normal = collider.getNormal();
      //shapes.drawOutlinedLine(collider.a.x, collider.a.y, collider.b.x, collider.b.y, SCALE, COLLIDER_COLOR, OUTLINE_COLOR);
    }
    renderer.end();
  }

  private final Color color = new Color();
  private void drawJoints(Collection<Joint> constraints) {
    renderer.begin(ShapeRenderer.ShapeType.Filled);
    for (Joint joint : constraints) {
      float tension = Math.min(joint.getTension(), 1f);
      color.set(JOINT_COLOR.r + tension, JOINT_COLOR.g - tension * .5f, JOINT_COLOR.b - tension, JOINT_COLOR.a);
      shapes.drawOutlinedLine(joint.red, joint.blue, SCALE, color, OUTLINE_COLOR);
    }
    renderer.end();
  }

}
