package main.constructors;

import softsys.Particle;
import softsys.World;
import softsys.joints.Joint;

import java.util.ArrayList;
import java.util.List;

public abstract class Constructor {

  protected List<Particle> localParticles = new ArrayList<Particle>();
  protected List<Joint> localJoints = new ArrayList<Joint>();

  public final void flush(World world) {
    world.joints.addAll(localJoints);
    world.particles.addAll(localParticles);
  }

}
