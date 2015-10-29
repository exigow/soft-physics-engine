package demos.utils.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

  public final static ShapeRenderer SHAPE_RENDERER = new ShapeRenderer();

  public static void renderPoint(Vector2f point, float size, Color color) {
    SHAPE_RENDERER.setColor(ElementColor.OUTLINE.color);
    SHAPE_RENDERER.circle(point.x, point.y, size / 2f + 2);
    SHAPE_RENDERER.setColor(color);
    SHAPE_RENDERER.circle(point.x, point.y, size / 2f);
  }

  public static void renderLine(Vector2f a, Vector2f b, float width, Color color) {
    SHAPE_RENDERER.setColor(ElementColor.OUTLINE.color);
    SHAPE_RENDERER.rectLine(a.x, a.y, b.x, b.y, width + 2);
    SHAPE_RENDERER.setColor(color);
    SHAPE_RENDERER.rectLine(a.x, a.y, b.x, b.y, width);
  }

  public static void fillBackground(Color color) {
    Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
  }

}
