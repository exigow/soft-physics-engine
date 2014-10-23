package softsys.draw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Shapes {

  private final ShapeRenderer renderer;

  public Shapes(ShapeRenderer renderer) {
    this.renderer = renderer;
  }

  public void drawDot(float x, float y, float size, Color color) {
    renderer.setColor(color);
    renderer.circle(x, y, size / 2f);
  }

  public void drawOutlinedDot(float x, float y, float size, Color color, Color outlineColor) {
    drawDot(x, y, size + 2f, outlineColor);
    drawDot(x, y, size, color);
  }

  public void drawLine(float ax, float ay, float bx, float by, float width, Color color) {
    renderer.setColor(color);
    renderer.rectLine(ax, ay, bx, by, width);
    renderer.circle(ax, ay, width / 2f);
    renderer.circle(bx, by, width / 2f);
  }

  public void drawOutlinedLine(float ax, float ay, float bx, float by, float width, Color color, Color outlineColor) {
    drawLine(ax, ay, bx, by, width + 2f, outlineColor);
    drawLine(ax, ay, bx, by, width, color);
  }

}
