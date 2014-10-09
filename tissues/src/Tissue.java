import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import softsys.Particle;
import softsys.Vector;
import softsys.World;
import softsys.joints.Joint;
import softsys.joints.SpringJoint;

import java.util.ArrayList;
import java.util.List;

public class Tissue {

  private final List<Particle> particles = new ArrayList<Particle>();
  private final List<Joint> joints = new ArrayList<Joint>();
  private final int size;

  public Tissue(Vector centerPos, Vector size, int segments, float stiffness) {
    segments += 1;
    this.size = segments;
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
  }

  public final Tissue flush(World world) {
    world.joints.addAll(joints);
    world.particles.addAll(particles);
    return this;
  }

  public void draw(PolygonSpriteBatch batch, PolygonRegion polygonRegion) {
    for (int i = 0; i < (size - 1) * (size - 1) + size - 1; i++)
      if ((i + 1) % size != 0) {
        computeQuadVerts(i);
        flushQuadVerts(polygonRegion);
        batch.draw(polygonRegion, 0f, 0f);
      }
  }

  private void flushQuadVerts(PolygonRegion region) {
    for (int i = 0; i < 8; i++)
      region.getVertices()[i] = quadVerts[i];
  }

  private float[] quadVerts = new float[8];
  private void computeQuadVerts(int i) {
    quadVerts[0] = particles.get(i + 0 + 0).x;
    quadVerts[1] = particles.get(i + 0 + 0).y;
    quadVerts[2] = particles.get(i + 0 + 1).x;
    quadVerts[3] = particles.get(i + 0 + 1).y;
    quadVerts[4] = particles.get(i + size + 0).x;
    quadVerts[5] = particles.get(i + size + 0).y;
    quadVerts[6] = particles.get(i + size + 1).x;
    quadVerts[7] = particles.get(i + size + 1).y;
  }

}
