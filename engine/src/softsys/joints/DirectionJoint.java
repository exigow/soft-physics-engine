package softsys.joints;

import softsys.Particle;

public class DirectionJoint extends Joint {

  public DirectionJoint(Particle red, Particle blue) {
    super(red, blue, 0f);
  }

  @Override
  public void relax(float delta) {
    float direction = red.angleTo(blue);

  }

}
