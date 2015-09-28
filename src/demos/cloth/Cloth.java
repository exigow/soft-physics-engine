package demos.cloth;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import engine.Particle;
import engine.World;
import engine.joints.Joint;
import engine.joints.SpringJoint;
import engine.joints.StaticJoint;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cloth {

  private final List<Particle> particles = new ArrayList<Particle>();
  private final List<Joint> joints = new ArrayList<Joint>();
  private final PolygonRegion region;
  private final short segments;

  public Cloth(Vector2f centerPos, Vector2f size, int segments, float stiffness, TextureRegion textureRegion) {
    this.segments = (short) segments;
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
    int step = 6;
    for (int i = 0; i <= segments; i += step)
      joints.add(new StaticJoint(particles.get(segments * segments - i - 1)));
    region = createVbo(segments, textureRegion);
  }

  public final Cloth flush(World world) {
    Collections.shuffle(joints);
    world.joints.addAll(joints);
    world.particles.addAll(particles);
    return this;
  }

  private PolygonRegion createVbo(int segments, TextureRegion textureRegion) {
    float[] vertices = new float[segments * segments * 2];
    PolygonRegion region = new PolygonRegion(textureRegion, vertices, createTriangles(segments));
    createTextureCoordinates(region.getTextureCoords());
    return region;
  }

  private short[] createTriangles(int segments){
    short[] result = new short[2 * (segments - 1) * (segments - 1) * 3];
    short rowAlert = (short) (this.segments - 1);
    for (short i = 0, j = 0; i < (this.segments * (this.segments - 1)); i++, j += 6) {
      if (i == rowAlert) {
        j -= 6;
        rowAlert += this.segments;
        continue;
      }
      result[j] = i;
      result[j + 1] = (short) (i + 1);
      result[j + 2] = (short) (i + this.segments);
      result[j + 3] = (short) (i + 1);
      result[j + 4] = (short) (i + this.segments);
      result[j + 5] = (short) (i + this.segments + 1);
    }
    return result;
  }

  private void createTextureCoordinates(float[] coordinates) {
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
