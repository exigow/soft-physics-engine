package demos.collision;

import demos.Demo;
import engine.Particle;
import engine.joints.CollisionJoint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;

import static com.badlogic.gdx.math.MathUtils.random;

public class CollisionDemo extends Demo {

  {
    //createTriangle();

    for (int i = 0; i < 16; i++) {
      Particle a = Particle.on(random(-128, 128), random(-128, 128));
      world.particles.add(a);
    }

    createPlatform();
  }

  private void createTriangle() {
    Particle a = Particle.on(-16, 0);
    world.particles.add(a);
    Particle b = Particle.on(16, 0);
    world.particles.add(b);
    Particle c = Particle.on(0, -26);
    world.particles.add(c);
    float flexibility = .5f;
    world.joints.add(new SpringJoint(a, b, flexibility));
    world.joints.add(new SpringJoint(b, c, flexibility));
    world.joints.add(new SpringJoint(c, a, flexibility));
  }

  private void createPlatform() {
    Particle a = createSoftPin(-256, -128);
    Particle b = createSoftPin(256, -128 - 8);
    world.joints.add(new CollisionJoint(a, b));
    world.joints.add(new SpringJoint(a, b, .125f));
  }

  private Particle createSoftPin(float x, float y) {
    Particle pin = Particle.on(x, y);
    world.particles.add(pin);
    Particle soft = Particle.on(x, y);
    world.particles.add(soft);
    world.joints.add(PinJoint.pin(pin));
    world.joints.add(new SpringJoint(pin, soft, .125f, 16));
    return soft;
  }

  public static void main(String[] args) {
    Initializer.initializeLazy(CollisionDemo::new);
  }

}
