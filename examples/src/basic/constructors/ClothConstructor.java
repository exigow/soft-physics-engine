package basic.constructors;


import softsys.Particle;
import softsys.Vector;
import softsys.joints.SpringJoint;
import softsys.joints.StaticJoint;

public class ClothConstructor extends Constructor {

  public ClothConstructor(Vector centerPos, Vector size, int segments, float stiffness) {
    segments += 1;
    float xStride = size.x / segments;
    float yStride = size.y / segments;
    for (int y = 0; y < segments; y++) {
      for (int x = 0; x < segments; x++) {
        float px = centerPos.x + x * xStride - size.x / 2f + xStride / 2f,
          py = centerPos.y + y * yStride - size.y / 2f + yStride / 2f;
        particles.add(new Particle(px, py));
        if (x > 0)
          joints.add(new SpringJoint(particles.get(y * segments + x), particles.get(y * segments + x - 1), stiffness));
        if (y > 0)
          joints.add(new SpringJoint(particles.get(y * segments + x), particles.get((y - 1) * segments + x), stiffness));
      }
    }

    int step = 4;
    for (int i = 0; i < segments; i += step)
      joints.add(new StaticJoint(particles.get(segments * segments - i - 1)));
  }

}
