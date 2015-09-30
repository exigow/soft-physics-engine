package demos.rope;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class BezierConcentrator {

  public static List<Vector2f> concentrate(List<Vector2f> positions, int maxSegments) {
    positions.add(positions.get(positions.size() - 1)); // hack, duplicate last reference
    List<Vector2f> result = new ArrayList<>();
    Vector2f prevControl = new Vector2f();
    Vector2f nextControl = new Vector2f();
    Vector2f prev = new Vector2f();
    Vector2f next = new Vector2f();
    for (int i = 0; i < positions.size() - 2; i += 1) {
      Vector2f a = positions.get(i);
      Vector2f b = positions.get(i + 1);
      Vector2f c = positions.get(i + 2);
      prev.set(c).sub(a).normalize().mul(32);
      prevControl.set(a.x + next.x, a.y + next.y);
      nextControl.set(b.x - prev.x, b.y - prev.y);
      next.set(prev);
      for (int f = 0; f < maxSegments; f += 1) {
        float t = (1f / maxSegments) * f;
        Vector2f point = computeBezier(a, prevControl, nextControl, b, t);
        result.add(point);
      }
    }
    return result;
  }

  private static Vector2f computeBezier(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3, float t) {
    float x = (1 - t) * (1 - t) * (1 - t) * p0.x + 3 * (1 - t) * (1 - t) * t * p1.x + 3 * (1 - t) * t * t * p2.x + t * t * t * p3.x;
    float y = (1 - t) * (1 - t) * (1 - t) * p0.y + 3 * (1 - t) * (1 - t) * t * p1.y + 3 * (1 - t) * t * t * p2.y + t * t * t * p3.y;
    return new Vector2f(x, y);
  }

}
