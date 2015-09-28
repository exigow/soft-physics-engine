package demos.cloth;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import demos.utils.DefaultConfig;
import demos.utils.FingerProcessor;
import demos.utils.WorldDebugRenderer;
import engine.World;
import org.joml.Vector2f;

public class ClothDemo implements ApplicationListener {

  private OrthographicCamera camera;
  private final World world = new World();
  private PolygonSpriteBatch polygonSpriteBatch;
  private Cloth cloth;
  private final FingerProcessor processor = FingerProcessor.withFingerCount(4);

  @Override
  public void create() {
    Vector2f size = new Vector2f(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    polygonSpriteBatch = new PolygonSpriteBatch();
    cloth = new Cloth(new Vector2f(0f, 0f), new Vector2f(512, 512), 25, .75f, createRegion()).flush(world);
  }

  @Override
  public void render() {
    processor.update(camera, world);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 4);
    WorldDebugRenderer.render(world, camera.combined);
    polygonSpriteBatch.setProjectionMatrix(camera.combined);
    polygonSpriteBatch.setColor(1f, 1f, 1f, .75f);
    cloth.draw(polygonSpriteBatch);
  }

  private static TextureRegion createRegion() {
    Texture texture = new Texture(Gdx.files.internal("data/troll.png"));
    texture.setFilter(Texture.TextureFilter.Linear,  Texture.TextureFilter.Linear);
    return new TextureRegion(texture);
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
    new LwjglApplication(new ClothDemo(), DefaultConfig.create());
  }

}
