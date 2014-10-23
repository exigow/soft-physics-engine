import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.lwjgl.opengl.GL11;
import softsys.Vector;
import softsys.World;
import softsys.draw.WorldDebugDraw;

import java.util.ArrayList;
import java.util.Collection;

public class Application implements ApplicationListener {

  private OrthographicCamera camera;
  private final World world = new World();
  WorldDebugDraw worldDebugDraw;
  private PolygonSpriteBatch polygonSpriteBatch;
  private Tissue tissue;
  public static BitmapFont debugFont;
  private final Collection<FingerProcessor> processors = new ArrayList<FingerProcessor>() {{
    for (int i = 0; i < 4; i++)
      add(new FingerProcessor(i));
  }};

  @Override
  public void create() {
    Vector size = new Vector(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    worldDebugDraw = new WorldDebugDraw(world);
    polygonSpriteBatch = new PolygonSpriteBatch();
    TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("data/troll.png")));
    debugFont = new BitmapFont(Gdx.files.internal("data/arial_16px.fnt"), Gdx.files.internal("data/arial_16px_0.png"), false);
    tissue = new Tissue(new Vector(0f, 0f), new Vector(512, 512), 8, .75f, textureRegion).flush(world);
  }

  @Override
  public void render() {
    for (FingerProcessor processor : processors)
      processor.update(camera, world.particles);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 4);
    Gdx.gl.glClearColor(.454901961f, .541176471f, .592156863f, 1);
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
    polygonSpriteBatch.setProjectionMatrix(camera.combined);
    tissue.draw(polygonSpriteBatch);
    worldDebugDraw.draw(camera.combined);
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
