package demos.cloth;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import demos.utils.DefaultConfig;
import engine.Vector;
import engine.World;
import demos.utils.WorldDebugRenderer;
import demos.utils.FingerProcessor;

public class ClothDemo implements ApplicationListener {

  private OrthographicCamera camera;
  private final World world = new World();
  private PolygonSpriteBatch polygonSpriteBatch;
  private Cloth cloth;
  public static BitmapFont debugFont;
  private final FingerProcessor processor = new FingerProcessor(4);

  @Override
  public void create() {
    Vector size = new Vector(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    polygonSpriteBatch = new PolygonSpriteBatch();
    TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("data/troll.png")));
    debugFont = new BitmapFont(Gdx.files.internal("data/arial_16px.fnt"), Gdx.files.internal("data/arial_16px_0.png"), false);
    cloth = new Cloth(new Vector(0f, 0f), new Vector(512, 512), 25, .75f, textureRegion).flush(world);
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
