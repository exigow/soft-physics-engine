package main.constructors;

import softsys.Particle;
import softsys.Vector;
import softsys.joints.AngleJoint;
import softsys.joints.SpringJoint;
import softsys.joints.StaticJoint;

public class PlantConstructor extends Constructor {

  public PlantConstructor() {
    int i = 0;
    for (; i < 8; i++) {
      Particle particle = new Particle(i * 32f, 0);
      particles.add(particle);
      if (i > 0) {
        joints.add(new SpringJoint(particles.get(i - 1), particles.get(i), .875f));
        joints.add(new AngleJoint(particles.get(i - 1), particles.get(i)));
      }
    }
    joints.add(new StaticJoint(particles.get(0), new Vector()));
  }

}
