package lsystem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.joml.Matrix4f;
import org.joml.MatrixStack;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class HardcodedLSystemTest implements ApplicationListener {

  private final MatrixStack stack = new MatrixStack(8);
  private final Matrix4f result = new Matrix4f();
  private int depth = 4;

  @Override
  public void create() {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-256, 256, -256, 256, 1, -1);
    glMatrixMode(GL_MODELVIEW);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    stack.pushMatrix();
    stack.translate(-256, 0, 0);
    applyFunction();
    stack.popMatrix();
  }

  private void applyFunction() {
    if (depth < 0)
      return;
    put(96, .65f, -.45f);
    put(128, .85f, 0);
    put(96, .65f, .45f);
  }

  private void put(float displacement, float scale, float angle) {
    stack.pushMatrix();
    stack.get(result);
    drawLine(displacement, result);
    stack.translate(displacement, 0, 0);
    stack.scale(scale, scale, 1);
    stack.rotate(angle, 0, 0, 1);
    --depth;
    applyFunction();
    ++depth;
    stack.popMatrix();
  }

  private static Vector3f a = new Vector3f(), b = new Vector3f();
  private static void drawLine(float length, Matrix4f matrix) {
    a.set(0, 0, 0).mulProject(matrix);
    b.set(length, 0, 0).mulProject(matrix);
    glColor3f(.5f, .5f, 1f);
    glBegin(GL_LINES);
    glVertex2f(a.x, a.y);
    glVertex2f(b.x, b.y);
    glEnd();
  }

  @Override
  public void render() {
  }

  public static void main(String[] args) throws Exception {
    new LwjglApplication(new HardcodedLSystemTest(), new LwjglApplicationConfiguration() {{
      title = "demo";
      width = 768;
      height = 768;
      resizable = false;
      samples = 8;
    }});
  }

  @Override
  public void resize(int i, int i1) {
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

}
