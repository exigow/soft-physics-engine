package lsystem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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

  @Override
  public void create() {
    Element root = fileToRoot("data/test.xml");

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-256, 256, -256, 256, 1, -1);
    glMatrixMode(GL_MODELVIEW);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glPushMatrix();
    glRotatef(90, 0, 0, 1);
    applyFunction(root, 5);
    glPopMatrix();
  }

  private static void applyFunction(Element element, int depth) {
    if (depth < 0)
      return;
    Elements products = element.select("product");
    for (Element product : products) {
      glPushMatrix();
      float length = Float.parseFloat(product.select("translate").first().text());
      float rotation = Float.parseFloat(product.select("rotate").first().text());
      float scale = Float.parseFloat(product.select("scale").first().text());
      glTranslatef(length, 0, 0);
      glScalef(scale, scale, 1);
      glRotatef(rotation, 0, 0, 1);
      drawLine(length);
      applyFunction(element, --depth);
      depth += 1;
      glPopMatrix();
    }
  }

  private static void drawLine(float length) {
    glColor3f(.5f, .5f, 1f);
    glBegin(GL_LINES);
    glVertex2f(0, 0);
    glVertex2f(length, 0);
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
