package basic;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Runner {

  public static void main(String[] args) {
    LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
    cfg.title = "";
    cfg.width = 1280;
    cfg.height = 640;
    cfg.resizable = false;
    new LwjglApplication(new Example(), cfg);
  }

}
