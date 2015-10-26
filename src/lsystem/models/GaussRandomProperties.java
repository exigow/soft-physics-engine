package lsystem.models;

import java.util.Random;

public class GaussRandomProperties {

  private static ThreadLocal<Random> RANDOM_GENERATOR = new ThreadLocal<Random>() {

    protected synchronized Random initialValue() {
      return new Random();
    }

  };
  public float mean;
  public float variance;

  public float nextValue() {
    return mean + (float) RANDOM_GENERATOR.get().nextGaussian() * variance;
  }

}
