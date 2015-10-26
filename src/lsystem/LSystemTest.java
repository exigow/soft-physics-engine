package lsystem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lsystem.models.GaussRandomProperties;
import org.joml.Matrix4f;
import org.joml.MatrixStack;
import org.joml.Vector3f;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.*;

public class LSystemTest implements ApplicationListener {

  private final MatrixStack stack = new MatrixStack(8);
  private final Matrix4f result = new Matrix4f();

  @Override
  public void create() {
    Element root = fileToRoot("data/test.xml");

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-256, 256, -256, 256, 1, -1);
    glMatrixMode(GL_MODELVIEW);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    stack.pushMatrix();
    stack.translate(-256, 0, 0);
    applyFunction(root, 4);
    stack.popMatrix();
  }

  private void applyFunction(Element element, int depth) {
    if (depth < 0)
      return;
    Elements products = element.select("product");
    for (Element product : products) {
      stack.pushMatrix();
      GaussRandomProperties displacement = parseGauss(product.select("displacement").first());
      GaussRandomProperties rotation = parseGauss(product.select("rotation").first());
      GaussRandomProperties scale = parseGauss(product.select("scale").first());
      float displacementValue = displacement.nextValue();
      stack.get(result);
      drawLine(displacementValue, result);
      stack.translate(displacementValue, 0, 0);
      float scaleValue = scale.nextValue();
      stack.scale(scaleValue, scaleValue, 1);
      stack.rotate(rotation.nextValue(), 0, 0, 1);
      applyFunction(element, --depth);
      depth += 1;
      stack.popMatrix();
    }
  }

  private static GaussRandomProperties parseGauss(Element from) {
    GaussRandomProperties gaussRandomProperties = new GaussRandomProperties();
    gaussRandomProperties.mean = Float.parseFloat(from.attr("mean"));
    gaussRandomProperties.variance = Float.parseFloat(from.attr("variance"));
    return gaussRandomProperties;
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

  private static Element fileToRoot(String filename) {
    byte[] encoded;
    try {
      encoded = Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String content;
    try {
      content = new String(encoded, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return Jsoup.parse(content, "", Parser.xmlParser());
  }

  public static void main(String[] args) throws Exception {
    new LwjglApplication(new LSystemTest(), new LwjglApplicationConfiguration() {{
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
