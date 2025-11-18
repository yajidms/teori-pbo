import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ListCompare {
  public static void main(String[] args) {
    List<String> arr = new ArrayList<>();
    arr.add("A"); arr.add("B"); arr.add("C");
    System.out.println("ArrayList get(1): " + arr.get(1));

    List<String> arr2 = new ArrayList<>();
    arr2.add("X"); arr2.add(0, "HEAD");
    System.out.println("ArrayList (prepend): " + arr2);

    LinkedList<String> link = new LinkedList<>();
    link.add("X"); link.addFirst("HEAD");
    System.out.println("LinkedList (addFirst): " + link);

    arr2.remove(0);
    link.removeFirst();
    System.out.println("ArrayList setelah remove front: " + arr2);
    System.out.println("LinkedList setelah removeFirst: " + link);
  }
}