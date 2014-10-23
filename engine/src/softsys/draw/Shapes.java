package softsys.draw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import softsys.Vector;

public class Shapes {

  private final ShapeRenderer renderer;

  public Shapes(ShapeRenderer renderer) {
    this.renderer = renderer;
  }

  public void drawDot(Vector point, float size, Color color) {
    renderer.setColor(color);
    renderer.circle(point.x, point.y, size / 2f);
  }

  public void drawOutlinedDot(Vector point, float size, Color color, Color outlineColor) {
    drawDot(point, size + 2f, outlineColor);
    drawDot(point, size, color);
  }

  public void drawLine(Vector a, Vector b, float width, Color color) {
    renderer.setColor(color);
    renderer.rectLine(a.x, a.y, b.x, b.y, width);
    renderer.circle(a.x, a.y, width / 2f);
    renderer.circle(b.x, b.y, width / 2f);
  }

  public void drawOutlinedLine(Vector a, Vector b, float width, Color color, Color outlineColor) {
    drawLine(a, b, width + 2f, outlineColor);
    drawLine(a, b, width, color);
  }

}
