package demos.cloth;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Runner {

  public static void main(String[] args) {
    LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
    cfg.title = "demos.cloth.Cloth";
    cfg.width = 1280;
    cfg.height = 768;
    cfg.resizable = false;
    cfg.samples = 4;
    new LwjglApplication(new Application(), cfg);
  }

}
