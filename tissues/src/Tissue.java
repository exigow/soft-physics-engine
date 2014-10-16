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
    n = (short)segments;
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
    // updateTriangles(region);
    printVbo(region);
    return region;
  }

  private void printVbo(PolygonRegion region) {
    System.out.print(
      "creating region:\n" +
        "\tvertices: " + Arrays.toString(region.getVertices()) + " {" + region.getVertices().length + "} floats\n" +
        "\ttriangles: " + Arrays.toString(region.getTriangles()) + " {" + region.getTriangles().length + "} shorts\n" +
        "\tcoordinates: " + Arrays.toString(region.getTextureCoords()) + " {" + region.getTextureCoords().length + "} floats\n");
  }

  /*private void updateTriangles(PolygonRegion region) {
    short[] triangles = region.getTriangles();
    for (short i = 0; i < triangles.length / 6; i += 1) {
      System.out.println("create quad: " + i);
    }*/

    /*for (int i = 0; i < particles.size(); i++) {
      int ptr = (i * 2);
      region.getVertices()[ptr] = particles.get(i).x;
      region.getVertices()[ptr + 1] = particles.get(i).y;
    }*/
  //}

  /*static int[] createTriangle(int n){
    int[] ret=new int[2*(n-1)*(n-1)*3];
    int rowAlert=n-1;
    for(int i=0,j=0;i<(n*(n-1));i++,j+=6){
      if(i==rowAlert){
        j-=6;
        rowAlert+=n;
        continue;
      }
      //1 trojkat
      ret[j]=i;
      ret[j+1]=i+1;
      ret[j+2]=i+n;
      //2trojkat
      ret[j+3]=i+1;
      ret[j+4]=i+n;
      ret[j+5]=i+n+1;
    }
    return ret;
  }*/

  public void draw(PolygonSpriteBatch batch) {
    int i = 0;
    for (Particle particle : particles)
      Application.debugFont.draw(batch, "" + i++, particle.x, particle.y);
  }



}
