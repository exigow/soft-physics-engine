package demos.cloth;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import engine.Vector;
import engine.World;
import engine.draw.WorldDebugDraw;
import engine.interactionhelpers.FingerProcessor;

public class Application implements ApplicationListener {

  private OrthographicCamera camera;
  private final World world = new World();
  WorldDebugDraw worldDebugDraw;
  private PolygonSpriteBatch polygonSpriteBatch;
  private Cloth cloth;
  public static BitmapFont debugFont;
  private final FingerProcessor processor = new FingerProcessor(4);

  @Override
  public void create() {
    Vector size = new Vector(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    worldDebugDraw = new WorldDebugDraw(world);
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
    worldDebugDraw.draw(camera.combined);
    polygonSpriteBatch.setProjectionMatrix(camera.combined);
    polygonSpriteBatch.setColor(1f, 1f, 1f, .75f);
    cloth.draw(polygonSpriteBatch);
  }

  @Override
  public void resize(int i, int i2) {
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
