package demos.cloth;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import engine.Particle;
import engine.Simulator;
import engine.joints.Joint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cloth {

  private final List<Particle> particles = new ArrayList<>();
  private final List<Joint> joints = new ArrayList<>();
  private final PolygonRegion region;
  private final short segmentsCount;

  public Cloth(Vector2f centerPos, Vector2f size, int segmentsCount, float stiffness, TextureRegion textureRegion) {
    this.segmentsCount = (short) segmentsCount;
    float xStride = size.x / segmentsCount;
    float yStride = size.y / segmentsCount;
    for (int y = 0; y < segmentsCount; y++) {
      for (int x = 0; x < segmentsCount; x++) {
        float px = centerPos.x + x * xStride - size.x / 2f + xStride / 2f,
          py = centerPos.y + y * yStride - size.y / 2f + yStride / 2f;
        particles.add(Particle.on(px, py));
        if (x > 0)
          joints.add(SpringJoint.connect(particles.get(y * segmentsCount + x), particles.get(y * segmentsCount + x - 1), stiffness));
        if (y > 0)
          joints.add(SpringJoint.connect(particles.get(y * segmentsCount + x), particles.get((y - 1) * segmentsCount + x), stiffness));
      }
    }
    int step = 6;
    for (int i = 0; i <= segmentsCount; i += step) {
      Particle part = particles.get(segmentsCount * segmentsCount - i - 1);
      joints.add(PinJoint.pinToActualPlace(part));
    }
    region = createVbo(segmentsCount, textureRegion);
  }

  public final Cloth flush(Simulator simulator) {
    Collections.shuffle(joints);
    simulator.joints.addAll(joints);
    simulator.particles.addAll(particles);
    return this;
  }

  private PolygonRegion createVbo(int segments, TextureRegion textureRegion) {
    float[] vertices = new float[segments * segments * 2];
    short[] indices = createGridTriangleStripIndices(segments);
    PolygonRegion region = new PolygonRegion(textureRegion, vertices, indices);
    createTextureCoordinates(region.getTextureCoords(), segmentsCount);
    return region;
  }

  private static short[] createGridTriangleStripIndices(int segments){
    short[] result = new short[2 * (segments - 1) * (segments - 1) * 3];
    short cycle = (short) (segments - 1);
    for (short i = 0, j = 0; i < (segments * (segments - 1)); i++, j += 6) {
      if (i == cycle) {
        j -= 6;
        cycle += segments;
        continue;
      }
      result[j] = i;
      result[j + 1] = (short) (i + 1);
      result[j + 2] = (short) (i + segments);
      result[j + 3] = (short) (i + 1);
      result[j + 4] = (short) (i + segments);
      result[j + 5] = (short) (i + segments + 1);
    }
    return result;
  }

  private static void createTextureCoordinates(float[] coordinates, int segments) {
    float size = 1f / (segments - 1);
    for (int i = 0; i < segments; i++)
      for (int j = 0; j < segments; j++) {
        coordinates[(i * segments + j) * 2] = j * size;
        coordinates[(i * segments + j) * 2 + 1] = 1f - i * size;
      }
  }

  public void draw(PolygonSpriteBatch batch) {
    updateVerticesPositions();
    batch.begin();
    batch.draw(region, 0, 0);
    batch.end();
  }

  public void updateVerticesPositions() {
    for (int i = 0; i < particles.size(); i++) {
      Particle particle = particles.get(i);
      region.getVertices()[i * 2] = particle.pos.x;
      region.getVertices()[i * 2 + 1] = particle.pos.y;
    }
  }

}
