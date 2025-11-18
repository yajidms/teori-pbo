public class ContohThrow1 {
  public static void main(String[] args) {
    RuntimeException x;
    x = new RuntimeException("ekcepsi RuntimeException");

    System.out.println("sebelum throw");
    throw(x);
  }
}
