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

  public Cloth(Vector2f centerPos, Vector2f size, int segments, float stiffness, TextureRegion textureRegion) {
    Vector2f stride = new Vector2f(size).mul(1f / segments);
    for (int y = 0; y < segments; y++) {
      for (int x = 0; x < segments; x++) {
        float px = centerPos.x + x * stride.x - size.x / 2f + stride.x / 2f,
          py = centerPos.y + y * stride.y - size.y / 2f + stride.y / 2f;
        particles.add(Particle.on(px, py));
        if (x > 0)
          joints.add(SpringJoint.connect(particles.get(y * segments + x), particles.get(y * segments + x - 1), stiffness));
        if (y > 0)
          joints.add(SpringJoint.connect(particles.get(y * segments + x), particles.get((y - 1) * segments + x), stiffness));
      }
    }
    int step = 6;
    for (int i = 0; i <= segments; i += step) {
      Particle part = particles.get(segments * segments - i - 1);
      joints.add(PinJoint.pinToActualPlace(part));
    }
    region = createVbo(segments, textureRegion);
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
    createTextureCoordinates(region.getTextureCoords(), segments);
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
