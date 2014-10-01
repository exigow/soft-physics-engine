package main.constructors;


import softsys.Particle;
import softsys.Vector;

public class ClothConstructor extends Constructor {

  public ClothConstructor(Vector centerPos, Vector size, int segments, float stiffness) {
    Vector tex = new Vector().set(size).divide(segments);
    for (int x = 0; x < segments; ++x)
      for (int y = 0; y < segments; ++y) {
        Vector position = new Vector(x, y).mul(tex).sub(new Vector().set(size).divide(2f)).add(centerPos);
        particles.add(new Particle(position));
        //if (x > 0)
          //joints.add(new SpringJoint(particles.get(y * segments + x), particles.get(y * segments + x - 1), stiffness));
      }

    /*float xStride = width / segments;
    float yStride = height / segments;
    for (int y = 0; y < segments; ++y) {
      for (int x = 0; x < segments; ++x) {
        float px = origin.x + x * xStride - width / 2f + xStride / 2f,
          py = origin.y + y * yStride - height / 2f + yStride / 2f;
        particles.add(new Particle(px, py));
        if (x > 0)
          joints.add(new SpringJoint(particles.get(y * segments + x), particles.get(y * segments + x - 1), stiffness));
        if (y > 0)
          joints.add(new SpringJoint(particles.get(y * segments + x), particles.get((y - 1) * segments + x), stiffness));
      }
    }*/


  }

}
