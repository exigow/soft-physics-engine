package demos.rope;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Matrix4;
import org.joml.Vector2f;

import java.util.Iterator;
import java.util.List;

public class RopeRenderer {

  private final ImmediateModeRenderer20 renderer = new ImmediateModeRenderer20(false, true, 1);

  public void renderRope(Matrix4 matrix, List<Vector2f> positions, Texture tex) {
    float alpha = .75f;
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    tex.bind();
    renderer.begin(matrix, GL20.GL_TRIANGLE_STRIP);
    Iterator<Vector2f> iterator = positions.iterator();
    Vector2f previous = iterator.next();
    Vector2f perpendicular = new Vector2f();
    float step = 0f;
    while (iterator.hasNext()) {
      Vector2f next = iterator.next();
      step += .25f;
      perpendicular.set(next).sub(previous).normalize().perpendicular().mul(32);
      renderer.texCoord(1, step);
      renderer.color(1, 1, 1, alpha);
      renderer.vertex(next.x + perpendicular.x, next.y + perpendicular.y, 0);
      renderer.texCoord(0, step);
      renderer.color(1, 1, 1, alpha);
      renderer.vertex(next.x - perpendicular.x, next.y - perpendicular.y, 0);
      previous = next;
    }
    renderer.end();
  }

}
