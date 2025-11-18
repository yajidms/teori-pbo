public class ContohThrows1 {
  public static void test() throws IllegalArgumentException {
    throw new IllegalArgumentException("kesalahan illegal access");
  }

  public static void main(String[] args) {
    try
    {
      test();
    }
    catch (Exception x)
    {
      System.out.println("eksepsi ditangkap disini");
      System.out.println(x.getMessage());
    }
    System.out.println("statement setelah blok try-catch");
  }
}