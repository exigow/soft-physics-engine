import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Tissues implements ApplicationListener {

  @Override
  public void create() {
  }

  @Override
  public void resize(int i, int i2) {
  }

  @Override
  public void render() {
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
