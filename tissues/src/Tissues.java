import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.lwjgl.opengl.GL11;
import softsys.DebugDraw;
import softsys.Vector;
import softsys.World;

import java.util.ArrayList;
import java.util.Collection;

public class Tissues implements ApplicationListener {

  private OrthographicCamera camera;
  private final World world = new World();
  private DebugDraw worldDebugDraw = new DebugDraw(world);
  private ShapeRenderer shapeRenderer;
  private PolygonSpriteBatch polygonSpriteBatch;
  private PolygonRegion quadRegion;
  private Tissue tissue;
  private final Collection<FingerProcessor> processors = new ArrayList<FingerProcessor>() {{
    for (int i = 0; i < 4; i++)
      add(new FingerProcessor(i));
  }};

  @Override
  public void create() {
    Vector size = new Vector(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    shapeRenderer  = new ShapeRenderer();
    polygonSpriteBatch = new PolygonSpriteBatch();
    TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("data/troll.png")));
    quadRegion = new PolygonRegion(textureRegion, new float[8], new short[] {0, 1, 2, 1, 2, 3});
    float[] coords = new float[] {0f, 0f, 1f, 0f, 0, 1f, 1f, 1f};
    for (int i = 0; i < 8; i++)
      quadRegion.getTextureCoords()[i] = coords[i];
    tissue = new Tissue(new Vector(0f, 0f), new Vector(512, 512), 16, .5f).flush(world);
  }

  @Override
  public void render() {
    for (FingerProcessor processor : processors)
      processor.update(camera, world.particles);
    camera.update();
    world.simulate(Gdx.graphics.getDeltaTime(), 4);
    Gdx.gl.glClearColor(.075f, .075f, .075f, 1f);
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
    polygonSpriteBatch.setProjectionMatrix(camera.combined);
    polygonSpriteBatch.begin();
    tissue.draw(polygonSpriteBatch, quadRegion);
    polygonSpriteBatch.end();
    shapeRenderer.setProjectionMatrix(camera.combined);
    worldDebugDraw.drawAll(shapeRenderer);
    for (FingerProcessor processor : processors)
      processor.draw(shapeRenderer);
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

  public static void main(String[] args) {
    LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
    cfg.title = "Tissues";
    cfg.width = 640;
    cfg.height = 480;
    cfg.resizable = false;
    new LwjglApplication(new Tissues(), cfg);
  }

}
