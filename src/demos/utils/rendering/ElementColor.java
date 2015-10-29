package demos.utils.rendering;

import com.badlogic.gdx.graphics.Color;

enum ElementColor {

  BACKGROUND(.454f, .541f, .592f),
  SHAPE(.785f, .854f, .160f),
  SPRING_JOINT(.643f, .807f, .227f),
  PIN_JOINT(.733f, .329f, .458f),
  ANGLE_JOINT_A(.997f, .905f, .298f),
  ANGLE_JOINT_B(.356F, .751f, .921f),
  OUTLINE(.223f, .258f, .278f);

  public final Color color;

  ElementColor(float r, float g, float b) {
    float alpha = 1f;
    color  = new Color(r, g, b, alpha);
  }
  
}
