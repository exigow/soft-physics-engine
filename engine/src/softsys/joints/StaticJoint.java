package softsys.joints;

import softsys.Particle;

public class StaticJoint extends Joint {

  public StaticJoint(Particle red, Particle blue) {
    super(red, blue);
  }

  @Override
  public void relax(float delta) {

  }

}
