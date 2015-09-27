package engine;

// todo wywalic dziedziczenie. mimo że podobne zachowanie, wektor a czasteczka to sa dwie osobne rzeczy!
// todo (czaczteczka powinna zawierac pole pozycja ktore jest wektorem)
public class Particle extends Vector {

  public final Vector prev = new Vector();

  public Particle(float x, float y) {
    super(x, y);
    prev.set(this);
  }

  public Particle(Vector where) {
    this(where.x, where.y);
  }

  public void forcePosition(float x, float y) {
    set(x, y);
    prev.set(x, y);
  }

}