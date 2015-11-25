package demos.cloth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import demos.Demo;
import demos.utils.DemoTextureLoader;
import org.joml.Vector2f;

public class ClothDemo extends Demo {

  private final PolygonSpriteBatch polygonSpriteBatch = new PolygonSpriteBatch();
  private final Texture region = DemoTextureLoader.loadTroll();
  private final Cloth cloth = new Cloth(new Vector2f(0f, 0f), new Vector2f(512, 512), 25, .75f, region).flush(simulator);

  @Override
  public void onUpdate() {
    polygonSpriteBatch.setProjectionMatrix(camera.combined);
    polygonSpriteBatch.setColor(1f, 1f, 1f, .75f);
    cloth.draw(polygonSpriteBatch);
  }

  public static void main(String[] args) {
    Initializer.initializeLazy(ClothDemo::new);
  }

}
