import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import softsys.Particle;
import softsys.Vector;
import softsys.World;
import softsys.joints.Joint;
import softsys.joints.SpringJoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tissue {

  private final List<Particle> particles = new ArrayList<Particle>();
  private final List<Joint> joints = new ArrayList<Joint>();
  private final PolygonRegion region;
  private final short n;

  public Tissue(Vector centerPos, Vector size, int segments, float stiffness, TextureRegion textureRegion) {
    n = (short) segments;
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
    region = createVbo(segments, textureRegion);
  }

  public final Tissue flush(World world) {
    world.joints.addAll(joints);
    world.particles.addAll(particles);
    return this;
  }

  private PolygonRegion createVbo(int segments, TextureRegion textureRegion) {
    float[] vertices = new float[segments * segments * 2];
    short[] triangles = new short[2 * (segments - 1) * (segments - 1) * 3];
    PolygonRegion region = new PolygonRegion(textureRegion, vertices, triangles);
    rewriteTriangles(region.getTriangles());
    rewriteCoordinates(region.getTextureCoords());
    printVbo(region);
    return region;
  }

  private void printVbo(PolygonRegion region) {
    System.out.print(
      "creating region:\n" +
      "\tvertices: {" + region.getVertices().length + "} " + Arrays.toString(region.getVertices()) + "\n" +
      "\ttriangles: {" + region.getTriangles().length + "} " + Arrays.toString(region.getTriangles()) + "\n" +
      "\tcoordinates: {" + region.getTextureCoords().length + "} " + Arrays.toString(region.getTextureCoords()) + "\n");
  }

  void rewriteTriangles(short[] ret){
    short rowAlert = (short) (n - 1);
    for (short i = 0, j = 0; i < (n * (n - 1)); i++, j += 6) {
      if (i == rowAlert) {
        j -= 6;
        rowAlert += n;
        continue;
      }
      ret[j] = i;
      ret[j + 1] = (short) (i + 1);
      ret[j + 2] = (short) (i + n);
      ret[j + 3] = (short) (i + 1);
      ret[j + 4] = (short) (i + n);
      ret[j + 5] = (short) (i + n + 1);
    }
  }

  private void rewriteCoordinates(float[] coordinates) {
    float size = 1f / (n - 1);
    for (int i = 0; i < n; i++) {
      float y = i * size;
      for (int j = 0; j < n; j++) {
        float x = j * size;
        coordinates[(i * n + j) * 2] = x;
        coordinates[(i * n + j) * 2 + 1] = y;
      }
    }
  }

  public void draw(PolygonSpriteBatch batch) {
    updateVerticesPositions();
    batch.begin();
    batch.draw(region, 0, 0);
    int i = 0;
    for (Particle particle : particles)
      Application.debugFont.draw(batch, "" + i++, particle.x, particle.y);
    batch.end();
  }

  public void updateVerticesPositions() {
    for (int i = 0; i < particles.size(); i++) {
      Particle particle = particles.get(i);
      region.getVertices()[i * 2] = particle.x;
      region.getVertices()[i * 2 + 1] = particle.y;
    }
  }

}
