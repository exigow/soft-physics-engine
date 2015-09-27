package demos.utils;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DefaultConfig {

  public static LwjglApplicationConfiguration instantiate() {
    LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
    cfg.title = "demo";
    cfg.width = 1280;
    cfg.height = 768;
    cfg.resizable = false;
    cfg.samples = 4;
    return cfg;
  }

}
