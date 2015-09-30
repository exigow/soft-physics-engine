package demos.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class DemoTextureLoader {

  public static Texture loadTroll() {
    Texture texture = new Texture(Gdx.files.internal("data/troll.png"));
    texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    return texture;
  }

}
