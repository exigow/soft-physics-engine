public class Hello {

  public native void sayHello(int length);

  public static void main(String args[]) {
    String str = "I am a good boy";
    Hello h = new Hello();
    h.sayHello(str.length());
  }

  static {
    System.loadLibrary("Hello");
  }

}